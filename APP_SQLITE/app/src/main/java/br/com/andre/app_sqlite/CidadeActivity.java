package br.com.andre.app_sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

public class CidadeActivity extends AppCompatActivity {

    private Cidade cidade;
    private EditText edtCodMun;
    private EditText edtNomeMun;
    private EditText edtCodUf;
    private EditText edtUf;
    private EditText edtPopulacao;
    private CidadeDAO cidadeDAO;

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

        cidadeDAO = new CidadeDAO(this);

    }

    public void alterar(View view){
        cidade.setCodmun(Integer.parseInt(edtCodMun.getText().toString().trim()));
        cidade.setNomemunic(edtNomeMun.getText().toString().trim());
        cidade.setCoduf(Integer.parseInt(edtCodUf.getText().toString().trim()));
        cidade.setUf(edtUf.getText().toString().trim());
        cidade.setPopulacao(Integer.parseInt(edtPopulacao.getText().toString().trim()));
        long tempoInicial = System.currentTimeMillis();
        Log.i("TEMPO", "INICIO: " + tempoInicial);
        cidadeDAO.atualizar(cidade);
        long tempoFinal = System.currentTimeMillis();
        long tempoExecucao = tempoFinal - tempoInicial;
        Log.i("TEMPO","FINAL: " + tempoFinal);
        Log.i("TEMPO","EXECUÇÃO: " + tempoExecucao);
        Toast.makeText(view.getContext(), "Cidade atualizada com sucesso!", Toast.LENGTH_SHORT).show();
    }

}
