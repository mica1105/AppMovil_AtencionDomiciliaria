package com.example.atenciondomiciliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atenciondomiciliaria.R;
import com.example.atenciondomiciliaria.modelo.Enfermero;
import com.example.atenciondomiciliaria.modelo.RealPathUtil;
import com.example.atenciondomiciliaria.request.ApiClientRetrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String> textoBoton;
    private MutableLiveData<Enfermero> usuario;
    private MutableLiveData<Boolean> estado;
    private final String token;
    private final ApiClientRetrofit.ApiAtencionDomiciliaria api;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        token= ApiClientRetrofit.leerToken(context);
        api= ApiClientRetrofit.getApiAtencionDomiciliaria();
    }

    public LiveData<String> getTextoBoton() {
        if(textoBoton == null){
            textoBoton= new MutableLiveData<>();
        }
        return textoBoton;
    }

    public LiveData<Enfermero> getUsuario() {
        if(usuario == null){
            usuario= new MutableLiveData<>();
        }
        return usuario;
    }

    public LiveData<Boolean> getEstado() {
        if(estado == null){
            estado= new MutableLiveData<>();
        }
        return estado;
    }

    public void accionBoton(String boton, Enfermero enfermero, Uri uriImagen){
        if (boton.equals("Editar")){
            textoBoton.setValue("Guardar");
            estado.setValue(true);
        }
        if(boton.equals("Guardar")){
            if(uriImagen != null){
                subirImagen(enfermero, uriImagen);
            } else {
                guardarCambiosUsuario(enfermero);
            }
            textoBoton.setValue("Editar");
            estado.setValue(false);
        }
    }

    private void subirImagen(Enfermero enfermero, Uri uriImagen) {
        try {
            File file = convertirUriAArchivo(uriImagen);
            if (file == null) {
                Toast.makeText(context, "Error al obtener la imagen", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("subirImagen", "Archivo obtenido: " + file.getAbsolutePath());

            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imageFile", file.getName(), fileBody);

            Call<Enfermero> call = api.editarAvatar(token, imagePart);
            call.enqueue(new Callback<Enfermero>() {
                @Override
                public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                    if (response.isSuccessful()) {
                        Enfermero nuevo= response.body();
                        enfermero.setAvatar(nuevo.getAvatar());
                        if(!enfermero.equals(nuevo)) {
                            guardarCambiosUsuario(enfermero);
                        }
                        Log.d("subirImagen", "Imagen subida exitosamente: " + nuevo.getAvatar());
                    } else {
                        Toast.makeText(context, "Error al subir imagen: " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("subirImagen", "Error al subir imagen: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Enfermero> call, Throwable t) {
                    Toast.makeText(context, "Error al subir imagen: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("subirImagen", "Error al subir imagen: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error al subir imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("subirImagen", "Error al subir imagen: " + e.getMessage());
        }
    }

    private File convertirUriAArchivo(Uri uri) {
        File archivo = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String filePath = cursor.getString(columnIndex);
                archivo = new File(filePath);
                Log.d("convertirUriAArchivo", "Archivo convertido: " + filePath);
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("convertirUriAArchivo", "Error al convertir URI a archivo: " + e.getMessage());
        }
        return archivo;
    }


    private void guardarCambiosUsuario(Enfermero enfermero){
        if(enfermero.getAvatar().startsWith("http://192.168.1.15:5000")){
            String url= enfermero.getAvatar().replace("http://192.168.1.15:5000","");
            enfermero.setAvatar(url);
        }
        Call<Enfermero> call= api.modificarUsuario(token,enfermero);
        call.enqueue(new Callback<Enfermero>() {
            @Override
            public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                if(response.isSuccessful()){
                    Enfermero enfermero= response.body();
                    usuario.postValue(enfermero);
                    Toast.makeText(context, "Usuario correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("salida respuesta", response.raw().message());
                    Toast.makeText(context, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Enfermero> call, Throwable t) {
                Log.d("salida respuesta", t.getMessage());
                Toast.makeText(context, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarPerfil(){
        Call<Enfermero> call= api.obtenerEnfermero(token);
        call.enqueue(new Callback<Enfermero>() {
            @Override
            public void onResponse(Call<Enfermero> call, Response<Enfermero> response) {
                if(response.isSuccessful()){
                    Enfermero enfermero= response.body();
                    if(enfermero.getAvatar() != ""){
                        String url="http://192.168.1.15:5000";
                        enfermero.setAvatar(url+enfermero.getAvatar());
                    }
                    usuario.postValue(enfermero);
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Enfermero> call, Throwable t) {
                Log.d("salida falla",t.getMessage());
            }
        });
    }

}