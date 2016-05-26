package br.com.andre.app_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.andre.app_sqlite.R;
import br.com.andre.app_sqlite.entidade.Cidade;

/**
 * Created by Ruth on 26/05/2016.
 */
public class CidadePAdapter extends BaseAdapter {

    private Context context;
    private List<Cidade> cidadeList;

    public CidadePAdapter(Context context, List<Cidade> cidadeList) {
        this.context = context;
        this.cidadeList = cidadeList;
    }

    @Override
    public int getCount() {
        return cidadeList.size();
    }

    @Override
    public Object getItem(int position) {
        return cidadeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cidade cidade = cidadeList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cidade_p, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.tvNome);
        tvNome.setText(cidade.getNomemunic());

        TextView tvPopulacao = (TextView) layout.findViewById(R.id.tvPopulacao);
        tvPopulacao.setText("População: " + cidade.getPopulacao());

        return layout;
    }
}
