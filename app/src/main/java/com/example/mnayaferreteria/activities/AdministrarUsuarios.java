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
import com.example.mnayaferreteria.adaptadores.AdapterUsuarios;
import com.example.mnayaferreteria.bbdd.Consultas;
import com.example.mnayaferreteria.infraestructura.BaseActivity;
import com.example.mnayaferreteria.model.Articulo;
import com.example.mnayaferreteria.model.Usuario;

import java.util.ArrayList;

public class AdministrarUsuarios extends BaseActivity {

    ArrayList<Usuario> listaUsuario;
    String tipoUsuario;
    AdapterUsuarios adapter;
    Usuario usuarioDetalle;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbarUsuarios));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        lista = findViewById(R.id.listaUsuarios);
        tipoUsuario = getSession().getTipo();

        try (Consultas consulta = new Consultas(AdministrarUsuarios.this)) {
            listaUsuario = consulta.listadoUsuarios();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        adapter = new AdapterUsuarios(AdministrarUsuarios.this,listaUsuario);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                usuarioDetalle = listaUsuario.get(position);
                String usuario = usuarioDetalle.getUsuario();

                Intent in = new Intent(AdministrarUsuarios.this, UsuarioDetalle.class);
                in.putExtra("usuario",usuario);
                startActivity(in);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && listaUsuario != null) {
            actualizarLista();
        }
    }

    private void actualizarLista() {
        try (Consultas consulta = new Consultas(AdministrarUsuarios.this)) {
            listaUsuario.clear();
            listaUsuario.addAll(consulta.listadoUsuarios());
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}