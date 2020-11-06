package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Arrays;

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;

public class AcompanhamentoPedidosAdminActivity extends ActivityBase {

    ProgressBar progressBar;
    ListView lvPedidos;
    Spinner spnSituacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_acompanhamentopedidosadmin);

        progressBar = findViewById(R.id.progressBar);
        lvPedidos = findViewById(R.id.lvPedidos);
        spnSituacao = findViewById(R.id.Spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.situacao_pedidos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSituacao.setAdapter(adapter);

        spnSituacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AtualizarPedidos();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
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
        RequisicaoGET(cServidor + "/Pedidos/" + GetCaminhoRequisicao());
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Gson gson = new Gson();
        Pedidos[] pedidos = gson.fromJson(resposta.toString(), Pedidos[].class);

        final PedidosAdapter pedidosAdapter = new PedidosAdapter(this, R.layout.custompedidos, Arrays.asList(pedidos));
        lvPedidos.setAdapter(pedidosAdapter);

        lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AbrirEdicao(pedidosAdapter.getItem(i));
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        AtualizarPedidos();
    }

    private String GetCaminhoRequisicao()
    {
        switch (spnSituacao.getSelectedItemPosition())
        {
            case 0: return "PedidosAbertos";
            case 1: return "PedidosProducao";
            case 2: return "PedidosFinalizados";
            case 3: return "PedidosCancelados";
        }

        return null;
    }

    public void AbrirEdicao(Pedidos pedido)
    {
        Gson gson = new Gson();

        Intent i = new Intent(this, AlterarStatusPedidoActivity.class);
        i.putExtra("pedido", gson.toJson(pedido));
        startActivity(i);
    }
}