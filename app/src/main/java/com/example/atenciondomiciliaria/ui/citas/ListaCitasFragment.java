package com.example.atenciondomiciliaria.ui.citas;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentListaCitasBinding;

public class ListaCitasFragment extends Fragment {

    private ListaCitasViewModel mViewModel;
    private FragmentListaCitasBinding binding;
    private CitasAdapter adapter;
    private Context context;

    public static ListaCitasFragment newInstance() {
        return new ListaCitasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentListaCitasBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        Bundle bundle= getArguments();
        String fecha= bundle.getString("fecha");
        String anio = fecha.substring(0,4);
        String mes = fecha.substring(5, 6);
        String dia = fecha.substring(7, 9);

        String fechaConvertida = dia + "-" + mes + "-" + anio;
        binding.tvFecha.setText(fechaConvertida);
        mViewModel= new ViewModelProvider(this).get(ListaCitasViewModel.class);
        mViewModel.getCitas().observe(getViewLifecycleOwner(), visitas -> {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false);
            binding.rvCitas.setLayoutManager(gridLayoutManager);
            adapter= new CitasAdapter(context,visitas,getLayoutInflater());
            binding.rvCitas.setAdapter(adapter);
        });
        mViewModel.cargarCitas(fecha);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}