package com.example.mnayaferreteria.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mnayaferreteria.adaptadores.AdapterUltimos;
import com.example.mnayaferreteria.model.Articulo;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.user.SessionManager;

import java.util.ArrayList;

public class Principal extends BaseActivity {

    ArrayList<Articulo> listaArticulos;
    String nombre;
    String tipoUsuario;
    AdapterUltimos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarPrincipal));

        nombre = getSession().getNombre();
        tipoUsuario = getSession().getTipo();

        Bundle bundle = getIntent().getExtras();

        TextView bienvenida = findViewById(R.id.textSaludo);
        LinearLayout layout = findViewById(R.id.layoutLista);
        ListView lista = findViewById(R.id.listaPrincipal);

        bienvenida.setText(String.format("Hola, %s",nombre));

        try (Consultas consulta = new Consultas(Principal.this)) {

            listaArticulos = consulta.ultimosArticulosRegistrados();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        adapter = new AdapterUltimos(Principal.this,listaArticulos);
        lista.setAdapter(adapter);

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