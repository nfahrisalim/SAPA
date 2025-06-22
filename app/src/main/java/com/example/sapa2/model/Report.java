// Simpan file ini di: com/example/sapa2/model/Report.java

package com.example.sapa2.model;

public class Report {
    // Properti untuk data laporan
    private String id;
    private String userId;
    private String judul;
    private String deskripsi;
    private String lokasi;
    private String tingkatBahaya;
    private String imageUrl;
    private String status;
    private long timestamp;

    // PENTING: Konstruktor kosong ini WAJIB ada untuk Firebase
    public Report() {
    }

    // Konstruktor lengkap yang akan kita gunakan
    public Report(String id, String userId, String judul, String deskripsi, String lokasi, String tingkatBahaya, String imageUrl, String status, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.tingkatBahaya = tingkatBahaya;
        this.imageUrl = imageUrl;
        this.status = status;
        this.timestamp = timestamp;
    }

    // GETTERS (WAJIB ada untuk Firebase membaca data)
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getJudul() { return judul; }
    public String getDeskripsi() { return deskripsi; }
    public String getLokasi() { return lokasi; }
    public String getTingkatBahaya() { return tingkatBahaya; }
    public String getImageUrl() { return imageUrl; }
    public String getStatus() { return status; }
    public long getTimestamp() { return timestamp; }

    // SETTERS (Baik untuk dimiliki)
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public void setTingkatBahaya(String tingkatBahaya) { this.tingkatBahaya = tingkatBahaya; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
