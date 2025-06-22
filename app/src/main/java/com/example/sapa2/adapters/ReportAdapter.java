package com.example.sapa2.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sapa2.R;
import com.example.sapa2.fragment.LaporanDetailFragment;
import com.example.sapa2.model.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<Report> reportList;

    // --- PERBAIKAN: Hanya satu konstruktor yang bersih ---
    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reportList.get(position);

        holder.tvTitle.setText(report.getJudul());
        holder.tvSubtitle.setText("Tingkat Bahaya: " + report.getTingkatBahaya());
        holder.tvLocationDate.setText(report.getLokasi());

        // --- PERBAIKAN: Menampilkan status dari data, bukan statis ---
        holder.tvStatus.setText(report.getStatus());

        if (report.getImageUrl() != null && !report.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(report.getImageUrl())
                    .placeholder(R.drawable.bg_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(holder.ivReportImage);
        } else {
            holder.ivReportImage.setImageResource(R.drawable.ic_image_placeholder);
        }

        // --- PERBAIKAN UTAMA: Menambahkan OnClickListener dengan data lengkap ---
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            // --- PENTING: Mengirim semua data yang dibutuhkan ---
            bundle.putString("userId", report.getUserId());
            bundle.putString("judul", report.getJudul());
            bundle.putString("deskripsi", report.getDeskripsi());
            bundle.putString("lokasi", report.getLokasi());
            bundle.putString("tingkatBahaya", report.getTingkatBahaya());
            bundle.putString("imageUrl", report.getImageUrl());
            bundle.putString("status", report.getStatus());

            LaporanDetailFragment detailFragment = new LaporanDetailFragment();
            detailFragment.setArguments(bundle);

            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment) // Pastikan ID ini benar
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle, tvLocationDate, tvStatus;
        ImageView ivReportImage;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_report_title);
            tvSubtitle = itemView.findViewById(R.id.tv_report_subtitle);
            tvLocationDate = itemView.findViewById(R.id.tv_location_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivReportImage = itemView.findViewById(R.id.iv_report_image);
        }
    }
}
