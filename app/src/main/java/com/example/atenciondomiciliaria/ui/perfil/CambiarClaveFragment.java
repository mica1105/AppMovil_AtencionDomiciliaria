package com.example.atenciondomiciliaria.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCambiarClaveBinding.inflate(inflater, container, false);
        mViewModel= new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        View root= binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel.getError().observe(getViewLifecycleOwner(), s -> {
            binding.tvErrores.setText(s);
            binding.tvErrores.setVisibility(View.VISIBLE);
        });
        mViewModel.getExito().observe(getViewLifecycleOwner(), s -> {
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_perfil);
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        binding.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrores.setText("");
                binding.tvErrores.setVisibility(View.VISIBLE);
                String clave = binding.etClaveActual.getText().toString();
                String nuevaClave = binding.etClaveNueva.getText().toString();
                mViewModel.cambiarClave(clave, nuevaClave);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}