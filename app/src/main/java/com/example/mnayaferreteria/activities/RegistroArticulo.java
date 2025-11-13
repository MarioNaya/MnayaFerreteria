package com.example.mnayaferreteria.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.mnayaferreteria.model.Articulo;
import com.example.mnayaferreteria.utilidades.Utilidades;
import com.example.mnayaferreteria.utilidades.Validaciones;

public class RegistroArticulo extends BaseActivity {

    Articulo articulo;
    String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_articulo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarRegistroArticulo));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tipoUsuario = getSession().getTipo();

        LinearLayout layout = findViewById(R.id.layoutRegistroArticulo);
        EditText nom = findViewById(R.id.textRegisNombreArt);
        EditText des = findViewById(R.id.textRegisDescArt);
        EditText pre = findViewById(R.id.textRegisPrecioArt);
        EditText sto = findViewById(R.id.textRegisStockArt);
        Spinner cat = findViewById(R.id.spinnerRegCat);
        RadioGroup gruRad = findViewById(R.id.groupRadioOrigenReg);
        RadioButton radNac = findViewById(R.id.radioNacionalReg);
        RadioButton radInt = findViewById(R.id.radioInterReg);
        Button reg = findViewById(R.id.btnRegArt);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.categorias,
                android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cat.setAdapter(adapterSpinner);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validaciones.comruebaCamposVacios(layout, RegistroArticulo.this)) {
                    return;
                }

                if (!Validaciones.validarDoublePositivo(pre, RegistroArticulo.this)) {
                    pre.setText("");
                    return;
                }

                if (Validaciones.validarEnteroPositivo(sto, RegistroArticulo.this)) {
                    sto.setText("");
                    return;
                }

                int origenId = gruRad.getCheckedRadioButtonId();

                if (origenId == -1) {
                    Toast.makeText(RegistroArticulo.this, "Selecciona un origen", Toast.LENGTH_SHORT).show();
                    return;
                }

                String origen = (origenId == R.id.radioNacionalReg) ? "nacional" : "internacional";

                articulo = new Articulo(
                        nom.getText().toString().trim(),
                        cat.getSelectedItem().toString().trim().toLowerCase(),
                        des.getText().toString().trim(),
                        Double.parseDouble(pre.getText().toString().trim()),
                        Integer.parseInt(sto.getText().toString().trim()),
                        origen
                );

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroArticulo.this);
                builder.setTitle(getString(R.string.registroArticulo));
                builder.setMessage("Va s registrar un nuevo artículo. ¿Desea continuar?");
                builder.setCancelable(true);

                builder.setPositiveButton("Registrar", null);
                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();


                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v ->{
                    dialog.show();
                    try {
                        Consultas consulta = new Consultas(RegistroArticulo.this);
                        if (consulta.registrarArticulo(articulo)){
                            Toast.makeText(RegistroArticulo.this,"Registro realizado correctamente",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegistroArticulo.this,"Error en el registro. Vuelva a intentarlo.",Toast.LENGTH_LONG).show();
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