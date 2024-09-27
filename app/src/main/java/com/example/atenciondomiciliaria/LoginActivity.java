package com.example.atenciondomiciliaria;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.atenciondomiciliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inicializar();
    }

     private void inicializar(){
        loginViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensaje.setText(s);
                binding.tvMensaje.setVisibility(View.VISIBLE);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvMensaje.setText("");
                binding.tvMensaje.setVisibility(View.GONE);
                loginViewModel.autenticar(binding.etEmail.getText().toString(), binding.etPass.getText().toString());
                binding.etEmail.setText("");
                binding.etPass.setText("");
            }
        });
         String text = "¿No tienes una cuenta? Regístrate";
         SpannableString spannableString = new SpannableString(text);

         ClickableSpan clickableSpan= new ClickableSpan() {

             @Override
             public void onClick(@NonNull View widget) {
                 Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }

             @Override
             public  void updateDrawState(@NonNull TextPaint ds) {
                 super.updateDrawState(ds);
                 ds.setUnderlineText(true);
             }
         };
         spannableString.setSpan(clickableSpan, 23, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
         binding.tvRegistrar.setText(spannableString);
         binding.tvRegistrar.setMovementMethod(LinkMovementMethod.getInstance());;
     }
     
}