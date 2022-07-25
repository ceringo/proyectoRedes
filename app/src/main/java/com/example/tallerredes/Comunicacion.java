package com.example.tallerredes;

public interface Comunicacion {
    void toggleProgressBar(boolean status);

    void lanzarActividad(Class<?> tipoActividad);

    void showMessage(String msg);
}
