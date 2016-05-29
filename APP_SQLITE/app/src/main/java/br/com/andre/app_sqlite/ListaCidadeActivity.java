package br.com.andre.app_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.andre.app_sqlite.adapter.CidadeAdapter;
import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

public class ListaCidadeActivity extends AppCompatActivity {

    private List<Cidade> cidadeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cidade);

        long tempoInicial = System.currentTimeMillis();
        Log.i("TEMPO", "INICIO: " + tempoInicial);
        CidadeDAO cidadeDAO = new CidadeDAO(this);
        cidadeList = cidadeDAO.listar();
        long tempoFinal = System.currentTimeMillis();
        long tempoExecucao = tempoFinal - tempoInicial;
        Log.i("TEMPO","FINAL: " + tempoFinal);
        Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);

        ListView lvCidade = (ListView) findViewById(R.id.lvCidade);
        lvCidade.setAdapter(new CidadeAdapter(this, cidadeList));

        lvCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidade = cidadeList.get(position);
                Intent intent = new Intent(view.getContext(), CidadeActivity.class);
                intent.putExtra("cidade",cidade);
                startActivity(intent);
            }
        });

    }
}
