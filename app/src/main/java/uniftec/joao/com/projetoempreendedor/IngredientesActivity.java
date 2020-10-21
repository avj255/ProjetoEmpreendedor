package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;

public class IngredientesActivity extends ActivityBase {

    ListView mListView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_ingredientes);
        mListView = findViewById(R.id.listaIngredientes);
        progressBar = findViewById(R.id.progressBarIngredientes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizarListaIngredientes();
    }

    public void AdicionarIngrediente(View view)
    {
        Intent j = new Intent(this, CadastroIngredienteActivity.class);
        startActivity(j);
    }

    void AtualizarListaIngredientes()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Ingredientes");
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Gson gson = new Gson();
        List<Ingredientes> ingredientes = Arrays.asList(gson.fromJson(resposta.toString(), Ingredientes[].class));

        final IngredientesAdapter ingredientesAdapter = new IngredientesAdapter(this, R.layout.customingredientes, ingredientes);
        mListView.setAdapter(ingredientesAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j = new Intent(IngredientesActivity.this, CadastroIngredienteActivity.class);
                j.putExtra("id", ingredientesAdapter.getItem(i).ingredienteID);
                j.putExtra("nome", ingredientesAdapter.getItem(i).nome);
                j.putExtra("peso", ingredientesAdapter.getItem(i).peso);
                j.putExtra("calorias", ingredientesAdapter.getItem(i).calorias);
                startActivity(j);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }
    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        AtualizarListaIngredientes();
    }
}