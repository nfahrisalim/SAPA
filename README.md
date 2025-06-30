# 🌐 SAPA - Citizen Report & Complaint System

![SAPA Banner](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Banner.png)

**SAPA** is a modern Android application that bridges citizens and authorities. It allows users to report issues such as potholes, floods, and public facility problems directly from their smartphones, with real-time backend integration via Firebase.

---

## ✨ Key Features

* 👤 **User Authentication**
  Secure login and registration via Firebase Authentication.

* 📝 **Advanced Reporting**
  Create reports with a title, description, danger level, and photo.

* 📍 **Accurate Location**

  * **Auto GPS**: Automatically fetch current location with one tap.
  * **Smart Search**: Manually search locations via Google Places Autocomplete.

* 🖼️ **Profile Management**

  * View submitted reports.
  * Upload and update profile picture.

* ⚡ **Real-time & Interactive**

  * Reports are displayed in real-time on the homepage.
  * Report details show reporter's name and photo.
  * Users can delete their own reports.

* 🌐 **Offline Mode**

  * Emergency data is loaded from a local file when there's no internet.

* 🔄 **Modern UI**

  * Loading indicators during navigation.
  * *Pull-to-refresh* support on the homepage.

---

## 🛠️ Technologies Used

* **Language**: Java
* **Backend**: Firebase

  * Realtime Database
  * Firebase Storage
  * Firebase Authentication
* **APIs**:

  * Google Places API
  * Google Location Services
* **Third-Party Libraries**:

  * `Glide`: Image display
  * `ImagePicker`: Pick images from camera/gallery
  * `CircleImageView`: Circular profile images
  * `Gson`: JSON ↔ Java object conversion

---

## 📁 Project Structure

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
│   │   ├── Add_reportFragment.java
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
│   └── no_internet.json
└── values/
```

---

## 🚀 Installation & Setup Guide

1. **Clone the Repository**

   ```bash
   git clone https://github.com/USERNAME/SAPA2.git
   ```

2. **Set Up Firebase**

   * Create a project at [Firebase Console](https://console.firebase.google.com/)
   * Add Android app (`com.example.sapa2`)
   * Download `google-services.json` and place it in the `app/` directory

3. **Enable Firebase Services**

   * **Authentication**: Enable Email/Password method
   * **Realtime Database**: Set to *Test Mode*
   * **Storage**: Enable Firebase Storage

4. **Configure Google APIs**

   * Enable **Places API** in [Google Cloud Console](https://console.cloud.google.com/)
   * Add your API Key to `AndroidManifest.xml`:

     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="YOUR_API_KEY_HERE"/>
     ```

5. **Add SHA-1 Fingerprint**

   * Run:

     ```bash
     ./gradlew signingReport
     ```
   * Add the SHA-1 key in Firebase Console > Project Settings

6. **Set the Database URL**

   ```java
   FirebaseDatabase.getInstance("https://your-project-name.firebaseio.com/")
   ```

7. **Build & Run**

   * Go to **Build > Rebuild Project**
   * Run the app on an emulator or a physical device

---

## 📸 Screenshots

| Login Page | Homepage | Add Report |
|:----------:|:--------:|:-----------:|
| ![Login](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Login.jpeg) | ![Homepage](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Beranda.jpeg) | ![Add](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Tambah.jpeg) |

| Report Details | User Profile | Location Search |
|:--------------:|:------------:|:----------------:|
| ![Details](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Detail.jpeg) | ![Profile](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Profil.jpeg) | ![Location](https://github.com/nfahrisalim/Assets/blob/main/SAPA/Lokasi.jpeg) |
---

## 🤝 Contributions

Contributions are highly appreciated!
Follow these steps:

1. Fork the repository

2. Create a new branch

   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. Commit your changes

   ```bash
   git commit -m "Add AmazingFeature"
   ```

4. Push to your branch

   ```bash
   git push origin feature/AmazingFeature
   ```

5. Open a Pull Request

---
