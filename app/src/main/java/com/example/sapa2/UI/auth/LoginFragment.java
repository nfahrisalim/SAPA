package com.example.sapa2.UI.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // <-- PERBAIKAN: Ganti ke Button biasa
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sapa2.MainActivity;
import com.example.sapa2.R;
// Import MaterialButton tidak lagi diperlukan
// import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText etUsername, etPassword;
    private Button btnLogin; // <-- PERBAIKAN: Ubah tipe variabel ke Button

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);

        // Sekarang aman karena tipe Button cocok dengan hampir semua tombol
        btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> loginUser());

        return view;
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username wajib diisi");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password wajib diisi");
            etPassword.requestFocus();
            return;
        }

        String emailForAuth = username + "@sapa.id";

        btnLogin.setEnabled(false);

        mAuth.signInWithEmailAndPassword(emailForAuth, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Login gagal: Username atau password salah.", Toast.LENGTH_LONG).show();
                        btnLogin.setEnabled(true);
                    }
                });
    }
}
