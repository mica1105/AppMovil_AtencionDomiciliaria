package com.example.atenciondomiciliaria.ui.citas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.FragmentCitaBinding;
import com.example.atenciondomiciliaria.modelo.Paciente;
import com.example.atenciondomiciliaria.modelo.Visita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CitaFragment extends Fragment {

    private CitaViewModel mViewModel;
    private FragmentCitaBinding binding;
    private Spinner spinner;
    private Context context;
    private int pacienteId;
    private String prestaciones;

    public static CitaFragment newInstance() {
        return new CitaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCitaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        spinner = binding.sPacientes;
        binding.etFecha.setOnClickListener(v1 -> {
            final Calendar c = Calendar.getInstance();
            int anio = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        String fechaFormateada = dateFormat.format(calendar.getTime());
                        binding.etFecha.setText(fechaFormateada);

                    },
                    anio, mes, dia);
            datePickerDialog.show();
        });
        binding.etHoraInicio.setOnClickListener(v1 -> mostrarTimePicker(binding.etHoraInicio));
        binding.etHoraFin.setOnClickListener(v1 -> mostrarTimePicker(binding.etHoraFin));
        mViewModel = new ViewModelProvider(this).get(CitaViewModel.class);
        mViewModel.getPacientes().observe(getViewLifecycleOwner(), (pacientes) -> {
            List<String> listaPacientes = new ArrayList<>();

            for (Paciente paciente : pacientes) {
                listaPacientes.add(paciente.getNombre()+" "+paciente.getApellido());
            }

            // Crea el ArrayAdapter después de que listaPacientes se haya actualizado
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listaPacientes);
            spinner.setAdapter(arrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                String nombreCompleto;
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) { // Verifica si la vista no es nula
                        ((TextView) view).setTextSize(20);
                        ((TextView) view).setTextColor(Color.BLACK);
                    }
                    for (Paciente paciente : pacientes) {
                        nombreCompleto = parent.getItemAtPosition(position).toString();
                        String[] partes = nombreCompleto.split(" ");
                        String nombre = partes[0];
                        String apellido = partes[1];
                        if (paciente.getNombre().equals(nombre) && paciente.getApellido().equals(apellido)) {
                            pacienteId = paciente.getId();
                            break;
                        }
                    }

                    Toast.makeText(context, "Paciente: " + pacienteId + " " + nombreCompleto, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No es necesario hacer nada aquí si no se requiere ninguna acción
                }
            });
        });
        mViewModel.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nav_citas);
                Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.obtetenerPacientes();
        binding.btGuardar.setOnClickListener(v -> {
            LinearLayout llPrestacion = binding.llPrestacion;
            List<String> prestacionesSeleccionadas = new ArrayList<>();

            for (int i = 0; i < llPrestacion.getChildCount(); i++) {
                View child = llPrestacion.getChildAt(i);
                if(child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                    String prestacion = ((CheckBox) child).getText().toString();
                    prestacionesSeleccionadas.add(prestacion);
                }
            }
           // prestaciones= "" + prestacionesSeleccionadas;
            Visita cita= new Visita();
            cita.setPacienteId(pacienteId);
            //cita.setFechaAtencion(binding.etFecha.getText().toString());
            cita.setInicioAtencion(binding.etHoraInicio.getText().toString()+":00");
            cita.setFinAtencion(binding.etHoraFin.getText().toString()+":00");
            cita.setPrestaciones(cita.modificarPrestaciones(prestacionesSeleccionadas));
            String fecha=binding.etFecha.getText().toString();
            mViewModel.crearVisita(cita, fecha);
        });

    }

    private void mostrarTimePicker(EditText editText) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, selectedHour, selectedMinute) ->
                        editText.setText(String.format("%02d:%02d", selectedHour, selectedMinute)),
                hora, minutos, true);
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}