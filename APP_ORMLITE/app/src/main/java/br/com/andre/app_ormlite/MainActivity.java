package br.com.andre.app_ormlite;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import br.com.andre.app_ormlite.dao.CidadeDAO;
import br.com.andre.app_ormlite.dao.DatabaseHelper;
import br.com.andre.app_ormlite.entidade.Cidade;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inserir(View view){
        try {
            long tempoInicial = System.currentTimeMillis();
            Log.i("TEMPO", "INICIO: " + tempoInicial);
            DatabaseHelper dh = new DatabaseHelper(view.getContext());
            CidadeDAO cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            File file = new File(exportDir, "dados.csv");
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            try{
                int x = 0;
                while ((line = reader.readNext()) != null){
                    if(x != 0) {
                        Cidade cidade = new Cidade();
                        cidade.setCodmun(Integer.parseInt(line[2].toString()));
                        cidade.setNomemunic(line[3].toString());
                        cidade.setCoduf(Integer.parseInt(line[1].toString()));
                        cidade.setUf(line[0].toString());
                        cidade.setPopulacao(Integer.parseInt(line[4].toString()));
                        Map<String, Object> values = new HashMap<String, Object>();
                        values.put("codmun",cidade.getCodmun());
                        List<Cidade> cidadeList = cidadeDAO.queryForFieldValues(values);
                        if(cidadeList.isEmpty())
                            cidadeDAO.create(cidade);
                    }
                    x++;
                }
                long tempoFinal = System.currentTimeMillis();
                long tempoExecucao = tempoFinal - tempoInicial;
                Log.i("TEMPO","FINAL: " + tempoFinal);
                Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
                startActivity(new Intent(view.getContext(), MainActivity.class));
            } catch (IOException e) {
                Log.e("TEMPO","IOException: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            Log.e("TEMPO", "FileNotFoundException: " + e.getMessage());
        }catch (SQLException e){
            Log.e("TEMPO","SQLException: " + e.getMessage());
        }
    }

    public void listar(View view){
        startActivity(new Intent(view.getContext(), ListaCidadeActivity.class));
    }

    public void exibirPopulacao(View view){
        startActivity(new Intent(view.getContext(), PopulacaoActivity.class));
    }

    public void apagar(View view){
        try {
            DatabaseHelper dh = new DatabaseHelper(view.getContext());
            long tempoInicial = System.currentTimeMillis();
            Log.i("TEMPO", "INICIO: " + tempoInicial);
            CidadeDAO cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            List<Cidade> cidadeList = cidadeDAO.queryForAll();
            for (Cidade cidade : cidadeList) {
                cidadeDAO.delete(cidade);
            }
            long tempoFinal = System.currentTimeMillis();
            long tempoExecucao = tempoFinal - tempoInicial;
            Log.i("TEMPO", "FINAL: " + tempoFinal);
            Log.i("TEMPO", "EXECUÇÃO: " + tempoExecucao);
            Toast.makeText(view.getContext(), "Cidades Apagada com Sucesso!", Toast.LENGTH_SHORT).show();
        }catch (SQLException e){}
    }

}
