package com.example.food.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.databinding.FragmentOnboarding3Binding;


public class Onboarding3Fragment extends Fragment {


    private FragmentOnboarding3Binding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboarding3Binding.inflate(inflater,container,false);

        binding.btnNext.setOnClickListener(v -> {
            OnboardingScreenFragment onboardingScreenFragment = new OnboardingScreenFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboardingScreenFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });

        binding.btnCircle3.setOnClickListener(v -> {
            OnboardingScreenFragment onboardingScreenFragment = new OnboardingScreenFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboardingScreenFragment,"findThisFragment")
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