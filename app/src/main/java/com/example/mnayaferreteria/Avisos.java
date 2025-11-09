package com.example.mnayaferreteria;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class Avisos {

    public static AlertDialog.Builder campoObligatorio(View campo, Context context){
        AlertDialog.Builder aviso = new AlertDialog.Builder(context);
        aviso.setTitle(R.string.campo_obligatorio_title);
        aviso.setMessage(String.format("El campo %s es obligatorio",campo.getTag()));
        aviso.setCancelable(true);
        return aviso;
    }

    public static AlertDialog.Builder datosAcceso(Context context){
        AlertDialog.Builder aviso = new AlertDialog.Builder(context);
        aviso.setTitle(R.string.datos_acceso_title);
        aviso.setMessage(R.string.datos_acceso_mensaje);
        aviso.setCancelable(true);
        return aviso;
    }
}
