package br.com.andre.app_sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ruth on 25/05/2016.
 */
public class BDCore extends SQLiteOpenHelper {

    private static final String NOME_BD = "cidade";
    private static final int VERSAO_BD = 1;

    public BDCore(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table cidade(id integer primary key autoincrement, codmun integer not null, nomemunic text not null, " +
                "coduf integer not null, uf text not null, populacao integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table cidade;");
        onCreate(bd);
    }

}
