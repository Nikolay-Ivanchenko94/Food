package com.example.food.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.databinding.FragmentOnboardingScreenBinding;
import com.example.food.logins.SignInFragment;
import com.example.food.logins.SignUpFragment;


public class OnboardingScreenFragment extends Fragment {

  private FragmentOnboardingScreenBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboardingScreenBinding.inflate(inflater,container,false);



        binding.btnSignUp.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            signUpFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            SignInFragment signInFragment = new SignInFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            signInFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });

        return binding.getRoot();

    }
}