package com.example.tallerredes;

import static com.example.tallerredes.VariablesGlobales.BASE_URL;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.example.tallerredes.apis.EndpointsPolls;
import com.example.tallerredes.dtos.PutLocationDtoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Localizacion implements LocationListener {


    private SharedPreferences preferences;

    private EndpointsPolls endpointsPolls;
    private Retrofit retrofit;
    private Call<PutLocationDtoResponse> putLocationApi;

    private int pollsterId;

    public Localizacion(SharedPreferences sharedPreferences) {
        this.preferences = sharedPreferences;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpointsPolls = retrofit.create(EndpointsPolls.class);

        pollsterId = preferences.getInt("pollsterId", 0);

    }

    @Override
    public void onLocationChanged(Location location) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas


        String texto = "Mi ubicación es: \n" +
                "Latitud = " + location.getLatitude() + "\n" +
                "Longitud = " + location.getLongitude();
        VariablesGlobales.latitud = String.valueOf(location.getLatitude());
        VariablesGlobales.longitud = String.valueOf(location.getLongitude());
       // System.out.println(texto);

        preferences.edit()
                .putFloat("latitude",(float)location.getLatitude())
                .putFloat("longitude",(float)location.getLongitude())
                .commit();

        putLocationApi = endpointsPolls.putLocation(pollsterId, Float.parseFloat(VariablesGlobales.latitud), Float.parseFloat(VariablesGlobales.longitud));
        putLocationApi.enqueue(new Callback<PutLocationDtoResponse>() {
            @Override
            public void onResponse(Call<PutLocationDtoResponse> call, Response<PutLocationDtoResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("ENDPOINT LOCATION: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<PutLocationDtoResponse> call, Throwable t) {
                Log.i("ENDPOINT LOCATION ERROR: ", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("GPS Activado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS Desactivado");
    }
}
