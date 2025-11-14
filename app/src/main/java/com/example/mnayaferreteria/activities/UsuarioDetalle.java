package com.example.mnayaferreteria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.model.Usuario;
import com.example.mnayaferreteria.utilidades.Validaciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class UsuarioDetalle extends BaseActivity {

    // ✅ SOLO DECLARAR los campos, NO inicializarlos aquí
    private String tipoUsuario;
    private TextView userText;
    private EditText nom;
    private EditText ape;
    private EditText eda;
    private EditText pass;
    private Spinner tip;
    private FloatingActionButton edit;
    private FloatingActionButton delete;
    private FloatingActionButton send;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarDetUser));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tipoUsuario = getSession().getTipo();
        userText = findViewById(R.id.textUsuario);
        nom = findViewById(R.id.editTextNombre);
        ape = findViewById(R.id.editTextApellidos);
        eda = findViewById(R.id.editTextEdad);
        pass = findViewById(R.id.editTextPass);
        tip = findViewById(R.id.spinnerDetUser);
        edit = findViewById(R.id.floatingEditUser);
        delete = findViewById(R.id.floatingDeleteUser);
        send = findViewById(R.id.floatingEnviar);
        layout = findViewById(R.id.layoutDetalles);

        Usuario usuario;

        Intent intent = getIntent();
        String userName = intent.getStringExtra("usuario");

        userText.setText(userName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);  // ← Usa TU layout dropdown
        tip.setAdapter(adapter);

        tip.setEnabled(false);

        try {
            Consultas consulta = new Consultas(UsuarioDetalle.this);
            usuario = consulta.recuperarUsuario(userName);
        } catch (Exception e){
            throw new RuntimeException();
        }

        nom.setText(usuario.getNombre());
        ape.setText(usuario.getApellidos());
        eda.setText(String.valueOf(usuario.getEdad()));
        pass.setText(usuario.getPass());

        String tipUser = usuario.getTipo();
        android.util.Log.d("UsuarioDetalle", "Tipo usuario DB: '" + tipUser + "'");

        if (tipUser != null) {
            tipUser = tipUser.trim();

            for (int i = 0; i < tip.getCount(); i++) {
                String itemSpinner = tip.getItemAtPosition(i).toString().trim();
                android.util.Log.d("UsuarioDetalle", "Item spinner " + i + ": '" + itemSpinner + "'");

                if (itemSpinner.equalsIgnoreCase(tipUser)) {
                    android.util.Log.d("UsuarioDetalle", "Match encontrado en posición: " + i);
                    tip.setSelection(i);
                    break;
                }
            }
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activarCampos();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validaciones.comruebaCamposVacios(layout,UsuarioDetalle.this)){
                    return;
                }

                if (!Validaciones.validarEnteroPositivo(eda,UsuarioDetalle.this)){
                    return;
                }

                int edad = Integer.parseInt(eda.getText().toString().trim());
                if(edad<18 || edad>99){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(UsuarioDetalle.this);
                    alerta.setTitle("Edad usuario");
                    alerta.setMessage("El usuario debe tener entre 18 y 99 años");
                    alerta.setCancelable(true);
                    alerta.show();
                    return;
                }

                usuario.setNombre(nom.getText().toString().trim());
                usuario.setApellidos(ape.getText().toString().trim());
                usuario.setEdad(edad);
                usuario.setPass(pass.getText().toString().trim());
                usuario.setTipo(tip.getSelectedItem().toString().trim().toLowerCase());

                try {
                    Consultas consulta = new Consultas(UsuarioDetalle.this);

                    if (consulta.actualizarUsuario(usuario)){
                        Toast.makeText(UsuarioDetalle.this,"Usuario actualizado correctamente",Toast.LENGTH_LONG).show();
                        desactivarCampos();
                    }
                } catch (Exception e) {
                    Toast.makeText(UsuarioDetalle.this,"Error al actualizar usuario. Vuelva a intentarlo",Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder aviso = new android.app.AlertDialog.Builder(UsuarioDetalle.this);
                aviso.setTitle("Eliminación de usuario");
                aviso.setMessage("¿Está seguro de que desea eliminar este usuario?");
                aviso.setPositiveButton("Eliminar", null);
                aviso.setNegativeButton("Cancelar", null);
                android.app.AlertDialog dialog = aviso.create();
                dialog.show();

                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(vi -> {
                    try {
                        Consultas consulta = new Consultas(UsuarioDetalle.this);
                        if (consulta.eliminarUsuario(String.valueOf(usuario.getUsuario()))){
                            Toast.makeText(UsuarioDetalle.this, getString(R.string.eliminar_articulo_confirmado),Toast.LENGTH_LONG).show();
                            consulta.close();
                            dialog.dismiss();
                            finish();
                        }
                    } catch (Exception e){
                        Toast.makeText(UsuarioDetalle.this, getString(R.string.eliminar_articulo_error),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();

        if ("admin".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_admin, menu);
        } else if ("user".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_user, menu);
        }

        return true;
    }

    public void activarCampos(){
        nom.setEnabled(true);
        ape.setEnabled(true);
        eda.setEnabled(true);
        pass.setEnabled(true);
        tip.setEnabled(true);
        send.setEnabled(true);
    }

    public void desactivarCampos(){
        nom.setEnabled(false);
        ape.setEnabled(false);
        eda.setEnabled(false);
        pass.setEnabled(false);
        tip.setEnabled(false);
        send.setEnabled(false);
    }


}