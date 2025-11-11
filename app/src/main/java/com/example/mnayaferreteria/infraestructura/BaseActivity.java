package com.example.mnayaferreteria.infraestructura;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.user.SessionManager;
import com.example.mnayaferreteria.activities.MainActivity;

public class BaseActivity extends AppCompatActivity {

    protected SessionManager getSession(){
        return SessionManager.getInstance(this);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        int idItem = item.getItemId();

        if (idItem == R.id.subitemHerramientas) {

            return true;
        } else if (idItem == R.id.subitemTodos) {

            return true;
        }  else if (idItem == R.id.subitemMenaje) {

            return true;
        } else if (idItem == R.id.subitemIluminacion) {

            return true;
        } else if (idItem == R.id.subitemJardin) {

            return true;
        } else if (idItem == R.id.itemCuenta) {

            return true;
        } else if (idItem == R.id.subitemTodosAdmin) {

            return true;
        } else if (idItem == R.id.subitemHerramientasAdmin) {

            return true;
        } else if (idItem == R.id.subitemMenajeAdmin) {

            return true;
        } else if (idItem == R.id.subitemIluminacionAdmin) {

            return true;
        } else if (idItem == R.id.subitemJardinAdmin) {

            return true;
        } else if (idItem == R.id.itemAdminRegistroArticulo) {

            return true;
        } else if (idItem == R.id.itemAdminUsuarios) {

            return true;
        } else if (idItem == R.id.itemAdminRegistroUsuario) {

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
