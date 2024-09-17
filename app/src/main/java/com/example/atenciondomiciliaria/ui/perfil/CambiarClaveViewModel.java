package com.example.atenciondomiciliaria.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {
    private Context context;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;
    private final String token;
    private MutableLiveData<String> error;
    private MutableLiveData<String> exito;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        token= ApiClientRetrofit.leerToken(context);
    }

    public MutableLiveData<String> getError() {
        if(error == null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public MutableLiveData<String> getExito() {
        if(exito == null){
            exito= new MutableLiveData<>();
        }
        return exito;
    }

    public void cambiarClave(String clave, String nuevaClave){
        if (clave.equals("")){
            error.setValue("La clave actual no puede estar vacia");
        } else if (nuevaClave.equals("")){
            error.setValue("La nueva clave no puede estar vacia");

        }
        Call<String> call= api.cambiarClave(token, clave, nuevaClave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String guardarToken= "Bearer "+response.body();
                    ApiClientRetrofit.guardarToken(context, guardarToken);
                    exito.setValue("Clave cambiada correctamente");
                } else {
                    error.postValue("*"+response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }
}