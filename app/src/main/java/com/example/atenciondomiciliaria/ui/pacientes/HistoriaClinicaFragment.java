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

public class HistoriaClinicaFragment extends Fragment {

    private HistoriaClinicaViewModel mViewModel;

    public static HistoriaClinicaFragment newInstance() {
        return new HistoriaClinicaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historia_clinica, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoriaClinicaViewModel.class);
        // TODO: Use the ViewModel
    }

}