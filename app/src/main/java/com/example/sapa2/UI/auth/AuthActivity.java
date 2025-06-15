package com.example.sapa2.UI.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sapa2.R;

public class AuthActivity extends AppCompatActivity {

    private Button btnMasuk, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        // Atur padding untuk edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi tombol
        btnMasuk = findViewById(R.id.btn_masuk);
        btnDaftar = findViewById(R.id.btn_daftar);

        // Klik tombol MASUK
        btnMasuk.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignActivity.class);
            intent.putExtra("action", "login");
            startActivity(intent);
        });

        // Klik tombol DAFTAR
        btnDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignActivity.class);
            intent.putExtra("action", "register");
            startActivity(intent);
        });
    }
}
