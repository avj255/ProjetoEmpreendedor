package uniftec.joao.com.projetoempreendedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;

public class PratoIngredientesAdapter extends ArrayAdapter<Ingredientes> {

    public PratoIngredientesAdapter(@NonNull Context context, int resource, @NonNull List<Ingredientes> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Ingredientes ingredientes = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custompratoingredientes, parent, false);

        TextView tvIngredientes = (TextView) view.findViewById(R.id.tvNomeIngredientes);
        tvIngredientes.setText(ingredientes.nome);

        return view;
    }
}
