package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Arrays;

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;

public class AcompanhamentoPedidosActivity extends ActivityBase {

    ProgressBar progressBar;
    ListView lvPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_acompanhamentopedidos);

        progressBar = findViewById(R.id.progressBar);
        lvPedidos = findViewById(R.id.lvPedidos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizarPedidos();
    }

    private void AtualizarPedidos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Pedidos/"+ Sessao.usuarioLogado.userID);
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Gson gson = new Gson();
        Pedidos[] pedidos = gson.fromJson(resposta.toString(), Pedidos[].class);

        final PedidosAdapter pedidosAdapter = new PedidosAdapter(this, R.layout.custompedidos, Arrays.asList(pedidos));
        lvPedidos.setAdapter(pedidosAdapter);

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        AtualizarPedidos();
    }
}
