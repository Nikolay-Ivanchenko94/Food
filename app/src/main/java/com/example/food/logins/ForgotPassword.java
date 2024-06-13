package com.example.food.logins;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class ForgotPassword extends Fragment {


    private FragmentForgotPasswordBinding binding;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private AuthCredential credential;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);


        binding.btnSubmit.setOnClickListener(v -> {
            String emailPhone = binding.evEmailPhone.getText().toString().trim();

            if (emailPhone.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailPhone).matches()) {
                Toast.makeText(getActivity().getApplicationContext(), "Invalid email format", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailPhone)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("ForgotPassword", "Password reset email sent.");
                                    Toast.makeText(getActivity().getApplicationContext(), "Password reset email sent", Toast.LENGTH_LONG).show();


                                    ResetFragment resetFragment = new ResetFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, resetFragment, "findThisFragment")
                                            .addToBackStack(null).commit();
                                } else {
                                    Log.e("ForgotPassword", "Password reset failed", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Password reset failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    startPhoneNumberVerification(emailPhone);
                                }
                            }
                        });

                
                mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted:" + credential);
                        signInWithPhoneAuthCredential();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }
                };

            }
        });
        return binding.getRoot();
    }

    private void signInWithPhoneAuthCredential() {

    }

    private void startPhoneNumberVerification(String emailPhone) {
        PhoneAuthOptions phoneAuthOptions =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(emailPhone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallback)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
}