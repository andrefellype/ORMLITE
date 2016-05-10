package br.edu.ifnmg.app_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifnmg.app_sqlite.entidade.Cidade;

/**
 * Created by Ruth on 09/05/2016.
 */
public class CidadeDAO {

    private SQLiteDatabase bd;

    public CidadeDAO(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Cidade cidade){
        ContentValues valores = new ContentValues();
        valores.put("codmun", cidade.getCodmun());
        valores.put("nomemunic", cidade.getNomemunic());
        valores.put("coduf", cidade.getCoduf());
        valores.put("uf", cidade.getUf());
        valores.put("populacao", cidade.getPopulacao());

        bd.insert("cidade", null, valores);
    }


    public void atualizar(Cidade cidade){
        ContentValues valores = new ContentValues();
        valores.put("codmun", cidade.getCodmun());
        valores.put("nomemunic", cidade.getNomemunic());
        valores.put("coduf", cidade.getCoduf());
        valores.put("uf", cidade.getUf());
        valores.put("populacao", cidade.getPopulacao());
        bd.update("cidade", valores, "id = ?", new String[]{""+cidade.getId()});
    }


    public void deletar(Cidade cidade){
        bd.delete("cidade", "id = "+cidade.getId(), null);
    }

    public List<Cidade> buscarPorEstado(String uf){
        List<Cidade> list = new ArrayList<>();
        String[] colunas = new String[]{"id", "codmun", "nomemunic","coduf","uf","populacao"};
        Cursor cursor = bd.query(true, "cidade", colunas, "uf like '" + uf + "'", null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Cidade c = new Cidade();
                c.setId(cursor.getInt(0));
                c.setCodmun(cursor.getInt(1));
                c.setNomemunic(cursor.getString(2));
                c.setCoduf(cursor.getInt(3));
                c.setUf(cursor.getString(4));
                c.setPopulacao(cursor.getInt(5));
                list.add(c);
            }while(cursor.moveToNext());
        }

        return list;
    }

    public List<Cidade> buscar(Cidade cidade){
        List<Cidade> list = new ArrayList<>();
        String[] colunas = new String[]{"id", "codmun", "nomemunic","coduf","uf","populacao"};
        Cursor cursor = bd.query(true, "cidade", colunas, "codmun = " + cidade.getCodmun(), null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Cidade c = new Cidade();
                c.setId(cursor.getInt(0));
                c.setCodmun(cursor.getInt(1));
                c.setNomemunic(cursor.getString(2));
                c.setCoduf(cursor.getInt(3));
                c.setUf(cursor.getString(4));
                c.setPopulacao(cursor.getInt(5));
                list.add(c);
            }while(cursor.moveToNext());
        }

        return list;
    }

    public List<Cidade> listarGroupEstado(){
        List<Cidade> list = new ArrayList<>();
        String[] colunas = new String[]{"id", "codmun", "nomemunic","coduf","uf","populacao"};

        Cursor cursor = bd.query("cidade", colunas, null, null, "uf", null, "uf ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Cidade c = new Cidade();
                c.setId(cursor.getInt(0));
                c.setCodmun(cursor.getInt(1));
                c.setNomemunic(cursor.getString(2));
                c.setCoduf(cursor.getInt(3));
                c.setUf(cursor.getString(4));
                c.setPopulacao(cursor.getInt(5));
                list.add(c);
            }while(cursor.moveToNext());
        }

        return(list);
    }


    public List<Cidade> listar(){
        List<Cidade> list = new ArrayList<>();
        String[] colunas = new String[]{"id", "codmun", "nomemunic","coduf","uf","populacao"};

        Cursor cursor = bd.query("cidade", colunas, null, null, null, null, "uf ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Cidade c = new Cidade();
                c.setId(cursor.getInt(0));
                c.setCodmun(cursor.getInt(1));
                c.setNomemunic(cursor.getString(2));
                c.setCoduf(cursor.getInt(3));
                c.setUf(cursor.getString(4));
                c.setPopulacao(cursor.getInt(5));
                list.add(c);
            }while(cursor.moveToNext());
        }

        return(list);
    }

}
