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
            // Código para herramientas
            return true;
        } else if (idItem == R.id.subitemTodos) {
            // Código para menaje
            return true;
        }  else if (idItem == R.id.subitemMenaje) {
            // Código para menaje
            return true;
        } else if (idItem == R.id.subitemIluminacion) {
            // Código para iluminación
            return true;
        } else if (idItem == R.id.subitemJardin) {
            // Código para jardín
            return true;
        } else if (idItem == R.id.itemCuenta) {
            // Código para cuenta
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
