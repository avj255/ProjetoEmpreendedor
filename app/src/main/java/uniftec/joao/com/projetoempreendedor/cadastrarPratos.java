package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

public class cadastrarPratos  extends ActivityBase {

    ListView mListView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrarpratos);
        mListView = findViewById(R.id.listaPratos);
        progressBar = findViewById(R.id.progressBarCadastroPratos);
        atualizarListaPratosCadastro();
    }

    public void AdicionarPrato(View view)
    {
        Sessao.idPrato = null;
        Intent j = new Intent(this, editarPratos.class);
        startActivity(j);
    }


    void atualizarListaPratosCadastro()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Pratos/ListaPratos");
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        List<Pratos> pratos = new ArrayList<Pratos>();

        Gson gson = new Gson();
        pratos = Arrays.asList(gson.fromJson(resposta.toString(), Pratos[].class));

        final PratosCadastroAdapter pratosAdapter = new PratosCadastroAdapter(this, R.layout.customcadastropratos, pratos);
        mListView.setAdapter(pratosAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Sessao.idPrato = pratosAdapter.getItem(i).pratoID;
                Intent j = new Intent(cadastrarPratos.this, editarPratos.class);
                startActivity(j);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }
    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        atualizarListaPratosCadastro();
    }



}