package uniftec.joao.com.projetoempreendedor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_DiaSemana;
import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.RespostaLogin;

public class pratos extends ActivityBase {

    ListView Ingredientes;
    ImageView imagePrato;
    TextView valorprato;
    EditText quantidade;
    ProgressBar progressBar;
    TextView tvNomePrato;
    Button BtnPedido;
    TextView tvQtde;
    View view4;
    int listViewTouchAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pratos);
        imagePrato = findViewById(R.id.ImagePrato);
        Ingredientes = findViewById(R.id.ListaIngredientes);
        valorprato = findViewById(R.id.textViewValorPrato);
        quantidade = findViewById(R.id.editTextQuantidade);
        progressBar = findViewById(R.id.progressBar);
        tvNomePrato = findViewById(R.id.textViewPrato);
        BtnPedido = findViewById(R.id.botaoRealizarPedido);
        tvQtde = findViewById(R.id.textViewPrato6);
        view4 = findViewById(R.id.view4);


        setListViewScrollable(Ingredientes);
        if (Sessao.diaSemana != Utilidades.WeekDay())
        {
            BtnPedido.setVisibility(View.INVISIBLE);
            tvQtde.setVisibility(View.INVISIBLE);
            quantidade.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
        }
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

        tvNomePrato.setText(Sessao.prato.nome);
        valorprato.setText("R$ " + String.format("%.2f", Sessao.prato.valor));

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    public void RegistrarPedido(View view)
    {
        try {
            Integer.parseInt(quantidade.getText().toString());
        } catch (Exception e)
        {
            Toast.makeText(this, "Digite uma quantidade válida", Toast.LENGTH_LONG).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmação do Pedido")
                .setMessage("\nQuantidade: " + quantidade.getText().toString() + "\nValor Total: " + "R$ " + String.format("%.2f", Sessao.prato.valor * Integer.parseInt(quantidade.getText().toString())))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Pedidos pedido = new Pedidos();
                        pedido.mesa = "0";
                        pedido.prato = Sessao.prato.pratoID;
                        pedido.quantidade = Integer.parseInt(quantidade.getText().toString());
                        pedido.usuario = Sessao.usuarioLogado.userID;
                        pedido.valor = Sessao.prato.valor * pedido.quantidade;
                        pedido.situacao = 1;
                        progressBar.setVisibility(View.VISIBLE);
                        desabilitaInteracao();
                        RequisicaoPOST(cServidor + "/Pedidos", pedido);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        super.RetornoPOST(resposta);

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        Gson gson = new Gson();
        Resposta resp = gson.fromJson(resposta.toString(), Resposta.class);

        if (resp.codigoRetorno == 1) {
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
            dlg.setTitle("Pedido");
            dlg.setMessage("Pedido efetuado com sucesso!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        } else
        {
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
            dlg.setTitle("Pedido");
            dlg.setMessage("Ocorreu um erro ao tentar realizar o seu pedido.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListViewScrollable(final ListView list) {
        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listViewTouchAction = event.getAction();
                if (listViewTouchAction == MotionEvent.ACTION_MOVE)
                {
                    list.scrollBy(0, 1);
                }
                return false;
            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (listViewTouchAction == MotionEvent.ACTION_MOVE)
                {
                    list.scrollBy(0, -1);
                }
            }
        });
    }

}