package com.example.sapa2; // Pastikan nama paket ini sesuai dengan proyek Anda

import android.app.Application;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.appcheck.FirebaseAppCheck;
//import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Inisialisasi Firebase App
        FirebaseApp.initializeApp(this);

        // Dapatkan instance FirebaseAppCheck
////        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
////
////        // Instal provider DEBUG untuk lingkungan pengembangan
////        // Ini sekarang akan berjalan paling pertama saat aplikasi dimulai.
////        firebaseAppCheck.installAppCheckProviderFactory(
////                DebugAppCheckProviderFactory.getInstance()
//        );
    }
}
