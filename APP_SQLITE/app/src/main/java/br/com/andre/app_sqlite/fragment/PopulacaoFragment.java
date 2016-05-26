package br.com.andre.app_sqlite.fragment;

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

import java.util.ArrayList;
import java.util.List;

import br.com.andre.app_sqlite.R;
import br.com.andre.app_sqlite.adapter.CidadePAdapter;
import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;

/**
 * Created by Ruth on 26/05/2016.
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

        lvPopulacao = (ListView) layout.findViewById(R.id.lvPopulacao);
        estadoList = new ArrayList<>();
        estadoList.add("Selecione um estado...");
        Spinner spEstado = (Spinner) layout.findViewById(R.id.spEstado);
        cidadeDAO = new CidadeDAO(layout.getContext());
        for(Cidade cidade : cidadeDAO.listarGroupEstado()){
            estadoList.add(cidade.getUf());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(layout.getContext(), android.R.layout.simple_spinner_dropdown_item, estadoList);
        spEstado.setAdapter(adapter);


        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estado = estadoList.get(position);
                if(!estado.equals("Selecione um estado...")){
                    List<Cidade> cidadeList = cidadeDAO.buscarPorEstado(estado);
                    lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), cidadeList));
                }else{
                    lvPopulacao.setAdapter(new CidadePAdapter(view.getContext(), new ArrayList<Cidade>()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return layout;
    }

}
