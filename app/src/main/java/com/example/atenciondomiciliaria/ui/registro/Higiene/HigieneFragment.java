package com.example.atenciondomiciliaria.ui.registro.Higiene;

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
import com.example.atenciondomiciliaria.databinding.FragmentHigieneBinding;
import com.example.atenciondomiciliaria.modelo.HigieneyConfort;

public class HigieneFragment extends Fragment {

    private HigieneViewModel mViewModel;
    private FragmentHigieneBinding binding;
    private ArrayAdapter<String> adapter;
    private String[] tipo= {"Baño en ducha", "Baño en cama completo" , "Lavado de cabeza", "Higiene de genitales","Higiene de la boca"};
    private int IdVisita, IdHigiene;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentHigieneBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(HigieneViewModel.class);

        adapter= new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, tipo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipo.setAdapter(adapter);

        mViewModel.getIdVisita().observe(getViewLifecycleOwner(), integer -> {
            IdVisita= integer;
            IdHigiene= 0;
            binding.etPaciente.setVisibility(View.GONE);
            binding.tv1.setVisibility(View.GONE);
        });

        mViewModel.getRegistro().observe(getViewLifecycleOwner(), hygiene -> {
            IdHigiene= hygiene.getId();
            binding.etPaciente.setText(String.format("%s %s", hygiene.getVisita().getPaciente().getNombre(),
                    hygiene.getVisita().getPaciente().getApellido()));
            binding.spinnerTipo.setSelection(adapter.getPosition(hygiene.getTipo()));
            binding.etMateriales.setText(hygiene.getMateriales());
            binding.cbPaniales.setChecked(hygiene.isPaniales());
            binding.cbSV.setChecked(hygiene.isSondaVesical());
            binding.cbSNG.setChecked(hygiene.isSondaNasoGastrica());
            binding.etObservaciones.setText(hygiene.getObservaciones());
            IdVisita= hygiene.getVisitaId();
        });

        mViewModel.getMensaje().observe(getViewLifecycleOwner(), string -> {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_registros);
        });

        mViewModel.getActivos().observe(getViewLifecycleOwner(), bool -> {
            binding.etPaciente.setEnabled(bool);
            binding.spinnerTipo.setEnabled(bool);
            binding.etMateriales.setEnabled(bool);
            binding.cbPaniales.setEnabled(bool);
            binding.cbSV.setEnabled(bool);
            binding.cbSNG.setEnabled(bool);
            binding.etObservaciones.setEnabled(bool);
        });

        mViewModel.getBoton().observe(getViewLifecycleOwner(), string -> binding.btnGuardar.setText(string));

        Bundle bundle= getArguments();
        mViewModel.cargarDatos(bundle);

        binding.btnGuardar.setOnClickListener(v -> {
            HigieneyConfort hyc= new HigieneyConfort();
            hyc.setId(IdHigiene);
            hyc.setTipo(binding.spinnerTipo.getSelectedItem().toString());
            hyc.setMateriales(binding.etMateriales.getText().toString());
            hyc.setPaniales(binding.cbPaniales.isChecked());
            hyc.setSondaVesical(binding.cbSV.isChecked());
            hyc.setSondaNasoGastrica(binding.cbSNG.isChecked());
            hyc.setObservaciones(binding.etObservaciones.getText().toString());
            hyc.setVisitaId(IdVisita);
            String textoBoton= binding.btnGuardar.getText().toString();
            mViewModel.accionBoton(textoBoton, hyc);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }

}