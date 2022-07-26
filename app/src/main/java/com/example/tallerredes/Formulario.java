package com.example.tallerredes;

import static com.example.tallerredes.VariablesGlobales.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tallerredes.apis.EndpointsPolls;
import com.example.tallerredes.dtos.EncuestaDto;
import com.example.tallerredes.dtos.QuestionDto;
import com.example.tallerredes.dtos.ResponsePostPollDto;
import com.example.tallerredes.dtos.ResponseSignInDto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Formulario extends AppCompatActivity {
    private StringBuilder result1;
    private StringBuilder result2;
    private StringBuilder result3;
    private StringBuilder result4;
    private StringBuilder result5;
    private StringBuilder result6;
    private StringBuilder result7;
    private StringBuilder result8;
    private StringBuilder result9;
    private StringBuilder result10;
    private MediaRecorder grabacion;
    private Button btn_recorder;
    private String archivoSalida;
    private String jsonFormulario;
    private String latitud;
    private String longitud;
    private CheckBox Cbx_respuesta1, Cbx_respuesta11,
            Cbx_respuesta2, Cbx_respuesta21, Cbx_respuesta22, Cbx_respuesta23,
            Cbx_respuesta3, Cbx_respuesta31, Cbx_respuesta32, Cbx_respuesta33, Cbx_respuesta34,
            Cbx_respuesta4, Cbx_respuesta41, Cbx_respuesta42, Cbx_respuesta43,
            Cbx_respuesta5, Cbx_respuesta51, Cbx_respuesta52, Cbx_respuesta53,
            Cbx_respuesta6, Cbx_respuesta61, Cbx_respuesta62, Cbx_respuesta63, Cbx_respuesta64,
            Cbx_respuesta7, Cbx_respuesta71,
            Cbx_respuesta8, Cbx_respuesta81, Cbx_respuesta82, Cbx_respuesta83, Cbx_respuesta84, Cbx_respuesta85,
            Cbx_respuesta9, Cbx_respuesta91, Cbx_respuesta92, Cbx_respuesta93, Cbx_respuesta94,
            Cbx_respuesta10, Cbx_respuesta101, Cbx_respuesta102, Cbx_respuesta103;
    private TextView Tv_Estado, tv_pregunta1, tv_pregunta2, tv_pregunta3, tv_pregunta4, tv_pregunta5, tv_pregunta6, tv_pregunta7, tv_pregunta8, tv_pregunta9, tv_pregunta10;
    private EditText Et_nombre, Et_fechaNacimiento, Et_Direccion, Et_Celular;

    private EndpointsPolls endpointsPolls;
    private Retrofit retrofit;
    private Call<ResponsePostPollDto> postPollApi;
    private SharedPreferences accessUserSharedPreferences;
    FloatingActionButton fab_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        fab_button = findViewById(R.id.floatingActionButton4);
        btn_recorder = findViewById(R.id.btn_record);
        Tv_Estado = findViewById(R.id.estado);
        Et_nombre = findViewById(R.id.nombre);
        Et_fechaNacimiento = findViewById(R.id.fechanacimiento);
        Et_Direccion = findViewById(R.id.direccion);
        Et_Celular = findViewById(R.id.celular);
        tv_pregunta1 = findViewById(R.id.pregunta1);
        tv_pregunta2 = findViewById(R.id.pregunta2);
        tv_pregunta3 = findViewById(R.id.pregunta3);
        tv_pregunta4 = findViewById(R.id.pregunta4);
        tv_pregunta5 = findViewById(R.id.pregunta5);
        tv_pregunta6 = findViewById(R.id.pregunta6);
        tv_pregunta7 = findViewById(R.id.pregunta7);
        tv_pregunta8 = findViewById(R.id.pregunta8);
        tv_pregunta9 = findViewById(R.id.pregunta9);
        tv_pregunta10 = findViewById(R.id.pregunta10);


        Cbx_respuesta1 = findViewById(R.id.respuesta1);
        Cbx_respuesta11 = findViewById(R.id.respuesta11);
        Cbx_respuesta2 = findViewById(R.id.respuesta2);
        Cbx_respuesta21 = findViewById(R.id.respuesta21);
        Cbx_respuesta22 = findViewById(R.id.respuesta22);
        Cbx_respuesta23 = findViewById(R.id.respuesta23);
        Cbx_respuesta3 = findViewById(R.id.respuesta3);
        Cbx_respuesta31 = findViewById(R.id.respuesta31);
        Cbx_respuesta32 = findViewById(R.id.respuesta32);
        Cbx_respuesta33 = findViewById(R.id.respuesta33);
        Cbx_respuesta34 = findViewById(R.id.respuesta34);
        Cbx_respuesta4 = findViewById(R.id.respuesta4);
        Cbx_respuesta41 = findViewById(R.id.respuesta41);
        Cbx_respuesta42 = findViewById(R.id.respuesta42);
        Cbx_respuesta43 = findViewById(R.id.respuesta43);
        Cbx_respuesta5 = findViewById(R.id.respuesta5);
        Cbx_respuesta51 = findViewById(R.id.respuesta51);
        Cbx_respuesta52 = findViewById(R.id.respuesta52);
        Cbx_respuesta53 = findViewById(R.id.respuesta53);
        Cbx_respuesta6 = findViewById(R.id.respuesta6);
        Cbx_respuesta61 = findViewById(R.id.respuesta61);
        Cbx_respuesta62 = findViewById(R.id.respuesta62);
        Cbx_respuesta63 = findViewById(R.id.respuesta63);
        Cbx_respuesta64 = findViewById(R.id.respuesta64);
        Cbx_respuesta7 = findViewById(R.id.respuesta7);
        Cbx_respuesta71 = findViewById(R.id.respuesta71);
        Cbx_respuesta8 = findViewById(R.id.respuesta8);
        Cbx_respuesta81 = findViewById(R.id.respuesta81);
        Cbx_respuesta82 = findViewById(R.id.respuesta82);
        Cbx_respuesta83 = findViewById(R.id.respuesta83);
        Cbx_respuesta84 = findViewById(R.id.respuesta84);
        Cbx_respuesta85 = findViewById(R.id.respuesta85);
        Cbx_respuesta9 = findViewById(R.id.respuesta9);
        Cbx_respuesta91 = findViewById(R.id.respuesta91);
        Cbx_respuesta92 = findViewById(R.id.respuesta92);
        Cbx_respuesta93 = findViewById(R.id.respuesta93);
        Cbx_respuesta94 = findViewById(R.id.respuesta94);
        Cbx_respuesta10 = findViewById(R.id.respuesta10);
        Cbx_respuesta101 = findViewById(R.id.respuesta101);
        Cbx_respuesta102 = findViewById(R.id.respuesta102);
        Cbx_respuesta103 = findViewById(R.id.respuesta103);

        AlertDialog dialogo = new AlertDialog
                .Builder(Formulario.this) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Formulario.this, MainActivity.class);
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
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.show();

            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Formulario.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        accessUserSharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpointsPolls = retrofit.create(EndpointsPolls.class);

    }


    public String convertAudioToBase64() {
        String encoded = "";

        try {
            File file = new File(archivoSalida);
            byte[] bytes = FileUtils.readFileToByteArray(file);
            encoded = Base64.encodeToString(bytes, 0);
        } catch (IOException e) {
            encoded = null;
        } finally {
            return encoded;
        }

    }

    public void Recorder(View view) {
        if (grabacion == null) {
            archivoSalida = getExternalFilesDir(null).getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);
            try {
                grabacion.prepare();
                grabacion.start();
                Tv_Estado.setText("Grabando..");
            } catch (IOException e) {

            }
            btn_recorder.setBackgroundResource(R.drawable.rec);

            Toast.makeText(getApplicationContext(), "Grabando..", Toast.LENGTH_SHORT).show();
        } else if (grabacion != null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            Tv_Estado.setText("Grabacion Finalizada.");
            Toast.makeText(getApplicationContext(), "Grabacion finalizada.", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducir(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e) {

        }
        mediaPlayer.start();
        Tv_Estado.setText("Reproduciendo..");
        Toast.makeText(getApplicationContext(), "Reproduciendo audio..", Toast.LENGTH_SHORT).show();
    }

    public void enviardatos(View view) {
        try {
            try {
                result1 = new StringBuilder();
                result1.append("Respuestas1:");
                if (Cbx_respuesta1.isChecked()) {
                    result1.append("\n" + Cbx_respuesta1.getText() + "\n");
                }
                if (Cbx_respuesta11.isChecked()) {
                    result1.append("\n" + Cbx_respuesta11.getText() + "\n");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result2 = new StringBuilder();
                result2.append("Respuestas2:");
                if (Cbx_respuesta2.isChecked()) {
                    result2.append("\n" + Cbx_respuesta2.getText() + "\n");
                }
                if (Cbx_respuesta21.isChecked()) {
                    result2.append("\n" + Cbx_respuesta21.getText() + "\n");
                }
                if (Cbx_respuesta22.isChecked()) {
                    result2.append("\n" + Cbx_respuesta22.getText() + "\n");
                }
                if (Cbx_respuesta23.isChecked()) {
                    result2.append("\n" + Cbx_respuesta23.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result3 = new StringBuilder();
                result3.append("Respuestas3:");
                if (Cbx_respuesta1.isChecked()) {
                    result3.append("\n" + Cbx_respuesta3.getText() + "\n");
                }
                if (Cbx_respuesta31.isChecked()) {
                    result3.append("\n" + Cbx_respuesta31.getText() + "\n");
                }
                if (Cbx_respuesta32.isChecked()) {
                    result3.append("\n" + Cbx_respuesta32.getText() + "\n");
                }
                if (Cbx_respuesta33.isChecked()) {
                    result3.append("\n" + Cbx_respuesta33.getText() + "\n");
                }
                if (Cbx_respuesta34.isChecked()) {
                    result3.append("\n" + Cbx_respuesta34.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result4 = new StringBuilder();
                result4.append("Respuestas4:");
                if (Cbx_respuesta4.isChecked()) {
                    result4.append("\n" + Cbx_respuesta4.getText() + "\n");
                }
                if (Cbx_respuesta41.isChecked()) {
                    result4.append("\n" + Cbx_respuesta41.getText() + "\n");
                }
                if (Cbx_respuesta42.isChecked()) {
                    result4.append("\n" + Cbx_respuesta42.getText() + "\n");
                }
                if (Cbx_respuesta43.isChecked()) {
                    result4.append("\n" + Cbx_respuesta43.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result5 = new StringBuilder();
                result5.append("Respuestas5:");
                if (Cbx_respuesta5.isChecked()) {
                    result5.append("\n" + Cbx_respuesta5.getText() + "\n");
                }
                if (Cbx_respuesta51.isChecked()) {
                    result5.append("\n" + Cbx_respuesta51.getText() + "\n");
                }
                if (Cbx_respuesta52.isChecked()) {
                    result5.append("\n" + Cbx_respuesta52.getText() + "\n");
                }
                if (Cbx_respuesta53.isChecked()) {
                    result5.append("\n" + Cbx_respuesta53.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result6 = new StringBuilder();
                result6.append("Respuestas6:");
                if (Cbx_respuesta6.isChecked()) {
                    result6.append("\n" + Cbx_respuesta6.getText() + "\n");
                }
                if (Cbx_respuesta61.isChecked()) {
                    result6.append("\n" + Cbx_respuesta61.getText() + "\n");
                }
                if (Cbx_respuesta62.isChecked()) {
                    result6.append("\n" + Cbx_respuesta62.getText() + "\n");
                }
                if (Cbx_respuesta63.isChecked()) {
                    result6.append("\n" + Cbx_respuesta63.getText() + "\n");
                }
                if (Cbx_respuesta64.isChecked()) {
                    result6.append("\n" + Cbx_respuesta64.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result7 = new StringBuilder();
                result7.append("Respuestas7:");
                if (Cbx_respuesta7.isChecked()) {
                    result7.append("\n" + Cbx_respuesta7.getText() + "\n");
                }
                if (Cbx_respuesta71.isChecked()) {
                    result7.append("\n" + Cbx_respuesta71.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result8 = new StringBuilder();
                result8.append("Respuestas8:");
                if (Cbx_respuesta8.isChecked()) {
                    result8.append("\n" + Cbx_respuesta8.getText() + "\n");
                }
                if (Cbx_respuesta81.isChecked()) {
                    result8.append("\n" + Cbx_respuesta81.getText() + "\n");
                }
                if (Cbx_respuesta82.isChecked()) {
                    result8.append("\n" + Cbx_respuesta82.getText() + "\n");
                }
                if (Cbx_respuesta83.isChecked()) {
                    result8.append("\n" + Cbx_respuesta83.getText() + "\n");
                }
                if (Cbx_respuesta84.isChecked()) {
                    result8.append("\n" + Cbx_respuesta84.getText() + "\n");
                }
                if (Cbx_respuesta85.isChecked()) {
                    result8.append("\n" + Cbx_respuesta85.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result9 = new StringBuilder();
                result9.append("Respuestas9:");
                if (Cbx_respuesta9.isChecked()) {
                    result9.append("\n" + Cbx_respuesta9.getText() + "\n");
                }
                if (Cbx_respuesta91.isChecked()) {
                    result9.append("\n" + Cbx_respuesta91.getText() + "\n");
                }
                if (Cbx_respuesta92.isChecked()) {
                    result9.append("\n" + Cbx_respuesta92.getText() + "\n");
                }
                if (Cbx_respuesta93.isChecked()) {
                    result9.append("\n" + Cbx_respuesta93.getText() + "\n");
                }
                if (Cbx_respuesta94.isChecked()) {
                    result9.append("\n" + Cbx_respuesta94.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result10 = new StringBuilder();
                result10.append("Respuestas10:");
                if (Cbx_respuesta10.isChecked()) {
                    result10.append("\n" + Cbx_respuesta10.getText() + "\n");
                }
                if (Cbx_respuesta101.isChecked()) {
                    result10.append("\n" + Cbx_respuesta101.getText() + "\n");
                }
                if (Cbx_respuesta102.isChecked()) {
                    result10.append("\n" + Cbx_respuesta102.getText() + "\n");
                }
                if (Cbx_respuesta103.isChecked()) {
                    result10.append("\n" + Cbx_respuesta103.getText() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EncuestaDto encuestaDto = new EncuestaDto()
                .setFullname(Et_nombre.getText().toString().trim())
                .setDatebirth(Et_fechaNacimiento.getText().toString().trim())
                .setAddress(Et_Direccion.getText().toString().trim())
                .setPhoneNumber(Et_Celular.getText().toString().trim())
                .setUserId(accessUserSharedPreferences.getInt("userId", 0))
                .setAudioEncode(convertAudioToBase64())
                .setQuestions(Arrays.asList(
                        new QuestionDto()
                                .setQuestionName("Pregunta 1 - " + tv_pregunta1.getText().toString())
                                .setAnswer(result1.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 2 - " + tv_pregunta2.getText().toString())
                                .setAnswer(result2.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 3 - " + tv_pregunta3.getText().toString())
                                .setAnswer(result3.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 4 - " + tv_pregunta4.getText().toString())
                                .setAnswer(result4.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 5 - " + tv_pregunta5.getText().toString())
                                .setAnswer(result5.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 6 - " + tv_pregunta6.getText().toString())
                                .setAnswer(result6.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 7 - " + tv_pregunta7.getText().toString())
                                .setAnswer(result7.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 8 - " + tv_pregunta8.getText().toString())
                                .setAnswer(result8.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 9 - " + tv_pregunta9.getText().toString())
                                .setAnswer(result9.toString()),
                        new QuestionDto()
                                .setQuestionName("Pregunta 10 - " + tv_pregunta10.getText().toString())
                                .setAnswer(result10.toString())
                ));

        postPollApi = endpointsPolls.postPoll(encuestaDto);
        postPollApi.enqueue(new Callback<ResponsePostPollDto>() {
            @Override
            public void onResponse(Call<ResponsePostPollDto> call, Response<ResponsePostPollDto> response) {
                if (response.isSuccessful()) {
                    if (response.body().success) {
                        Toast.makeText(Formulario.this, "Encuesta registrada correctamente", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(Formulario.this, EncuestaAtivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Formulario.this, "Ocurrio un error al registrar la encuesta", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostPollDto> call, Throwable t) {

            }
        });


        /*System.out.println("FORMULARIO JSON");
        jsonFormulario = "" + Et_nombre.getText() + "," + Et_fechaNacimiento.getText() + "," + Et_Direccion.getText() + "," + Et_Celular.getText() + "," + Et_pregunta1.getText() + "," + Et_pregunta2.getText() + "," + Et_pregunta3.getText() + "," + Et_pregunta4.getText();
        System.out.println(jsonFormulario);
        System.out.println(archivoSalida);*/

    }

}