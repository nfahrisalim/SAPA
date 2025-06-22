package com.example.sapa2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapa2.MainActivity;
import com.example.sapa2.R;
import com.example.sapa2.UI.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Dapatkan instance Firebase Auth
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            // Dapatkan pengguna yang sedang login
            FirebaseUser currentUser = mAuth.getCurrentUser();

            // Tentukan tujuan berikutnya
            Intent intent;
            if (currentUser != null) {
                // Jika pengguna sudah login (sesi aktif), langsung ke MainActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                // Jika tidak ada pengguna yang login, arahkan ke halaman autentikasi
                intent = new Intent(SplashActivity.this, AuthActivity.class);
            }

            // Tambahkan flags untuk membersihkan stack activity sebelumnya
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Tutup SplashActivity agar tidak bisa diakses dengan tombol back

        }, SPLASH_DURATION);
    }
}
