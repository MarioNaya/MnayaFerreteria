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

import java.util.Objects;

public class AdapterArticuloDetalle extends BaseAdapter {

    Context context;
    private Articulo articulo;
    private static LayoutInflater inflater;

    public AdapterArticuloDetalle(Context context, Articulo articulo){
        this.context=context;
        this.articulo=articulo;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
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

        View vista = view;

        if (vista == null) {
            vista = inflater.inflate(R.layout.adapter_detalle_articulo, viewGroup, false);
        }

        TextView idArt = vista.findViewById(R.id.textDetalleId);
        TextView nom = vista.findViewById(R.id.textDetalleNombre);
        TextView cat = vista.findViewById(R.id.textDetalleCategoria);
        TextView des = vista.findViewById(R.id.textDetalleDescripcion);
        TextView pre = vista.findViewById(R.id.textDetallePrecio);
        TextView sto = vista.findViewById(R.id.textDetalleStock);
        TextView ori = vista.findViewById(R.id.textDetalleOrigen);
        ImageView img = vista.findViewById(R.id.imageArticuloDetalle);


        if (articulo != null) {
            idArt.setText(String.valueOf(articulo.getIdArticulo()));
            nom.setText(articulo.getNombre());
            cat.setText(articulo.getCategoria());
            des.setText(articulo.getDescripcion());
            pre.setText(String.valueOf(articulo.getPrecio()));
            sto.setText(String.valueOf(articulo.getStock()));
            ori.setText(articulo.getOrigen());
        }

        if (Objects.equals(articulo.getCategoria(),"herramientas")){
            img.setImageResource(R.drawable.herramientaslogo);
        } else if (Objects.equals(articulo.getCategoria(),"iluminación")){
            img.setImageResource(R.drawable.iluminacionlogo);
        } else if (Objects.equals(articulo.getCategoria(),"jardín")){
            img.setImageResource(R.drawable.jardinlogo);
        } else if (Objects.equals(articulo.getCategoria(),"menaje")){
            img.setImageResource(R.drawable.menajelogo);
        }

        return vista;
    }
}
