package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;

public class AlterarStatusPedidoActivity extends ActivityBase {

    TextView tvPedido;
    Spinner spnSituacao;
    ProgressBar progressBar;

    Pedidos Pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tela_alterarstatuspedidoactivity);

        tvPedido = findViewById(R.id.TvPedido);
        spnSituacao = findViewById(R.id.SpnSituacao);
        progressBar = findViewById(R.id.progressBarSituacao);

        Gson gson = new Gson();
        Pedido = gson.fromJson(getIntent().getStringExtra("pedido"), Pedidos.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.situacao));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSituacao.setAdapter(adapter);
        spnSituacao.setSelection(Pedido.situacao - 1);

        tvPedido.setText("Pedido " + Pedido.pedidoID + " - " + Pedido.nomeUsuario);
    }

    public void Alterar(View view)
    {
        Pedido.situacao = spnSituacao.getSelectedItemPosition() + 1;
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoPOST(cServidor + "/Pedidos/AlteraSituacao", Pedido);
    }

    public void Cancelar(View view)
    {
        finish();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        super.RetornoPOST(resposta);

        Toast.makeText(this, "Situação Alterada com Sucesso.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        finish();
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);

        progressBar.setVisibility(View.VISIBLE);
        habilitaInteracao();
    }
}