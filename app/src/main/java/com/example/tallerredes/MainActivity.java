package com.example.tallerredes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tallerredes.apis.EndpointsPolls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements Comunicacion {
    private ProgressBar pgbEjecutanto;
    private Button btnIngresar;
    private TextView txtUsername, txtPassword;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pgbEjecutanto = findViewById(R.id.pgbEjecutanto);
        btnIngresar = findViewById(R.id.btnIngresar);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TareaAsincrona(MainActivity.this, preferences ).execute(txtUsername.getText().toString(), txtPassword.getText().toString(), 3000);
            }
        });
    }

    @Override
    public void toggleProgressBar(boolean status) {
        if (status) {
            pgbEjecutanto.setVisibility(View.VISIBLE);
        } else {
            pgbEjecutanto.setVisibility(View.GONE);
        }
    }

    @Override
    public void lanzarActividad(Class<?> tipoActividad) {
        Intent intent = new Intent(this, tipoActividad);
        startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
