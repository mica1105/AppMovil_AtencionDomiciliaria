package com.example.atenciondomiciliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClientRetrofit {
    private static final String URLBASE= "http://192.168.1.15:5000/";
    private static ApiAtencionDomiciliaria apiAtencionDomiciliaria;
    public static ApiAtencionDomiciliaria getApiAtencionDomiciliaria(){
        Gson gson= new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
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

        @PUT("Enfermeros")
        Call<Enfermero> modificarUsuario(@Header("Authorization") String token, @Body Enfermero enfermero);

        @FormUrlEncoded
        @PUT("Enfermeros/CambiarClave")
        Call<String> cambiarClave(@Header("Authorization") String token, @Field("ClaveActual") String claveActual,
                                  @Field("ClaveNueva") String claveNueva);
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

