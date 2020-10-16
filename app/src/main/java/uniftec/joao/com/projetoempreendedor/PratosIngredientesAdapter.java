package uniftec.joao.com.projetoempreendedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;

public class PratosIngredientesAdapter extends ArrayAdapter<Ingredientes> {

    public PratosIngredientesAdapter(@NonNull Context context, int resource, @NonNull List<Ingredientes> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Ingredientes ingrediente = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.customcadastropratos, parent, false);

        TextView tvCadastroPratos = (TextView) view.findViewById(R.id.tvCadastroPratos);
        tvCadastroPratos.setText(ingrediente.nome);
        return view;
    }

}
