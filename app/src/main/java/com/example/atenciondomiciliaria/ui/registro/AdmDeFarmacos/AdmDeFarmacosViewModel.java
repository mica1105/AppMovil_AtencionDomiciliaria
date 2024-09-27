package com.example.atenciondomiciliaria.ui.registro.AdmDeFarmacos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdmDeFarmacosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<AdmDeFarmacos> admDeFarmacos;
    private MutableLiveData<String> boton;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<Integer> idVisita;
    private MutableLiveData<Boolean> activos;
    private final String token;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;

    public AdmDeFarmacosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        token= ApiClientRetrofit.leerToken(context);
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    public LiveData<AdmDeFarmacos> getAdmDeFarmacos() {
        if (admDeFarmacos == null) {
            admDeFarmacos = new MutableLiveData<>();
        }
        return admDeFarmacos;
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

    public void accionBoton(String text, AdmDeFarmacos aDF) {
        if (text.equals("Editar")){
            activos.setValue(true);
            boton.setValue("Guardar");
        }
        if(text.equals("Guardar")){
            Call<AdmDeFarmacos> call= api.modificarAdmDeFarmacos(token,aDF);
            call.enqueue(new Callback<AdmDeFarmacos>() {
                @Override
                public void onResponse(Call<AdmDeFarmacos> call, Response<AdmDeFarmacos> response) {
                    if(response.isSuccessful() && response.body() != null){
                        AdmDeFarmacos nuevoAdmDeFarmacos= response.body();
                        admDeFarmacos.postValue(nuevoAdmDeFarmacos);
                        Toast.makeText(context, "Registro modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Error al modificar el registro"+
                                response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdmDeFarmacos> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            boton.setValue("Editar");
            activos.setValue(false);
        }
        if(text.equals("Nuevo Registro")){
            Call<AdmDeFarmacos> call= api.nuevaAdmDeFarmacos(token,aDF);
            call.enqueue(new Callback<AdmDeFarmacos>() {
                @Override
                public void onResponse(Call<AdmDeFarmacos> call, Response<AdmDeFarmacos> response) {
                    if(response.isSuccessful() && response.body() != null){
                        AdmDeFarmacos nuevoAdmDeFarmacos= response.body();
                        //admDeFarmacos.postValue(nuevoAdmDeFarmacos);
                        mensaje.setValue("Registro creado con éxito");
                    } else {
                        Toast.makeText(context, "Error al guardar el registro"+ response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdmDeFarmacos> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void cargarDatos(Bundle bundle) {
        if (bundle != null && bundle.containsKey("id")) {
            activos.setValue(false);
            boton.setValue("Editar");
            int idAdmDeFarmacos = bundle.getInt("id");
            Call<AdmDeFarmacos> call = api.obtenerAdmDeFarmacos(token, idAdmDeFarmacos);
            call.enqueue(new Callback<AdmDeFarmacos>() {
                @Override
                public void onResponse(Call<AdmDeFarmacos> call, Response<AdmDeFarmacos> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AdmDeFarmacos nuevoAdmDeFarmacos = response.body();
                        admDeFarmacos.postValue(nuevoAdmDeFarmacos);
                    }
                    else {
                        Toast.makeText(context, "Error "+ response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdmDeFarmacos> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (bundle != null && bundle.containsKey("idVisita")) {
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