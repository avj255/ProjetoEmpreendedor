package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;

public class pratos extends ActivityBase {

    ListView Ingredientes;
    ImageView imagePrato;
    TextView valorprato;
    EditText quantidade;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pratos);
        imagePrato = findViewById(R.id.ImagePrato);
        Ingredientes = findViewById(R.id.ListaIngredientes);
        valorprato = findViewById(R.id.textViewValorPrato);
        quantidade = findViewById(R.id.editTextQuantidade);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarPrato();
    }

    void atualizarPrato()
    {

        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();

        if (Sessao.prato.ingredientes != null)
        {
            PratoIngredientesAdapter pratosAdapter = new PratoIngredientesAdapter(this, R.layout.custompratoingredientes, Sessao.prato.ingredientes);
            Ingredientes.setAdapter(pratosAdapter);
        }

        if (Sessao.prato.foto != null) {
            byte[] decodedString = Base64.decode(Sessao.prato.foto, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imagePrato.setImageBitmap(decodedByte);
        }

        valorprato.setText(Double.toString(Sessao.prato.valor));

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }



}