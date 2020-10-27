package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.VolleyError;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.InputMismatchException;

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;
import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.RespostaLogin;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class alterarcadastro extends ActivityBase {

     EditText edtCpf;
     EditText edtUsuario;
     EditText edtEmail;
     EditText edtNome;
     ProgressBar progressBarCadastro;
     TextView edtCadastroInvalido;
     int adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alterarcadastro);
        edtCpf= (EditText) findViewById(R.id.editTextAlterarCpf);
        edtUsuario= (EditText) findViewById(R.id.EditTextAlterarUsuario);
        edtEmail= (EditText) findViewById(R.id.EditTextAlterarEmail);
        edtNome= (EditText) findViewById(R.id.editTextAlterarNome);
        progressBarCadastro = findViewById(R.id.progressBarAlterarCadastro);
        edtCadastroInvalido = findViewById(R.id.textviewAlteracaoCadastroInvalida);
        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        adm = Sessao.usuarioLogado.administrador;

        edtCpf.setText(Sessao.usuarioLogado.cpf);
        edtUsuario.setText(Sessao.usuarioLogado.usuario);
        edtEmail.setText(Sessao.usuarioLogado.email);
        edtNome.setText(Sessao.usuarioLogado.nome);

    }

    private boolean validaCampos()
    {
        Boolean res = false;
        String resposta = "";
        String cpf = edtCpf.getText().toString();
        String usuario = edtUsuario.getText().toString();
        String email = edtEmail.getText().toString();
        String nome = edtNome.getText().toString();

        if (res = isCampoVazio(usuario))
        {
            edtUsuario.requestFocus();
            resposta = "O Campo \"Usuário\" deve ser preenchido.";
        }
        else
            if (res = isCampoVazio(nome))
            {
                edtNome.requestFocus();
                resposta = "O Campo \"Nome\" deve ser preenchido.";
            }
            else
                if (res = !isCPfValido(MaskEditUtil.unmask(cpf)))
                {
                    edtCpf.requestFocus();
                    resposta = "CPF inválido.";
                }
                else
                    if (res = !isEmailValido(email))
                    {
                        edtEmail.requestFocus();
                        resposta = "E-mail inválido.";
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

    private boolean isCPfValido(String cpf)
    {
        if  (TextUtils.isEmpty(cpf) || cpf.trim().isEmpty())
            return (false);
        else
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    private boolean isEmailValido(String email)
    {
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void AlterarCadastro(View view)
    {
        if (validaCampos())
        {

            Usuarios usuario = new Usuarios();
            usuario.userID = Sessao.usuarioLogado.userID;
            usuario.usuario = edtUsuario.getText().toString();
            usuario.nome = edtNome.getText().toString();
            usuario.email = edtEmail.getText().toString();
            usuario.cpf = edtCpf.getText().toString();

            edtCadastroInvalido.setVisibility(View.INVISIBLE);
            progressBarCadastro.setVisibility(View.VISIBLE);
            desabilitaInteracao();
            RequisicaoPOST(cServidor + "/Usuarios/Cadastro", usuario);
        }
    }

    @Override
    void RetornoPOST(JSONObject resposta) {

        Gson gson = new Gson();
        Resposta respostaCadastro = gson.fromJson(resposta.toString(), Resposta.class);

        if (respostaCadastro.codigoRetorno == 2)
        {
            edtCadastroInvalido.setText(respostaCadastro.descricao);
            edtCadastroInvalido.setVisibility(View.VISIBLE);
        } else {

           // Sessao.usuarioLogado = new Usuarios();
            Sessao.usuarioLogado.userID  = Integer.parseInt(respostaCadastro.descricao);
            Sessao.usuarioLogado.usuario = edtUsuario.getText().toString();
            Sessao.usuarioLogado.nome    = edtNome.getText().toString();
            Sessao.usuarioLogado.administrador = adm;
            Sessao.usuarioLogado.cpf = edtCpf.getText().toString();
            Sessao.usuarioLogado.email = edtEmail.getText().toString();
            progressBarCadastro.setVisibility(View.INVISIBLE);
            habilitaInteracao();
            Toast.makeText(getApplicationContext(), "Cadastro Alterado com Sucesso!", Toast.LENGTH_SHORT).show();
            finish();
               }
    }

    @Override
    void ErroRequisicao(VolleyError erro) {
        super.ErroRequisicao(erro);
        progressBarCadastro.setVisibility(View.INVISIBLE);
        habilitaInteracao();
    }

    public void Cancelar(View view)
    {
        finish();
    }

}