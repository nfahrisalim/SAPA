# SAPA - Sistem Aduan Pelaporan Warga

![SAPA Banner](https://images.unsplash.com/photo-1593698054438-654b0553a84e?q=80&w=2070&auto=format&fit=crop)

**SAPA** adalah sebuah aplikasi Android modern yang dirancang untuk menjadi jembatan antara warga dan pihak berwenang. Aplikasi ini memungkinkan pengguna untuk dengan mudah melaporkan berbagai kendala di lingkungan sekitar, mulai dari jalan rusak, banjir, hingga masalah fasilitas umum lainnya, langsung dari smartphone mereka. Dengan integrasi backend real-time menggunakan Firebase, setiap laporan dapat dilihat dan ditindaklanjuti secara efisien.

---

## ✨ Fitur Utama

-   👤 **Autentikasi Pengguna**: Sistem login dan registrasi yang aman menggunakan Firebase Authentication.
-   📝 **Pelaporan Canggih**: Pengguna dapat membuat laporan baru dengan menyertakan judul, deskripsi, tingkat bahaya, dan foto sebagai bukti.
-   📍 **Lokasi Akurat**:
    -   **GPS Otomatis**: Mendapatkan lokasi saat ini dengan sekali tekan.
    -   **Pencarian Cerdas**: Mencari dan memilih lokasi spesifik dengan Google Places Autocomplete.
-   🖼️ **Manajemen Profil**:
    -   Pengguna dapat melihat semua laporan yang pernah mereka buat.
    -   Fitur untuk mengunggah dan memperbarui foto profil.
-   ⚡ **Real-time & Interaktif**:
    -   Semua laporan ditampilkan secara real-time di halaman beranda.
    -   Halaman detail untuk setiap laporan, menampilkan nama dan foto profil pelapor.
    -   Pengguna dapat menghapus laporan mereka sendiri.
-   🌐 **Mode Offline**: Aplikasi dapat menampilkan data darurat dari file lokal jika tidak ada koneksi internet.
-   🔄 **UI Modern**:
    -   Loading indicator yang informatif saat berpindah antar halaman.
    -   Fitur "Tarik untuk Refresh" (Pull-to-Refresh) di halaman beranda.

---

## 🛠️ Teknologi yang Digunakan

-   **Bahasa**: Java
-   **Backend**: Firebase
    -   **Realtime Database**: Untuk menyimpan data laporan dan profil pengguna secara real-time.
    -   **Firebase Storage**: Untuk menyimpan semua gambar yang diunggah (foto laporan dan foto profil).
    -   **Firebase Authentication**: Untuk mengelola autentikasi pengguna.
-   **APIs**:
    -   **Google Places API**: Untuk fitur pencarian lokasi (Autocomplete).
    -   **Google Location Services (FusedLocationProviderClient)**: Untuk mendapatkan lokasi GPS pengguna.
-   **Library Pihak Ketiga**:
    -   `com.github.bumptech.glide:glide`: Untuk memuat dan menampilkan gambar secara efisien.
    -   `com.github.dhaval2404:imagepicker`: Untuk fungsionalitas memilih gambar dari kamera atau galeri.
    -   `de.hdodenhof:circleimageview`: Untuk menampilkan gambar profil dalam bentuk lingkaran.
    -   `com.google.code.gson:gson`: Untuk mengubah data JSON menjadi objek Java (digunakan dalam mode offline).

---

## 📁 Struktur Proyek


SAPA2
└── app
└── src
└── main
├── AndroidManifest.xml
├── java
│   └── com/example/sapa2
│       ├── MainActivity.java
│       ├── MyApplication.java
│       ├── adapters
│       │   ├── ReportAdapter.java
│       │   └── UsersReportAdapter.java
│       ├── fragment
│       │   ├── Add_reportFragment.java
│       │   ├── BerandaFragment.java
│       │   ├── LaporanDetailFragment.java
│       │   └── ProfileFragment.java
│       ├── model
│       │   ├── Report.java
│       │   └── User.java
│       ├── UI
│       │   ├── SplashActivity.java
│       │   └── auth
│       │       ├── AuthActivity.java
│       │       ├── LoginFragment.java
│       │       ├── RegisterFragment.java
│       │       └── SignActivity.java
│       └── utils
│           └── NetworkUtils.java
└── res
├── drawable
├── layout
├── menu
├── mipmap
├── raw
│   └── no_internet.json
└── values


---

## 🚀 Panduan Instalasi & Konfigurasi

Untuk menjalankan proyek ini di komputer Anda, ikuti langkah-langkah berikut:

1.  **Clone Repositori**
    ```bash
    git clone [https://github.com/URL-ANDA/SAPA2.git](https://github.com/URL-ANDA/SAPA2.git)
    ```

2.  **Setup Proyek Firebase**
    -   Buat proyek baru di [Firebase Console](https://console.firebase.google.com/).
    -   Tambahkan aplikasi Android ke proyek Anda dengan nama paket `com.example.sapa2`.
    -   Unduh file `google-services.json` dan letakkan di dalam direktori `app/`.

3.  **Konfigurasi Firebase**
    -   **Authentication**: Di Firebase Console, pergi ke bagian Authentication > Sign-in method, dan aktifkan provider **Email/Password**.
    -   **Realtime Database**: Buat instance Realtime Database. Pilih lokasi server yang Anda inginkan (misal: `asia-southeast1`) dan mulai dalam **Test mode**.
    -   **Storage**: Aktifkan Firebase Storage.

4.  **Konfigurasi API Keys**
    -   **Google Places API**:
        -   Di [Google Cloud Console](https://console.cloud.google.com/), pastikan **"Places API"** sudah aktif untuk proyek Anda.
        -   Salin API Key proyek Anda.
        -   Buka `app/src/main/AndroidManifest.xml` dan masukkan API Key tersebut di dalam tag `<meta-data>`:
            ```xml
            <meta-data
                android:name="com.google.android.geo.API_KEY"
                android:value="MASUKKAN_API_KEY_ANDA_DI_SINI"/>
            ```
    -   **SHA-1 Fingerprint**:
        -   Jalankan `./gradlew signingReport` di terminal Android Studio.
        -   Salin sidik jari **SHA-1** untuk `Variant: debug`.
        -   Di Firebase Console, pergi ke **Project settings > Your apps**, dan tambahkan sidik jari tersebut di bagian **SHA certificate fingerprints**.

5.  **Perbarui URL Database**
    -   Pastikan semua panggilan `FirebaseDatabase.getInstance()` di dalam kode (di semua Fragment) sudah menggunakan URL database yang benar sesuai dengan lokasi server yang Anda pilih. Contoh:
        ```java
        FirebaseDatabase.getInstance("[https://nama-proyek-anda.firebaseio.com/](https://nama-proyek-anda.firebaseio.com/)")
        ```

6.  **Build & Run**
    -   Lakukan **Build > Rebuild Project**.
    -   Jalankan aplikasi di emulator atau perangkat fisik.

---

## 📸 Screenshot

| Halaman Login | Beranda | Halaman Tambah |
| :---: | :---: | :---: |
| _[Sisipkan gambar halaman login di sini]_ | _[Sisipkan gambar halaman beranda di sini]_ | _[Sisipkan gambar halaman tambah laporan di sini]_ |
| **Halaman Detail** | **Profil Pengguna** | **Pencarian Lokasi** |
| _[Sisipkan gambar halaman detail laporan di sini]_ | _[Sisipkan gambar halaman profil pengguna di sini]_ | _[Sisipkan gambar pencarian lokasi di sini]_ |

---

## 🤝 Kontribusi

Kontribusi dalam bentuk apapun sangat kami hargai! Silakan buat *fork* dari repositori ini dan ajukan *pull request* untuk perbaikan atau penambahan fitur.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---