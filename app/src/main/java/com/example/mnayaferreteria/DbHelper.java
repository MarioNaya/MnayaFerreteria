package com.example.mnayaferreteria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    Context context;

    public static final int VERSION_DB = 3;
    public static final String NOMBRE_DB = "ferreteria.db";
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_ARTICULOS = "articulos";

    public DbHelper(@Nullable Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    String crea_tabla_usuarios = "CREATE TABLE " + TABLA_USUARIOS +
            "(usuario TEXT PRIMARY KEY, " +
            "nombre TEXT NOT NULL, " +
            "apellidos TEXT NOT NULL, " +
            "edad INTEGER NOT NULL, " +
            "pass TEXT NOT NULL, " +
            "tipo TEXT NOT NULL);";

    String crea_tabla_articulos = "CREATE TABLE " + TABLA_ARTICULOS +
            "(idArticulo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "categoria TEXT NOT NULL, " +
            "descripcion TEXT NOT NULL, " +
            "precio NUMERIC NOT NULL, " +
            "stock INTEGER NOT NULL, " +
            "origen TEXT NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(crea_tabla_usuarios);
        db.execSQL(crea_tabla_articulos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + "ususarios");
        db.execSQL("DROP TABLE " + TABLA_ARTICULOS);
        onCreate(db);
    }
}
