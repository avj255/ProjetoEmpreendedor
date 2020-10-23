package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CardapioSemanalAdminActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cardapiosemanaladmin);
    }

    public void CardapioSegunda(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 1);
        i.putExtra("descricao", "Segunda-Feira");
        startActivity(i);
    }

    public void CardapioTerca(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 2);
        i.putExtra("descricao", "Terça-Feira");
        startActivity(i);
    }

    public void CardapioQuarta(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 3);
        i.putExtra("descricao", "Quarta-Feira");
        startActivity(i);
    }
    public void CardapioQuinta(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 4);
        i.putExtra("descricao", "Quinta-Feira");
        startActivity(i);
    }
    public void CardapioSexta(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 5);
        i.putExtra("descricao", "Sexta-Feira");
        startActivity(i);
    }
    public void CardapioSabado(View view)
    {
        Intent i = new Intent(this, CardapioDiaAdminActivity.class);
        i.putExtra("diaSemana", 6);
        i.putExtra("descricao", "Sábado");
        startActivity(i);
    }
}