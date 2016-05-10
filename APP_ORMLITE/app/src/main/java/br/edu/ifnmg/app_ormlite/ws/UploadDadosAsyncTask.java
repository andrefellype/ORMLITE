package br.edu.ifnmg.app_ormlite.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifnmg.app_ormlite.dao.CidadeDAO;
import br.edu.ifnmg.app_ormlite.dao.DatabaseHelper;
import br.edu.ifnmg.app_ormlite.entidade.Cidade;

/**
 * Created by Ruth on 10/05/2016.
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
            DatabaseHelper dh = new DatabaseHelper(context);
            CidadeDAO cidadeDAO = new CidadeDAO(dh.getConnectionSource());
            JSONArray json = null;
            JSONObject mJsonObject = new JSONObject();
            json = new JSONArray(result);

            for(int i=0; i<json.length(); i++){

                mJsonObject = (JSONObject) json.get(i);
                Cidade cidade = new Cidade();
                Map<String, Object> values = new HashMap<>();
                values.put("codmun",mJsonObject.getString("CodMun"));
                List<Cidade> cidadeList = cidadeDAO.queryForFieldValues(values);
                if(!cidadeList.isEmpty()) cidade = cidadeList.get(0);

                cidade.setCodmun(mJsonObject.getInt("CodMun"));
                cidade.setNomemunic(mJsonObject.getString("NomeMunic"));
                cidade.setCoduf(mJsonObject.getInt("CodUF"));
                cidade.setUf(mJsonObject.getString("UF"));
                cidade.setPopulacao(mJsonObject.getInt("Populacao"));

                if(cidadeList.isEmpty()) cidadeDAO.create(cidade);
                else cidadeDAO.update(cidade);

            }

        }catch (JSONException e) {
            Toast.makeText(context, " JSONException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }catch (SQLException e){
            Toast.makeText(context, " SQLException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        this.progessDialo.hide();

    }

}