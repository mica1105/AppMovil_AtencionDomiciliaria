package com.example.atenciondomiciliaria.ui.registro;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.ItemRegistroBinding;
import com.example.atenciondomiciliaria.modelo.Registro;

import java.util.List;

public class AdapterVistaRegistros extends RecyclerView.Adapter<AdapterVistaRegistros.RegistroViewHolder> {

    private final List<Registro> registros;
    private final Context context;
    private final LayoutInflater inflater;
    private Bundle bundle;

    public AdapterVistaRegistros(Context context, List<Registro> registros) {
        this.context = context;
        this.registros = registros;
        this.inflater = LayoutInflater.from(context);
    }

    public void setBundle(Bundle bundle){
      this.bundle= bundle;
    }

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRegistroBinding binding = ItemRegistroBinding.inflate(inflater, parent, false);
        return new RegistroViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        Registro registro = registros.get(position);
        holder.binding.ivRegistro.setImageResource(registro.getRutaImagen());
        holder.binding.tvRegistro.setText(registro.getDescripcion());
        holder.itemView.setOnClickListener(v -> {
            if(this.bundle != null && this.bundle.containsKey("idVisita")){
                switch (registro.getDescripcion()){
                    case "Admin de Farmacos":
                        Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.admDeFarmacosFragment, bundle);
                        break;
                    case "Control de Signos Vitales":
                        Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.csvFragment, bundle);
                        break;
                    case "Higiene y Confort":
                        Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.higieneFragment,bundle);
                        break;
                    case "Curaciones":
                    Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                            .navigate(R.id.curacionesFragment,bundle);
                    break;
                }
            } else {
                Bundle bundle= new Bundle();
                bundle.putString("descripcion", registro.getDescripcion());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.listaRegistrosFragment, bundle);
            }
            /**/
        });
    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    static class RegistroViewHolder extends RecyclerView.ViewHolder {
        private final ItemRegistroBinding binding;

        public RegistroViewHolder(ItemRegistroBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}