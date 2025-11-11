package com.example.mnayaferreteria.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.mnayaferreteria.model.Articulo;
import com.example.mnayaferreteria.model.Usuario;

import java.util.ArrayList;

public class Consultas extends DbHelper {


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

        try (db; Cursor cursor = db.rawQuery("SELECT nombre, categoria, descripcion FROM " + TABLA_ARTICULOS + " ORDER BY idArticulo DESC LIMIT 3", null)) {
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
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return resultados;
    }

    public ArrayList<Articulo> listadoArticulos(String categoria){
        ArrayList<Articulo> resultados = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (db; Cursor cursor = db.rawQuery("SELECT nombre, precio, stock FROM " + TABLA_ARTICULOS + " WHERE 1" + categoria, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Articulo articulo = new Articulo(
                            cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("stock"))
                    );
                    resultados.add(articulo);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {

        }
        return resultados;
    }

    public Articulo articuloPorNombre(String nombre){

        Articulo articulo = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_ARTICULOS + " WHERE nombre = ?", new String[]{nombre});
        if (cursor.moveToFirst()){
            articulo = new Articulo(cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    cursor.getString(cursor.getColumnIndexOrThrow("categoria")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("stock")),
                    cursor.getString(cursor.getColumnIndexOrThrow("origen")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idArticulo"))
            );
        }
        cursor.close();
        db.close();
        return articulo;
    }

    public boolean actualizarArticulo(Articulo articulo){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean actualizacionCorrecta = false;

        try {
            ContentValues values = new ContentValues();

            values.put("nombre",articulo.getNombre());
            values.put("categoria",articulo.getCategoria());
            values.put("descripcion",articulo.getDescripcion());
            values.put("precio",articulo.getPrecio());
            values.put("stock",articulo.getStock());
            values.put("origen",articulo.getOrigen());

            int filas = db.update(
                    TABLA_ARTICULOS,
                    values,
                    "idArticulo = ?",
                    new String[]{String.valueOf(articulo.getIdArticulo())}
            );

            actualizacionCorrecta = (filas > 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return actualizacionCorrecta;
    }
}
