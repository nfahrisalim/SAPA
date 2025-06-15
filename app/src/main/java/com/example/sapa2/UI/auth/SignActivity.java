package com.example.sapa2.UI.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sapa2.R;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // Setup tombol kembali
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke AuthActivity
                Intent intent = new Intent(SignActivity.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Ambil data dari intent
        String action = getIntent().getStringExtra("action");

        Fragment selectedFragment;
        if ("register".equalsIgnoreCase(action)) {
            selectedFragment = new RegisterFragment();
        } else {
            selectedFragment = new LoginFragment();
        }

        // Tampilkan fragment ke container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, selectedFragment);
        transaction.commit();
    }
}
