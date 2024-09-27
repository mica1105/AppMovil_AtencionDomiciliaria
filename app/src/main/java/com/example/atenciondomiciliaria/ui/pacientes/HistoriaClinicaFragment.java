package com.example.atenciondomiciliaria.ui.pacientes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentHistoriaClinicaBinding;
import com.example.atenciondomiciliaria.databinding.FragmentListaCitasBinding;

public class HistoriaClinicaFragment extends Fragment {

    private HistoriaClinicaViewModel mViewModel;
    private FragmentHistoriaClinicaBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentHistoriaClinicaBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(HistoriaClinicaViewModel.class);
        Bundle bundle= getArguments();
        int idPaciente= bundle.getInt("idPaciente");
        mViewModel.getHistoriaClinica().observe(getViewLifecycleOwner(), historiaClinica -> {
            if (historiaClinica != null) {
                binding.etPaciente.setText(String.format("%s %s", historiaClinica.getPaciente().getNombre(), historiaClinica.getPaciente().getApellido()));
                binding.etFN.setText(historiaClinica.getPaciente().convertirFecha(historiaClinica.getPaciente().getFechaNacimiento()));
                binding.etDni.setText(String.valueOf(historiaClinica.getPaciente().getDni()));
                binding.etDiagnostico.setText(historiaClinica.getDiagnostico());
                binding.etTratamiento.setText(historiaClinica.getMedicacion());
                binding.cbFuma.setChecked(historiaClinica.isFuma());
                binding.cbBebe.setChecked(historiaClinica.isBebe());
                binding.cbDrogas.setChecked(historiaClinica.isDrogas());
                binding.cbDbt.setChecked(historiaClinica.isDbt());
                binding.cbHta.setChecked(historiaClinica.isHta());
                binding.etAlergias.setText(historiaClinica.getAlergias());
                binding.etTraumas.setText(historiaClinica.getTraumas());
                binding.etCirugias.setText(historiaClinica.getCirugias());
            }
        });
        mViewModel.cargarHistoriaClinica(idPaciente);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}