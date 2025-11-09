package com.example.mnayaferreteria;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Validaciones {

    public static boolean comruebaCamposVacios(LinearLayout layout, Context context){

        for (int i = 0;i<layout.getChildCount();i++){
            View campo = layout.getChildAt(i);
            if (campo instanceof EditText && ((EditText) campo).getText().toString().trim().isEmpty()){
                Avisos.campoObligatorio(campo,context).show();
                return false;
            }

            if (campo instanceof Spinner && ((Spinner) campo).getSelectedItemPosition()==0){
                Avisos.campoObligatorio(campo,context).show();
                return false;
            }
        }
        return true;
    }
}
