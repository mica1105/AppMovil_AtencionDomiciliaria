package com.example.atenciondomiciliaria.ui.citas;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.Visita;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCitasViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Visita>> citas;

    public ListaCitasViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public MutableLiveData<List<Visita>> getCitas(){
        if(citas==null){
            citas= new MutableLiveData<>();
        }
        return citas;
    }

    public void cargarCitas(String fecha){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<List<Visita>> call =api.agendaFecha(token,fecha);
        call.enqueue(new Callback<List<Visita>>() {
            @Override
            public void onResponse(Call<List<Visita>> call, Response<List<Visita>> response) {
                if (response.isSuccessful()){
                    List<Visita> visitas= response.body();
                    citas.postValue(visitas);
                } else {
                    Log.d("salida respuesta: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Visita>> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });
    }
}