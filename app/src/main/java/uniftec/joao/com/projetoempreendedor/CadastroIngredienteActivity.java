package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;

public class CadastroIngredienteActivity extends ActivityBase {

    EditText edtNome;
    EditText edtPeso;
    EditText edtCaloria;
    ProgressBar progressBar;
    Button btnExcluirIngrediente;

    int idIngrediente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastroingrediente);

        edtNome = findViewById(R.id.edtNomeIngrediente);
        edtPeso = findViewById(R.id.edtPeso);
        edtCaloria = findViewById(R.id.edtCalorias);
        progressBar = findViewById(R.id.progressBarCadastroIngredientes);
        btnExcluirIngrediente = findViewById(R.id.botaoExcluirIngrediente);

        btnExcluirIngrediente.setVisibility(View.INVISIBLE);

        if (getIntent().hasExtra("id"))
        {
            idIngrediente = getIntent().getIntExtra("id", 0);
            edtNome.setText(getIntent().getStringExtra("nome"));
            edtPeso.setText(String.valueOf(getIntent().getDoubleExtra("peso", 0.0)));
            edtCaloria.setText(String.valueOf(getIntent().getDoubleExtra("calorias", 0.0)));

            btnExcluirIngrediente.setVisibility(View.VISIBLE);
        }
    }

    public void ExcluirIngrediente(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmação");
        builder.setMessage("Deseja Excluir o Ingrediente:"+edtNome.getText().toString()+"?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                desabilitaInteracao();
                RequisicaoDELETE(cServidor + "/Ingredientes/" + idIngrediente);
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

    public void SalvarIngrediente(View view)
    {
        if (edtNome.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Informe um nome para o Ingrediente.", Toast.LENGTH_LONG).show();
            return;
        }

        Ingredientes ingrediente = new Ingredientes();

        if (idIngrediente != 0)
            ingrediente.ingredienteID = idIngrediente;

        ingrediente.nome = edtNome.getText().toString().trim();

        try {
            ingrediente.peso = Double.parseDouble(edtPeso.getText().toString().trim());
        }
        catch (NumberFormatException e) {
            ingrediente.peso = 0.0;
        }

        try {
            ingrediente.calorias = Double.parseDouble(edtCaloria.getText().toString().trim());
        }
        catch (NumberFormatException e) {
            ingrediente.calorias = 0.0;
        }

        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoPOST(cServidor + "/Ingredientes", ingrediente);
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        super.RetornoPOST(resposta);

        Toast.makeText(this, "Ingrediente Cadastrado com Sucesso.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        finish();
    }

    @Override
    void RetornoDELETE(JSONObject resposta) {
        super.RetornoDELETE(resposta);

        Toast.makeText(this, "Ingrediente Excluído com Sucesso.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        finish();
    }
}