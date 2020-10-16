package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

public class editarPratos extends ActivityBase {

    ProgressBar progressBar;
    EditText editTextNome;
    EditText editValor;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editarpratos);
        progressBar = findViewById(R.id.progressBarEditarPrato);
        mListView = findViewById(R.id.ListViewIngredientes);
        editTextNome = findViewById(R.id.edtitTextNomePrato);
        editValor = findViewById(R.id.editTextValorPrato);
        atualizarPrato();
    }

    void atualizarPrato()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Pratos/"+ Sessao.idPrato.toString());
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Pratos pratos = new Pratos();
        List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();

        Gson gson = new Gson();


        pratos = gson.fromJson(resposta.toString(), Pratos.class);


        for (Ingredientes pratosIngredientes : pratos.ingredientes)
        {
            ingredientes.add(gson.fromJson(gson.toJson(pratosIngredientes), Ingredientes.class));
        }

        final PratosIngredientesAdapter pratosIngredientesAdapter = new PratosIngredientesAdapter(this, R.layout.custompratoingredientes, ingredientes);
        mListView.setAdapter(pratosIngredientesAdapter);
        editTextNome.setText(pratos.nome);

//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Sessao.prato = pratosAdapter.getItem(i);
//                Intent j = new Intent(cardapiodia.this, pratos.class);
//                startActivity(j);
//            }
//        });

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }


    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        atualizarPrato();
    }
}