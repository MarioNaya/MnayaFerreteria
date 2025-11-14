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

    public boolean eliminarArticulo(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        boolean articuloEliminado = false;

        try {
            int filas = db.delete(
                    TABLA_ARTICULOS,
                    "idArticulo = ?",
                    new String[]{id});

            articuloEliminado = (filas > 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return articuloEliminado;
    }

    public boolean registrarArticulo(Articulo articulo){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean articuloRegistrado = false;

        ContentValues values = new ContentValues();
        values.put("nombre",articulo.getNombre());
        values.put("categoria",articulo.getCategoria());
        values.put("descripcion",articulo.getDescripcion());
        values.put("origen",articulo.getOrigen());
        values.put("precio",String.valueOf(articulo.getPrecio()));
        values.put("stock",String.valueOf(articulo.getStock()));

        try{
            int filas = Math.toIntExact(db.insert(
                    TABLA_ARTICULOS,
                    null,
                    values
            ));
            articuloRegistrado = (filas >0);

        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            if (db != null){
                db.close();
            }
        }
        return articuloRegistrado;
    }

    public ArrayList<Usuario> listadoUsuarios(){
        ArrayList<Usuario> resultados = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (db; Cursor cursor = db.rawQuery("SELECT nombre, edad, tipo, usuario FROM " + TABLA_USUARIOS, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario(
                            cursor.getString(cursor.getColumnIndexOrThrow("usuario")),
                            cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                            cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                    );
                    resultados.add(usuario);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {

        }
        return resultados;
    }

    public Usuario recuperarUsuario(String user){
        Usuario usuario = null;

        try (SQLiteDatabase db = getReadableDatabase()) {

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_USUARIOS + " WHERE usuario = ?;", new String[]{user});

            if (cursor.moveToFirst()) {
                usuario = new Usuario(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
            }

            cursor.close();
            return usuario;

        } catch (Exception e) {
            return usuario;
        }
    }

    public boolean actualizarUsuario(Usuario user){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean actualizacionCorrecta = false;

        try {
            ContentValues values = new ContentValues();

            values.put("usuario",user.getUsuario());
            values.put("nombre",user.getNombre());
            values.put("apellidos",user.getApellidos());
            values.put("edad",user.getEdad());
            values.put("pass",user.getPass());
            values.put("tipo",user.getTipo());

            int filas = db.update(
                    TABLA_USUARIOS,
                    values,
                    "usuario = ?",
                    new String[]{String.valueOf(user.getUsuario())}
            );

            actualizacionCorrecta = (filas > 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return actualizacionCorrecta;
    }

    public boolean registrarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean usuarioRegistrado = false;

        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getUsuario());
        values.put("nombre", usuario.getNombre());
        values.put("apellidos", usuario.getApellidos());
        values.put("edad", usuario.getEdad());
        values.put("pass", usuario.getPass());
        values.put("tipo", usuario.getTipo());

        try {
            int filas = Math.toIntExact(db.insert(
                    TABLA_USUARIOS,
                    null,
                    values
            ));
            usuarioRegistrado = (filas > 0);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return usuarioRegistrado;
    }

    public boolean eliminarUsuario(String user){

        SQLiteDatabase db = this.getWritableDatabase();
        boolean usuarioEliminado = false;

        try {
            int filas = db.delete(
                    TABLA_USUARIOS,
                    "usuario = ?",
                    new String[]{user});

            usuarioEliminado = (filas > 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return usuarioEliminado;
    }
}
