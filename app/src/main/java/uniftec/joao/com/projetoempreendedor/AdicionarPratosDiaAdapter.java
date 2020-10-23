package uniftec.joao.com.projetoempreendedor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;

public class AdicionarPratosDiaAdapter extends ArrayAdapter<Pratos> {

    private Pratos[] values;

    public AdicionarPratosDiaAdapter(@NonNull Context context, int resource, @NonNull Pratos[] objects) {
        super(context, resource, objects);
        values = objects;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public Pratos getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].nome);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].nome);
        return label;
    }
}
