package com.example.atenciondomiciliaria;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atenciondomiciliaria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private ImageView imageView;
    private TextView nombre, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);;

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);
        navigationView.setItemIconSize(90);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_citas, R.id.nav_pacientes, R.id.nav_registros)
                .setOpenableLayout(drawer)
                .build();
        iniciarHeader(binding.navView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void iniciarHeader(NavigationView navView) {
        View header= navView.getHeaderView(0);
        imageView= header.findViewById(R.id.iv_profile);
        nombre= header.findViewById(R.id.tv_name);
        email= header.findViewById(R.id.tv_email);

        viewModel.getUsuario().observe(this, enfermero -> {

            Glide.with(getApplicationContext())
                    .load(enfermero.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            nombre.setText(String.format("%s %s", enfermero.getNombre(), enfermero.getApellido()));
            email.setText(enfermero.getEmail());

        });
        viewModel.recuperarUsuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}