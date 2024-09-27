package com.example.atenciondomiciliaria.ui.registro.CSV;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.modelo.Csv;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsvViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Csv> registro;
    private MutableLiveData<String> boton;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<Integer> idVisita;
    private MutableLiveData<Boolean> activos;
    private final String token;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;

    public CsvViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        token= ApiClientRetrofit.leerToken(context);
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    public LiveData<Csv> getRegistro() {
        if (registro == null) {
            registro = new MutableLiveData<>();
        }
        return registro;
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

    public void accionBoton(String text, Csv csv) {
        if (text.equals("Editar")){
            activos.setValue(true);
            boton.setValue("Guardar");
        }
        if(text.equals("Guardar")){
            Call<Csv> call= api.modificarCsv(token, csv);
            call.enqueue(new Callback<Csv>() {
                @Override
                public void onResponse(Call<Csv> call, Response<Csv> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Csv control= response.body();
                        registro.postValue(control);
                        Toast.makeText(context, "Registro modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Error al modificar el registro"+
                                response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Csv> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            boton.setValue("Editar");
            activos.setValue(false);
        }
        if(text.equals("Nuevo Registro")){
            Call<Csv> call= api.nuevoCsv(token,csv);
            call.enqueue(new Callback<Csv>() {
                @Override
                public void onResponse(Call<Csv> call, Response<Csv> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Csv control= response.body();
                        //registro.postValue(control);
                        mensaje.setValue("Registro creo con éxito");
                    } else {
                        Toast.makeText(context, "Error al guardar el registro"+ response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Csv> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void cargarDatos(Bundle bundle) {
        if (bundle != null && bundle.containsKey("id")) {
            activos.setValue(false);
            boton.setValue("Editar");
            int idCSV = bundle.getInt("id");
            Call<Csv> call= api.obtenerCsv(token, idCSV);
            call.enqueue(new Callback<Csv>() {
                @Override
                public void onResponse(Call<Csv> call, Response<Csv> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Csv nuevoCsv= response.body();
                        registro.postValue(nuevoCsv);
                        idVisita.postValue(nuevoCsv.getVisita().getId());
                    }
                    else {
                        Toast.makeText(context, "Error "+ response.raw().message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Csv> call, Throwable t) {
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