package com.example.mnayaferreteria;

import android.content.Context;
import android.content.Intent;
import android.widget.Switch;

public class AuthService {

    public static void autenticacionNivelAcceso(String role, Context context){

        String nivelAcceso = role;
        if (nivelAcceso != null){
            switch (nivelAcceso){
                case "admin":

                    break;
                case "user":

                    break;

            }
        }


    }
}
