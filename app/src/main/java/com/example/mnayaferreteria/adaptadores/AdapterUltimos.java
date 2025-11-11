package com.example.mnayaferreteria.adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mnayaferreteria.R;
import com.example.mnayaferreteria.model.Articulo;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterUltimos extends BaseAdapter {

    Context context;
    private ArrayList<Articulo> articulos;
    private static LayoutInflater inflater;

    public AdapterUltimos(Context context, ArrayList<Articulo> articulos){
        this.context = context;
        this.articulos = articulos;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.adapter_ultimos,null);

        TextView nom = vista.findViewById(R.id.textNombreAdapter);
        TextView cat = vista.findViewById(R.id.textCategoriaAdapter);
        TextView des = vista.findViewById(R.id.textDescripcionAdapter);

        ImageView img = vista.findViewById(R.id.imageCategoria);

        nom.setText(articulos.get(position).getNombre());
        cat.setText(articulos.get(position).getCategoria());
        des.setText(articulos.get(position).getDescripcion());

        if (Objects.equals(articulos.get(position).getCategoria(),"herramientas")){
            img.setImageResource(R.drawable.herramientaslogo);
        } else if (Objects.equals(articulos.get(position).getCategoria(),"iluminación")){
            img.setImageResource(R.drawable.iluminacionlogo);
        } else if (Objects.equals(articulos.get(position).getCategoria(),"jardín")){
            img.setImageResource(R.drawable.jardinlogo);
        } else if (Objects.equals(articulos.get(position).getCategoria(),"menaje")){
            img.setImageResource(R.drawable.menajelogo);
        }
        return vista;
    }
}
