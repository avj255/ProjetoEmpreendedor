package uniftec.joao.com.projetoempreendedor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

public class cardapiodia extends ActivityBase {

    TextView diaSemana;
    ListView mListView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cardapiodia);
        diaSemana = findViewById(R.id.textViewDiaSemana);
        diaSemana.setText(Sessao.descricaoDiaSemana);
        mListView = findViewById(R.id.ListaIngredientes);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarListaPratos();
    }

    void atualizarListaPratos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Pratos_DiaSemana/"+ Sessao.diaSemana);
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        List<Pratos> pratos = new ArrayList<Pratos>();

        Gson gson = new Gson();
        Pratos_DiaSemana[] pratosDiaSemana = gson.fromJson(resposta.toString(), Pratos_DiaSemana[].class);

        for (Pratos_DiaSemana pratosDia : pratosDiaSemana)
        {
            pratos.add(gson.fromJson(gson.toJson(pratosDia.prato), Pratos.class));
        }
        final PratosAdapter pratosAdapter = new PratosAdapter(this, R.layout.custompratos, pratos);
        mListView.setAdapter(pratosAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Sessao.prato = pratosAdapter.getItem(i);
                Intent j = new Intent(cardapiodia.this, pratos.class);
                startActivity(j);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }


    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        atualizarListaPratos();
    }
}