package com.example.sapa2.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sapa2.R;
import com.example.sapa2.model.Report;
import com.github.dhaval2404.imagepicker.ImagePicker;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Add_reportFragment extends Fragment {

    private static final int REQUEST_MEDIA_PERMISSION = 100;
    private static final int REQUEST_LOCATION_PERMISSION = 101;
    // --- TAMBAHAN: Request code untuk Places Autocomplete ---
    private static final int AUTOCOMPLETE_REQUEST_CODE = 102;

    private EditText etJudul, etDeskripsi, etLokasi;
    private RadioGroup rgTingkatBahaya;
    private Button btnLapor, btnAmbilFoto, btnUnggahFoto;
    private ImageView ivPreview, btnLocation;
    private Uri imageUri;
    private DatabaseReference databaseRef;
    private FusedLocationProviderClient fusedLocationClient;
    private ProgressDialog progressDialog;
    private View rootView;

    public Add_reportFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_report, container, false);

        // --- TAMBAHAN: Inisialisasi Google Places SDK ---
        if (!Places.isInitialized()) {
            String apiKey = "";
            try {
                // Mengambil API Key yang sudah Anda masukkan di AndroidManifest
                apiKey = requireActivity().getPackageManager().getApplicationInfo(requireActivity().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
                Places.initialize(requireContext(), apiKey);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PlacesApi", "API Key tidak ditemukan di Manifest", e);
                Toast.makeText(getContext(), "Places API Key tidak ditemukan.", Toast.LENGTH_LONG).show();
            }
        }

        initViews();
        initFirebase();
        initListeners();
        return rootView;
    }

    private void initViews() {
        etJudul = rootView.findViewById(R.id.et_judul_laporan);
        etDeskripsi = rootView.findViewById(R.id.et_deskripsi_laporan);
        etLokasi = rootView.findViewById(R.id.et_lokasi);
        rgTingkatBahaya = rootView.findViewById(R.id.rg_tingkat_bahaya);
        btnLapor = rootView.findViewById(R.id.btn_lapor_sekarang);
        ivPreview = rootView.findViewById(R.id.iv_preview_gambar);
        btnLocation = rootView.findViewById(R.id.btn_location);
        btnAmbilFoto = rootView.findViewById(R.id.btn_ambil_foto);
        btnUnggahFoto = rootView.findViewById(R.id.btn_unggah_foto);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mengirim laporan...");
        progressDialog.setCancelable(false);
    }
    private void initFirebase() {
        final String DATABASE_URL = "https://sapa-9ce9a-default-rtdb.asia-southeast1.firebasedatabase.app/";
        databaseRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference("laporan");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    private void initListeners() {
        btnLapor.setOnClickListener(v -> attemptSaveReport());
        btnAmbilFoto.setOnClickListener(v -> checkPermissionAndPickImage(true));
        btnUnggahFoto.setOnClickListener(v -> checkPermissionAndPickImage(false));

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

    // --- PERBAIKAN: onActivityResult sekarang menangani Image Picker DAN Autocomplete ---
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
            return; // Hentikan eksekusi setelah menangani hasil autocomplete
        }

        // Kode ini sama dengan ImagePicker.REQUEST_CODE
        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                ivPreview.setImageURI(imageUri);
                ivPreview.setVisibility(View.VISIBLE);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        }
    }

    // --- Sisa kode tidak perlu diubah dan sudah benar ---
    private void checkLocationPermissionAndGet() { if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION); } else { getCurrentLocation(); } }
    private void getCurrentLocation() { if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; } fusedLocationClient.getLastLocation().addOnSuccessListener(location -> { if (location != null) { convertCoordinatesToAddress(location); } else { Toast.makeText(getContext(), "Gagal mendapatkan lokasi.", Toast.LENGTH_SHORT).show(); } }); }
    private void convertCoordinatesToAddress(Location location) { Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault()); try { List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); if (addresses != null && !addresses.isEmpty()) { etLokasi.setText(addresses.get(0).getAddressLine(0)); } else { etLokasi.setText(location.getLatitude() + ", " + location.getLongitude()); } } catch (IOException e) { etLokasi.setText(location.getLatitude() + ", " + location.getLongitude()); } }
    private void checkPermissionAndPickImage(boolean isCamera) { String permission = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ? Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE; if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) { requestPermissions(new String[]{permission}, REQUEST_MEDIA_PERMISSION); } else { startImagePicker(isCamera); } }
    private void startImagePicker(boolean isCamera) { ImagePicker.Builder picker = ImagePicker.with(this); if (isCamera) { picker.cameraOnly(); } else { picker.galleryOnly(); } picker.start(); }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { super.onRequestPermissionsResult(requestCode, permissions, grantResults); if (requestCode == REQUEST_LOCATION_PERMISSION) { if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { getCurrentLocation(); } else { Toast.makeText(getContext(), "Izin lokasi ditolak.", Toast.LENGTH_SHORT).show(); } } else if (requestCode == REQUEST_MEDIA_PERMISSION) { if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { Toast.makeText(getContext(), "Izin diberikan, silakan pilih gambar lagi.", Toast.LENGTH_SHORT).show(); } else { Toast.makeText(getContext(), "Izin media dibutuhkan.", Toast.LENGTH_SHORT).show(); } } }
    private void attemptSaveReport() { FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); if (currentUser == null) { Toast.makeText(getContext(), "Anda harus login.", Toast.LENGTH_LONG).show(); return; } String judul = etJudul.getText().toString().trim(); String deskripsi = etDeskripsi.getText().toString().trim(); String lokasi = etLokasi.getText().toString().trim(); int selectedRadioId = rgTingkatBahaya.getCheckedRadioButtonId(); if (TextUtils.isEmpty(judul) || TextUtils.isEmpty(deskripsi) || TextUtils.isEmpty(lokasi) || selectedRadioId == -1 || imageUri == null) { Toast.makeText(getContext(), "Mohon lengkapi semua data.", Toast.LENGTH_SHORT).show(); return; } progressDialog.show(); RadioButton selectedRadio = rootView.findViewById(selectedRadioId); String tingkatBahaya = selectedRadio.getText().toString(); StorageReference fileRef = FirebaseStorage.getInstance().getReference("laporan_foto/" + UUID.randomUUID().toString()); fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> { saveReportToDatabase(currentUser.getUid(), judul, deskripsi, lokasi, tingkatBahaya, uri.toString()); })).addOnFailureListener(e -> { progressDialog.dismiss(); Toast.makeText(getContext(), "Gagal upload gambar.", Toast.LENGTH_LONG).show(); }); }
    private void saveReportToDatabase(String userId, String judul, String deskripsi, String lokasi, String tingkatBahaya, String imageUrl) { String reportId = databaseRef.push().getKey(); if (reportId == null) { progressDialog.dismiss(); return; } HashMap<String, Object> reportMap = new HashMap<>(); reportMap.put("id", reportId); reportMap.put("userId", userId); reportMap.put("judul", judul); reportMap.put("deskripsi", deskripsi); reportMap.put("lokasi", lokasi); reportMap.put("tingkatBahaya", tingkatBahaya); reportMap.put("imageUrl", imageUrl); reportMap.put("status", "Baru"); reportMap.put("timestamp", System.currentTimeMillis()); databaseRef.child(reportId).setValue(reportMap, (error, ref) -> { progressDialog.dismiss(); if (error == null) { Toast.makeText(getContext(), "Laporan berhasil dikirim!", Toast.LENGTH_SHORT).show(); resetForm(); } else { Toast.makeText(getContext(), "DATABASE ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show(); } }); }
    private void resetForm() { etJudul.setText(""); etDeskripsi.setText(""); etLokasi.setText(""); rgTingkatBahaya.clearCheck(); imageUri = null; ivPreview.setImageURI(null); ivPreview.setVisibility(View.GONE); }
}
