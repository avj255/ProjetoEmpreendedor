package uniftec.joao.com.projetoempreendedor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class cadastrar extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        Button botaoConfirmaCadastro = (Button) findViewById(R.id.botaoConfirmarCadastro);
        botaoConfirmaCadastro.setOnClickListener(this);
        Button botaoCancelar = (Button) findViewById(R.id.botaoCancelarCadastro);
        botaoCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botaoConfirmarCadastro:
//                Intent i = new Intent(Cadastrar.this, Inicio.class);
//                startActivity(i);
//                finish();
                break;
            case R.id.botaoCancelarCadastro:
                Intent i = new Intent(cadastrar.this, inicio.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }

    }
}