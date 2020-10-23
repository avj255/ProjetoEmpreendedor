package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

import static uniftec.joao.com.projetoempreendedor.Sessao.idPrato;

public class CardapioDiaAdminActivity extends ActivityBase {

    ListView mListView;
    ProgressBar progressBar;
    TextView tvDiaSemana;

    int DiaSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cardapiodiaadmin);

        mListView = findViewById(R.id.listaCardapioDiaAdmin);
        progressBar = findViewById(R.id.progressBarCardapioDiaAdmin);
        tvDiaSemana = findViewById(R.id.tvDiaSemana);

        DiaSemana = getIntent().getIntExtra("diaSemana", 0);
        tvDiaSemana.setText(getIntent().getStringExtra("descricao"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        AtualizarListaPratos();
    }

    public void AdicionarPrato(View view)
    {
        Intent j = new Intent(this, SelecaoPratoDiaActivity.class);
        j.putExtra("diaSemana", DiaSemana);
        startActivity(j);
    }

    private void AtualizarListaPratos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoGET(cServidor + "/Pratos_DiaSemana/PratoDiaSemImagem/" + DiaSemana);
    }

    public void ExcluirPrato(final Pratos prato)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmação");
        builder.setMessage("Deseja Desvincular o Prato " + prato.nome + "?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Pratos_DiaSemana pds = new Pratos_DiaSemana();
                pds.diasemana = DiaSemana;
                pds.prato = new Pratos();
                pds.prato.pratoID = prato.pratoID;

                RequisicaoPOST(cServidor + "/Pratos_DiaSemana/DesvincularPratoDiaSemana", pds);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        Gson gson = new Gson();
        List<Pratos> pratos = Arrays.asList(gson.fromJson(resposta.toString(), Pratos[].class));

        final PratoDiaSemanaAdapter pratosDiaAdapter = new PratoDiaSemanaAdapter(this, R.layout.custompratosdiasemana, pratos);
        mListView.setAdapter(pratosDiaAdapter);

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        super.RetornoPOST(resposta);

        Toast.makeText(this, "Prato Desvinculado com Sucesso.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        AtualizarListaPratos();
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        AtualizarListaPratos();
    }
}