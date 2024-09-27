package com.example.atenciondomiciliaria;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atenciondomiciliaria.databinding.ActivityRegisterBinding;
import com.example.atenciondomiciliaria.modelo.Enfermero;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inicializar();
    }

    private void inicializar() {
        viewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegisterViewModel.class);
        viewModel.getError().observe(this, s -> {
            binding.tvErrores.setText(String.format("* %s", s));
            binding.tvErrores.setVisibility(View.VISIBLE);
        });

        binding.btEditar.setOnClickListener(v -> {
            Enfermero enfermero= new Enfermero();
            enfermero.setDni(Integer.parseInt(binding.etDni.getText().toString()));
            enfermero.setApellido(binding.etApellido.getText().toString());
            enfermero.setNombre(binding.etNombre.getText().toString());
            enfermero.setDomicilio(binding.etDomicilio.getText().toString());
            enfermero.setEmail(binding.etEmail.getText().toString());
            enfermero.setPassword(binding.etPassword.getText().toString());
            viewModel.crearUsuario(enfermero);
        });

        binding.btAtras.setOnClickListener(v -> finish());
    }
}