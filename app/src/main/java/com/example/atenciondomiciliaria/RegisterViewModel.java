package com.example.atenciondomiciliaria;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
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

public class RegisterViewModel extends AndroidViewModel {

    private MutableLiveData<String> error;
    private Context context;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();

    }

    public LiveData<String> getError() {
        if(error==null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public void crearUsuario(Enfermero enfermero){
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<Enfermero> call= api.nuevoUsuario(enfermero);
        call.enqueue(new Callback<Enfermero>() {
            @Override
            public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Enfermero usuario= response.body();
                    Intent intent= new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    Toast.makeText(context, "Usuario creado, logeese con su email y clase generada", Toast.LENGTH_SHORT).show();
                }else{
                    error.setValue("Error al crear usuario: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Enfermero> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }
}
