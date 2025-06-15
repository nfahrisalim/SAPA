package com.example.sapa2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sapa2.R;
import com.example.sapa2.models.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<Report> reportList;

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

        holder.tvTitle.setText(report.getTitle());
        holder.tvSubtitle.setText(report.getSubtitle());
        holder.tvLocationDate.setText(report.getLocation());
        holder.tvStatus.setText(report.getStatus());

        // Set image and avatar (placeholder logic, replace with actual image loading library like Glide or Picasso)
        holder.ivReportImage.setImageResource(R.drawable.ic_launcher_foreground); // Replace with actual image logic
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