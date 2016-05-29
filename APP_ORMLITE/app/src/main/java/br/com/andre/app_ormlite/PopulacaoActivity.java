package br.com.andre.app_ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.andre.app_ormlite.adapter.CidadePAdapter;
import br.com.andre.app_ormlite.dao.CidadeDAO;
import br.com.andre.app_ormlite.dao.DatabaseHelper;
import br.com.andre.app_ormlite.entidade.Cidade;

public class PopulacaoActivity extends AppCompatActivity {

    private List<String> estadoList;
    private CidadeDAO cidadeDAO;
    private ListView lvPopulacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populacao);

        estadoList = new ArrayList<>();
        estadoList.add("Selecione um estado...");
        Spinner spEstado = (Spinner) findViewById(R.id.spEstado);
        lvPopulacao = (ListView) findViewById(R.id.lvPopulacao);
        try {
            DatabaseHelper dh = new DatabaseHelper(this);
            cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            QueryBuilder<Cidade, ?> qb = cidadeDAO.queryBuilder();
            qb.groupBy("uf");
            List<Cidade> listaTemp = qb.query();
            for (Cidade cidade : listaTemp) {
                estadoList.add(cidade.getUf());
            }
        }catch (SQLException e){}

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estadoList);
        spEstado.setAdapter(adapter);


        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estado = estadoList.get(position);
                if(!estado.equals("Selecione um estado...")){
                    try {
                        long tempoInicial = System.currentTimeMillis();
                        Log.i("TEMPO", "INICIO: " + tempoInicial);
                        Map<String, Object> values = new HashMap<String, Object>();
                        values.put("uf", estado);
                        List<Cidade> cidadeList = cidadeDAO.queryForFieldValues(values);
                        long tempoFinal = System.currentTimeMillis();
                        long tempoExecucao = tempoFinal - tempoInicial;
                        Log.i("TEMPO","FINAL: " + tempoFinal);
                        Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
                        lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), cidadeList));
                    }catch (SQLException e){}
                }else{
                    List<Cidade> cidades = new ArrayList<>();
                    lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), cidades));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
