package com.example.food.logins;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.databinding.FragmentResetBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ResetFragment extends Fragment {

private FragmentResetBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResetBinding.inflate(inflater,container,false);


        binding.btnBack.setOnClickListener(v -> {
            ForgotPassword forgotPassword = new ForgotPassword();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,forgotPassword,"ThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnSkip.setOnClickListener(v -> {
            SignInFragment signInFragment = new SignInFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,signInFragment,"findThisFragment")
                    .addToBackStack(null).commit();
        });
        binding.btnConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.evConfirm.setTransformationMethod(null);
                } else {
                    binding.evConfirm.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        binding.btnNewPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.evNewPSw.setTransformationMethod(null);
                } else {
                    binding.evNewPSw.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        binding.btnVerify.setOnClickListener(v -> {
            String evConfirm = binding.evConfirm.getText().toString();
            String evNew = binding.evNewPSw.getText().toString();
            if (evNew.isEmpty() || evConfirm.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext()," Fields cannot be empty",Toast.LENGTH_LONG);
            } else {
                HashMap<String,String > userInfo = new HashMap<>();
                userInfo.put("newPsw",binding.evNewPSw.getText().toString());
                userInfo.put("confirmPsw",binding.evConfirm.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(userInfo);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Заміна Пароля");
                Reset2Fragment resetFragment2 = new Reset2Fragment();
                getActivity().getSupportFragmentManager().
                        beginTransaction().replace(R.id.container,resetFragment2,
                                "findThisFragment").addToBackStack(null)
                        .commit();
            }
        });



        return  binding.getRoot();
    }
}