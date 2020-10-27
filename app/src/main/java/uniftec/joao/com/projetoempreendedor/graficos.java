package uniftec.joao.com.projetoempreendedor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.data.Set;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uniftec.joao.com.projetoempreendedor.Entidades.Pedidos;



public class graficos extends ActivityBase implements AdapterView.OnItemSelectedListener  {

    AnyChartView grafico;
    ProgressBar progressBar;
    String[] graficos = { "Gráfico - Top 5 Pratos Pedidos", "Gráfico - Situações"};
    Spinner spin;
    private Pie graf;
    List<DataEntry> dataGraficoPrato,dataGraficoSituacoes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_grafico);
        grafico = findViewById(R.id.Grafico);
        progressBar = findViewById(R.id.progressBarGrafico);
        spin = findViewById(R.id.spinnerGraficos);
        graf = AnyChart.pie();

        spin.setOnItemSelectedListener(this);
        ArrayAdapter adap = new ArrayAdapter(this,android.R.layout.simple_spinner_item,graficos);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adap);

        getGraficoPratos();
    }

    private void getGraficoPratos()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "GraficoPratos";
        RequisicaoGET(cServidor + "/Pedidos/PratosMaisPedido");
        return;
    }

    private void getGraficoSituacoes()
    {
        progressBar.setVisibility(View.VISIBLE);
        desabilitaInteracao();
        tipoRequisicao = "GraficoSituacoes";
        RequisicaoGET(cServidor + "/Pedidos/Situacoes");
        return;
    }
    @Override
    void RetornoGET(JSONArray resposta) {
        super.RetornoGET(resposta);
        Gson gson = new Gson();
        Pedidos[] arrayPedidos;
        arrayPedidos = gson.fromJson(resposta.toString(), Pedidos[].class);

        if (tipoRequisicao.equals("GraficoPratos"))
        {
            dataGraficoPrato = new ArrayList<>();
            for (Pedidos pedidos: arrayPedidos)
            {
                dataGraficoPrato.add(new ValueDataEntry(pedidos.pratoObj.nome,pedidos.quantidade));
            }
             graf.data(dataGraficoPrato);
             grafico.setChart(graf);
        }
        else
            if (tipoRequisicao.equals("GraficoSituacoes"))
            {
                dataGraficoSituacoes = new ArrayList<>();
                for (Pedidos pedidos: arrayPedidos)
                {
                    dataGraficoSituacoes.add(new ValueDataEntry(Utilidades.getDescricaoSituacao(pedidos.situacao),pedidos.quantidade));
                }
                graf.data(dataGraficoSituacoes);
            }

        progressBar.setVisibility(View.INVISIBLE);
        habilitaInteracao();
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            getGraficoPratos();
        }
        else
        {
            getGraficoSituacoes();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}