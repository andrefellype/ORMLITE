package br.com.andre.app_ormlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.com.andre.app_ormlite.adapter.CidadeAdapter;
import br.com.andre.app_ormlite.dao.CidadeDAO;
import br.com.andre.app_ormlite.dao.DatabaseHelper;
import br.com.andre.app_ormlite.entidade.Cidade;

public class ListaCidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cidade);

        try {

            long tempoInicial = System.currentTimeMillis();
            Log.i("TEMPO", "INICIO: " + tempoInicial);
            DatabaseHelper dh = new DatabaseHelper(this);
            CidadeDAO cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            final List<Cidade> cidadeList = cidadeDAO.queryForAll();
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

        }catch (SQLException e){}


    }
}
