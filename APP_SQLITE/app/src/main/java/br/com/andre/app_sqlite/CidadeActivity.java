package br.com.andre.app_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        cidadeDAO.atualizar(cidade);
        Toast.makeText(view.getContext(), "Cidade atualizada com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void apagar(View view){cidade.setCodmun(Integer.parseInt(edtCodMun.getText().toString().trim()));
        cidadeDAO.deletar(cidade);
        Toast.makeText(view.getContext(), "Cidade apagada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}