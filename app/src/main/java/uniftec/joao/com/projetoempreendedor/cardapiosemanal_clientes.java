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
        Sessao.descricaoDiaSemana = "Segunda-Feira";
        Sessao.diaSemana = "1";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }

    public void CardapioTerca(View view)
    {
        Sessao.descricaoDiaSemana = "Terça-Feira";
        Sessao.diaSemana = "2";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }

    public void CardapioQuarta(View view)
    {
        Sessao.descricaoDiaSemana = "Quarta-Feira";
        Sessao.diaSemana = "3";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
        finish();
    }
    public void CardapioQuinta(View view)
    {
        Sessao.descricaoDiaSemana = "Quinta-Feira";
        Sessao.diaSemana = "4";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }
    public void CardapioSexta(View view)
    {
        Sessao.descricaoDiaSemana = "Sexta-Feira";
        Sessao.diaSemana = "5";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }
    public void CardapioSabado(View view)
    {
        Sessao.descricaoDiaSemana = "Sábado";
        Sessao.diaSemana = "6";
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }
}