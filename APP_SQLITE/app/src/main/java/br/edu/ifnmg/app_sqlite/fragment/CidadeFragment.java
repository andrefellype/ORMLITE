package br.edu.ifnmg.app_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifnmg.app_sqlite.CidadeActivity;
import br.edu.ifnmg.app_sqlite.R;
import br.edu.ifnmg.app_sqlite.adapter.CidadeAdapter;
import br.edu.ifnmg.app_sqlite.dao.CidadeDAO;
import br.edu.ifnmg.app_sqlite.entidade.Cidade;

/**
 * Created by Ruth on 09/05/2016.
 */
public class CidadeFragment extends Fragment {

    public CidadeFragment() {
    }

    private List<Cidade> cidadeList;
    private CidadeDAO cidadeDAO;
    private ListView lvCidade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.fragment_cidade, null);

        cidadeDAO = new CidadeDAO(layout.getContext());
        cidadeList = cidadeDAO.listar();

        lvCidade = (ListView) layout.findViewById(R.id.lvCidade);
        lvCidade.setAdapter(new CidadeAdapter(layout.getContext(), cidadeList));

        lvCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidade = cidadeList.get(position);
                Intent intent = new Intent(view.getContext(), CidadeActivity.class);
                intent.putExtra("cidade",cidade);
                startActivity(intent);
            }
        });

        return layout;
    }
}
