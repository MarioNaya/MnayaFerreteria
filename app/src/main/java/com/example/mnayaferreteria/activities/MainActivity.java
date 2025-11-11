package com.example.mnayaferreteria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mnayaferreteria.utilidades.Avisos;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.model.Usuario;
import com.example.mnayaferreteria.utilidades.Utilidades;
import com.example.mnayaferreteria.utilidades.Validaciones;

public class MainActivity extends BaseActivity {

    EditText user;
    EditText pass;
    Button acceso;
    LinearLayout layout;

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



        if (getSession().haySesionActiva()){
            irAPantallaPrincipal();
            return;
        }

        user = findViewById(R.id.campoUsuario);
        pass = findViewById(R.id.campoPass);
        acceso = findViewById(R.id.btnAcceder);
        layout = findViewById(R.id.layoutLogin);



        acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Validaciones.comruebaCamposVacios(layout,MainActivity.this)){
                    return;
                }

                Usuario usuario = null;
                try (Consultas consulta = new Consultas(MainActivity.this)) {
                    usuario = consulta.solicitarAccesoConTipo(user.getText().toString(), pass.getText().toString(), MainActivity.this);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }

                if (validarCredenciales(usuario)){
                    String nombre = usuario.getNombre();
                    String tipo = usuario.getTipo();

                    getSession().guardarSesion(nombre,tipo);

                    irAPantallaPrincipal();

                } else {
                    String titulo = getString(R.string.datos_acceso_title);
                    String mensaje = getString(R.string.datos_acceso_mensaje);
                    Avisos.avisoSinBotones(MainActivity.this,titulo,mensaje).show();
                    Utilidades.limpiaCampos(layout);
                }
            }
        });
    }

    private void irAPantallaPrincipal() {
        Intent intent = new Intent(MainActivity.this, Principal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private boolean validarCredenciales(Usuario usuario) {

        return usuario != null;
    }
}