package br.edu.ifnmg.app_sqlite.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import br.edu.ifnmg.app_sqlite.dao.CidadeDAO;
import br.edu.ifnmg.app_sqlite.entidade.Cidade;

/**
 * Created by Ruth on 09/05/2016.
 */
public class UploadDadosAsyncTask extends WebServiceGenerico<String,String, String> {

    ProgressDialog progessDialo;
    HttpURLConnection urlConnection;
    Context context;

    public UploadDadosAsyncTask(Context context){
        this.context = context;
        this.setCaminho("CIDADE_ESTADO/upload.php");
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            urlConnection = (HttpURLConnection) this.getURL().openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

    @Override
    protected void onPreExecute() {
        this.progessDialo = ProgressDialog.show(context, "Requisição", "Chamando serviço", false, false);
    }
    @Override
    protected void onPostExecute(String result) {

        try {
            JSONArray json = null;
            JSONObject mJsonObject = new JSONObject();
            json = new JSONArray(result);

            for(int i=0; i<json.length(); i++){

                mJsonObject = (JSONObject) json.get(i);
                Cidade cidade = new Cidade();
                cidade.setCodmun(mJsonObject.getInt("CodMun"));
                cidade.setNomemunic(mJsonObject.getString("NomeMunic"));
                cidade.setCoduf(mJsonObject.getInt("CodUF"));
                cidade.setUf(mJsonObject.getString("UF"));
                cidade.setPopulacao(mJsonObject.getInt("Populacao"));

                CidadeDAO cidadeDAO = new CidadeDAO(context);
                List<Cidade> cidadeList = cidadeDAO.buscar(cidade);

                if(cidadeList.isEmpty()) cidadeDAO.inserir(cidade);
                else cidadeDAO.atualizar(cidade);

            }

        }catch (JSONException e) {
            Toast.makeText(context, result + " JSONException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        this.progessDialo.hide();

    }

}
