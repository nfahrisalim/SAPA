// Simpan file ini di: com/example/sapa2/model/User.java

package com.example.sapa2.model;

public class User {
    // Nama field harus sama persis dengan kunci di Firebase Database
    public String uid;
    public String username;
    public String nama_lengkap;
    public String lokasi;
    public String role;
    public String profileImageUrl; // <-- FIELD BARU DITAMBAHKAN

    // Konstruktor kosong ini WAJIB ada untuk Firebase
    public User() {}

    // GETTERS (WAJIB ada untuk Firebase membaca data)
    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getRole() {
        return role;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // SETTERS
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
