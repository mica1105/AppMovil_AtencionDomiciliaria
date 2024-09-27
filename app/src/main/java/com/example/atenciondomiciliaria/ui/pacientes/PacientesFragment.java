package com.example.atenciondomiciliaria.ui.pacientes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.atenciondomiciliaria.databinding.FragmentPacientesBinding;


public class PacientesFragment extends Fragment {

    private FragmentPacientesBinding binding;
    private PacientesViewModel pacientesViewModel;
    private PacienteAdapter adapter;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPacientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= root.getContext();
        inicializar(root);

        return root;
    }

    private void inicializar(View root) {
        pacientesViewModel =
                new ViewModelProvider(this).get(PacientesViewModel.class);
        pacientesViewModel.getPacientes().observe(getViewLifecycleOwner(), pacientes -> {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false);
            binding.rvPaciente.setLayoutManager(gridLayoutManager);
            adapter= new PacienteAdapter(context,pacientes);
            binding.rvPaciente.setAdapter(adapter);
            });
        pacientesViewModel.cargarPacientes();
        binding.btnBuscar.setOnClickListener(v -> {
            String nombre = binding.etBuscarPaciente.getText().toString();
            pacientesViewModel.buscarPaciente(nombre);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}