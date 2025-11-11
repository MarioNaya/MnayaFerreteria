package com.example.mnayaferreteria.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.adaptadores.AdapterArticuloDetalle;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.model.Articulo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArticuloDetalle extends BaseActivity {

    Articulo articulo;
    String tipoUsuario;
    String nombreArticulo;
    AdapterArticuloDetalle adapter;
    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulo_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarDetalleArticulo));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        FloatingActionButton edit = findViewById(R.id.floatingEditar);
        FloatingActionButton delete = findViewById(R.id.floatingEliminar);

        tipoUsuario = getSession().getTipo();
        lista = findViewById(R.id.listaDetalles);
        nombreArticulo = getIntent().getStringExtra("nombre");

        try {
            Consultas consulta = new Consultas(ArticuloDetalle.this);
            articulo = consulta.articuloPorNombre(nombreArticulo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        adapter = new AdapterArticuloDetalle(ArticuloDetalle.this, articulo);
        lista.setAdapter(adapter);

        if (tipoUsuario.equals("admin")) {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ArticuloDetalle.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_editar, null);
                builder.setView(view);

                EditText editId = view.findViewById(R.id.editId);
                EditText editNombre = view.findViewById(R.id.editNombre);
                Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
                EditText editPrecio = view.findViewById(R.id.editPrecio);
                EditText editStock = view.findViewById(R.id.editStock);
                RadioGroup radioOrigen = view.findViewById(R.id.radioOrigen);
                EditText editDescripcion = view.findViewById(R.id.editDescripcion);

                String[] categorias = {"herramientas", "jardín", "menaje", "iluminación"};
                ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(ArticuloDetalle.this, android.R.layout.simple_spinner_item, categorias);
                adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapterSpinner);

                editId.setText(String.valueOf(articulo.getIdArticulo()));  // ID no editable
                editNombre.setText(articulo.getNombre());
                editPrecio.setText(String.valueOf(articulo.getPrecio()));
                editDescripcion.setText(articulo.getDescripcion());
                editStock.setText(String.valueOf(articulo.getStock()));

                int index = java.util.Arrays.asList(categorias).indexOf(articulo.getCategoria());
                if (index >= 0) spinnerCategoria.setSelection(index);

                if ("Nacional".equals(articulo.getOrigen())) {
                    radioOrigen.check(R.id.radioNacional);
                } else {
                    radioOrigen.check(R.id.radioInternacional);
                }

                builder.setPositiveButton("Guardar", null);
                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {

                    String nombre = editNombre.getText().toString().trim();
                    String precioStr = editPrecio.getText().toString().trim();
                    String descripcion = editDescripcion.getText().toString().trim();
                    String categoria = spinnerCategoria.getSelectedItem().toString();
                    String idArticulo = editId.getText().toString().trim();
                    String stock = editStock.getText().toString().trim();

                    if (nombre.isEmpty()) {
                        editNombre.setError("El nombre no puede estar vacío");
                        return;
                    }

                    if (precioStr.isEmpty()) {
                        editPrecio.setError("Introduce un precio");
                        return;
                    }

                    double precioValue = Double.parseDouble(precioStr);
                    if (precioValue <= 0) {
                        editPrecio.setError("El precio debe ser mayor que 0");
                        return;
                    }

                    int stockValue = Integer.parseInt(stock);
                    if (stockValue <= 0) {
                        editPrecio.setError("El stock debe ser mayor o igual a 0");
                        return;
                    }

                    if (descripcion.isEmpty()) {
                        editDescripcion.setError("La descripción no puede estar vacía");
                        return;
                    }

                    int origenId = radioOrigen.getCheckedRadioButtonId();
                    if (origenId == -1) {
                        Toast.makeText(ArticuloDetalle.this, "Selecciona un origen", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String origen = (origenId == R.id.radioNacional) ? "nacional" : "internacional";

                    int idValue = Integer.parseInt(idArticulo);

                    articulo.setNombre(nombre);
                    articulo.setCategoria(categoria);
                    articulo.setPrecio(precioValue);
                    articulo.setDescripcion(descripcion);
                    articulo.setOrigen(origen);
                    articulo.setIdArticulo(idValue);
                    articulo.setStock(stockValue);

                    try {
                        Consultas c = new Consultas(ArticuloDetalle.this);
                        if (c.actualizarArticulo(articulo)){
                            Toast.makeText(ArticuloDetalle.this,"Registro actualizado correctamente",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ArticuloDetalle.this, "Error al guardar en BD", Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();

                    dialog.dismiss();
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
