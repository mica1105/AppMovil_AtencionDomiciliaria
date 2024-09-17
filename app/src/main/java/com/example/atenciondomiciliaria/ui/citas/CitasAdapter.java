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

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.modelo.Visita;

import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitasViewHolder> {
    private Context context;
    private List<Visita> citas;
    private LayoutInflater inflater;

    public CitasAdapter(Context context, List<Visita> citas, LayoutInflater inflater) {
        this.context = context;
        this.citas = citas;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_cita, parent, false);
        return new CitasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {
        holder.tvCita.setText(citas.get(position).getHoraInicio()+" hs");
        holder.tvPaciente.setText(citas.get(position).getPaciente().getNombre());
        holder.tvTelefono.setText(citas.get(position).getPaciente().getTelefono());
        holder.tvDireccion.setText(citas.get(position).getPaciente().getDomicilio());
        holder.btnBorrar.setOnClickListener((view)->{
            citas.remove(position);
            notifyDataSetChanged();
        });
        holder.btnEditar.setOnClickListener((view)->{
            Bundle bundle = new Bundle();
            Visita visita = citas.get(position);
            bundle.putSerializable("visita", visita);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nuevaCitaFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return  citas.size();
    }

    class CitasViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCita, tvPaciente, tvTelefono, tvDireccion;
        private ImageButton btnBorrar, btnEditar;
        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCita= itemView.findViewById(R.id.tvCita);
            tvPaciente= itemView.findViewById(R.id.tvPaciente);
            tvTelefono= itemView.findViewById(R.id.tvTelefono);
            tvDireccion= itemView.findViewById(R.id.tvDireccion);
            btnBorrar= itemView.findViewById(R.id.btnBorrarCita);
            btnEditar= itemView.findViewById(R.id.btnEditarCita);
            itemView.setOnClickListener((view)->{
                Bundle bundle = new Bundle();
                Visita visita = citas.get(getAdapterPosition());
                bundle.putSerializable("visita", visita);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nuevaCitaFragment, bundle);

            });
        }
    }
}

