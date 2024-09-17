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
import com.example.atenciondomiciliaria.databinding.FragmentListaCitasBinding;

public class ListaCitasFragment extends Fragment {

    private ListaCitasViewModel mViewModel;
    private FragmentListaCitasBinding binding;
    private CitasAdapter adapter;

    public static ListaCitasFragment newInstance() {
        return new ListaCitasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentListaCitasBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mViewModel= new ViewModelProvider(this).get(ListaCitasViewModel.class);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}