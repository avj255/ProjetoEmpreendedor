package uniftec.joao.com.projetoempreendedor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;

public class PratosCadastroAdapter extends ArrayAdapter<Pratos> {

    public PratosCadastroAdapter(@NonNull Context context, int resource, @NonNull List<Pratos> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pratos prato = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.customcadastropratos, parent, false);

        TextView tvNomePrato = (TextView) view.findViewById(R.id.tvCadastroPratos);
        tvNomePrato.setText(prato.nome);
        return view;
    }

}
