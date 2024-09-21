package com.example.atenciondomiciliaria.ui.citas;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.Paciente;
import com.example.atenciondomiciliaria.modelo.Visita;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitaViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<List<Paciente>> pacientes;

    public CitaViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();

    }

    public LiveData<String> getMensaje() {
        if(mensaje == null) {
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    public LiveData<List<Paciente>> getPacientes() {
        if (pacientes == null) {
            pacientes = new MutableLiveData<>();
        }
        return pacientes;
    }

    public void obtetenerPacientes() {
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<List<Paciente>> call= api.obtenerPacientes(token);
        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    pacientes.postValue(response.body());
                } else {
                    Log.d("salida respuesta: ", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });
    }

    public void crearVisita(Visita visita) {
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<Visita> call= api.nuevaCita(token, visita);
        call.enqueue(new Callback<Visita>() {
            @Override
            public void onResponse(Call<Visita> call, Response<Visita> response) {
                if(response.isSuccessful()){
                    Visita cita= response.body();
                    mensaje.setValue("Cita creada: Fecha "+ cita.convertirFecha(cita.getFecha())
                            +" Hora: "+cita.convertirHora(cita.getInicioAtencion()));
                } else {
                    Log.d("salida respuesta: ", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Visita> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });

    }
}