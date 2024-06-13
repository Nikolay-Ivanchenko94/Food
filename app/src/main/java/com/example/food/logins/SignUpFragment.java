package com.example.food.logins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.databinding.FragmentSignUpBinding;
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


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private static final int RG_SIGN_UP = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    private CallbackManager callbackManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private SharedPreferences sharedPreferences;

    private FirebaseAuth firebaseAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        binding.btnForgot.setOnClickListener(v -> {
            ForgotPassword forgotPassword = new ForgotPassword();
            getActivity().getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,forgotPassword,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnSignInOr.setOnClickListener(v -> {
            SignInFragment signInFragment = new SignInFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.container,signInFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            String email = binding.evEmail.getText().toString().trim();
            String password = binding.evLock.getText().toString().trim();
            String name = binding.evName.getText().toString().trim();
            String phone = binding.evCall.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity().getApplicationContext(), "Invalid email format", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    if (firebaseUser != null) {
                                        Log.d("SignUpFragment", "Sign up successful");
                                        HashMap<String, String> userInfo = new HashMap<>();
                                        userInfo.put("name", name);
                                        userInfo.put("email", email);
                                        userInfo.put("phone", phone);
                                        FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(firebaseUser.getUid())
                                                .setValue(userInfo);
                                        SignInFragment signInFragment = new SignInFragment();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction().replace(R.id.container, signInFragment, "findThisFragment")
                                                .addToBackStack(null)
                                                .commit();
                                    } else {
                                        Log.e("SignUpFragment", "Firebase user is null.");
                                    }
                                } else {
                                    Log.e("SignUpFragment", "Sign up failed", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        //Google//
          GoogleSignInOptions signInOptions = new GoogleSignInOptions.
                 Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.id_client))
                  .requestEmail().build();


        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(),signInOptions);

        firebaseAuth = FirebaseAuth.getInstance();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
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
        binding.btnGoogle.setOnClickListener(v -> {
            if (mGoogleSignInClient != null) {
                Intent signInIntent =mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            } else {
                Log.e(TAG, "GoogleSignInClient is not initialized");
            }
        });
////Facebook ///

        callbackManager = CallbackManager.Factory.create();
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            callbackManager.onActivityResult(result.getResultCode(), result.getResultCode(), data);
                        }
                    }
                }
        );
        binding.btnLogin.setReadPermissions("email", "public_profile");
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
        return binding.getRoot();
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                } else {

                }
            }
        });
    }
}

