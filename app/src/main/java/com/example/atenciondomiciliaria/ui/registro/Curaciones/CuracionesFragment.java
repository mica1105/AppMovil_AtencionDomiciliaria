package com.example.atenciondomiciliaria.ui.registro.Curaciones;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCuracionesBinding;
import com.example.atenciondomiciliaria.modelo.Curaciones;

public class CuracionesFragment extends Fragment {

    private CuracionesViewModel mViewModel;
    private FragmentCuracionesBinding binding;
    private ArrayAdapter<String> adapter, adapter1;
    private String[] clases= {"Clase I", "Clase II", "Clase III", "Clase IV"};
    private String[] dolor= {"Sin dolor", "Leve", "Moderado", "Severo"};
    private int IdVisita, IdCuraciones;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding= FragmentCuracionesBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(CuracionesViewModel.class);

        adapter= new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_item, clases);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerClase.setAdapter(adapter);

        adapter1= new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_item, dolor);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDolor.setAdapter(adapter1);

        mViewModel.getIdVisita().observe(getViewLifecycleOwner(), integer -> {
            IdVisita= integer;
            IdCuraciones= 0;
            binding.etPaciente.setVisibility(View.GONE);
            binding.tv1.setVisibility(View.GONE);
        });

        mViewModel.getRegistro().observe(getViewLifecycleOwner(), curaciones -> {
            IdCuraciones= curaciones.getId();
            binding.etPaciente.setText(String.format("%s %s", curaciones.getVisita().getPaciente().getNombre(),
                    curaciones.getVisita().getPaciente().getApellido()));
            binding.etTipo.setText(curaciones.getTipo());
            binding.etUbicacion.setText(curaciones.getUbicacion());
            binding.etTamanio.setText(String.valueOf(curaciones.getTamanio()));
            binding.etBordes.setText(curaciones.getBordes());
            binding.spinnerClase.setSelection(adapter.getPosition(curaciones.getClase()));
            binding.spinnerDolor.setSelection(adapter1.getPosition(curaciones.getDolor()));
            binding.etInfeccion.setText(curaciones.getSignosInfeccion());
            binding.etObservaciones.setText(curaciones.getObservaciones());
            IdVisita= curaciones.getVisitaId();
        });

        mViewModel.getMensaje().observe(getViewLifecycleOwner(), string ->{
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_registros);
        });

        mViewModel.getActivos().observe(getViewLifecycleOwner(), bool -> {
            binding.etPaciente.setEnabled(bool);
            binding.etTipo.setEnabled(bool);
            binding.etUbicacion.setEnabled(bool);
            binding.etTamanio.setEnabled(bool);
            binding.etBordes.setEnabled(bool);
            binding.spinnerClase.setEnabled(bool);
            binding.spinnerDolor.setEnabled(bool);
            binding.etInfeccion.setEnabled(bool);
            binding.etObservaciones.setEnabled(bool);
        });

        mViewModel.getBoton().observe(getViewLifecycleOwner(), string -> binding.btnGuardar.setText(string));

        Bundle bundle= getArguments();
        mViewModel.cargarDatos(bundle);

        binding.btnGuardar.setOnClickListener(v -> {
            Curaciones curaciones= new Curaciones();
            curaciones.setId(IdCuraciones);
            curaciones.setTipo(binding.etTipo.getText().toString());
            curaciones.setUbicacion(binding.etUbicacion.getText().toString());
            curaciones.setTamanio(Float.parseFloat(binding.etTamanio.getText().toString()));
            curaciones.setClase(binding.spinnerClase.getSelectedItem().toString());
            curaciones.setBordes(binding.etBordes.getText().toString());
            curaciones.setDolor(binding.spinnerDolor.getSelectedItem().toString());
            curaciones.setSignosInfeccion(binding.etInfeccion.getText().toString());
            curaciones.setObservaciones(binding.etObservaciones.getText().toString());
            curaciones.setVisitaId(IdVisita);
            String textoBoton= binding.btnGuardar.getText().toString();
            mViewModel.accionBoton(textoBoton, curaciones);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}