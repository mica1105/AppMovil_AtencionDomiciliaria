package com.example.atenciondomiciliaria.ui.registro;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.modelo.Csv;
import com.example.atenciondomiciliaria.modelo.Curaciones;
import com.example.atenciondomiciliaria.modelo.HigieneyConfort;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaRegistrosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Object>> lista;


    public ListaRegistrosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Object>> getLista() {
        if (lista == null) {
            lista = new MutableLiveData<>();
        }
        return lista;
    }

    public void cargarLista(String descripcion) {

        String token= ApiClientRetrofit.leerToken(context);
        List<Object> listaAux= new ArrayList<>();
        ApiClientRetrofit.ApiAtencionDomiciliaria api= ApiClientRetrofit.getApiAtencionDomiciliaria();
        if (descripcion.equals("Admin de Farmacos")){
            Call<List<AdmDeFarmacos>> call= api.getAdmDeFarmacos(token);
            call.enqueue(new Callback<List<AdmDeFarmacos>>() {
                @Override
                public void onResponse(Call<List<AdmDeFarmacos>> call, Response<List<AdmDeFarmacos>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        listaAux.clear();
                        listaAux.addAll(response.body());
                        lista.postValue(listaAux);
                    } else {
                        Log.d("Salida respuesta", response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<List<AdmDeFarmacos>> call, Throwable t) {
                    Log.d("Salida error", t.getMessage());
                }
            });
        }
        if(descripcion.equals("Higiene y Confort")){
            Call<List<HigieneyConfort>> call= api.getHigieneyConfort(token);
            call.enqueue(new Callback<List<HigieneyConfort>>() {
                @Override
                public void onResponse(Call<List<HigieneyConfort>> call, Response<List<HigieneyConfort>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        listaAux.clear();
                        listaAux.addAll(response.body());
                        lista.postValue(listaAux);
                    } else {
                        Log.d("Salida respuesta", response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<List<HigieneyConfort>> call, Throwable t) {
                    Log.d("Salida error", t.getMessage());
                }
            });
        }
        if(descripcion.equals("Curaciones")){
            Call<List<Curaciones>> call= api.getCuraciones(token);
            call.enqueue(new Callback<List<Curaciones>>() {
                @Override
                public void onResponse(Call<List<Curaciones>> call, Response<List<Curaciones>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        listaAux.clear();
                        listaAux.addAll(response.body());
                        lista.postValue(listaAux);
                    } else {
                        Log.d("Salida respuesta", response.raw().message());
                    }
                }
                @Override
                public void onFailure(Call<List<Curaciones>> call, Throwable t) {
                    Log.d("Salida error", t.getMessage());
                }
            });
        }
        if(descripcion.equals("Control de Signos Vitales")){
            Call<List<Csv>> call= api.getCsv(token);
            call.enqueue(new Callback<List<Csv>>() {
                @Override
                public void onResponse(Call<List<Csv>> call, Response<List<Csv>> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        listaAux.clear();
                        listaAux.addAll(response.body());
                        lista.postValue(listaAux);
                    } else {
                        Log.d("Salida respuesta", response.raw().message());
                    }
                }
                @Override
                public void onFailure(Call<List<Csv>> call, Throwable t) {
                    Log.d("Salida error", t.getMessage());
                }
            });
        }
    }

    public void accionBoton(String descripcion){
        if (descripcion.equals("Admin de Farmacos")){
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.admDeFarmacosFragment);
        }
        if(descripcion.equals("Higiene y Confort")){
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.higieneFragment);
        }
        if(descripcion.equals("Curaciones")) {
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.curacionesFragment);
        }
        if(descripcion.equals("Control de Signos Vitales")){
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.csvFragment);
        }
    }
}