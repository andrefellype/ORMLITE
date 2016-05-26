package br.com.andre.app_ormlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.andre.app_ormlite.entidade.Cidade;

/**
 * Created by Ruth on 26/05/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String databaseName = "diarioeletronico";
    private static final int databaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase sd, ConnectionSource cs) {
        try {
            TableUtils.createTable(cs, Cidade.class);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sd, ConnectionSource cs, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(cs, Cidade.class, true);
            onCreate(sd, cs);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close(){
        super.close();
    }

}
