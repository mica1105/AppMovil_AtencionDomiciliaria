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
import com.example.atenciondomiciliaria.databinding.FragmentListaCitasBinding;
import com.example.atenciondomiciliaria.databinding.FragmentListaRegistrosBinding;

import java.util.ArrayList;
import java.util.List;

public class ListaRegistrosFragment extends Fragment {

    private MultiTypeAdapter adapter;
    private ListaRegistrosViewModel mViewModel;
    private FragmentListaRegistrosBinding binding;
    private Context context;
    private List<Object> lista1 = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentListaRegistrosBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        Bundle bundle= getArguments();
        String descripcion= bundle.getString("descripcion");
        binding.etDescripcion.setText(descripcion);
        mViewModel = new ViewModelProvider(this).get(ListaRegistrosViewModel.class);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        binding.rvListaRegistros.setLayoutManager(gridLayoutManager);
        adapter= new MultiTypeAdapter(lista1,context);
        binding.rvListaRegistros.setAdapter(adapter);
        mViewModel.getLista().observe(getViewLifecycleOwner(), lista -> {
            lista1.clear();
            lista1.addAll(lista);
            adapter.notifyDataSetChanged();
        });
        mViewModel.cargarLista(descripcion);
        binding.fabAgregarRegistro.setOnClickListener(v -> {
            mViewModel.accionBoton(descripcion);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}