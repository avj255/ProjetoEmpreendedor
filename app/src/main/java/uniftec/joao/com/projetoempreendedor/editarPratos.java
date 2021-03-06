package uniftec.joao.com.projetoempreendedor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;

public class editarPratos extends ActivityBase  {

    ProgressBar progressBar;
    EditText editTextNome;
    EditText editValor;
    ListView mListView;
    TextView textviewAtualizaPrato;
    ImageView ImagemPrato;
    Ingredientes[] arrayIngredientes;
    PratosEditarAdapter pratosIngredientesAdapter;
    Pratos[] pratos;
    Boolean editouImagem;
    Button btnExcluir;
    int listViewTouchAction;

    private static final int PICK_PHOTO_FOR_AVATAR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editarpratos);
        progressBar = findViewById(R.id.progressBarEditarPrato);
        mListView = findViewById(R.id.ListViewIngredientes);
        editTextNome = findViewById(R.id.edtitTextNomePrato);
        editValor = findViewById(R.id.editTextValorPrato);
        ImagemPrato = findViewById(R.id.ImagePratoEditar);
        textviewAtualizaPrato = findViewById(R.id.textViewPratoAtualizado);
        btnExcluir = findViewById(R.id.botaoExcluir);
        setListViewScrollable(mListView);

        if (!(Sessao.idPrato == null))
        {
            btnExcluir.setEnabled(true);
            getPrato();
        }
        else
        {
            btnExcluir.setEnabled(false);
            List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
            pratosIngredientesAdapter = new PratosEditarAdapter(this, R.layout.custompratosingredientescadastro, ingredientes);
            mListView.setAdapter(pratosIngredientesAdapter);
        }
        editTextNome.requestFocus();
        editouImagem = false;
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
    private boolean isCampoVazio(String valor)
    {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    private boolean validaCamposPrato()
    {
        Boolean res = false;
        String resposta = "";
        String nome   = editTextNome.getText().toString();
        String valor = editValor.getText().toString();

        if (res = isCampoVazio(nome))
        {
            editTextNome.requestFocus();
            resposta = "O Campo \"Nome\" deve ser preenchido.";
        }
        else
        if (res = isCampoVazio(valor))
        {
            editValor.requestFocus();
            resposta = "O Campo \"Valor\" deve ser preenchido.";
        }
        else
            if (res = valor.equals("0"))
            {
                editValor.requestFocus();
                resposta = "O Campo \"Valor\" deve ser preenchido.";
            }

        if (res)
        {
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(this,R.style.AlertDialogTheme);
            dlg.setTitle("Aviso");
            dlg.setMessage(resposta);
            dlg.setNeutralButton("OK",null);
            dlg.show();
            return false;
        }

        return true;
    }

    public void ExcluirImagem(View view)
    {
        ImagemPrato.setImageResource(0);
        editouImagem = false;
    }

    public void SalvarPrato(View view)
    {
        AtualizaSalvarPrato();
    }

    public void excluirPrato(View view)
    {
        DeletarPrato();
    }


    private void DeletarPrato()
    {
        if (!(pratos == null))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirmação");
            builder.setMessage("Deseja Excluir o prato:"+pratos[0].nome+"?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    progressBar.setVisibility(View.VISIBLE);
                    desabilitaInteracao();
                    tipoRequisicao = "CarregarPrato";
                    RequisicaoDELETE(cServidor + "/Pratos/"+ pratos[0].pratoID);
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
    }
    @Override
    void RetornoDELETE(JSONObject resposta) {

        Gson gson = new Gson();
        Resposta respostaCadastro = gson.fromJson(resposta.toString(), Resposta.class);

        if (respostaCadastro.codigoRetorno == 2)
        {
            textviewAtualizaPrato.setText(respostaCadastro.descricao);
            textviewAtualizaPrato.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            habilitaInteracao();
            Toast.makeText(getApplicationContext(), "Prato Excluído com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void AtualizaSalvarPrato(){

        if (validaCamposPrato())
        {
            Pratos prato;
            if (Sessao.idPrato == null)
            {
                prato = new Pratos();
                prato.pratoID = 0;
            }
            else
            {
                prato = pratos[0];
            }

            prato.nome = editTextNome.getText().toString();
            prato.valor = Double.parseDouble(editValor.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
            desabilitaInteracao();

            if (editouImagem)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BitmapDrawable drawable = (BitmapDrawable) ImagemPrato.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                prato.foto = imageString;
            }
            else
                prato.foto = null;

            prato.pratos_Ingredientes = new ArrayList<Pratos_Ingredientes>();
            prato.modopreparo = "";

            for (Ingredientes ingrediente: pratosIngredientesAdapter.ArrayIngredientes)
            {
                Pratos_Ingredientes pi = new Pratos_Ingredientes();
                pi.ingredienteID = ingrediente.ingredienteID;
                pi.pratoID = null;
                prato.pratos_Ingredientes.add(pi);
            }

            tipoRequisicao = "SalvarPrato";
            RequisicaoPOST(cServidor + "/Pratos", prato);
        }
    }



    @Override
    void RetornoPOST(JSONObject resposta) {
        Gson gson = new Gson();
        Resposta respostaCadastro = gson.fromJson(resposta.toString(), Resposta.class);

        if (respostaCadastro.codigoRetorno == 2)
        {
            textviewAtualizaPrato.setText(respostaCadastro.descricao);
            textviewAtualizaPrato.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            habilitaInteracao();
            Toast.makeText(getApplicationContext(), "Prato Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

     private void getPrato()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "CarregarPrato";
        RequisicaoGET(cServidor + "/Pratos/"+ Sessao.idPrato.toString());
        return;
    }
    private void atualizaListaIngredientes()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "CadastrarIngredientes";
        RequisicaoGET(cServidor + "/Ingredientes");
    }


    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal,
                                      float newWidth, float newHeight) {
        Bitmap novoBmp = null;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;

        //Calcula escala em percentagem do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;

        // Criando uma matrix para manipulação da imagem BitMap
        Matrix matrix = new Matrix();

        // Definindo a proporção da escala para o matrix
        matrix.postScale(scalaW, scalaH);

        //criando o novo BitMap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
    }


    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);

        if (tipoRequisicao.equals("CadastrarIngredientes")) {

            Gson gson = new Gson();
            arrayIngredientes = gson.fromJson(resposta.toString(), Ingredientes[].class);

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
            View mView = getLayoutInflater().inflate(R.layout.tela_dialogingredientes, null);
            mBuilder.setTitle("Escolha um Ingrediente");
            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.Spinneringredientes);

            final AdicionarIngredientesAdapter adapter = new AdicionarIngredientesAdapter(editarPratos.this,
                    android.R.layout.simple_spinner_item,
                    arrayIngredientes);
            mSpinner.setAdapter(adapter); // Set the custom adapter to the spinner

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mBuilder.setView(mView);
            mBuilder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Boolean ExisteIngrediente = false;
                    Ingredientes ingrediente = new Ingredientes();
                    ingrediente.nome = adapter.getItem(mSpinner.getSelectedItemPosition()).nome;
                    ingrediente.ingredienteID = adapter.getItem(mSpinner.getSelectedItemPosition()).ingredienteID;

                    for(Ingredientes list : pratosIngredientesAdapter.ArrayIngredientes){
                        if (list.nome.equals(ingrediente.nome))
                        {
                            ExisteIngrediente = true;
                        }
                    }
                    if(!ExisteIngrediente)
                    {
                        pratosIngredientesAdapter.insert(ingrediente, 0);
                    }
                    else
                    {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(editarPratos.this,R.style.AlertDialogTheme);
                        dlg.setTitle("Aviso");
                        dlg.setMessage("Esse Ingrediente já está na lista!");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }
                }
            });
            AlertDialog b = mBuilder.create();
            b.show();
            progressBar.setVisibility(View.INVISIBLE);
            habilitaInteracao();
        }
        else
            if (tipoRequisicao.equals("CarregarPrato"))
            {
                List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();

                Gson gson = new Gson();
                pratos = gson.fromJson(resposta.toString(), Pratos[].class);


                for (Ingredientes pratosIngredientes : pratos[0].ingredientes)
                {
                    ingredientes.add(gson.fromJson(gson.toJson(pratosIngredientes), Ingredientes.class));
                }

                pratosIngredientesAdapter = new PratosEditarAdapter(this, R.layout.custompratosingredientescadastro, ingredientes);
                mListView.setAdapter(pratosIngredientesAdapter);
                editTextNome.setText(pratos[0].nome);
                editValor.setText(pratos[0].valor.toString());

                if (pratos[0].foto != null) {
                    byte[] decodedString = Base64.decode(pratos[0].foto, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ImagemPrato.setImageBitmap(decodedByte);
                }
                progressBar.setVisibility(View.INVISIBLE);
                habilitaInteracao();
            }
    }

    public void EditarImagem(View view)
    {
        AtualizarImagem();
    }

    public void AdicionarIngrediente(View view)
    {
        atualizaListaIngredientes();
    }


    private void AtualizarImagem() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(editarPratos.this,R.style.AlertDialogTheme);
                dlg.setTitle("Aviso");
                dlg.setMessage("Erro ao carregar imagem!");
                dlg.setNeutralButton("OK",null);
                dlg.show();
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream,null,null);
                bitmap = resizeImage(this, bitmap, 100, 100);
                ImagemPrato.setImageBitmap(bitmap);
                editouImagem = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        if (tipoRequisicao.equals("CadastrarIngredientes"))
        {
            atualizaListaIngredientes();
        }else
        if (tipoRequisicao.equals("CarregarPrato"))
        {
            getPrato();
        }
        else
        if (tipoRequisicao.equals("SalvarPrato"))
        {
            AtualizaSalvarPrato();
        }
    }
}