package com.example.food.logins;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.databinding.FragmentSignInBinding;
import com.example.food.homescreen.HomeFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.fido.u2f.api.common.SignRequestParams;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInOptions googleSignInOptions;

    private GoogleSignInClient mGoogleSignInClient;
    private SignRequestParams signRequest;
    private FirebaseAuth firebaseAuth;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher<Intent> signInLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);


        binding.btnBack.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, signUpFragment, "findThisFragment")
                    .addToBackStack(null).commit();
        });


        binding.btnSignUpOr.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, signUpFragment, "findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnForgot.setOnClickListener(v -> {
            ForgotPassword forgotPassword = new ForgotPassword();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, forgotPassword, "ThisFragment")
                    .addToBackStack(null).commit();
        });


        /// Sign In ////

        binding.btnSignIn.setOnClickListener(v -> {
            String email = binding.evEmail.getText().toString().trim();
            String password = binding.evLock.getText().toString().trim();


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity().getApplicationContext(), "Invalid email format", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    if (firebaseUser != null) {
                                        Log.d("SignUpFragment", "Sign in successful");

                                        HashMap<String, String> userInfo = new HashMap<>();
                                        userInfo.put("email", email);

                                        FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(firebaseUser.getUid())
                                                .setValue(userInfo);

                                        HomeFragment homeFragment = new HomeFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.container, homeFragment, "Fragment")
                                                .addToBackStack(null).commit();
                                    } else {
                                        Log.e("SignInFragment", "Firebase user is null.");
                                    }
                                } else {
                                    Log.e("SignInFragment", "Sign in failed", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        ///Google ///

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.id_client))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);



        firebaseAuth = FirebaseAuth.getInstance();

        signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
        ,result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Task<GoogleSignInAccount> accountTask =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                            if (account !=null) {
                                firebaseAuthWithGoogle(account.getIdToken());
                            }
                        } catch (ApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });





        binding.btnLoginGoogle.setOnClickListener(v -> {
            if (mGoogleSignInClient != null) {
                Intent signInIntent =mGoogleSignInClient.getSignInIntent();
                signInLauncher.launch(signInIntent);
            } else {
                Log.e(TAG, "GoogleSignInClient is not initialized");
            }
        });



        /// Facebook ///

        callbackManager = CallbackManager.Factory.create();
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            callbackManager.onActivityResult(result.getResultCode(), result.getResultCode(), data);
                        }
                    }
                }
        );
        binding.btnLogin.setReadPermissions("email", "public_profile");
        binding.btnLogin.setFragment(this);
        binding.btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

            private void handleFacebookAccessToken(AccessToken token) {
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                firebaseAuth
                        .signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                } else {

                                }
                            }
                        });
            }
        });


        return  binding.getRoot();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(),task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
             } else {

                    }
                });
    }


}