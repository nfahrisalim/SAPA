package com.example.sapa2.UI.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sapa2.R;

// Import Firebase tidak lagi diperlukan di sini karena sudah dihandle di MyApplication.java
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.appcheck.FirebaseAppCheck;
// import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;

public class AuthActivity extends AppCompatActivity {

    private Button btnMasuk, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // --- Kode Inisialisasi Firebase dan App Check telah dihapus dari sini ---
        // --- untuk menghindari duplikasi karena sudah ada di MyApplication.java ---

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
