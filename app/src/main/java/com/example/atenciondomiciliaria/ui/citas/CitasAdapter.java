package com.example.atenciondomiciliaria.ui.citas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.ItemCitaBinding;
import com.example.atenciondomiciliaria.modelo.Visita;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitasViewHolder> {
    private final Context context;
    private final List<Visita> citas;
    private final LayoutInflater inflater;

    public CitasAdapter(Context context, List<Visita> citas) {
        this.context = context;
        this.citas = citas;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCitaBinding binding = ItemCitaBinding.inflate(inflater, parent, false);
        return new CitasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {
        Visita cita = citas.get(position);
        String paciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido();

        holder.binding.tvCita.setText(cita.convertirHora(cita.getInicioAtencion()));
        holder.binding.tvPaciente.setText(paciente);
        holder.binding.tvTelefono.setText(cita.getPaciente().getTelefono());
        holder.binding.tvDireccion.setText(cita.getPaciente().getDomicilio());
        holder.binding.tvPrestaciones.setText(cita.getPrestaciones());
        if(cita.isEstado()){
            holder.binding.btnBorrarCita.setVisibility(View.GONE);
        }
        holder.binding.btnBorrarCita.setOnClickListener(view -> {
            String token = ApiClientRetrofit.leerToken(context);
            ApiClientRetrofit.ApiAtencionDomiciliaria api = ApiClientRetrofit.getApiAtencionDomiciliaria();
            Call<Visita> call = api.borrarCita(token, cita.getId());
            call.enqueue(new Callback<Visita>() {
                @Override
                public void onResponse(Call<Visita> call, Response<Visita> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Visita cita = response.body();
                        Toast.makeText(context, "La cita de las " + cita.convertirHora(cita.getInicioAtencion()) + " fue borrada", Toast.LENGTH_SHORT).show();
                        citas.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Visita> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.binding.btnRegistro.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            int idVisita= citas.get(holder.getAdapterPosition()).getId();
            bundle.putInt("idVisita", idVisita);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_registros, bundle);
        });
        /*
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            Visita visita = citas.get(holder.getAdapterPosition());
            bundle.putSerializable("visita", visita);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nuevaCitaFragment, bundle);
        });*/
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    static class CitasViewHolder extends RecyclerView.ViewHolder {
        private final ItemCitaBinding binding;

        public CitasViewHolder(ItemCitaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

