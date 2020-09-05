package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import org.json.JSONObject;

import uniftec.joao.com.projetoempreendedor.Entidades.RespostaLogin;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class inicio extends ActivityBase {

    ProgressBar progressBar;
    EditText edtUsuario;
    EditText edtSenha;
    TextView edtLoginInvalido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial);

        progressBar = findViewById(R.id.progressBar);
        edtUsuario = findViewById(R.id.editTextUsuario);
        edtSenha = findViewById(R.id.editTextSenhaLogin);
        edtLoginInvalido = findViewById(R.id.textViewLoginInvalido);
    }

    public void Login(View view)
    {
        Usuarios usuario = new Usuarios();
        usuario.usuario = edtUsuario.getText().toString();
        usuario.senha = edtSenha.getText().toString();
        edtLoginInvalido.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        RequisicaoPOST(cServidor + "/Usuarios/Login", usuario);
    }

    public void Cadastrar(View view)
    {
        Intent i = new Intent(this, cadastrar.class);
        startActivity(i);
        finish();
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        Gson gson = new Gson();
        RespostaLogin respostaLogin = gson.fromJson(resposta.toString(), RespostaLogin.class);

        if (respostaLogin.codigoRetorno == 2)
        {
            edtLoginInvalido.setVisibility(View.VISIBLE);
        } else {
            if (respostaLogin.administrador == 1) {
                // Abre tela de administrador
            } else {
                // Abre tela de usuário
            }
        }
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }
}