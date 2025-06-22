package com.example.sapa2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout; // Import FrameLayout
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sapa2.fragment.Add_reportFragment;
import com.example.sapa2.fragment.BerandaFragment;
import com.example.sapa2.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;
    private FrameLayout fragmentContainer; // Tambahkan referensi ke container

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        fragmentContainer = findViewById(R.id.fragment_container); // Inisialisasi container

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, v.getPaddingBottom());
            return insets;
        });

        setupBottomNavigation();

        if (savedInstanceState == null) {
            // Memuat fragment awal tanpa menambahkan ke back stack
            loadFragment(new BerandaFragment(), false);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Mencegah memuat ulang fragment yang sama
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            int itemId = item.getItemId();

            if (itemId == R.id.nav_list && !(currentFragment instanceof BerandaFragment)) {
                loadFragment(new BerandaFragment(), true);
                return true;
            } else if (itemId == R.id.nav_add && !(currentFragment instanceof Add_reportFragment)) {
                loadFragment(new Add_reportFragment(), true);
                return true;
            } else if (itemId == R.id.nav_profile && !(currentFragment instanceof ProfileFragment)) {
                loadFragment(new ProfileFragment(), true);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        // Logika loading sekarang dipindahkan ke fragment masing-masing
        // untuk kontrol yang lebih baik saat data dimuat.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
        );

        transaction.replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    // --- TAMBAHAN: Metode publik untuk mengontrol loading dari fragment ---
    public void showLoading(boolean isLoading) {
        if (progressBar != null && fragmentContainer != null) {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            // Sembunyikan konten saat loading agar tidak terlihat "melompat"
            fragmentContainer.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
