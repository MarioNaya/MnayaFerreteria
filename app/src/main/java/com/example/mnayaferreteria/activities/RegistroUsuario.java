package com.example.mnayaferreteria.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.mnayaferreteria.utilidades.Utilidades;
import com.example.mnayaferreteria.utilidades.Validaciones;

public class RegistroUsuario extends BaseActivity {

    Usuario usuario;
    String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarRegistroUsuario));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tipoUsuario = getSession().getTipo();

        LinearLayout layout = findViewById(R.id.layoutRegistroUsuario);
        EditText usu = findViewById(R.id.textRegisUsuario);
        EditText nom = findViewById(R.id.textRegisNombre);
        EditText ape = findViewById(R.id.textRegisApellidos);
        EditText eda = findViewById(R.id.textRegisEdad);
        EditText pas = findViewById(R.id.textRegisPassword);
        Spinner tip = findViewById(R.id.spinnerRegisTipo);
        Button reg = findViewById(R.id.btnRegUsuario);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(
                this,
                R.array.tipos,
                R.layout.spinner_item
        );
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tip.setAdapter(adapterSpinner);

        tip.post(new Runnable() {
            @Override
            public void run() {
                tip.setDropDownVerticalOffset(tip.getHeight());
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Validaciones.comruebaCamposVacios(layout, RegistroUsuario.this)) {
                    return;
                }

                if (!Validaciones.validarEnteroPositivo(eda, RegistroUsuario.this)) {
                    eda.setText("");
                    return;
                }

                int edadValue = Integer.parseInt(eda.getText().toString().trim());
                if (edadValue < 18 || edadValue > 99) {
                    Toast.makeText(RegistroUsuario.this, "La edad debe estar entre 18 y 99 años", Toast.LENGTH_SHORT).show();
                    eda.setText("");
                    return;
                }

                usuario = new Usuario(
                        usu.getText().toString().trim(),
                        nom.getText().toString().trim(),
                        ape.getText().toString().trim(),
                        edadValue,
                        pas.getText().toString().trim(),
                        tip.getSelectedItem().toString().trim().toLowerCase()
                );

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroUsuario.this);
                builder.setTitle("Registro de Usuario");
                builder.setMessage("Va a registrar un nuevo usuario. ¿Desea continuar?");
                builder.setCancelable(true);

                builder.setPositiveButton("Registrar", null);
                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

                    try {
                        Consultas consulta = new Consultas(RegistroUsuario.this);
                        if (consulta.registrarUsuario(usuario)) {
                            Toast.makeText(RegistroUsuario.this, "Registro realizado correctamente", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Utilidades.limpiaCampos(layout);
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegistroUsuario.this, "Error en el registro. Vuelva a intentarlo.", Toast.LENGTH_LONG).show();
                        throw new RuntimeException(e);
                    }
                });

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        if ("admin".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_admin, menu);
        } else if ("user".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_user, menu);
        }

        return true;
    }
}