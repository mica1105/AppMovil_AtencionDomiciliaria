package com.example.atenciondomiciliaria.ui.salir;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

public class LogoutViewModel extends AndroidViewModel {
    private Context context;
    public LogoutViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public void salir(){
        ApiClientRetrofit.borrarToken(context);
    }
}