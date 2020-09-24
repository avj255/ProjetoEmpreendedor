package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import uniftec.joao.com.projetoempreendedor.ActivityBase;

public class clientes extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cliente);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void CardapioDia(View view)
    {

        Sessao.descricaoDiaSemana = weekDayDescription();
        Sessao.diaSemana = weekDay();
        Intent i = new Intent(this, cardapiodia.class);
        startActivity(i);
    }

    public void CardapioSemanal(View view)
    {
        Intent i = new Intent(this, cardapiosemanal_clientes.class);
        startActivity(i);
    }
    public String weekDay() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                return "1";
            case Calendar.TUESDAY:
                return "2";
            case Calendar.WEDNESDAY:
                return "3";
            case Calendar.THURSDAY:
                return "4";
            case Calendar.FRIDAY:
                return "5";
            case Calendar.SATURDAY:
                return "6";
            case Calendar.SUNDAY:
                return "7";
        }
        return "";
    }
    public String weekDayDescription() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                return "Segunda-Feira";
            case Calendar.TUESDAY:
                return "Terça-Feira";
            case Calendar.WEDNESDAY:
                return "Quarta-Feira";
            case Calendar.THURSDAY:
                return "Quinta-Feira";
            case Calendar.FRIDAY:
                return "Sexta-Feira";
            case Calendar.SATURDAY:
                return "Sábado";
            case Calendar.SUNDAY:
                return "Domingo";
        }
        return "";
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.AlterarSenha:
                Intent i = new Intent(this, alterarSenha.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}