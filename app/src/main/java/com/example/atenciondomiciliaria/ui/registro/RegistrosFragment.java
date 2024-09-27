package com.example.atenciondomiciliaria.ui.registro;

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
import com.example.atenciondomiciliaria.databinding.FragmentRegistrosBinding;
import com.example.atenciondomiciliaria.modelo.Registro;

import java.util.ArrayList;
import java.util.List;

public class RegistrosFragment extends Fragment {

    private RegistrosViewModel mViewModel;
    private FragmentRegistrosBinding binding;
    private AdapterVistaRegistros adapter;
    private Context context;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentRegistrosBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(RegistrosViewModel.class);
        mViewModel.getRegistros().observe(getViewLifecycleOwner(), registros -> {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
            binding.rvRegistros.setLayoutManager(gridLayoutManager);
            adapter = new AdapterVistaRegistros(context, registros);
            Bundle bundle= getArguments();
            if(bundle != null && bundle.containsKey("idVisita")){
                adapter.setBundle(bundle);
            }
            binding.rvRegistros.setAdapter(adapter);
        });
        mViewModel.cargarRegistros();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}