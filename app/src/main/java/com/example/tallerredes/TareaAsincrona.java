package com.example.tallerredes;

import static com.example.tallerredes.VariablesGlobales.BASE_URL;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.tallerredes.apis.EndpointsPolls;
import com.example.tallerredes.dtos.SignInDtoResponse;
import com.example.tallerredes.dtos.SignInDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TareaAsincrona extends AsyncTask<Object, Void, Boolean> {
    private Comunicacion comunicacion;

    private EndpointsPolls endpointsPolls;
    private Retrofit retrofit;
    private Call<SignInDtoResponse> authenticateCallApi;
    private SharedPreferences accessUserSharedPreferences;

    public TareaAsincrona(Comunicacion comunicacion, SharedPreferences preferences) {
        this.comunicacion = comunicacion;
        this.accessUserSharedPreferences = preferences;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpointsPolls = retrofit.create(EndpointsPolls.class);
    }


    @Override
    protected void onPreExecute() {
        comunicacion.toggleProgressBar(true);
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        try {
            Thread.sleep((int) objects[2]);
            String user = (String) objects[0];
            String pass = (String) objects[1];

            SignInDto params = new SignInDto()
                    .setEmail(user)
                    .setPassword(pass);

            authenticateCallApi = endpointsPolls.signIn(params, 1);
            authenticateCallApi.enqueue(new Callback<SignInDtoResponse>() {
                @Override
                public void onResponse(Call<SignInDtoResponse> call, Response<SignInDtoResponse> response) {

                    if (response.isSuccessful()) {

                        accessUserSharedPreferences.edit()
                                .putBoolean("auth", true)
                                .putInt("userId", (int) response.body().getUserId())
                                .putInt("pollsterId", (int) response.body()
                                        .getData()
                                        .getPollsterId())
                                .commit();

                    } else {
                        accessUserSharedPreferences.edit()
                                .putBoolean("auth", false)
                                .commit();

                    }


                }

                @Override
                public void onFailure(Call<SignInDtoResponse> call, Throwable t) {

                }
            });
            /*if (user.equals("admin") && pass.equals("admin")) {
                return true;
            } else {
                return true;
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean bo) {
        if (accessUserSharedPreferences.getBoolean("auth", false)) {
            comunicacion.lanzarActividad(HuellaActivity.class);
            comunicacion.showMessage("Datos Correctos");
            comunicacion.toggleProgressBar(false);
        } else {
            comunicacion.showMessage("Datos Incorrectos");

        }
        /*if (bo) {
            this.comunicacion.lanzarActividad(HuellaActivity.class);
            this.comunicacion.showMessage("Datos Correctos");
            this.comunicacion.toggleProgressBar(false);
        } else {
            this.comunicacion.showMessage("Datos Incorrectos");

        }*/
    }


}
