package com.example.mnayaferreteria;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Consultas extends DbHelper{


    Context context;
    public Consultas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Solicita usuario y contraseña para devolver string con el nivel de acceso del usuario.
     * @param user string con usuario
     * @param pass string con contraseña
     * @param activity requiere contexto para ejecutar el toast
     * @return string con nivel de acceso si el usuario y pass correctos//string null si el logado es incorrecto
     */
    public String solicitarTipoUsuario(String user, String pass, Context activity){

        String nivelAcceso = null;

        try (SQLiteDatabase db = getReadableDatabase()) {

            Cursor cursor = db.rawQuery("SELECT tipo FROM " + TABLA_USUARIOS + " WHERE usuario = ? AND pass = ?;", new String[]{user,pass});

            if (cursor.moveToFirst()) {
                nivelAcceso = cursor.getString(0);
            }

            cursor.close();
            return nivelAcceso;

        } catch (Exception e) {
            Toast.makeText(activity,"Datos incorrectos",Toast.LENGTH_LONG).show();
            return nivelAcceso;
        }

    }
}
