package com.example.sapa2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sapa2.R;
import com.example.sapa2.adapters.ReportAdapter;
import com.example.sapa2.utils.SampleDataProvider;

public class BerandaFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private ImageView btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        recyclerView = rootView.findViewById(R.id.rv_reports);
        btnBack = rootView.findViewById(R.id.btn_back);

        setupRecyclerView();
        setupClickListeners();

        return rootView;
    }

    private void setupRecyclerView() {
        // Set up layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Create and set adapter
        adapter = new ReportAdapter(getContext(), SampleDataProvider.getSampleReports());
        recyclerView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            // Handle back button click, e.g., go back to previous fragment or activity
            requireActivity().onBackPressed();
        });
    }
}