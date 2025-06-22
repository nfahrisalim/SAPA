package com.example.sapa2.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sapa2.R;
import com.example.sapa2.UI.auth.AuthActivity;
import com.example.sapa2.adapters.UsersReportAdapter;
import com.example.sapa2.model.Report;
import com.example.sapa2.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference userRef, reportsRef;
    private FirebaseUser currentUser;

    private TextView tvProfileName, tvProfileLocation, tvNoReports;
    private RecyclerView rvReports;
    private UsersReportAdapter adapter;
    private List<Report> reportList;
    private ImageView ivProfilePicture;
    private Button btnLogout;

    private final String DATABASE_URL = "https://sapa-9ce9a-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            goToAuthActivity();
            return rootView;
        }

        userRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users").child(currentUser.getUid());
        reportsRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference("laporan");

        initViews(rootView);
        setupRecyclerView();

        loadUserProfile();
        loadUserReports();
        setupLogout();

        return rootView;
    }

    private void initViews(View rootView) {
        tvProfileName = rootView.findViewById(R.id.tv_profile_name);
        tvProfileLocation = rootView.findViewById(R.id.tv_profile_location);
        ivProfilePicture = rootView.findViewById(R.id.iv_profile_picture);
        rvReports = rootView.findViewById(R.id.rv_reports);
        btnLogout = rootView.findViewById(R.id.btn_logout);
        tvNoReports = rootView.findViewById(R.id.tv_no_reports); // Pastikan ID ini ada di XML
    }

    private void setupRecyclerView() {
        rvReports.setLayoutManager(new LinearLayoutManager(getContext()));
        reportList = new ArrayList<>();
        adapter = new UsersReportAdapter(getContext(), reportList, this);
        rvReports.setAdapter(adapter);
    }

    private void loadUserProfile() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isAdded() && snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        tvProfileName.setText(user.getNama_lengkap());
                        tvProfileLocation.setText(user.getLokasi());
                        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty() && getContext() != null) {
                            Glide.with(getContext()).load(user.getProfileImageUrl()).placeholder(R.drawable.ic_person).into(ivProfilePicture);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "Gagal memuat profil: " + error.getMessage());
            }
        });
    }

    private void loadUserReports() {
        Query userReportsQuery = reportsRef.orderByChild("userId").equalTo(currentUser.getUid());
        userReportsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) return;

                // --- LOG DEBUGGING DITAMBAHKAN DI SINI ---
                Log.d("ProfileReports", "onDataChange dipanggil. Jumlah data ditemukan: " + snapshot.getChildrenCount());

                reportList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Report report = dataSnapshot.getValue(Report.class);
                    if (report != null) {
                        reportList.add(report);
                        Log.d("ProfileReports", "Menambahkan laporan: " + report.getJudul());
                    }
                }

                Log.d("ProfileReports", "Ukuran akhir reportList: " + reportList.size());

                Collections.reverse(reportList);

                if (reportList.isEmpty()) {
                    tvNoReports.setVisibility(View.VISIBLE);
                    rvReports.setVisibility(View.GONE);
                } else {
                    tvNoReports.setVisibility(View.GONE);
                    rvReports.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileReports", "Query dibatalkan: " + error.getMessage());
            }
        });
    }

    public void deleteReport(Report report) {
        if (report == null || report.getId() == null) {
            Toast.makeText(getContext(), "Error: Laporan tidak valid.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Hapus Laporan")
                .setMessage("Apakah Anda yakin ingin menghapus laporan '" + report.getJudul() + "'?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    reportsRef.child(report.getId()).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                if (report.getImageUrl() != null && !report.getImageUrl().isEmpty()) {
                                    StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(report.getImageUrl());
                                    imageRef.delete();
                                }
                                Toast.makeText(getContext(), "Laporan berhasil dihapus.", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Toast.makeText(getContext(), "Gagal menghapus laporan.", Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void setupLogout() {
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            goToAuthActivity();
        });
    }

    private void goToAuthActivity() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
