package uniftec.joao.com.projetoempreendedor;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class cardapiodia extends ActivityBase {

    TextView diaSemana;
    ListView mListView;
    int[] images = {R.drawable.lasanha,
                    R.drawable.salada_caprese,
                    R.drawable.macarrao};
    String[] Names = {"Lasanha",
                      "Salada Caprese",
                      "Macarrao"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cardapiodia);
        diaSemana = findViewById(R.id.textViewDiaSemana);
        diaSemana.setText(Sessao.diaSemana);
        mListView = findViewById(R.id.ListaCardapioDia);
        CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);
    }

    class CustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View views = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView mImageView = (ImageView) views.findViewById(R.id.ImageViewLista);
            TextView mTextView = views.findViewById(R.id.textViewPratosLista);

            mImageView.setImageResource(images[i]);
            mTextView.setText(Names[i]);
            return views;
        }
    }


}