package com.example.sapa2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sapa2.MainActivity;
import com.example.sapa2.R;
import com.example.sapa2.adapters.ReportAdapter;
import com.example.sapa2.model.Report;
import com.example.sapa2.utils.NetworkUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BerandaFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private List<Report> reportList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoReports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        // Inisialisasi Views
        recyclerView = rootView.findViewById(R.id.rv_reports);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        tvNoReports = rootView.findViewById(R.id.tv_no_reports_beranda);

        setupRecyclerView();
        setupSwipeRefresh();

        // Memulai proses loading saat fragment pertama kali dibuat
        loadData(true);

        return rootView;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reportList = new ArrayList<>();
        adapter = new ReportAdapter(getContext(), reportList);
        recyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefresh() {
        // Mengatur listener untuk aksi "pull-to-refresh"
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("BerandaFragment", "Refresh dipicu oleh pengguna.");
            // Panggil metode load data, jangan tampilkan progress bar utama
            loadData(false);
        });
    }

    private void loadData(boolean isInitialLoad) {
        // Tampilkan ProgressBar utama hanya jika ini adalah loading awal
        if (isInitialLoad && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showLoading(true);
        } else if (!isInitialLoad) {
            // Tampilkan indikator loading dari SwipeRefreshLayout
            swipeRefreshLayout.setRefreshing(true);
        }

        // Cek koneksi internet
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            fetchReportsFromFirebase();
        } else {
            Toast.makeText(getContext(), "Tidak ada koneksi. Menampilkan data offline.", Toast.LENGTH_LONG).show();
            loadReportsFromLocal();
        }
    }

    private void fetchReportsFromFirebase() {
        final String DATABASE_URL = "https://sapa-9ce9a-default-rtdb.asia-southeast1.firebasedatabase.app/";
        DatabaseReference ref = FirebaseDatabase.getInstance(DATABASE_URL).getReference("laporan");

        // Menggunakan listener sekali ambil agar tidak loop saat refresh
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) return; // Pastikan fragment masih terpasang
                reportList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Report report = dataSnapshot.getValue(Report.class);
                    if (report != null) {
                        reportList.add(report);
                    }
                }
                Collections.reverse(reportList);
                adapter.notifyDataSetChanged();
                updateEmptyState();
                onLoadingFinished(); // Panggil ini setelah selesai
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (!isAdded()) return;
                Log.e("BerandaFragment", "Gagal memuat laporan dari Firebase: ", error.toException());
                Toast.makeText(getContext(), "Gagal memuat data laporan.", Toast.LENGTH_SHORT).show();
                onLoadingFinished(); // Panggil ini juga jika gagal
            }
        });
    }

    private void loadReportsFromLocal() {
        if (getContext() == null) return;
        try {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.no_internet);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<Report>>(){}.getType();
            List<Report> reports = new Gson().fromJson(reader, listType);

            reportList.clear();
            if (reports != null) {
                reportList.addAll(reports);
            }
            adapter.notifyDataSetChanged();
            updateEmptyState();
        } catch (Exception e) {
            Log.e("BerandaFragment", "Error membaca data JSON lokal", e);
        }
        onLoadingFinished(); // Panggil ini setelah selesai
    }

    private void updateEmptyState() {
        if (reportList.isEmpty()) {
            tvNoReports.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoReports.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void onLoadingFinished() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showLoading(false);
        }
    }
}
