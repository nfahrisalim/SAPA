package com.example.sapa2.models;

public class Report {
    private String id;
    private String title;
    private String subtitle;
    private String location;
    private String description;
    private String imageUrl;
    private String status;
    private String reporterName;
    private String reporterAvatarUrl;

    public Report(String id, String title, String subtitle, String location,
                  String description, String imageUrl, String status,
                  String reporterName, String reporterAvatarUrl) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.location = location;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.reporterName = reporterName;
        this.reporterAvatarUrl = reporterAvatarUrl;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getStatus() { return status; }
    public String getReporterName() { return reporterName; }
    public String getReporterAvatarUrl() { return reporterAvatarUrl; }
}