package com.example.food.homescreen;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private static final String CHANNEL_ID = "your_channel_id";
    private FragmentHomeBinding binding;

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
        binding.btnFood.setOnClickListener(v -> {
            FoodFragment foodFragment  = new FoodFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,foodFragment,"ThisFragment")
                    .addToBackStack(null).commit();
        });
        /// Retrofit//


    }
}



