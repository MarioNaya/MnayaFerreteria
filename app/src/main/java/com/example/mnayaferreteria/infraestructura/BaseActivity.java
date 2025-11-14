package com.example.mnayaferreteria.infraestructura;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.activities.AdministrarUsuarios;
import com.example.mnayaferreteria.activities.ArticulosVista;
import com.example.mnayaferreteria.activities.RegistroArticulo;
import com.example.mnayaferreteria.activities.RegistroUsuario;
import com.example.mnayaferreteria.user.SessionManager;
import com.example.mnayaferreteria.activities.MainActivity;

public class BaseActivity extends AppCompatActivity {

    protected SessionManager getSession(){
        return SessionManager.getInstance(this);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        int idItem = item.getItemId();

        if (idItem == R.id.subitemHerramientas) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='herramientas'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemTodos) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria","");
            startActivity(i);
            return true;
        }  else if (idItem == R.id.subitemMenaje) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='menaje'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemIluminacion) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='iluminación'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemJardin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='jardín'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.itemCuenta) {

            return true;
        } else if (idItem == R.id.subitemTodosAdmin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria","");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemHerramientasAdmin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='herramientas'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemMenajeAdmin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='menaje'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemIluminacionAdmin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='iluminación'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.subitemJardinAdmin) {
            Intent i = new Intent(this, ArticulosVista.class);
            i.putExtra("categoria"," AND categoria='jardín'");
            startActivity(i);
            return true;
        } else if (idItem == R.id.itemAdminRegistroArticulo) {
            Intent i = new Intent(this, RegistroArticulo.class);
            startActivity(i);
            return true;
        } else if (idItem == R.id.itemAdminUsuarios) {
            Intent i = new Intent(this, AdministrarUsuarios.class);
            startActivity(i);
            return true;
        } else if (idItem == R.id.itemAdminRegistroUsuario) {
            Intent i = new Intent(this, RegistroUsuario.class);
            startActivity(i);
            return true;
        } else if (idItem == R.id.itemCerrarSesion) {
            getSession().cerrarSesion();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
