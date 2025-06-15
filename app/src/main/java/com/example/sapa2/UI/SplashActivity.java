package com.example.sapa2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sapa2.UI.auth.AuthActivity;
import com.example.sapa2.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Pindah ke AuthActivity setelah delay
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, AuthActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}
