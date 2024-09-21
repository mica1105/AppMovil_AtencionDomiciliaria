package com.example.atenciondomiciliaria.ui.citas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCitasBinding;

import java.util.Calendar;

public class CitasFragment extends Fragment {

    private FragmentCitasBinding binding;
    private CitasViewModel citasViewModel;
    private Calendar calendar;
    private String f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        citasViewModel =
                new ViewModelProvider(this).get(CitasViewModel.class);

        binding = FragmentCitasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        setDate();
        citasViewModel.getTextoBoton().observe(getViewLifecycleOwner(), s -> {
            binding.btCitas.setText(s);
        });
        binding.cvCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                citasViewModel.cargarBoton(year+"-"+(month+1)+"-"+day);
                f=year+"-"+(month+1)+"-"+day;
            }
        });
        binding.btCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("fecha", f);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.listaCitasFragment, bundle);
            }
        });
        binding.fabAgregarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nuevaCitaFragment);
            }
        });
    }

    public void setDate() {
       calendar = Calendar.getInstance();
       int year = calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH);
       int day = calendar.get(Calendar.DAY_OF_MONTH);

       calendar.set(Calendar.YEAR, year);
       calendar.set(Calendar.MONTH, month);
       calendar.set(Calendar.DAY_OF_MONTH, day);
       long date = calendar.getTimeInMillis();
       binding.cvCitas.setDate(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}