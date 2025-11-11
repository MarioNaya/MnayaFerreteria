package com.example.mnayaferreteria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.adaptadores.AdapterArticulos;
import com.example.mnayaferreteria.adaptadores.AdapterUltimos;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.model.Articulo;

import java.util.ArrayList;

public class ArticulosVista extends BaseActivity {

    ArrayList<Articulo> listaArticulos;
    String tipoUsuario;
    String categoria;
    AdapterArticulos adapter;
    Articulo articuloDetalle;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulos_vista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarArticulos));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        categoria = getIntent().getStringExtra("categoria");
        lista = findViewById(R.id.listaLayoutArticulos);
        tipoUsuario = getSession().getTipo();

        try (Consultas consulta = new Consultas(ArticulosVista.this)) {
            listaArticulos = consulta.listadoArticulos(categoria);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        adapter = new AdapterArticulos(ArticulosVista.this, listaArticulos);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                articuloDetalle = listaArticulos.get(i);
                String nombreArticulo = articuloDetalle.getNombre();

                Intent intent = new Intent(ArticulosVista.this, ArticuloDetalle.class);
                intent.putExtra("nombre",nombreArticulo);
                startActivity(intent);
            }
        });
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