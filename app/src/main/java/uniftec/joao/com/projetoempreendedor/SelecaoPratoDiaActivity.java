package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

public class SelecaoPratoDiaActivity extends ActivityBase {

    Spinner spnPratos;
    ProgressBar progressBar;

    int DiaSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tela_selecaopratodia);

        spnPratos = findViewById(R.id.SpnPratos);
        progressBar = findViewById(R.id.progressBarPratosDia);
        DiaSemana = getIntent().getIntExtra("diaSemana", 0);

        AtualizaListaPratos();
    }

    public void IncluirPrato(View view)
    {
        int idPrato = ((Pratos)spnPratos.getAdapter().getItem(spnPratos.getSelectedItemPosition())).pratoID;
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "IncluirPrato";

        Pratos_DiaSemana pds = new Pratos_DiaSemana();
        pds.diasemana = DiaSemana;
        pds.prato = new Pratos();
        pds.prato.pratoID = idPrato;

        RequisicaoPOST(cServidor + "/Pratos_DiaSemana", pds);
    }

    public void Cancelar(View view)
    {
        finish();
    }

    private void AtualizaListaPratos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "ListaPratos";
        RequisicaoGET(cServidor + "/Pratos/ListaPratos");
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Gson gson = new Gson();
        Pratos[] pratos = gson.fromJson(resposta.toString(), Pratos[].class);

        final AdicionarPratosDiaAdapter adapter = new AdicionarPratosDiaAdapter(this,
                android.R.layout.simple_spinner_item,
                pratos);
        spnPratos.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPratos.setAdapter(adapter);

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        super.RetornoPOST(resposta);

        Toast.makeText(this, "Prato Vinculado com Sucesso.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        finish();
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);

        if (tipoRequisicao.equals("ListaPratos"))
        {
            AtualizaListaPratos();
        }
    }
}