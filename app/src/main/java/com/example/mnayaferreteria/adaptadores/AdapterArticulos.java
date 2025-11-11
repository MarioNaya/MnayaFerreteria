package com.example.mnayaferreteria.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.model.Articulo;

import java.util.ArrayList;

public class AdapterArticulos extends BaseAdapter {

    Context context;
    private ArrayList<Articulo> articulos;
    private static LayoutInflater inflater;

    public AdapterArticulos(Context context, ArrayList<Articulo> articulos){
        this.context = context;
        this.articulos = articulos;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articulos.size();
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

        nom.setText(articulos.get(i).getNombre());
        pre.setText("Precio: " + String.valueOf(articulos.get(i).getPrecio()));
        sto.setText("Stock: " + String.valueOf(articulos.get(i).getStock()));

        return vista;
    }
}
