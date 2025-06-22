package com.example.sapa2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.sapa2.R;
import com.example.sapa2.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LaporanDetailFragment extends Fragment {

    // UI Views
    private TextView tvTitle, tvSubtitle, tvLocation, tvStatus, tvDescription, tvReporterName;
    private ImageView ivReportImage, ivReporterAvatar;
    private ImageView btnBack;

    private final String DATABASE_URL = "https://sapa-9ce9a-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public LaporanDetailFragment() {
        // Diperlukan konstruktor kosong
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laporan_detail, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getContext(), "Data laporan tidak dapat dimuat.", Toast.LENGTH_SHORT).show();
            if (isAdded()) requireActivity().getSupportFragmentManager().popBackStack();
            return view;
        }

        populateViews(bundle);

        btnBack.setOnClickListener(v -> {
            if (isAdded()) requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void initViews(View view) {
        tvTitle = view.findViewById(R.id.tv_report_title);
        tvSubtitle = view.findViewById(R.id.tv_report_subtitle);
        tvLocation = view.findViewById(R.id.tv_location);
        tvStatus = view.findViewById(R.id.tv_status);
        tvDescription = view.findViewById(R.id.tv_description);
        ivReportImage = view.findViewById(R.id.iv_report_image);
        btnBack = view.findViewById(R.id.btn_back);
        tvReporterName = view.findViewById(R.id.tv_reporter_name);
        ivReporterAvatar = view.findViewById(R.id.iv_reporter_avatar);
    }

    private void populateViews(Bundle bundle) {
        tvTitle.setText(bundle.getString("judul", "Judul Tidak Ditemukan"));
        tvLocation.setText(bundle.getString("lokasi", "Lokasi Tidak Diketahui"));
        tvStatus.setText(bundle.getString("status", "Status Tidak Diketahui"));
        tvDescription.setText(bundle.getString("deskripsi", "Tidak ada deskripsi."));

        String tingkatBahaya = bundle.getString("tingkatBahaya", "Tidak diketahui");
        tvSubtitle.setText("Tingkat Bahaya: " + tingkatBahaya);

        String imageUrl = bundle.getString("imageUrl");
        if (getContext() != null && imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.bg_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(ivReportImage);
        } else {
            ivReportImage.setImageResource(R.drawable.ic_image_placeholder);
        }

        String userId = bundle.getString("userId");
        if (userId != null && !userId.isEmpty()) {
            loadReporterInfo(userId);
        } else {
            tvReporterName.setText("Pelapor Anonim");
        }
    }

    private void loadReporterInfo(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users").child(userId);

        tvReporterName.setText("Memuat nama...");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) return;

                User user = snapshot.getValue(User.class);
                String finalReporterName = "Pelapor Tidak Ditemukan";

                if (user != null) {
                    if (user.getNama_lengkap() != null && !user.getNama_lengkap().isEmpty()) {
                        finalReporterName = user.getNama_lengkap();
                    }

                    // --- PERBAIKAN FINAL DI SINI ---
                    // Memuat gambar avatar pelapor jika URL-nya ada
                    String avatarUrl = user.getProfileImageUrl();
                    if (getContext() != null && avatarUrl != null && !avatarUrl.isEmpty()) {
                        Glide.with(getContext())
                                .load(avatarUrl)
                                .placeholder(R.drawable.ic_person) // Placeholder default
                                .error(R.drawable.ic_person) // Tampilkan placeholder jika gagal
                                .into(ivReporterAvatar);
                    } else {
                        // Jika tidak ada URL, tampilkan gambar default
                        ivReporterAvatar.setImageResource(R.drawable.ic_person);
                    }
                }

                // Set nama setelah semua data diproses
                tvReporterName.setText(finalReporterName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(!isAdded()) return;
                Log.e("LaporanDetailFragment", "Gagal memuat info pelapor: " + error.getMessage());
                tvReporterName.setText("Gagal Memuat");
            }
        });
    }
}
