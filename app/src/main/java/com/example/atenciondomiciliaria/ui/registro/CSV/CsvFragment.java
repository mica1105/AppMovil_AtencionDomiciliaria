package com.example.atenciondomiciliaria.ui.registro.CSV;

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
import android.widget.Toast;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCsvBinding;
import com.example.atenciondomiciliaria.modelo.Csv;

public class CsvFragment extends Fragment {

    private CsvViewModel mViewModel;
    private FragmentCsvBinding binding;
    private int IdVisita, IdCsv;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCsvBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(CsvViewModel.class);

        mViewModel.getIdVisita().observe(getViewLifecycleOwner(), integer -> {
            IdVisita= integer;
            IdCsv= 0;
            binding.etPaciente.setVisibility(View.GONE);
            binding.tv1.setVisibility(View.GONE);
        });

        mViewModel.getMensaje().observe(getViewLifecycleOwner(), string ->{

            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_registros);
        });

        mViewModel.getRegistro().observe(getViewLifecycleOwner(), register -> {
            IdCsv= register.getId();
            binding.etPaciente.setText(String.format("%s %s", register.getVisita().getPaciente().getNombre(),
                    register.getVisita().getPaciente().getApellido()));
            binding.etTA.setText(register.getTa());
            binding.etFC.setText( String.valueOf(register.getFc()));
            binding.etSo2.setText(String.valueOf(register.getSo2()));
            binding.etTemp.setText(String.valueOf(register.getTemp()));
            binding.etGlucemia.setText(String.valueOf(register.getHgt()));
            binding.etObservaciones.setText(register.getObservaciones());
        });

        mViewModel.getActivos().observe(getViewLifecycleOwner(), actives -> {
            binding.etPaciente.setEnabled(actives);
            binding.etTA.setEnabled(actives);
            binding.etFC.setEnabled(actives);
            binding.etSo2.setEnabled(actives);
            binding.etTemp.setEnabled(actives);
            binding.etGlucemia.setEnabled(actives);
            binding.etObservaciones.setEnabled(actives);
        });

        mViewModel.getBoton().observe(getViewLifecycleOwner(), string -> binding.btnGuardar.setText(string));

        Bundle bundle= getArguments();
        mViewModel.cargarDatos(bundle);

        binding.btnGuardar.setOnClickListener(v -> {
            Csv csv= new Csv();
            csv.setId(IdCsv);
            csv.setTa(binding.etTA.getText().toString());
            csv.setFc(Integer.parseInt(binding.etFC.getText().toString()));
            csv.setSo2(Integer.parseInt(binding.etSo2.getText().toString()));
            csv.setTemp(Float.parseFloat(binding.etTemp.getText().toString()));
            csv.setHgt(Integer.parseInt(binding.etGlucemia.getText().toString()));
            csv.setObservaciones(binding.etObservaciones.getText().toString());
            csv.setVisitaId(IdVisita);
            String textoBoton= binding.btnGuardar.getText().toString();
            mViewModel.accionBoton(textoBoton, csv);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }

}