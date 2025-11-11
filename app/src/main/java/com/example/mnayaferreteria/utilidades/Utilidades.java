package com.example.mnayaferreteria.utilidades;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Utilidades {

    public static void limpiaCampos(LinearLayout layout){

        for (int i = 0; i< layout.getChildCount();i++){
            View campo = layout.getChildAt(i);
            if (campo instanceof EditText){
                ((EditText) campo).setText("");
            }

            if (campo instanceof Spinner){
                ((Spinner) campo).setSelection(0);
            }
        }
        layout.getChildAt(0).requestFocus();
    }
}
