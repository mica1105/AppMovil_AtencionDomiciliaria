package com.example.atenciondomiciliaria.ui.perfil;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentPerfilBinding;
import com.example.atenciondomiciliaria.modelo.Enfermero;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private Enfermero usuario;
    private PerfilViewModel perfilViewModel;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private Uri uriImagen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
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
                    if(enfermero.getAvatar().startsWith("http://")) {
                        Glide.with(getContext())
                                .load(enfermero.getAvatar())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.ivFotoPerfil);
                    }
                    usuario = enfermero;
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
                if(aBoolean) {
                    binding.btEditarImagen.setVisibility(View.VISIBLE);
                } else {
                    binding.btEditarImagen.setVisibility(View.GONE);
                }
            }
        });
        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditar.setText(s);
            }
        });
        perfilViewModel.cargarPerfil();
        pickMedia= registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),uri -> {
            if(uri != null) {
                binding.ivFotoPerfil.setImageURI(uri);
                uriImagen = uri;
                //perfilViewModel.actualizarImagen(usuario, uriImagen);
            }
        });
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{
            if(isGranted){
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            } else {
                Toast.makeText(getContext(), "Se necesita permiso para acceder a la galería", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setDni(Integer.parseInt(binding.etDni.getText().toString()));
                usuario.setApellido(binding.etApellido.getText().toString());
                usuario.setNombre(binding.etNombre.getText().toString());
                usuario.setTelefono(binding.etTelefono.getText().toString());
                usuario.setDomicilio(binding.etDomicilio.getText().toString());
                String boton= binding.btEditar.getText().toString();
                perfilViewModel.accionBoton(boton, usuario, uriImagen);
            }
        });

        binding.btEditarImagen.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission
                    .READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
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