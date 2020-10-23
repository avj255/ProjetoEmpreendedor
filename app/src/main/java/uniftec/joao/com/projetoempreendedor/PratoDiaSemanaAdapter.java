package uniftec.joao.com.projetoempreendedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;

public class PratoDiaSemanaAdapter extends ArrayAdapter<Pratos> {

    public PratoDiaSemanaAdapter(@NonNull Context context, int resource, @NonNull List<Pratos> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Pratos prato = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custompratosdiasemana, parent, false);

        TextView tvNomePrato = view.findViewById(R.id.tvNomePrato);
        tvNomePrato.setText(prato.nome);

        ImageButton BtnExcluir = view.findViewById(R.id.botaoExcluirPratoDiaSemana);
        BtnExcluir.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        ((CardapioDiaAdminActivity) getContext()).ExcluirPrato(prato);
                    }
                }
        );

        return view;
    }
}
