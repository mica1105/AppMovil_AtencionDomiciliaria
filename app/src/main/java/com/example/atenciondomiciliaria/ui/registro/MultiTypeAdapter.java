package com.example.atenciondomiciliaria.ui.registro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.databinding.ItemListaregistrosBinding;
import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.modelo.Csv;
import com.example.atenciondomiciliaria.modelo.Curaciones;
import com.example.atenciondomiciliaria.modelo.HigieneyConfort;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeAdapter.MyViewHolder> {

    private List<Object> items;
    private Context context;
    private LayoutInflater inflater;
    private String token;
    private ApiClientRetrofit.ApiAtencionDomiciliaria api;

    public MultiTypeAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        token = ApiClientRetrofit.leerToken(context);
        api = ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListaregistrosBinding binding = ItemListaregistrosBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Object item = items.get(position);
        if(item instanceof AdmDeFarmacos){
            AdmDeFarmacos admDeFarmacos= (AdmDeFarmacos) item;
            holder.binding.tvPaciente.setText(
                    String.format("%s %s",
                            admDeFarmacos.getVisita().getPaciente().getNombre(),
                            admDeFarmacos.getVisita().getPaciente().getApellido())
            );
            holder.binding.tvDni.setText(String.format("%s%s", "DNI: ",
                    admDeFarmacos.getVisita().getPaciente().getDni()));
            holder.binding.tvFecha.setText(String.format("%s%s", "Fecha: ",
                    admDeFarmacos.getVisita().convertirFecha(admDeFarmacos.getVisita().getFechaAtencion())));
            holder.binding.tvObservaciones.setText(admDeFarmacos.getObservaciones());
            holder.binding.ibBorrar.setOnClickListener(v -> {
                Call<AdmDeFarmacos> call= api.borrarAdmDeFarmacos(token, admDeFarmacos.getId());
                call.enqueue(new Callback<AdmDeFarmacos>() {
                    @Override
                    public void onResponse(Call<AdmDeFarmacos> call, Response<AdmDeFarmacos> response) {
                       if(response.isSuccessful()) {
                           AdmDeFarmacos eliminado = response.body();
                           Toast.makeText(context, "El registro " + eliminado + " fue eliminado", Toast.LENGTH_SHORT).show();
                           items.remove(position);
                           notifyItemRemoved(position);
                       } else{
                           Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                           Log.d("Salida error", response.raw().message());
                       }
                    }

                    @Override
                    public void onFailure(Call<AdmDeFarmacos> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Salida error", t.getMessage());
                    }
                });
            });
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle= new Bundle();
                bundle.putInt("id", admDeFarmacos.getId());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.admDeFarmacosFragment, bundle);
            });
        }
        if(item instanceof Csv){
            Csv csv= (Csv) item;
            holder.binding.tvPaciente.setText(
                    String.format("%s %s",
                            csv.getVisita().getPaciente().getNombre(),
                            csv.getVisita().getPaciente().getApellido())
            );
            holder.binding.tvDni.setText(String.format("%s%s", "DNI: ",
                    csv.getVisita().getPaciente().getDni()));
            holder.binding.tvFecha.setText(String.format("%s%s", "Fecha: ",
                    csv.getVisita().convertirFecha(csv.getVisita().getFechaAtencion())));
            holder.binding.tvObservaciones.setText(csv.getObservaciones());
            holder.binding.ibBorrar.setOnClickListener(v -> {
                Call<Csv> call= api.borrarCsv(token, csv.getId());
                call.enqueue(new Callback<Csv>() {
                    @Override
                    public void onResponse(Call<Csv> call, Response<Csv> response) {
                        if(response.isSuccessful()) {
                            Csv eliminado = response.body();
                            Toast.makeText(context, "El registro " + eliminado + " fue eliminado", Toast.LENGTH_SHORT).show();
                            items.remove(position);
                            notifyItemRemoved(position);
                        } else{
                            Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                            Log.d("Salida error", response.raw().message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Csv> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Salida error", t.getMessage());
                    }
                });
            });
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle= new Bundle();
                bundle.putInt("id", csv.getId());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.csvFragment, bundle);
            });
        }
        if(item instanceof HigieneyConfort){
            HigieneyConfort higieneyConfort= (HigieneyConfort) item;
            holder.binding.tvPaciente.setText(
                    String.format("%s %s",
                            higieneyConfort.getVisita().getPaciente().getNombre(),
                            higieneyConfort.getVisita().getPaciente().getApellido())
            );
            holder.binding.tvDni.setText(String.format("%s%s", "DNI: ",
                    higieneyConfort.getVisita().getPaciente().getDni()));
            holder.binding.tvFecha.setText(String.format("%s%s", "Fecha: ",
                    higieneyConfort.getVisita().convertirFecha(higieneyConfort.getVisita().getFechaAtencion())));
            holder.binding.tvObservaciones.setText(higieneyConfort.getObservaciones());
            holder.binding.ibBorrar.setOnClickListener(v -> {
                Call<HigieneyConfort> call= api.borrarHigieneyConfort(token, higieneyConfort.getId());
                call.enqueue(new Callback<HigieneyConfort>() {
                    @Override
                    public void onResponse(Call<HigieneyConfort> call, Response<HigieneyConfort> response) {
                        if(response.isSuccessful()) {
                            HigieneyConfort eliminado = response.body();
                            Toast.makeText(context, "El registro " + eliminado + " fue eliminado", Toast.LENGTH_SHORT).show();
                            items.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                            Log.d("Salida error", response.raw().message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HigieneyConfort> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Salida error", t.getMessage());
                    }
                });
            });
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle= new Bundle();
                bundle.putInt("id", higieneyConfort.getId());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.higieneFragment, bundle);
            });
        }
        if(item instanceof Curaciones){
            Curaciones curaciones= (Curaciones) item;
            holder.binding.tvPaciente.setText(
                    String.format("%s %s",
                            curaciones.getVisita().getPaciente().getNombre(),
                            curaciones.getVisita().getPaciente().getApellido())
            );
            holder.binding.tvDni.setText(String.format("%s%s", "DNI: ",
                    curaciones.getVisita().getPaciente().getDni()));
            holder.binding.tvFecha.setText(String.format("%s%s", "Fecha: ",
                    curaciones.getVisita().convertirFecha(curaciones.getVisita().getFechaAtencion())));
            holder.binding.tvObservaciones.setText(curaciones.getObservaciones());
            holder.binding.ibBorrar.setOnClickListener(v -> {
                Call<Curaciones> call= api.borrarCuracion(token, curaciones.getId());
                call.enqueue(new Callback<Curaciones>() {
                    @Override
                    public void onResponse(Call<Curaciones> call, Response<Curaciones> response) {
                        if(response.isSuccessful()) {
                            Curaciones eliminado = response.body();
                            Toast.makeText(context, "El registro " + eliminado + " fue eliminado", Toast.LENGTH_SHORT).show();
                            items.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                            Log.d("Salida error", response.raw().message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Curaciones> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Salida error", t.getMessage());
                    }
                });
            });
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle= new Bundle();
                bundle.putInt("id", curaciones.getId());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.curacionesFragment, bundle);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemListaregistrosBinding binding;
        public MyViewHolder(@NonNull ItemListaregistrosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
