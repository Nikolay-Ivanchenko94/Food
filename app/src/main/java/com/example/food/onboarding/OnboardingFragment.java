package com.example.food.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.databinding.FragmentOnboarding2Binding;
import com.example.food.databinding.FragmentOnboarding3Binding;
import com.example.food.databinding.FragmentOnboardingBinding;


public class OnboardingFragment extends Fragment {

    private FragmentOnboardingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboardingBinding.inflate(inflater,container,false);

        binding.btnNext.setOnClickListener(v -> {
            Onboarding2Fragment onboarding2Fragment = new Onboarding2Fragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboarding2Fragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnCircle.setOnClickListener(v -> {
            Onboarding2Fragment onboarding2Fragment = new Onboarding2Fragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboarding2Fragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnSkip.setOnClickListener(v -> {
            OnboardingScreenFragment onboardingScreenFragment = new OnboardingScreenFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboardingScreenFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });


        return binding.getRoot();
    }
}