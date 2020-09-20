package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.InputMismatchException;

import uniftec.joao.com.projetoempreendedor.Entidades.Resposta;
import uniftec.joao.com.projetoempreendedor.Entidades.RespostaLogin;
import uniftec.joao.com.projetoempreendedor.Entidades.Usuarios;

public class cadastro extends ActivityBase {

     EditText edtCpf;
     EditText edtUsuario;
     EditText edtEmail;
     EditText edtConfirmaEmail;
     EditText edtNome;
     EditText edtSenha;
     EditText edtConfirmaSenha;
     ProgressBar progressBarCadastro;
     TextView edtCadastroInvalido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        edtCpf= (EditText) findViewById(R.id.editTextCpf);
        edtUsuario= (EditText) findViewById(R.id.edtitTextCadastroUsuario);
        edtEmail= (EditText) findViewById(R.id.editTextEmail);
        edtConfirmaEmail= (EditText) findViewById(R.id.editTextConfirmaEmail);
        edtNome= (EditText) findViewById(R.id.editTextNome);
        edtSenha= (EditText) findViewById(R.id.editTextSenha);
        edtConfirmaSenha= (EditText) findViewById(R.id.editTextConfirmaSenha);
        progressBarCadastro = findViewById(R.id.progressBarCadastro);
        edtCadastroInvalido = findViewById(R.id.textViewCadastroInválido);
        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        Sessao.usuarioLogado = new Usuarios();
    }

    private boolean validaCampos()
    {
        Boolean res = false;
        String resposta = "";
        String cpf = edtCpf.getText().toString();
        String usuario = edtUsuario.getText().toString();
        String email = edtEmail.getText().toString();
        String confirmaEmail = edtConfirmaEmail.getText().toString();
        String nome = edtNome.getText().toString();
        String senha = edtSenha.getText().toString();
        String confirmaSenha = edtConfirmaSenha.getText().toString();

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
                    else
                        if(res = !isEmailValido(confirmaEmail))
                        {
                            edtConfirmaEmail.requestFocus();
                            resposta = "E-mail inválido.";
                        }
                        else
                            if(res = !email.equals(confirmaEmail))
                            {
                                edtConfirmaEmail.requestFocus();
                                resposta = "E-mails não conferem.";
                            }
                            else
                                if(res = isCampoVazio(senha))
                                {
                                    edtSenha.requestFocus();
                                    resposta = "O Campo \"Senha\" deve ser preenchido.";
                                }
                                else
                                    if(res = isCampoVazio(confirmaSenha))
                                    {
                                        edtConfirmaSenha.requestFocus();
                                        resposta = "O Campo \"Confirma Senha\" deve ser preenchido.";
                                    }
                                     else
                                        if (res = !senha.equals(confirmaSenha))
                                        {
                                            edtConfirmaSenha.requestFocus();
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

    public void CadastrarNovoUsuario(View view)
    {
        if (validaCampos())
        {
            Usuarios usuario = new Usuarios();
            usuario.usuario = edtUsuario.getText().toString();
            usuario.senha = edtSenha.getText().toString();
            usuario.administrador = 0; // Clientes
            usuario.nome = edtNome.getText().toString();
            usuario.email = edtEmail.getText().toString();
            usuario.cpf = edtCpf.getText().toString();
            usuario.cnpj = "";
            usuario.senha = edtSenha.getText().toString();

            edtCadastroInvalido.setVisibility(View.INVISIBLE);
            progressBarCadastro.setVisibility(View.VISIBLE);
            desabilitaInteracao();
            RequisicaoPOST(cServidor + "/Usuarios/Cadastro", usuario);
        }
    }

    @Override
    void RetornoPOST(JSONObject resposta) {
        progressBarCadastro.setVisibility(View.INVISIBLE);
        habilitaInteracao();

        Gson gson = new Gson();
        RespostaLogin respostaCadastro = gson.fromJson(resposta.toString(), RespostaLogin.class);

        if (respostaCadastro.codigoRetorno == 2)
        {
            edtCadastroInvalido.setText(respostaCadastro.descricao);
            edtCadastroInvalido.setVisibility(View.VISIBLE);
        } else {
            Sessao.usuarioLogado.usuario = edtUsuario.getText().toString();
            Sessao.usuarioLogado.nome = edtNome.getText().toString();
            Sessao.usuarioLogado.administrador = 0;
            Intent i = new Intent(this, clientes.class);
            startActivity(i);
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
        Intent i = new Intent(this, login.class);
        startActivity(i);
        finish();
    }

}