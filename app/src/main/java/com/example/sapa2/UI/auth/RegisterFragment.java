package com.example.sapa2.UI.auth;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sapa2.MainActivity;
import com.example.sapa2.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RegisterFragment extends Fragment {

    private EditText etUsername, etPassword, etNamaLengkap, etLokasi;
    private Button btnRegister;
    private ImageView btnLocation;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private FusedLocationProviderClient fusedLocationClient;
    private ProgressDialog progressDialog;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    // --- TAMBAHAN: Request code untuk Places Autocomplete ---
    private static final int AUTOCOMPLETE_REQUEST_CODE = 102;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // --- TAMBAHAN: Inisialisasi Google Places SDK ---
        if (!Places.isInitialized() && getContext() != null) {
            String apiKey = "";
            try {
                apiKey = requireActivity().getPackageManager().getApplicationInfo(requireActivity().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
                Places.initialize(requireContext(), apiKey);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PlacesApi", "API Key tidak ditemukan di Manifest", e);
            }
        }

        initViews(view);
        initFirebase();
        initListeners();

        return view;
    }

    private void initViews(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        etNamaLengkap = view.findViewById(R.id.nama_lengkap);
        etLokasi = view.findViewById(R.id.et_lokasi);
        btnRegister = view.findViewById(R.id.btn_register);
        btnLocation = view.findViewById(R.id.btn_location);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mendaftarkan...");
        progressDialog.setCancelable(false);
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        final String DATABASE_URL = "https://sapa-9ce9a-default-rtdb.asia-southeast1.firebasedatabase.app/";
        usersRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    private void initListeners() {
        btnRegister.setOnClickListener(v -> registerUser());

        // Listener untuk tombol ikon lokasi (mendapatkan lokasi saat ini)
        btnLocation.setOnClickListener(v -> checkLocationPermissionAndGet());

        // --- TAMBAHAN: Listener untuk kolom teks lokasi (membuka pencarian) ---
        etLokasi.setOnClickListener(v -> startPlaceAutocomplete());
        etLokasi.setFocusable(false);
        etLokasi.setFocusableInTouchMode(false);
    }

    // --- TAMBAHAN: Metode untuk memulai Google Places Autocomplete ---
    private void startPlaceAutocomplete() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("ID") // Prioritaskan hasil dari Indonesia
                .build(requireContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                etLokasi.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                if (status != null) {
                    Toast.makeText(getContext(), "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String namaLengkap = etNamaLengkap.getText().toString().trim();
        String lokasi = etLokasi.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(namaLengkap) || TextUtils.isEmpty(lokasi)) {
            Toast.makeText(getContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter.");
            etPassword.requestFocus();
            return;
        }

        progressDialog.show();
        btnRegister.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(username + "@sapa.id", password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserProfileInBackground();
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    } else {
                        progressDialog.dismiss();
                        btnRegister.setEnabled(true);
                        Toast.makeText(getContext(), "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserProfileInBackground() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) return;
        String uid = firebaseUser.getUid();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("uid", uid);
        userMap.put("username", etUsername.getText().toString().trim());
        userMap.put("nama_lengkap", etNamaLengkap.getText().toString().trim());
        userMap.put("lokasi", etLokasi.getText().toString().trim());
        userMap.put("role", "user");
        usersRef.child(uid).setValue(userMap).addOnFailureListener(e -> Log.e("RegisterFragment", "Gagal menyimpan profil pengguna", e));
    }

    private void checkLocationPermissionAndGet() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            ambilLokasiOtomatis();
        }
    }

    private void ambilLokasiOtomatis() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        etLokasi.setText(addresses.get(0).getAddressLine(0));
                    } else { etLokasi.setText(location.getLatitude() + ", " + location.getLongitude()); }
                } catch (IOException e) { etLokasi.setText(location.getLatitude() + ", " + location.getLongitude()); }
            } else { Toast.makeText(getContext(), "Lokasi tidak tersedia, pastikan GPS aktif.", Toast.LENGTH_SHORT).show(); }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { ambilLokasiOtomatis(); }
            else { Toast.makeText(getContext(), "Izin lokasi ditolak", Toast.LENGTH_SHORT).show(); }
        }
    }
}
