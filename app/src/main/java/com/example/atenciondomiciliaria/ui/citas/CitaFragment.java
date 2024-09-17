package com.example.atenciondomiciliaria.ui.citas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCitaBinding;

public class CitaFragment extends Fragment {

    private CitaViewModel mViewModel;
    private FragmentCitaBinding binding;

    public static CitaFragment newInstance() {
        return new CitaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCitaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel= new ViewModelProvider(this).get(CitaViewModel.class);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}