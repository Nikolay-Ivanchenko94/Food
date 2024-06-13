package com.example.food.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.databinding.FragmentOnboarding2Binding;

public class Onboarding2Fragment extends Fragment {

    private FragmentOnboarding2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboarding2Binding.inflate(inflater,container,false);


        binding.btnNext.setOnClickListener(v -> {
            Onboarding3Fragment onboarding3Fragment = new Onboarding3Fragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboarding3Fragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnCircle2.setOnClickListener(v -> {
            Onboarding3Fragment onboarding3Fragment = new Onboarding3Fragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboarding3Fragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnSkip.setOnClickListener(v -> {
            OnboardingScreenFragment onboardingScreenFragment = new OnboardingScreenFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboardingScreenFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });


        return  binding.getRoot();

    }
}