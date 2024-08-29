package com.example.atenciondomiciliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClientRetrofit {
    private static final String URLBASE= "http://192.168.1.101:5000/";
    private static ApiAtencionDomiciliaria apiAtencionDomiciliaria;
    public static ApiAtencionDomiciliaria getApiAtencionDomiciliaria(){
        Gson gson= new GsonBuilder().setLenient().create();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiAtencionDomiciliaria= retrofit.create(ApiAtencionDomiciliaria.class);
        return apiAtencionDomiciliaria;
    }


    public interface ApiAtencionDomiciliaria{
        @FormUrlEncoded
        @POST("Enfermeros/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("Enfermeros")
        Call<Enfermero> obtenerEnfermero(@Header("Authorization") String token);
    }

    public static void guardarToken(Context context, String token){
        SharedPreferences sp= context.getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String leerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token.xml", 0);
        return sp.getString("token", "");
    }

    public static void borrarToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token.xml",0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("token","");
        editor.commit();
    }
}

