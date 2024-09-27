package com.example.atenciondomiciliaria.ui.registro;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.modelo.Registro;

import java.util.ArrayList;
import java.util.List;

public class RegistrosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Registro>> registros;

    public RegistrosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Registro>> getRegistros() {
        if (registros == null) {
            registros = new MutableLiveData<>();
        }
        return registros;
    }

    public void cargarRegistros() {
        List<Registro> listaRegistros = new ArrayList<>();
        listaRegistros.add(new Registro(R.drawable.admdefarmacos, "Admin de Farmacos"));
        listaRegistros.add(new Registro(R.drawable.controlsv, "Control de Signos Vitales"));
        listaRegistros.add(new Registro(R.drawable.higieneyconfort, "Higiene y Confort"));
        listaRegistros.add(new Registro(R.drawable.curaciones, "Curaciones"));
        registros.setValue(listaRegistros);
    }

}