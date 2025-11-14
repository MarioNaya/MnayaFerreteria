package com.example.mnayaferreteria.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.model.Articulo;
import com.example.mnayaferreteria.model.Usuario;

import java.util.ArrayList;

public class AdapterUsuarios extends BaseAdapter {

    Context context;
    private ArrayList<Usuario> usuarios;
    private static LayoutInflater inflater;

    public AdapterUsuarios(Context context, ArrayList<Usuario> usuarios){
        this.context = context;
        this.usuarios = usuarios;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final View vista = inflater.inflate(R.layout.adapter_articulos,null);

        TextView nom = vista.findViewById(R.id.textNombreArticulo);
        TextView pre = vista.findViewById(R.id.textPrecioAdapter);
        TextView sto = vista.findViewById(R.id.textStock);

        nom.setText(usuarios.get(i).getNombre());
        pre.setText("Edad: " + String.valueOf(usuarios.get(i).getEdad()));
        sto.setText("Tipo: " + String.valueOf(usuarios.get(i).getTipo()));

        return vista;
    }
}
