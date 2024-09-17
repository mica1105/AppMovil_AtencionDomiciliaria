package com.example.atenciondomiciliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String> textoBoton;
    private MutableLiveData<Enfermero> usuario;
    private MutableLiveData<Boolean> estado;
    private final String token;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        token= ApiClientRetrofit.leerToken(context);
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    public LiveData<String> getTextoBoton() {
        if(textoBoton == null){
            textoBoton= new MutableLiveData<>();
        }
        return textoBoton;
    }

    public LiveData<Enfermero> getUsuario() {
        if(usuario == null){
            usuario= new MutableLiveData<>();
        }
        return usuario;
    }

    public LiveData<Boolean> getEstado() {
        if(estado == null){
            estado= new MutableLiveData<>();
        }
        return estado;
    }

    public void accionBoton(String boton, Enfermero enfermero){
        if (boton.equals("Editar")){
            textoBoton.setValue("Guardar");
            estado.setValue(true);
        }
        if(boton.equals("Guardar")){
            Call<Enfermero> call= api.modificarUsuario(token,enfermero);
            call.enqueue(new Callback<Enfermero>() {
                @Override
                public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                    if(response.isSuccessful()){
                        Enfermero enfermero= response.body();
                        usuario.postValue(enfermero);
                        Toast.makeText(context, "Usuario correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("salida respuesta", response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<Enfermero> call, Throwable t) {
                    Log.d("salida respuesta", t.getMessage());
                }
            });
            textoBoton.setValue("Editar");
            estado.setValue(false);
        }
    }

    public void cargarPerfil(){
        Call<Enfermero> call= api.obtenerEnfermero(token);
        call.enqueue(new Callback<Enfermero>() {
            @Override
            public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                if(response.isSuccessful()){
                    Enfermero enfermero= response.body();
                    usuario.postValue(enfermero);
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Enfermero> call, Throwable t) {
                Log.d("salida falla",t.getMessage());
            }
        });
    }

}