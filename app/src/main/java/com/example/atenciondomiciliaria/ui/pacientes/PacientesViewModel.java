package com.example.atenciondomiciliaria.ui.pacientes;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.Paciente;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacientesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Paciente>> pacientes;

    public PacientesViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<List<Paciente>> getPacientes() {
        if (pacientes == null) {
            pacientes = new MutableLiveData<>();
        }
        return pacientes;
    }

    public void cargarPacientes(){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<List<Paciente>> call= api.pacientesAtendidos(token);
        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    pacientes.postValue(response.body());
                } else {
                    Log.d("salida respuesta: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });
    }

    public void buscarPaciente(String nombre){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        Call<List<Paciente>> call= api.buscarPaciente(token, nombre);
        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    pacientes.postValue(response.body());
                } else {
                    Log.d("salida respuesta: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                Log.d("salida falla: ", t.getMessage());
            }
        });
    }
}