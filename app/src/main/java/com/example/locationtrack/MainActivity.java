package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvSplashText = findViewById(R.id.tvSplashText);

        Animation zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);

        tvSplashText.startAnimation(zoomInAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the home activity
                Intent homeIntent = new Intent(MainActivity.this, LoginUserActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_DURATION);
    }
}
