package com.example.atenciondomiciliaria.ui.citas;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCitaBinding;
import com.example.atenciondomiciliaria.modelo.Paciente;

import java.util.List;

public class CitaFragment extends Fragment {

    private CitaViewModel mViewModel;
    private FragmentCitaBinding binding;
    private Spinner spinner;
    private Context context;
    private List<Paciente> listaPacientes;
    private int idPaciente;

    public static CitaFragment newInstance() {
        return new CitaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCitaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicializar(root);
        context= root.getContext();
        return root;
    }

    private void inicializar(View root) {
        spinner= binding.sPacientes;
        mViewModel= new ViewModelProvider(this).get(CitaViewModel.class);
        mViewModel.getPacientes().observe(getViewLifecycleOwner(), (pacientes)->{
            listaPacientes= pacientes;
        });
        mViewModel.obtetenerPacientes();
        ArrayAdapter<Paciente> arrayAdapter=
                new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaPacientes);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                idPaciente= ( (Paciente) parent.getSelectedItem()).getId();
                String nombre =((Paciente) parent.getSelectedItem()).getNombre();
                Toast.makeText(context, "Paciente: "+idPaciente+" "+nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}