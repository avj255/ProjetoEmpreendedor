package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class administradores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_administrador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void EditarCardapios(View view)
    {
        Intent i = new Intent(this, CardapioSemanalAdminActivity.class);
        startActivity(i);
    }

    public void CadastrarIngredientes(View view)
    {
        Intent i = new Intent(this, IngredientesActivity.class);
        startActivity(i);
    }

    public void CadastrarPratos(View view)
    {

       // Sessao.descricaoDiaSemana = weekDayDescription();
        //Sessao.diaSemana = Utilidades.WeekDay();
        Intent i = new Intent(this, cadastrarPratos.class);
        startActivity(i);
    }

    public void Pedidos(View view)
    {
        Intent i = new Intent(this, AcompanhamentoPedidosAdminActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.AdicionarAdm:
                i = new Intent(this, cadastroAdm.class);
                startActivity(i);
                break;
            case R.id.AlterarSenha:
                i = new Intent(this, alterarSenha.class);
                startActivity(i);
                break;
            case R.id.graficos:
                i = new Intent(this, graficos.class);
                startActivity(i);
                break;
            case R.id.alterarCadastro:
                i = new Intent(this, alterarcadastro.class);
                startActivity(i);
                break;
            case R.id.Sair:
                i = new Intent(this, login.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}