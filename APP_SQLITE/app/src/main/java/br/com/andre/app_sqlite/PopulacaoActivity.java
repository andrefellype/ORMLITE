package br.com.andre.app_sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.andre.app_sqlite.adapter.CidadePAdapter;
import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

public class PopulacaoActivity extends AppCompatActivity {

    private List<String> estadoList;
    private CidadeDAO cidadeDAO;
    private ListView lvPopulacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populacao);

        lvPopulacao = (ListView) findViewById(R.id.lvPopulacao);
        estadoList = new ArrayList<>();
        estadoList.add("Selecione um estado...");
        Spinner spEstado = (Spinner) findViewById(R.id.spEstado);
        cidadeDAO = new CidadeDAO(this);
        for(Cidade cidade : cidadeDAO.listarGroupEstado()){
            estadoList.add(cidade.getUf());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estadoList);
        spEstado.setAdapter(adapter);


        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estado = estadoList.get(position);
                if(!estado.equals("Selecione um estado...")){
                    long tempoInicial = System.currentTimeMillis();
                    Log.i("TEMPO", "INICIO: " + tempoInicial);
                    List<Cidade> cidadeList = cidadeDAO.buscarPorEstado(estado);
                    long tempoFinal = System.currentTimeMillis();
                    long tempoExecucao = tempoFinal - tempoInicial;
                    Log.i("TEMPO","FINAL: " + tempoFinal);
                    Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
                    lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), cidadeList));
                }else{
                    lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), new ArrayList<Cidade>()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
