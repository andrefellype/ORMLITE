package br.edu.ifnmg.app_ormlite.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import br.edu.ifnmg.app_ormlite.R;
import br.edu.ifnmg.app_ormlite.adapter.CidadePAdapter;
import br.edu.ifnmg.app_ormlite.dao.CidadeDAO;
import br.edu.ifnmg.app_ormlite.dao.DatabaseHelper;
import br.edu.ifnmg.app_ormlite.entidade.Cidade;

/**
 * Created by Ruth on 10/05/2016.
 */
public class PopulacaoFragment extends Fragment {

    private List<String> estadoList;
    private CidadeDAO cidadeDAO;
    private ListView lvPopulacao;

    public PopulacaoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_populacao, null);

        try {
            lvPopulacao = (ListView) layout.findViewById(R.id.lvPopulacao);
            estadoList = new ArrayList<>();
            estadoList.add("Selecione um estado...");
            Spinner spEstado = (Spinner) layout.findViewById(R.id.spEstado);
            DatabaseHelper dh = new DatabaseHelper(layout.getContext());
            cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            QueryBuilder<Cidade, ?> qb = cidadeDAO.queryBuilder();
            qb.groupBy("uf");
            List<Cidade> listaTemp = qb.query();
            for (Cidade cidade : listaTemp) {
                estadoList.add(cidade.getUf());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(layout.getContext(), android.R.layout.simple_spinner_dropdown_item, estadoList);
            spEstado.setAdapter(adapter);


            spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String estado = estadoList.get(position);
                    if (!estado.equals("Selecione um estado...")) {
                        try{
                            Map<String, Object> values = new HashMap<String, Object>();
                            values.put("uf",estado);
                            List<Cidade> cidadeList = cidadeDAO.queryForFieldValues(values);
                            lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), cidadeList));
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else {
                        lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), new ArrayList<Cidade>()));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }catch (SQLException e){
            e.printStackTrace();
        }

        return layout;
    }

}
