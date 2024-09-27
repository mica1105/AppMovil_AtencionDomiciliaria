package com.example.atenciondomiciliaria.ui.registro.Higiene;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.modelo.HigieneyConfort;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HigieneViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<HigieneyConfort> registro;
    private MutableLiveData<String> boton;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<Integer> idVisita;
    private MutableLiveData<Boolean> activos;
    private final String token;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;

    public HigieneViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        token= ApiClientRetrofit.leerToken(context);
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    public LiveData<HigieneyConfort> getRegistro() {
        if (registro == null) {
            registro = new MutableLiveData<>();
        }
        return registro;
    }

    public LiveData<Integer> getIdVisita() {
        if (idVisita == null) {
            idVisita = new MutableLiveData<>();
        }
        return idVisita;
    }

    public LiveData<Boolean> getActivos() {
        if (activos == null) {
            activos = new MutableLiveData<>();
        }
        return activos;
    }

    public LiveData<String> getBoton() {
        if (boton == null) {
            boton = new MutableLiveData<>();
        }
        return boton;
    }

    public LiveData<String> getMensaje() {
        if (mensaje == null) {
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    public void accionBoton(String text, HigieneyConfort higiene) {
        if (text.equals("Editar")){
            activos.setValue(true);
            boton.setValue("Guardar");
        }
        if(text.equals("Guardar")){
            Call<HigieneyConfort> call= api.modificarHigieneyConfort(token,higiene);
            call.enqueue(new Callback<HigieneyConfort>() {
                @Override
                public void onResponse(Call<HigieneyConfort> call, Response<HigieneyConfort> response) {
                    if(response.isSuccessful() && response.body() != null){
                        HigieneyConfort higieneyConfort= response.body();
                        registro.postValue(higieneyConfort);
                        Toast.makeText(context, "Registro modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Error al modificar el registro"+
                                response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HigieneyConfort> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            boton.setValue("Editar");
            activos.setValue(false);
        }
        if(text.equals("Nuevo Registro")){
            Call<HigieneyConfort> call= api.nuevoHigieneyConfort(token,higiene);
            call.enqueue(new Callback<HigieneyConfort>() {
                @Override
                public void onResponse(Call<HigieneyConfort> call, Response<HigieneyConfort> response) {
                    if(response.isSuccessful() && response.body() != null){
                        HigieneyConfort higiene= response.body();
                        //registro.postValue(higiene);
                        mensaje.setValue("Registro creado con éxito");
                    } else {
                        Toast.makeText(context, "Error al guardar el registro"+ response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HigieneyConfort> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void cargarDatos(Bundle bundle) {
        if (bundle != null && bundle.containsKey("id")) {
            activos.setValue(false);
            boton.setValue("Editar");
            int id = bundle.getInt("id");
            Call<HigieneyConfort> call= api.obtenerHigieneyConfort(token,id);
            call.enqueue(new Callback<HigieneyConfort>() {
                @Override
                public void onResponse(Call<HigieneyConfort> call, Response<HigieneyConfort> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HigieneyConfort nuevoHigieneyConfort = response.body();
                        registro.postValue(nuevoHigieneyConfort);
                        idVisita.postValue(nuevoHigieneyConfort.getVisita().getId());
                    } else {
                        Toast.makeText(context, "Error " + response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HigieneyConfort> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (bundle != null && bundle.containsKey("idVisita")) {
            activos.setValue(true);
            boton.setValue("Nuevo Registro");
            int idVisita = bundle.getInt("idVisita");
            this.idVisita.setValue(idVisita);
        } else {
            activos.setValue(true);
            boton.setValue("Guardar");
        }
    }
}