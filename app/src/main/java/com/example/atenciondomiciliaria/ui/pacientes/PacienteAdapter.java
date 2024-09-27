package com.example.atenciondomiciliaria.ui.pacientes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.ItemPacienteBinding;
import com.example.atenciondomiciliaria.modelo.Paciente;

import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {
    private final Context context;
    private final List<Paciente> pacientes;
    private final LayoutInflater inflater;

    public PacienteAdapter(Context context, List<Paciente> pacientes) {
        this.context = context;
        this.pacientes = pacientes;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPacienteBinding binding = ItemPacienteBinding.inflate(inflater, parent, false);
        return new PacienteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        Paciente paciente = pacientes.get(position);
        holder.binding.tvNombre.setText(String.format("%s %s", paciente.getNombre(), paciente.getApellido()));
        holder.binding.tvDni.setText(String.valueOf(paciente.getDni()));
        holder.binding.tvFNacimiento.setText(paciente.convertirFecha(paciente.getFechaNacimiento()));
        holder.binding.tvDireccion.setText(paciente.getDomicilio());
        holder.binding.tvTelefono.setText(paciente.getTelefono());
        holder.itemView.setOnClickListener( view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idPaciente", paciente.getId());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.historiaClinicaFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        private  final ItemPacienteBinding binding;
        public PacienteViewHolder(ItemPacienteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
