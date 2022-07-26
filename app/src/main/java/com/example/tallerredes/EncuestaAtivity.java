package com.example.tallerredes;

import static com.example.tallerredes.VariablesGlobales.BASE_URL;
import static com.example.tallerredes.VariablesGlobales.isOn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tallerredes.apis.EndpointsPolls;
import com.example.tallerredes.dtos.EncuestaDto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EncuestaAtivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;

    Thread cronos;
    int mili = 0, seg = 0, min = 0;
    Handler h = new Handler();
    FusedLocationProviderClient fusedLocationProviderClient;

    private EndpointsPolls endpointsPolls;
    private Retrofit retrofit;
    private Call<List<EncuestaDto>> getPollsByUserIdApi;
    private int userId;

    private SharedPreferences preferences;
    FloatingActionButton fab_button;
    private TextView tv_Encuesta;
    private ListView lv_AllEncuestas;

    public ArrayAdapter<String> adapter;
    public List<String> encuestasCorrelativos = new ArrayList<>();
    private List<String> detalleEncuestas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        tv_Encuesta = findViewById(R.id.tv1);
        lv_AllEncuestas = findViewById(R.id.list1);
        Switch simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        simpleSwitch.setChecked(isOn);
        fab_button = findViewById(R.id.floatingActionButton3);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        Button btnIngresar = findViewById(R.id.btn_nuevaEncuesta);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpointsPolls = retrofit.create(EndpointsPolls.class);

        userId = preferences.getInt("userId", 0);

        cargarEncuestas();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posicionesDeEncuestas(detalleEncuestas)); // Para ver todas las encuestas del encuestaoor
        //lv_AllEncuestas.setAdapter(adapter);

        lv_AllEncuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_Encuesta.setText(detalleEncuestas.get(position));
            }
        });
        AlertDialog dialogo = new AlertDialog
                .Builder(EncuestaAtivity.this) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EncuestaAtivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Confirmar") // El título
                .setMessage("¿Deseas cerrar Session?") // El mensaje
                .create();
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simpleSwitch.isChecked()) {
                    isOn = true;
                    hilocronometro();
                    iniciarLocalizacion();


                } else {
                    isOn = false;
                }
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOn == true) {
                    Intent intent = new Intent(EncuestaAtivity.this, Formulario.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EncuestaAtivity.this, "Active su GPS", Toast.LENGTH_SHORT).show();
                }

            }
        });
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.show();

            }
        });

    }

    private void cargarEncuestas() {
        getPollsByUserIdApi = endpointsPolls.getAllPollsByUserId(userId);
        getPollsByUserIdApi.enqueue(new Callback<List<EncuestaDto>>() {
            @Override
            public void onResponse(Call<List<EncuestaDto>> call, Response<List<EncuestaDto>> response) {
                if (response.isSuccessful()) {
                    int countPolls = response.body().size();
                    IntStream.range(0, countPolls)
                            .forEach(v -> {
                                encuestasCorrelativos.add(String.format("Encuesta #%s", v));
                            });
                }
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, encuestasCorrelativos); // Para ver todas las encuestas del encuestaoor
                detalleEncuestas = response.body().stream()
                        .map(poll -> poll.toString()).collect(Collectors.toList());
                lv_AllEncuestas.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<EncuestaDto>> call, Throwable t) {

            }
        });

    }

    private void iniciarLocalizacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion localizacion = new Localizacion(preferences);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, localizacion);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, localizacion);

        Log.d("debug", "Localizacion agregada");
    }

    private ArrayList posicionesDeEncuestas(String[] lista) {
        ArrayList posiciones = new ArrayList();
        for (int i = 0; i < lista.length; i++) {
            int x = i + 1;
            posiciones.add("Encuesta #" + x);
        }
        return posiciones;
    }

    private void hilocronometro() {
        cronos = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isOn) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mili++;
                        if (mili == 999) {
                            seg++;
                            mili = 0;
                            System.out.println(min + ":" + seg + ":" + mili);
                            VariablesGlobales.tiempoConexion = min + ":" + seg + ":" + mili;
                        }

                        if (seg == 59) {
                            min++;
                            seg = 0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String m = "", s = "", mi = "";
                                if (mili < 10) {
                                    m = "00" + mili;
                                } else if (mili < 100) {
                                    m = "0" + mili;
                                } else {
                                    m = "" + mili;
                                }
                                if (seg < 10) {
                                    s = "0" + seg;
                                } else {
                                    s = "" + seg;
                                }
                                if (min < 10) {
                                    mi = "0" + min;
                                } else {
                                    mi = "" + min;
                                }

                            }

                        });

                    }

                }
            }
        });
        cronos.start();
    }
}