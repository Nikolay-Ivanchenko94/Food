
package com.example.food;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.food.databinding.ActivityMainBinding;
import com.example.food.homescreen.CartFragment;
import com.example.food.homescreen.CategoryFragment;
import com.example.food.homescreen.HomeFragment;
import com.example.food.homescreen.ShopFragment;
import com.facebook.FacebookSdk;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView btnNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        BottomNavigationView btnNav = binding.btnNav;



        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment(),true);
        }



      binding.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              int itemId = item.getItemId();
              if (itemId == R.id.homeFragment) {
                  replaceFragment(new HomeFragment(),false);
              } else if (itemId == R.id.categoryFragment) {
                  replaceFragment(new CategoryFragment(),false);
              } else if (itemId == R.id.shopFragment) {
                  replaceFragment(new ShopFragment(),false);
              } else if (itemId == R.id.cartFragment) {
                  replaceFragment(new CartFragment(),false);
              } else if (itemId == R.id.shopFragment) {
                  replaceFragment(new ShopFragment(),false);
              }

              return false;
          }
      });
    }

    private void replaceFragment(Fragment fragment,boolean isAppUnitiliazed) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentManager.executePendingTransactions();


     ////   if (isAppUnitiliazed) {
     ///       fragmentTransaction.add(R.id.frameLayout,fragment);
   ///     } if (isAppUnitiliazed) {
   ///         fragmentTransaction.replace(R.id.frameLayout,fragment);
    ////    }

        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
}
