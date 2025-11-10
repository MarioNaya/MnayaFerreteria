package com.example.mnayaferreteria;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Consultas extends DbHelper{


    Context context;
    public Consultas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Solicita usuario y contraseña para devolver objeto usuario con el nombre, apellidos y nivel de acceso del usuario.
     * @param user string con usuario
     * @param pass string con contraseña
     * @param activity requiere contexto para ejecutar el toast
     * @return usuario con nivel de acceso si el usuario y pass correctos//objeto null si el logado es incorrecto
     */
    public Usuario solicitarAccesoConTipo(String user, String pass, Context activity){

        Usuario nivelAcceso = null;

        try (SQLiteDatabase db = getReadableDatabase()) {

            Cursor cursor = db.rawQuery("SELECT nombre, apellidos, tipo FROM " + TABLA_USUARIOS + " WHERE usuario = ? AND pass = ?;", new String[]{user,pass});

            if (cursor.moveToFirst()) {
                nivelAcceso = new Usuario(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
            }

            cursor.close();
            return nivelAcceso;

        } catch (Exception e) {
            return nivelAcceso;
        }

    }

    public ArrayList<Articulo> ultimosArticulosRegistrados(){
        ArrayList<Articulo> resultados = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, categoria, descripcion FROM " + TABLA_ARTICULOS + " ORDER BY idArticulo LIMIT 3", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Articulo articulo = new Articulo(
                            cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                            cursor.getString(cursor.getColumnIndexOrThrow("categoria")),
                            cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                    );
                    resultados.add(articulo);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return resultados;
    }
}
