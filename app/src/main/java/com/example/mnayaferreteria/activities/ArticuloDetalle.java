package com.example.mnayaferreteria.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

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

        adapter = new AdapterArticuloDetalle(ArticuloDetalle.this,articulo);
        lista.setAdapter(adapter);

        if (tipoUsuario.equals("admin")){
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();

        if ("admin".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_admin, menu);
        } else if ("user".equals(tipoUsuario)) {
            inflater.inflate(R.menu.menu_user, menu);
        }

        return true;
    }
}