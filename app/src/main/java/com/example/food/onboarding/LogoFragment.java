package com.example.food.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.databinding.FragmentLogoBinding;

public class LogoFragment extends Fragment {

    private FragmentLogoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLogoBinding.inflate(inflater,container,false);

        binding.btnNext.setOnClickListener(v -> {
            OnboardingFragment onboardingFragment = new OnboardingFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,onboardingFragment,"findThisFragment")
                    .addToBackStack(null).commit();


        });


        return  binding.getRoot();
    }
}