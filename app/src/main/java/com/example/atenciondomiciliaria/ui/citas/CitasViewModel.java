package com.example.atenciondomiciliaria.ui.citas;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitasViewModel extends AndroidViewModel {

    private MutableLiveData<String> textoBoton;
    private Context context;

    public CitasViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }


    public LiveData<String> getTextoBoton() {
        if(textoBoton == null) {
            textoBoton = new MutableLiveData<>();
        }
        return textoBoton;
    }

    public void cargarBoton(String fecha){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<Integer> call= api.cantidadVisitas(token,fecha);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    Integer cantidad= response.body();
                    String[] partes = fecha.split("-");
                    String fechaFormateada = partes[2] + "-" + partes[1] + "-" + partes[0];
                    textoBoton.postValue(fechaFormateada+"\nCitas perdientes: "+cantidad);
                } else {
                    textoBoton.postValue("Error en la conexión");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });
    }
}