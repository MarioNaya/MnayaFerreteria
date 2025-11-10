package com.example.mnayaferreteria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    Context context;

    public static final int VERSION_DB = 1;
    public static final String NOMBRE_DB = "ferreteria.db";
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_ARTICULOS = "articulos";

    public DbHelper(@Nullable Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    String crea_tabla_usuarios = "CREATE TABLE " + TABLA_USUARIOS +
            " (usuario TEXT PRIMARY KEY, " +
            "nombre TEXT NOT NULL, " +
            "apellidos TEXT NOT NULL, " +
            "edad INTEGER NOT NULL, " +
            "pass TEXT NOT NULL, " +
            "tipo TEXT NOT NULL);";

    String crea_tabla_articulos = "CREATE TABLE " + TABLA_ARTICULOS +
            " (idArticulo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "categoria TEXT NOT NULL, " +
            "descripcion TEXT NOT NULL, " +
            "precio NUMERIC NOT NULL, " +
            "stock INTEGER NOT NULL, " +
            "origen TEXT NOT NULL);";

    String insertar_usuarios = "INSERT INTO usuarios (usuario, nombre, apellidos, edad, pass, tipo) VALUES " +
            "('admin', 'Carlos', 'González Ruiz', 35, 'admin', 'admin'), " +
            "('user', 'Juan', 'Pérez López', 28, 'user', 'user')";

    String insertar_articulos = "INSERT INTO articulos (nombre, categoria, descripcion, precio, stock, origen) VALUES " +
            "('Foco LED exterior 50W', 'iluminación', 'Proyector LED con sensor de movimiento, IP65 resistente al agua', 32.90, 40, 'internacional'), " +
            "('Plafón LED circular 24W', 'iluminación', 'Plafón de techo luz neutra 4000K con difusor opalino', 28.50, 50, 'internacional'), " +
            "('Tira LED RGB 5m', 'iluminación', 'Tira LED multicolor con mando a distancia y fuente de alimentación', 22.99, 60, 'internacional'), " +
            "('Manguera de riego 25m', 'jardín', 'Manguera extensible con conector rápido y pistola de riego multifunción', 28.50, 45, 'internacional'), " +
            "('Tijeras de podar profesionales', 'jardín', 'Tijeras con hoja de acero inoxidable y mango ergonómico antideslizante', 22.90, 55, 'nacional'), " +
            "('Cortacésped eléctrico 1200W', 'jardín', 'Cortacésped con ancho de corte 32cm y cesto recolector de 30L', 125.00, 12, 'internacional'), " +
            "('Martillo de carpintero', 'herramientas', 'Martillo profesional con mango de madera reforzado, cabeza de acero templado', 15.99, 45, 'nacional'),\n" +
            "('Destornillador de precisión set 6 piezas', 'herramientas', 'Juego de destornilladores de precisión con puntas intercambiables y mango ergonómico', 12.50, 60, 'internacional'),\n" +
            "('Taladro eléctrico 650W', 'herramientas', 'Taladro percutor con regulador de velocidad y maletín de transporte', 89.99, 25, 'internacional'), " +
            "('Cubo de zinc galvanizado 12L', 'menaje', 'Cubo resistente de zinc con asa reforzada, ideal para limpieza', 16.50, 40, 'nacional'), " +
            "('Escoba de cerdas duras', 'menaje', 'Escoba con cerdas sintéticas resistentes y mango de madera de 130cm', 9.99, 70, 'nacional'), " +
            "('Fregona de microfibra', 'menaje', 'Fregona con cabezal de microfibra absorbente y palo telescópico', 12.75, 55, 'internacional')";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(crea_tabla_usuarios);
        db.execSQL(crea_tabla_articulos);
        db.execSQL(insertar_usuarios);
        db.execSQL(insertar_articulos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE " + TABLA_ARTICULOS);
        onCreate(db);
    }
}
