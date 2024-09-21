package com.example.atenciondomiciliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.example.atenciondomiciliaria.modelo.Paciente;
import com.example.atenciondomiciliaria.modelo.Visita;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    private static final String URLBASE= "http://192.168.1.102:5000/";
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
        @GET("Visitas/Cantidad/{fecha}")
        Call<Integer> cantidadVisitas(@Header("Authorization") String token, @Path("fecha") String fecha);

        @GET("Visitas/fechaAtencion/{fecha}")
        Call<List<Visita>> agendaFecha(@Header("Authorization") String token, @Path("fecha") String fecha);

        @POST("Visitas")
        Call<Visita> nuevaCita(@Header("Authorization") String token, @Body Visita cita);

        @PUT("Visitas")
        Call<Visita> editarCita(@Header("Authorization") String token, @Body Visita cita);

        @GET("Visitas/{id}")
        Call<Visita> obtenerCita(@Header("Authorization") String token, @Path("id") int id);

        @DELETE("Visitas/{id}")
        Call<Void> borrarCita(@Header("Authorization") String token, @Path("id") int id);

        @GET("Pacientes")
        Call<List<Paciente>> obtenerPacientes(@Header("Authorization") String token);

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

