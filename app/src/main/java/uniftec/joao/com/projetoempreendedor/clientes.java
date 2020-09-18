package uniftec.joao.com.projetoempreendedor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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