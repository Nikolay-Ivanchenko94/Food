package com.example.food.homescreen;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.food.R;
import com.example.food.databinding.FragmentHomeBinding;
import com.example.food.databse.APIService;
import com.example.food.databse.FoodDB;
import com.example.food.databse.MyDataBase;
import com.example.food.foods.Category;
import com.example.food.foods.Foods;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    private static final String CHANNEL_ID = "your_channel_id";
    private FragmentHomeBinding binding;
    private APIService service;
    private okhttp3.Response response;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        /// Notification //
        createNotificationChannel();
        binding.btnNot.setOnClickListener(v -> {
            sendNotification();
        });
        return binding.getRoot();
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification) // Replace with your app's notification icon
                .setContentTitle("Notification Title")
                .setContentText("This is a notification from the fragment.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build()); // 1 is the notification ID
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        /// Retrofit//

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = retrofit.create(APIService.class);

        reuqestFood();


    }

    private void reuqestFood() {
        service.getFood().enqueue(new Callback<Foods>() {
            @Override
            public void onResponse(Call<Foods> call, Response<Foods> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Foods foods = response.body();
                    Category category = foods.getCategory();
                    if (category != null) {
                        MyDataBase myDataBase = Room.databaseBuilder(
                                getActivity().getApplicationContext(),
                                MyDataBase.class, "database-name"
                        ).build();

                        ArrayList<FoodDB> foodDBArrayList = new ArrayList<>();
                        FoodDB foodDB = new FoodDB();
                        foodDB.setStrCategoryDescription(category.getStrCategoryDescription());
                        foodDB.setStrCategoryThumb(category.getStrCategoryThumb()); // Make sure to set this

                        foodDBArrayList.add(foodDB);

                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(() -> {
                            myDataBase.foodDao().insertFoodDBList(foodDBArrayList);
                            List<FoodDB> foodDBS = myDataBase.foodDao().getFoodDBS();
                            getActivity().runOnUiThread(() -> {
                                setDataToViews(foodDB);
                            });
                        });
                    } else {
                        Log.e("HomeFragment", "Category is null in Foods response");
                    }
                } else {
                    Log.e("HomeFragment", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<Foods> call, Throwable t) {
                Log.e("HomeFragment", "Network request failed", t);
            }
        });
    }

    private void setDataToViews(FoodDB foodDB) {
        if (foodDB != null) {
            Glide.with(HomeFragment.this)
                    .load("https:" + foodDB.getStrCategoryThumb())
                    .into(binding.ivDrink);
            binding.tvCookies.setText(foodDB.getStrCategoryDescription());
        } else {
            Log.e("HomeFragment", "FoodDB object is null");
        }
    }
}



