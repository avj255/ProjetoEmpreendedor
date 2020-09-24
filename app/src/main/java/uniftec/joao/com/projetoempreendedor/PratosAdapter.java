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

public class PratosAdapter extends ArrayAdapter<Pratos> {

    public PratosAdapter(@NonNull Context context, int resource, @NonNull List<Pratos> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pratos prato = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custompratos, parent, false);

        TextView tvNomePrato = (TextView) view.findViewById(R.id.tvNomeIngredientes);
        tvNomePrato.setText(prato.nome);

        if (prato.foto != null) {
            byte[] decodedString = Base64.decode(prato.foto, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imgFoto = (ImageView) view.findViewById(R.id.tvFotoPrato);
            imgFoto.setImageBitmap(decodedByte);
        }
        return view;
    }

}
