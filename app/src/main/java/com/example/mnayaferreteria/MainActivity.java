package com.example.mnayaferreteria;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText user = findViewById(R.id.campoUsuario);
        EditText pass = findViewById(R.id.campoPass);
        Button acceso = findViewById(R.id.btnAcceder);
        LinearLayout layout = findViewById(R.id.layoutLogin);

        Bundle bundle = new Bundle();

        acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Validaciones.comruebaCamposVacios(layout,MainActivity.this)){
                    return;
                }

                Consultas consulta = new Consultas(MainActivity.this);
                Usuario usuario = consulta.solicitarAccesoConTipo(user.getText().toString(), pass.getText().toString(), MainActivity.this);
                if (usuario != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("nombre", usuario.getNombre());
                    bundle.putString("apellidos",usuario.getApellidos());
                    bundle.putString("tipo",usuario.getTipo());
                    Intent i = new Intent(MainActivity.this, Principal.class);
                    startActivity(i);
                } else {
                    String titulo = getString(R.string.datos_acceso_title);
                    String mensaje = getString(R.string.datos_acceso_mensaje);
                    Avisos.avisoSinBotones(MainActivity.this,titulo,mensaje).show();
                    Utilidades.limpiaCampos(layout);
                }
            }
        });
    }
}