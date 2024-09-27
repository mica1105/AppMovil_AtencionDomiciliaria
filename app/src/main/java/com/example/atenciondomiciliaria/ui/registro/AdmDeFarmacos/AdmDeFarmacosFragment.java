package com.example.atenciondomiciliaria.ui.registro.AdmDeFarmacos;

import androidx.lifecycle.Observer;
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
import com.example.atenciondomiciliaria.databinding.FragmentAdmDeFarmacosBinding;
import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;

public class AdmDeFarmacosFragment extends Fragment {

    private AdmDeFarmacosViewModel mViewModel;
    private FragmentAdmDeFarmacosBinding binding;
    private ArrayAdapter<String> adapter;
    private String[] vias= {"Subcutanea", "Intravenosa", "Intramuscular", "Oral", "Topica", "Inhalatoria", "Intraocular", "Otica", "Nasal"};
    private int IdVisita, IdAdmDeFarmacos;
    private Context context;

    public static AdmDeFarmacosFragment newInstance() {
        return new AdmDeFarmacosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentAdmDeFarmacosBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(AdmDeFarmacosViewModel.class);

        adapter= new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_item, vias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerVia.setAdapter(adapter);

        mViewModel.getIdVisita().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                IdVisita= integer;
                IdAdmDeFarmacos= 0;
                binding.etPaciente.setVisibility(View.GONE);
                binding.tv1.setVisibility(View.GONE);
            }
        });

        mViewModel.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nav_registros);
            }
        });

        mViewModel.getAdmDeFarmacos().observe(getViewLifecycleOwner(), admDeFarmacos -> {
            IdAdmDeFarmacos= admDeFarmacos.getId();
            binding.etPaciente.setText(String.format("%s %s", admDeFarmacos.getVisita().getPaciente().getNombre(),
                    admDeFarmacos.getVisita().getPaciente().getApellido()));
            binding.etMedicacion.setText(admDeFarmacos.getMedicacion());
            binding.etDosis.setText( String.valueOf(admDeFarmacos.getDosis()));
            int posicionVia= adapter.getPosition(admDeFarmacos.getVia());
            binding.spinnerVia.setSelection(posicionVia);
            binding.etRA.setText(admDeFarmacos.getRa());
            binding.etObservaciones.setText(admDeFarmacos.getObservaciones());
            IdVisita= admDeFarmacos.getVisitaId();

        });

        mViewModel.getActivos().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean activos) {
                binding.etPaciente.setEnabled(activos);
                binding.etMedicacion.setEnabled(activos);
                binding.etDosis.setEnabled(activos);
                binding.spinnerVia.setEnabled(activos);
                binding.etRA.setEnabled(activos);
                binding.etObservaciones.setEnabled(activos);
            }
        });

        mViewModel.getBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                binding.btnGuardar.setText(string);
            }
        });

        Bundle bundle= getArguments();
        mViewModel.cargarDatos(bundle);

        binding.btnGuardar.setOnClickListener(v -> {

            AdmDeFarmacos admDeFarmacos = new AdmDeFarmacos();
            admDeFarmacos.setId(IdAdmDeFarmacos);
            admDeFarmacos.setVia(binding.spinnerVia.getSelectedItem().toString());
            admDeFarmacos.setMedicacion(binding.etMedicacion.getText().toString());
            admDeFarmacos.setDosis(Float.parseFloat( binding.etDosis.getText().toString()));
            admDeFarmacos.setRa(binding.etRA.getText().toString());
            admDeFarmacos.setObservaciones(binding.etObservaciones.getText().toString());
            admDeFarmacos.setVisitaId(IdVisita);
            String textoBoton= binding.btnGuardar.getText().toString();
            mViewModel.accionBoton(textoBoton, admDeFarmacos);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}