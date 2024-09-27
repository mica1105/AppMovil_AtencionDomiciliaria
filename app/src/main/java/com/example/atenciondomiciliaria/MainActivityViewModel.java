package com.example.atenciondomiciliaria;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Enfermero> usuario;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<Enfermero> getUsuario(){
        if(usuario== null){
            usuario= new MutableLiveData<>();
        }
        return usuario;
    }



    public void recuperarUsuario(){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<Enfermero> call= api.obtenerEnfermero(token);
        call.enqueue(new Callback<Enfermero>() {
            @Override
            public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Enfermero newUsuario= response.body();
                    if(newUsuario.getAvatar() != ""){
                        String url="http://192.168.1.15:5000";
                        newUsuario.setAvatar(url+newUsuario.getAvatar());
                    }
                    usuario.postValue(newUsuario);
                } else {
                    Toast.makeText(context, response.raw().message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Enfermero> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
