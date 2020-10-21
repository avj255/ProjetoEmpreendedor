package uniftec.joao.com.projetoempreendedor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos;
import uniftec.joao.com.projetoempreendedor.Entidades.Pratos_Ingredientes;
import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class editarPratos extends ActivityBase {

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
        if (!(Sessao.idPrato == null))
        {
            atualizarPratos();
        }
        else
        {
            List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
            pratosIngredientesAdapter = new PratosEditarAdapter(this, R.layout.custompratosingredientescadastro, ingredientes);
            mListView.setAdapter(pratosIngredientesAdapter);
        }
        editouImagem = false;
    }

    public void SalvarPrato(View view)
    {
        AtualizaSalvarPrato();
    }

    public void AtualizaSalvarPrato(){

        Pratos prato = new Pratos();

        prato.nome = editTextNome.getText().toString();
        prato.valor = Double.parseDouble(editValor.getText().toString());

        //encode image to base64 string
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

        if (Sessao.idPrato == null)
            prato.pratoID = 0;

        tipoRequisicao = "SalvarPrato";
        RequisicaoPOST(cServidor + "/Pratos", prato);
    }


    @Override
    void RetornoPOST(JSONObject resposta) {
        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        Gson gson = new Gson();
        Resposta respostaCadastro = gson.fromJson(resposta.toString(), Resposta.class);

        if (respostaCadastro.codigoRetorno == 2)
        {
            textviewAtualizaPrato.setText(respostaCadastro.descricao);
            textviewAtualizaPrato.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "Prato Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, cadastrarPratos.class);
            startActivity(i);
        }
    }

     void atualizarPratos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "CarregarPrato";
        RequisicaoGET(cServidor + "/Pratos/"+ Sessao.idPrato.toString());
        return;
    }
    void atualizaListaIngredientes()
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


    public void AtualizarImagem() {
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
            atualizarPratos();
        }
        else
        if (tipoRequisicao.equals("SalvarPrato"))
        {
            AtualizaSalvarPrato();
        }
    }
}