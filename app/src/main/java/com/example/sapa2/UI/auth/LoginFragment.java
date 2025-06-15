package com.example.sapa2.UI.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sapa2.R;

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView toRegister;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        toRegister = view.findViewById(R.id.to_register);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // Validasi sederhana
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Username dan Password wajib diisi", Toast.LENGTH_SHORT).show();
            } else {
                // Proses login di sini
                Toast.makeText(getContext(), "Login sebagai " + username, Toast.LENGTH_SHORT).show();
            }
        });

        // Navigasi ke RegisterFragment
        toRegister.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RegisterFragment());
            transaction.addToBackStack(null); // agar bisa tekan back
            transaction.commit();
        });

        return view;
    }
}
