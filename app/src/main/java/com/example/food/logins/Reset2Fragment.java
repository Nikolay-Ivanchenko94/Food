package com.example.food.logins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.food.homescreen.HomeFragment;
import com.example.food.R;
import com.example.food.databinding.FragmentReset2Binding;


public class Reset2Fragment extends Fragment {

   private FragmentReset2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentReset2Binding.inflate(inflater,container,false);


        binding.btnBack.setOnClickListener(v -> {
            ResetFragment resetFragment = new ResetFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,resetFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });

        binding.btnSkip.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,homeFragment,"find")
                    .addToBackStack(null).commit();
        });
        binding.btnHome.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,homeFragment,"find")
                    .addToBackStack(null).commit();
        } );

        return  binding.getRoot();
    }
}