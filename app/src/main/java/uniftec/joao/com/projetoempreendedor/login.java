package uniftec.joao.com.projetoempreendedor;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface.OnShowListener;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import org.json.JSONObject;

import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.RespostaLogin;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class login extends ActivityBase  implements OnShowListener, OnClickListener {

    ProgressBar progressBar;
    EditText edtUsuario;
    EditText edtSenha;
    EditText edtUsuarioRecuperaSenha;
    EditText edtEmailRecuperaSenha;
    TextView edtLoginInvalido;
    AlertDialog dialog;
    TextView edtUsuarioEmailInvalido;
    ProgressBar progressBarRecuperarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        progressBar = findViewById(R.id.progressBar);
        edtUsuario = findViewById(R.id.editTextUsuario);
        edtSenha = findViewById(R.id.editTextSenhaLogin);
        edtLoginInvalido = findViewById(R.id.textViewLoginInvalido);

        Sessao.usuarioLogado = new Usuarios();

        // Debug
        //Sessao.usuarioLogado.userID = 4;
        //Sessao.usuarioLogado.usuario = "Usuario Debug";
        //Sessao.usuarioLogado.nome = "Usuario Debug";
        //Sessao.usuarioLogado.administrador = 0;

        //Intent i = new Intent(this, administradores.class);
        //startActivity(i);
        //finish();
    }

    private boolean isCampoVazio(String valor)
    {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    private boolean validaCampos()
    {
        Boolean res = false;
        String resposta = "";
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();

        if (res = isCampoVazio(usuario))
        {
            edtUsuario.requestFocus();
            resposta = "O Campo \"Usuário\" deve ser preenchido.";
        }
        else
        if (res = isCampoVazio(senha))
        {
            edtSenha.requestFocus();
            resposta = "O Campo \"Senha\" deve ser preenchido.";
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

    public void Login(View view)
    {
        if (validaCampos())
        {
            Usuarios usuario = new Usuarios();
            usuario.usuario = edtUsuario.getText().toString();
            usuario.senha = edtSenha.getText().toString();
            edtLoginInvalido.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            desabilitaInteracao();
            tipoRequisicao ="";
            RequisicaoPOST(cServidor + "/Usuarios/Login", usuario);
        }
    }

    public void RecuperarSenha(View view)
    {
        openDialogRecuperarSenha();
    }

    public void Cadastrar(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
    }

    public void openDialogRecuperarSenha()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.tela_dialog, null);
        builder.setView(view).setTitle(R.string.TextoCaixaDialogoEmail).setNegativeButton("Voltar",null).setPositiveButton("Enviar", null);
        edtUsuarioRecuperaSenha = view.findViewById(R.id.editTextUsuarioRecuperarSenha);
        edtEmailRecuperaSenha = view.findViewById(R.id.editTextEmailRecuperarSenha);
        progressBarRecuperarSenha = view.findViewById(R.id.progressBarRecuperaSenha);
        edtUsuarioEmailInvalido = view.findViewById(R.id.textViewRedefinicaoSenhaInvalido);
        dialog = builder.create();
        dialog.setOnShowListener(this);
        dialog.show();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {

        if (tipoRequisicao.equals("RecuperarSenha"))
        {
            progressBarRecuperarSenha.setVisibility(View.INVISIBLE);
            habilitaInteracao();

            Gson gson = new Gson();
            Resposta respostaRedefinirSenha = gson.fromJson(resposta.toString(), Resposta.class);

            if (respostaRedefinirSenha.codigoRetorno == 2)
            {
                edtUsuarioEmailInvalido.setVisibility(View.VISIBLE);
            }
            else
                {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), respostaRedefinirSenha.descricao, Toast.LENGTH_SHORT).show();
                }
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            habilitaInteracao();

            Gson gson = new Gson();
            RespostaLogin respostaLogin = gson.fromJson(resposta.toString(), RespostaLogin.class);

            if (respostaLogin.codigoRetorno == 2)
            {
                edtLoginInvalido.setVisibility(View.VISIBLE);
            } else {
                Sessao.usuarioLogado.userID = respostaLogin.userID;
                Sessao.usuarioLogado.usuario = respostaLogin.usuario;
                Sessao.usuarioLogado.nome = respostaLogin.nome;
                Sessao.usuarioLogado.administrador = respostaLogin.administrador;

                if (respostaLogin.administrador == 1) {
                    Intent i = new Intent(this, administradores.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(this, clientes.class);
                    startActivity(i);
                }
            }
        }
    }

    @Override
    void ErroRequisicao(VolleyError erro) {

        if (tipoRequisicao.equals("RedefinirSenha"))
        {
            super.ErroRequisicao(erro);
            progressBarRecuperarSenha.setVisibility(View.INVISIBLE);
            habilitaInteracao();
        }
        else
        {
            super.ErroRequisicao(erro);
            progressBar.setVisibility(View.INVISIBLE);
            habilitaInteracao();
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

        Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b.setId(DialogInterface.BUTTON_POSITIVE);
        b.setOnClickListener(this);
    }

    private boolean validaCamposRecuperarSenha()
    {
        Boolean res = false;
        String resposta = "";
        String usuario = edtUsuarioRecuperaSenha.getText().toString();
        String email   = edtEmailRecuperaSenha.getText().toString();

        if (res = isCampoVazio(usuario))
        {
            edtUsuarioRecuperaSenha.requestFocus();
            resposta = "O Campo \"Usuário\" deve ser preenchido.";
        }
        else
        if (res = isCampoVazio(email))
        {
            edtEmailRecuperaSenha.requestFocus();
            resposta = "O Campo \"E-mail\" deve ser preenchido.";
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

    @Override
    public void onClick(View view) {

        if (view.getId() == DialogInterface.BUTTON_POSITIVE) {

            if (validaCamposRecuperarSenha())
            {
                Usuarios usuario = new Usuarios();
                usuario.email = edtEmailRecuperaSenha.getText().toString();
                usuario.usuario = edtUsuarioRecuperaSenha.getText().toString();
                edtUsuarioEmailInvalido.setVisibility(View.INVISIBLE);
                progressBarRecuperarSenha.setVisibility(View.VISIBLE);
                desabilitaInteracao();
                tipoRequisicao = "RecuperarSenha";
                RequisicaoPOST(cServidor + "/Usuarios/RedefinirSenha", usuario);
            }
        }
    }
}