package com.example.atenciondomiciliaria.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentPerfilBinding;
import com.example.atenciondomiciliaria.modelo.Enfermero;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private Enfermero usuario;
    private PerfilViewModel perfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        perfilViewModel.getUsuario().observe(getViewLifecycleOwner(), new Observer<Enfermero>() {
            @Override
            public void onChanged(Enfermero enfermero) {
                try {
                    binding.etCodigo.setText(enfermero.getId()+"");
                    binding.etDni.setText(enfermero.getDni() + "");
                    binding.etApellido.setText(enfermero.getApellido());
                    binding.etNombre.setText(enfermero.getNombre());
                    binding.etEmail.setText(enfermero.getEmail());
                    binding.etPassword.setText(enfermero.getPassword());
                    binding.etDomicilio.setText(enfermero.getDomicilio());
                    binding.etTelefono.setText(enfermero.getTelefono());
                    String url="http://192.168.1.15:5000";
                    Glide.with(getContext())
                            .load(url+enfermero.getAvatar())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.ivFotoPerfil);

                    usuario= enfermero;
                } catch (Exception e){
                    Log.d("salida error", "Error "+e);
                }
            }
        });
        perfilViewModel.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etDni.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etNombre.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.etDomicilio.setEnabled(aBoolean);
            }
        });
        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditar.setText(s);
            }
        });
        perfilViewModel.cargarPerfil();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setDni(Integer.parseInt(binding.etDni.getText().toString()));
                usuario.setApellido(binding.etApellido.getText().toString());
                usuario.setNombre(binding.etNombre.getText().toString());
                usuario.setTelefono(binding.etTelefono.getText().toString());
                usuario.setDomicilio(binding.etDomicilio.getText().toString());
                String boton= binding.btEditar.getText().toString();
                perfilViewModel.accionBoton(boton, usuario);
            }
        });
        binding.btEditarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.cambiarClaveFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}