# 🌐 SAPA - Sistem Aduan Pelaporan Warga

![SAPA Banner](https://images.unsplash.com/photo-1593698054438-654b0553a84e?q=80&w=2070&auto=format&fit=crop)

**SAPA** adalah aplikasi Android modern yang menjadi jembatan antara warga dan pihak berwenang. Aplikasi ini memungkinkan pengguna melaporkan masalah seperti jalan rusak, banjir, dan fasilitas umum langsung dari smartphone, dengan integrasi backend real-time menggunakan Firebase.

---

## ✨ Fitur Utama

- 👤 **Autentikasi Pengguna**  
  Login dan registrasi aman dengan Firebase Authentication.

- 📝 **Pelaporan Canggih**  
  Buat laporan dengan judul, deskripsi, tingkat bahaya, dan foto.

- 📍 **Lokasi Akurat**
  - **GPS Otomatis**: Lokasi otomatis dengan sekali tekan.
  - **Pencarian Cerdas**: Lokasi manual via Google Places Autocomplete.

- 🖼️ **Manajemen Profil**
  - Lihat laporan yang telah dibuat.
  - Unggah dan ubah foto profil.

- ⚡ **Real-time & Interaktif**
  - Laporan ditampilkan real-time di beranda.
  - Detail laporan menampilkan nama dan foto pelapor.
  - Pengguna bisa menghapus laporan mereka sendiri.

- 🌐 **Mode Offline**
  - Data darurat dari file lokal saat tidak ada internet.

- 🔄 **UI Modern**
  - Loading indicator saat navigasi.
  - Fitur *pull-to-refresh* di beranda.

---

## 🛠️ Teknologi yang Digunakan

- **Bahasa**: Java  
- **Backend**: Firebase
  - Realtime Database
  - Firebase Storage
  - Firebase Authentication
- **APIs**:
  - Google Places API
  - Google Location Services
- **Library Pihak Ketiga**:
  - `Glide`: Menampilkan gambar
  - `ImagePicker`: Pilih gambar dari kamera/galeri
  - `CircleImageView`: Gambar profil bulat
  - `Gson`: Konversi JSON ↔ Java object

---

## 📁 Struktur Proyek

```

SAPA2/
└── app/
└── src/
└── main/
├── AndroidManifest.xml
├── java/com/example/sapa2/
│   ├── MainActivity.java
│   ├── MyApplication.java
│   ├── adapters/
│   │   ├── ReportAdapter.java
│   │   └── UsersReportAdapter.java
│   ├── fragment/
│   │   ├── Add\_reportFragment.java
│   │   ├── BerandaFragment.java
│   │   ├── LaporanDetailFragment.java
│   │   └── ProfileFragment.java
│   ├── model/
│   │   ├── Report.java
│   │   └── User.java
│   ├── UI/
│   │   ├── SplashActivity.java
│   │   └── auth/
│   │       ├── AuthActivity.java
│   │       ├── LoginFragment.java
│   │       ├── RegisterFragment.java
│   │       └── SignActivity.java
│   └── utils/
│       └── NetworkUtils.java
└── res/
├── drawable/
├── layout/
├── menu/
├── mipmap/
├── raw/
│   └── no\_internet.json
└── values/

````

---

## 🚀 Panduan Instalasi & Konfigurasi

1. **Clone Repositori**
    ```bash
    git clone https://github.com/USERNAME/SAPA2.git
    ```

2. **Setup Firebase**
    - Buat proyek di [Firebase Console](https://console.firebase.google.com/)
    - Tambahkan app Android (`com.example.sapa2`)
    - Unduh `google-services.json` dan letakkan di direktori `app/`

3. **Aktifkan Layanan Firebase**
    - **Authentication**: Aktifkan metode Email/Password
    - **Realtime Database**: Gunakan *Test Mode*
    - **Storage**: Aktifkan Storage

4. **Konfigurasi API Google**
    - Aktifkan **Places API** di [Google Cloud Console](https://console.cloud.google.com/)
    - Tambahkan API Key di `AndroidManifest.xml`
      ```xml
      <meta-data
          android:name="com.google.android.geo.API_KEY"
          android:value="MASUKKAN_API_KEY_ANDA_DI_SINI"/>
      ```

5. **Tambahkan SHA-1**
    - Jalankan:
      ```bash
      ./gradlew signingReport
      ```
    - Tambahkan SHA-1 ke Firebase Console > Project Settings

6. **Setel URL Database**
    ```java
    FirebaseDatabase.getInstance("https://nama-proyek-anda.firebaseio.com/")
    ```

7. **Build & Jalankan**
    - Klik **Build > Rebuild Project**
    - Jalankan di emulator atau perangkat fisik

---

## 📸 Screenshot

| Halaman Login | Beranda | Tambah Laporan |
|:-------------:|:--------:|:--------------:|
| ![Login](URL-GAMBAR-LOGIN) | ![Beranda](URL-GAMBAR-BERANDA) | ![Tambah](URL-GAMBAR-TAMBAH) |

| Detail Laporan | Profil Pengguna | Pencarian Lokasi |
|:--------------:|:---------------:|:----------------:|
| ![Detail](URL-GAMBAR-DETAIL) | ![Profil](URL-GAMBAR-PROFIL) | ![Lokasi](URL-GAMBAR-LOKASI) |

> **Note**: Gantilah `URL-GAMBAR-*` dengan tautan gambar yang sesuai.

---

## 🤝 Kontribusi

Kontribusi sangat dihargai!  
Ikuti langkah berikut:

1. Fork repositori  
2. Buat branch baru  
   ```bash
   git checkout -b feature/AmazingFeature
````

3. Commit perubahan

   ```bash
   git commit -m "Add AmazingFeature"
   ```
4. Push ke branch

   ```bash
   git push origin feature/AmazingFeature
   ```
5. Buat Pull Request

---
