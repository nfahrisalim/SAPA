package com.example.sapa2.adapters;

import android.content.Context;
import android.os.Bundle;
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
import com.example.sapa2.fragment.ProfileFragment;
import com.example.sapa2.model.Report;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class UsersReportAdapter extends RecyclerView.Adapter<UsersReportAdapter.ViewHolder> {

    private Context context;
    private List<Report> reportList;
    // --- TAMBAHAN: Variabel untuk menyimpan referensi ke ProfileFragment ---
    private ProfileFragment fragment;

    // --- PERBAIKAN: Konstruktor diubah agar menerima ProfileFragment ---
    public UsersReportAdapter(Context context, List<Report> reportList, ProfileFragment fragment) {
        this.context = context;
        this.reportList = reportList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gunakan layout item yang sesuai. Pastikan layout ini ada dan memiliki semua ID yang diperlukan.
        View view = LayoutInflater.from(context).inflate(R.layout.users_report_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reportList.get(position);

        holder.tvTitle.setText(report.getJudul());
        holder.tvSubtitle.setText("Tingkat Bahaya : " + report.getTingkatBahaya());

        if (report.getImageUrl() != null && !report.getImageUrl().isEmpty()) {
            Glide.with(context).load(report.getImageUrl()).into(holder.ivReportImage);
        } else {
            holder.ivReportImage.setImageResource(R.drawable.ic_image_placeholder);
        }

        // --- Logika untuk Tombol Hapus ---
        String currentUserId = FirebaseAuth.getInstance().getUid();
        // Tampilkan tombol hapus HANYA jika laporan ini milik pengguna yang sedang login
        if (currentUserId != null && currentUserId.equals(report.getUserId())) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(v -> {
                // Panggil metode deleteReport di ProfileFragment
                if (fragment != null) {
                    fragment.deleteReport(report);
                }
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }

        // Listener untuk membuka halaman detail
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("reportId", report.getId());
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
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;
        ImageView ivReportImage, btnDelete; // Tambahkan btnDelete

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_report_title);
            tvSubtitle = itemView.findViewById(R.id.tv_report_subtitle);
            ivReportImage = itemView.findViewById(R.id.iv_report_image);
            // Inisialisasi tombol hapus, pastikan ID ini ada di users_report_item.xml
            btnDelete = itemView.findViewById(R.id.btn_delete_report);
        }
    }
}
