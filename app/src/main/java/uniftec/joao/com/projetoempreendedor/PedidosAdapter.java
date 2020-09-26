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

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;

public class PedidosAdapter extends ArrayAdapter<Pedidos> {

    public PedidosAdapter(@NonNull Context context, int resource, @NonNull List<Pedidos> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pedidos pedido = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custompedidos, parent, false);

        TextView tvNumeroPedido = (TextView) view.findViewById(R.id.tvNumeroPedido);
        TextView tvSituacao = (TextView) view.findViewById(R.id.tvSituacao);
        TextView tvNomePrato = (TextView) view.findViewById(R.id.tvPrato);
        TextView tvQuantidade = (TextView) view.findViewById(R.id.tvQtde);
        TextView tvValor = (TextView) view.findViewById(R.id.tvTotal);

        tvNumeroPedido.setText("Pedido " + pedido.pedidoID);
        tvSituacao.setText(Utilidades.getDescricaoSituacao(pedido.situacao));
        tvNomePrato.setText(pedido.pratoObj.nome);
        tvQuantidade.setText("Qtd: " + pedido.quantidade);
        tvValor.setText("Total: R$ " + String.format("%.2f", pedido.valor));

        tvSituacao.setTextColor(Utilidades.getCorSituacao(pedido.situacao));

        return view;
    }

}
