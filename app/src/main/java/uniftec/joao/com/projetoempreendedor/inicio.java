package uniftec.joao.com.projetoempreendedor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class inicio extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial);

        Button botaoCadastro = (Button) findViewById(R.id.botaoCadastrar);
        Button botaoEntrar = (Button) findViewById(R.id.botaoEntrar);

        botaoEntrar.setOnClickListener(this);
        botaoCadastro.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botaoCadastrar:
                Intent i = new Intent(this, cadastrar.class);
                startActivity(i);
                finish();
                // do your code
                break;
            case R.id.botaoEntrar:
                // do your code
                break;
            default:
                break;
        }
    }
}