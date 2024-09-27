package com.example.atenciondomiciliaria.ui.pacientes;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.HC;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriaClinicaViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<HC> historiaClinica;

    public HistoriaClinicaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<HC> getHistoriaClinica() {
        if (historiaClinica == null) {
            historiaClinica = new MutableLiveData<>();
        }
        return historiaClinica;
    }

    public void cargarHistoriaClinica(int idPaciente) {
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<HC> call= api.obtenerHC(token,idPaciente);
        call.enqueue(new Callback<HC>() {
            @Override
            public void onResponse(Call<HC> call, Response<HC> response) {
                if(response.isSuccessful()){
                    HC hc= response.body();
                    if(historiaClinica == null) {
                        Toast.makeText(context, "La paciente no tiene historia clinica cargada", Toast.LENGTH_SHORT).show();
                    } else {
                        historiaClinica.postValue(hc);
                    }
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<HC> call, Throwable t) {
                Log.d("salida error", t.getMessage());
            }
        });
    }

}