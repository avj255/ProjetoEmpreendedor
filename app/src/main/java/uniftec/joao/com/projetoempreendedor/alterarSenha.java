package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class alterarSenha extends ActivityBase  {

    EditText edtSenha;
    EditText edtNovaSenha;
    EditText edtConfirmaNovaSenha;
    ProgressBar progressBar;
    TextView edtAlteracaoInvalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alterarsenha);
        edtSenha= (EditText) findViewById(R.id.editTextAlterSenhaAtual);
        edtNovaSenha= (EditText) findViewById(R.id.editTextNovaSenha);
        edtConfirmaNovaSenha= (EditText) findViewById(R.id.editTextAlterarConfirmaSenha);
        progressBar = findViewById(R.id.progressBarAlterarSenha);
        edtAlteracaoInvalida = findViewById(R.id.textViewAlteracaoSenhaInvalida);
    }

    public void CancelarAlterarSenha(View view)
    {
        Intent i;

       // if (administradores)
       // {
             i = new Intent(this, administradores.class);
       // }
        //else
        //{
            // i = new Intent(this, clientes.class);
       // }
;
        startActivity(i);
        finish();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
//
//        Gson gson = new Gson();
//        Resposta respostaCadastro = gson.fromJson(resposta.toString(), Resposta.class);
//
//        if (respostaCadastro.codigoRetorno == 2)
//        {
//            edtCadastroInvalido.setText(respostaCadastro.descricao);
//            edtCadastroInvalido.setVisibility(View.VISIBLE);
//        } else {
//            Intent i = new Intent(this, administradores.class);
//            startActivity(i);
//            finish();
//        }
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }


    public void AlteraSenha(View view)

    {

        if (validaCampos())
        {
//            Usuarios usuario = new Usuarios();
//            usuario.usuario = edtUsuario.getText().toString();
//            usuario.senha = edtSenha.getText().toString();
//            usuario.administrador = 1; // Administrador
//            usuario.nome = edtNome.getText().toString();
//            usuario.email = edtEmail.getText().toString();
//            usuario.cpf = edtCpf.getText().toString();
//            usuario.cnpj = "";
//            usuario.senha = edtSenha.getText().toString();
//
//            edtCadastroInvalido.setVisibility(View.INVISIBLE);
//            progressBarCadastro.setVisibility(View.VISIBLE);
//            desabilitaInteracao();
//            RequisicaoPOST(cServidor + "/Usuarios/Cadastro", usuario);
        }

    }
    private boolean validaCampos()
    {
        Boolean res = false;
        String resposta = "";
        String senhaAtual = edtSenha.getText().toString();
        String novasenha = edtNovaSenha.getText().toString();
        String confirmaNovaSenha = edtConfirmaNovaSenha.getText().toString();

        if(res = isCampoVazio(senhaAtual))
        {
            edtSenha.requestFocus();
            resposta = "O Campo \"Senha Atual\" deve ser preenchido.";
        }
        else
        if(res = isCampoVazio(novasenha))
        {
            edtNovaSenha.requestFocus();
            resposta = "O Campo \"Nova Senha\" deve ser preenchido.";
        }
        else
        if(res = isCampoVazio(confirmaNovaSenha))
        {
            edtConfirmaNovaSenha.requestFocus();
            resposta = "O Campo \"Confirmar Nova Senha\" deve ser preenchido.";
        }
        else
        if (res = !novasenha.equals(confirmaNovaSenha))
        {
            edtConfirmaNovaSenha.requestFocus();
            resposta = "Senhas não conferem.";
        }

        if (res)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
            dlg.setTitle("Aviso");
            dlg.setMessage(resposta);
            dlg.setNeutralButton("OK",null);
            dlg.show();
            return false;
        }

        return true;
    }

    private boolean isCampoVazio(String valor)
    {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }


}