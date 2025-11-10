package com.example.mnayaferreteria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    ArrayList<Articulo> listaArticulos;
    String tipoUsuario;

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

        Bundle bundle = getIntent().getExtras();

        TextView bienvenida = findViewById(R.id.textSaludo);
        LinearLayout layout = findViewById(R.id.layoutLista);
        ListView lista = findViewById(R.id.listaPrincipal);


        if (bundle != null){
            tipoUsuario = bundle.getString("tipo");
        } else {
            String titulo = getString(R.string.error_logado_title);
            String mensaje = getString(R.string.error_logado_mensaje);
            Avisos.avisoSinBotones(Principal.this,titulo,mensaje);
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