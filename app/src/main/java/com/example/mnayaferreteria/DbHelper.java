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
            "('Fregona de microfibra', 'menaje', 'Fregona con cabezal de microfibra absorbente y palo telescópico', 12.75, 55, 'internacional'), " +
            "('Sierra de mano 18 pulgadas', 'herramientas', 'Sierra profesional con hoja de acero al carbono y empuñadura antideslizante', 22.75, 35, 'nacional'), " +
            "('Llave inglesa ajustable 12\"', 'herramientas', 'Llave ajustable cromada con apertura máxima de 30mm', 18.90, 50, 'internacional'), " +
            "('Nivel de burbuja 60cm', 'herramientas', 'Nivel de aluminio con 3 burbujas de precisión', 24.50, 40, 'internacional'), " +
            "('Alicates universales 8\"', 'herramientas', 'Alicates de corte con mango aislado y acero forjado', 14.25, 55, 'nacional'), " +
            "('Cinta métrica 5m', 'herramientas', 'Flexómetro con cinta de acero y freno automático', 8.99, 80, 'internacional'), " +
            "('Recogedor con cepillo', 'menaje', 'Set de recogedor metálico con cepillo de cerdas suaves', 7.50, 85, 'nacional'), " +
            "('Tendedero plegable aluminio', 'menaje', 'Tendedero extensible de aluminio con capacidad para 20m de ropa', 34.99, 20, 'internacional'), " +
            "('Escurridor de acero inoxidable', 'menaje', 'Escurridor resistente con base antideslizante y mangos ergonómicos', 13.90, 45, 'internacional'), " +
            "('Perchero de pared 6 ganchos', 'menaje', 'Perchero metálico con 6 ganchos dobles y tornillería incluida', 18.50, 35, 'nacional'), " +
            "('Papelera metálica 30L', 'menaje', 'Papelera con pedal, tapa silenciosa y cubo interior extraíble', 29.99, 25, 'internacional'), " +
            "('Tabla de planchar reforzada', 'menaje', 'Tabla con estructura metálica, altura regulable y funda acolchada', 42.50, 15, 'internacional'), " +
            "('Cinta métrica 5m', 'herramientas', 'Flexómetro con cinta de acero y freno automático', 8.99, 80, 'internacional'), " +
            "('Juego de llaves Allen', 'herramientas', 'Set de 9 llaves Allen métricas de 1.5mm a 10mm', 11.50, 65, 'internacional'), " +
            "('Tabla de planchar reforzada', 'menaje', 'Tabla con estructura metálica, altura regulable y funda acolchada', 42.50, 15, 'internacional'), " +
            "('Downlight empotrable LED 12W', 'iluminación', 'Foco empotrable extraplano con driver incluido, luz cálida', 14.75, 85, 'nacional'), s" +
            "('Lámpara de mesa escritorio LED', 'iluminación', 'Flexo LED regulable con brazo articulado y puerto USB de carga', 36.50, 35, 'internacional'), " +
            "('Bombilla inteligente WiFi E27', 'iluminación', 'Bombilla LED controlable por app, compatible con Alexa y Google', 18.90, 70, 'internacional'), " +
            "('Baliza solar jardín pack 6', 'iluminación', 'Luces solares para caminos con estaca, encendido automático', 29.99, 45, 'internacional'), " +
            "('Guirnalda LED exterior 10m', 'iluminación', 'Guirnalda con 20 bombillas LED, cable negro resistente a intemperie', 24.90, 38, 'nacional'), " +
            "('Aplique solar con sensor', 'iluminación', 'Lámpara solar de pared con detector de movimiento 180°', 26.75, 42, 'internacional'), " +
            "('Rastrillo metálico 14 púas', 'jardín', 'Rastrillo forjado con mango de madera y refuerzo metálico', 16.75, 40, 'nacional'), " +
            "('Macetas de terracota set 3 unidades', 'jardín', 'Macetas de barro cocido con plato, tamaños 20-25-30cm', 18.99, 35, 'nacional'), " +
            "('Pulverizador presión 5L', 'jardín', 'Pulverizador manual con lanza ajustable y correa para hombro', 24.50, 30, 'internacional'), " +
            "('Azada de jardín mango largo', 'jardín', 'Azada forjada con mango de madera de 120cm, ideal para cavar', 19.90, 38, 'nacional'), " +
            "('Guantes de jardinería reforzados', 'jardín', 'Guantes con protección en palma y dedos, talla universal', 8.50, 80, 'internacional'), " +
            "('Carretilla de jardín 60L', 'jardín', 'Carretilla metálica con rueda neumática y estructura galvanizada', 45.00, 18, 'internacional'), " +
            "('Sistema de riego por goteo 15m', 'jardín', 'Kit completo con goteros ajustables y temporizador programable', 35.75, 25, 'internacional'), " +
            "('Panel LED rectangular 120x30', 'iluminación', 'Panel ultrafino 40W luz fría 6500K con marco blanco', 48.00, 20, 'internacional'), " +
            "('Cesto de ropa 50L', 'menaje', 'Cesto plegable con asas reforzadas y tela transpirable', 19.25, 40, 'internacional'), " +
            "('Cortaalambres profesional', 'herramientas', 'Tenaza cortacables con mangos ergonómicos y acero templado', 19.99, 30, 'nacional');";

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
