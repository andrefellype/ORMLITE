package br.com.andre.app_ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import br.com.andre.app_ormlite.dao.CidadeDAO;
import br.com.andre.app_ormlite.dao.DatabaseHelper;
import br.com.andre.app_ormlite.entidade.Cidade;

public class CidadeActivity extends AppCompatActivity {

    private Cidade cidade;
    private CidadeDAO cidadeDAO;
    private EditText edtCodMun;
    private EditText edtNomeMun;
    private EditText edtCodUf;
    private EditText edtUf;
    private EditText edtPopulacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidade);

        cidade = (Cidade) getIntent().getExtras().getSerializable("cidade");

        edtCodMun = (EditText) findViewById(R.id.edtCodMun);
        edtNomeMun = (EditText) findViewById(R.id.edtNomeMun);
        edtCodUf = (EditText) findViewById(R.id.edtCodUF);
        edtUf = (EditText) findViewById(R.id.edtUF);
        edtPopulacao = (EditText) findViewById(R.id.edtPopulacao);

        edtCodMun.setText(Integer.toString(cidade.getCodmun()));
        edtNomeMun.setText(cidade.getNomemunic());
        edtCodUf.setText(Integer.toString(cidade.getCoduf()));
        edtUf.setText(cidade.getUf());
        edtPopulacao.setText(Integer.toString(cidade.getPopulacao()));

        try {
            DatabaseHelper dh = new DatabaseHelper(this);
            cidadeDAO = new CidadeDAO(dh.getConnectionSource());
        }catch (SQLException e){}

    }

    public void alterar(View view){
        try {
            cidade.setCodmun(Integer.parseInt(edtCodMun.getText().toString().trim()));
            cidade.setNomemunic(edtNomeMun.getText().toString().trim());
            cidade.setCoduf(Integer.parseInt(edtCodUf.getText().toString().trim()));
            cidade.setUf(edtUf.getText().toString().trim());
            cidade.setPopulacao(Integer.parseInt(edtPopulacao.getText().toString().trim()));
            long tempoInicial = System.currentTimeMillis();
            Log.i("TEMPO", "INICIO: " + tempoInicial);
            cidadeDAO.update(cidade);
            long tempoFinal = System.currentTimeMillis();
            long tempoExecucao = tempoFinal - tempoInicial;
            Log.i("TEMPO","FINAL: " + tempoFinal);
            Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
            Toast.makeText(view.getContext(), "Cidade atualizada com sucesso!", Toast.LENGTH_SHORT).show();
        }catch (SQLException e){}
    }

}
