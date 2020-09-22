package uniftec.joao.com.projetoempreendedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class cardapiosemanal_clientes extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cardapiosemanal);
    }

    public void CardapioSegunda(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }

    public void CardapioTerca(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }

    public void CardapioQuarta(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }
    public void CardapioQuinta(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }
    public void CardapioSexta(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }
    public void CardapioSabado(View view)
    {
        Intent i = new Intent(this, cadastro.class);
        startActivity(i);
        finish();
    }
}