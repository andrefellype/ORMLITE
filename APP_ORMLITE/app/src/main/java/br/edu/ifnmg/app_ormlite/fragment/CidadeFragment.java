package br.edu.ifnmg.app_ormlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.edu.ifnmg.app_ormlite.CidadeActivity;
import br.edu.ifnmg.app_ormlite.R;
import br.edu.ifnmg.app_ormlite.adapter.CidadeAdapter;
import br.edu.ifnmg.app_ormlite.dao.CidadeDAO;
import br.edu.ifnmg.app_ormlite.dao.DatabaseHelper;
import br.edu.ifnmg.app_ormlite.entidade.Cidade;

/**
 * Created by Ruth on 10/05/2016.
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

        View layout = inflater.inflate(R.layout.fragment_cidade, null);

        try {
            DatabaseHelper dh = new DatabaseHelper(layout.getContext());
            cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            cidadeList = cidadeDAO.queryForAll();

            lvCidade = (ListView) layout.findViewById(R.id.lvCidade);
            lvCidade.setAdapter(new CidadeAdapter(layout.getContext(), cidadeList));

            lvCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cidade cidade = cidadeList.get(position);
                    Intent intent = new Intent(view.getContext(), CidadeActivity.class);
                    intent.putExtra("cidade", cidade);
                    startActivity(intent);
                }
            });
        }catch (SQLException e){
            Log.i("SQLException",e.getMessage());
        }

        return layout;
    }

}
