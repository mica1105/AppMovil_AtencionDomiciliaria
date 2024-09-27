package com.example.atenciondomiciliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atenciondomiciliaria.modelo.AdmDeFarmacos;
import com.example.atenciondomiciliaria.modelo.Csv;
import com.example.atenciondomiciliaria.modelo.Curaciones;
import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.example.atenciondomiciliaria.modelo.HC;
import com.example.atenciondomiciliaria.modelo.HigieneyConfort;
import com.example.atenciondomiciliaria.modelo.Paciente;
import com.example.atenciondomiciliaria.modelo.Visita;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.Response;
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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    private static final String URLBASE= "http://192.168.1.15:5000/";
    private static ApiAtencionDomiciliaria apiAtencionDomiciliaria;
    public static ApiAtencionDomiciliaria getApiAtencionDomiciliaria(){
        Gson gson= new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();*/
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new ErrorInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
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

        @POST("Enfermeros/Crear")
        Call<Enfermero> nuevoUsuario(@Body Enfermero enfermero);

        @PUT("Enfermeros")
        Call<Enfermero> modificarUsuario(@Header("Authorization") String token, @Body Enfermero enfermero);

        @Multipart
        @POST("Enfermeros/EditarAvatar")
        Call<Enfermero> editarAvatar(@Header("Authorization") String token, @Part MultipartBody.Part imageFile);

        @FormUrlEncoded
        @PUT("Enfermeros/CambiarClave")
        Call<String> cambiarClave(@Header("Authorization") String token, @Field("claveActual") String claveActual,
                                  @Field("nuevaClave") String claveNueva);

        @GET("Visitas/Cantidad/{fecha}")
        Call<Integer> cantidadVisitas(@Header("Authorization") String token, @Path("fecha") String fecha);

        @GET("Visitas/fechaAtencion/{fecha}")
        Call<List<Visita>> agendaFecha(@Header("Authorization") String token, @Path("fecha") String fecha);

        @POST("Visitas/{fecha}")
        Call<Visita> nuevaCita(@Header("Authorization") String token, @Body Visita cita, @Path("fecha") String fecha);

        @GET("Visitas/{id}")
        Call<Visita> obtenerCita(@Header("Authorization") String token, @Path("id") int id);

        @DELETE("Visitas/{id}")
        Call<Visita> borrarCita(@Header("Authorization") String token, @Path("id") int id);

        @GET("Pacientes")
        Call<List<Paciente>> obtenerPacientes(@Header("Authorization") String token);

        @GET("Pacientes/Buscar/{nombre}")
        Call<List<Paciente>> buscarPaciente(@Header("Authorization") String token, @Path("nombre") String nombre);

        @GET("Pacientes/Atendidos")
        Call<List<Paciente>> pacientesAtendidos(@Header("Authorization") String token);

        @GET("HC/{id}")
        Call<HC> obtenerHC(@Header("Authorization") String token, @Path("id") int id);

        @GET("AdmDeFarmacos")
        Call<List<AdmDeFarmacos>> getAdmDeFarmacos(@Header("Authorization") String token);

        @POST("AdmDeFarmacos")
        Call<AdmDeFarmacos> nuevaAdmDeFarmacos(@Header("Authorization") String token, @Body AdmDeFarmacos admDeFarmacos);

        @GET("AdmDeFarmacos/{id}")
        Call<AdmDeFarmacos> obtenerAdmDeFarmacos(@Header("Authorization") String token, @Path("id") int id);

        @PUT("AdmDeFarmacos")
        Call<AdmDeFarmacos> modificarAdmDeFarmacos(@Header("Authorization") String token, @Body AdmDeFarmacos admDeFarmacos);

        @DELETE("AdmDeFarmacos/{id}")
        Call<AdmDeFarmacos> borrarAdmDeFarmacos(@Header("Authorization") String token, @Path("id") int id);

        @GET("Csvs")
        Call<List<Csv>> getCsv(@Header("Authorization") String token);

        @POST("Csvs")
        Call<Csv> nuevoCsv(@Header("Authorization") String token, @Body Csv csv);

        @GET("Csvs/{id}")
        Call<Csv> obtenerCsv(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Csvs")
        Call<Csv> modificarCsv(@Header("Authorization") String token, @Body Csv csv);

        @DELETE("Csvs/{id}")
        Call<Csv> borrarCsv(@Header("Authorization") String token, @Path("id") int id);

        @GET("HigieneyConforts")
        Call<List<HigieneyConfort>> getHigieneyConfort(@Header("Authorization") String token);

        @POST("HigieneyConforts")
        Call<HigieneyConfort> nuevoHigieneyConfort(@Header("Authorization") String token, @Body HigieneyConfort higieneyConfort);

        @GET("HigieneyConforts/{id}")
        Call<HigieneyConfort> obtenerHigieneyConfort(@Header("Authorization") String token, @Path("id") int id);

        @PUT("HigieneyConforts")
        Call<HigieneyConfort> modificarHigieneyConfort(@Header("Authorization") String token, @Body HigieneyConfort higieneyConfort);

        @DELETE("HigieneyConforts/{id}")
        Call<HigieneyConfort> borrarHigieneyConfort(@Header("Authorization") String token, @Path("id") int id);

        @GET("Curaciones")
        Call<List<Curaciones>> getCuraciones(@Header("Authorization") String token);

        @POST("Curaciones")
        Call<Curaciones> nuevaCuracion(@Header("Authorization") String token, @Body Curaciones curaciones);

        @GET("Curaciones/{id}")
        Call<Curaciones> obtenerCuracion(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Curaciones")
        Call<Curaciones> modificarCuracion(@Header("Authorization") String token, @Body Curaciones curaciones);

        @DELETE("Curaciones/{id}")
        Call<Curaciones> borrarCuracion(@Header("Authorization") String token, @Path("id") int id);

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
class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            // Manejar el error aquí (puedes lanzar una excepción o devolver un objeto de error)
            throw new IOException("Error: " + response.code());
        }
        return response;
    }
}


