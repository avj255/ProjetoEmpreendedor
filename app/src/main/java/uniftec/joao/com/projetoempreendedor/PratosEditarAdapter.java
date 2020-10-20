package uniftec.joao.com.projetoempreendedor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;

public class PratosEditarAdapter extends ArrayAdapter<Ingredientes> {

    List<Ingredientes> ArrayIngredientes;

    public PratosEditarAdapter(@NonNull Context context, int resource, @NonNull List<Ingredientes> objects) {
        super(context, resource, objects);
        ArrayIngredientes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Ingredientes ingrediente = getItem(position);
        final int Posicao = position;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View view = layoutInflater.inflate(R.layout.custompratosingredientescadastro, parent, false);
        final ListView listviewIngredientes = (ListView) view.findViewById(R.id.ListViewIngredientes);
        TextView tvNomeIngrediente = (TextView) view.findViewById(R.id.tvCadastroPratosIngredientes);
        tvNomeIngrediente.setText(ingrediente.nome);
        ImageButton btnExcluirIngrediente = (ImageButton)view.findViewById(R.id.botaoExcluirIngrediente);

        btnExcluirIngrediente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                adb.setTitle("Deletar Ingrediente");
                adb.setMessage("Deseja remover o ingrediente: '" + ingrediente.nome +"'?");
                adb.setNegativeButton("Cancelar", null);
                adb.setPositiveButton("Confirmar", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayIngredientes.remove(Posicao);
                        notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
        return view;
    }

}
