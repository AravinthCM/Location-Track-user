package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long ANIMATION_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView busImageView = findViewById(R.id.bus_image_view);
        TextView busTrackyTextView = findViewById(R.id.bus_tracky_text);

        Animation animation = new TranslateAnimation(-1000f, 2000f, 0, 0);
        animation.setDuration(ANIMATION_DURATION);
        busImageView.setAnimation(animation);

        animation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                busTrackyTextView.setVisibility(View.VISIBLE); // Set text view visible
            }
        }, ANIMATION_DURATION - 800); // Adjust delay as needed

        // Start the LoginActivity after the animation completes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();
            }
        }, ANIMATION_DURATION);
    }
}
