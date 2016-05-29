package br.com.andre.app_sqlite;

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
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inserir(View view){
        long tempoInicial = System.currentTimeMillis();
        Log.i("TEMPO", "INICIO: " + tempoInicial);
        CidadeDAO cidadeDAO = new CidadeDAO(view.getContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        File file = new File(exportDir, "dados.csv");
        try {
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
                        List<Cidade> cidadeList = cidadeDAO.buscar(cidade);
                        if(cidadeList.isEmpty())
                            cidadeDAO.inserir(cidade);
                    }
                    x++;
                }
                Toast.makeText(view.getContext(), "Cidade inseridas com sucesso!", Toast.LENGTH_SHORT).show();
                long tempoFinal = System.currentTimeMillis();
                long tempoExecucao = tempoFinal - tempoInicial;
                Log.i("TEMPO","FINAL: " + tempoFinal);
                Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
            } catch (IOException e) {
                Log.e("TEMPO","IOException: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            Log.e("TEMPO","FileNotFoundException: " + e.getMessage());
        }
    }

    public void exibir(View view){
        startActivity(new Intent(view.getContext(), ListaCidadeActivity.class));
    }

    public void exibirPopulacao(View view){
        startActivity(new Intent(view.getContext(), PopulacaoActivity.class));
    }

    public void apagar(View view){
        long tempoInicial = System.currentTimeMillis();
        Log.i("TEMPO", "INICIO: " + tempoInicial);
        CidadeDAO cidadeDAO = new CidadeDAO(view.getContext());
        List<Cidade> cidadeList = cidadeDAO.listar();
        for(Cidade cidade : cidadeList){
            cidadeDAO.deletar(cidade);
        }
        long tempoFinal = System.currentTimeMillis();
        long tempoExecucao = tempoFinal - tempoInicial;
        Log.i("TEMPO","FINAL: " + tempoFinal);
        Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
        Toast.makeText(view.getContext(),"Cidades Apagada com Sucesso!",Toast.LENGTH_SHORT).show();
    }
}
