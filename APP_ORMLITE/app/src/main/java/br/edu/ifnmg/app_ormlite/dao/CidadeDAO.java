package br.edu.ifnmg.app_ormlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.edu.ifnmg.app_ormlite.entidade.Cidade;

/**
 * Created by Ruth on 10/05/2016.
 */
public class CidadeDAO extends BaseDaoImpl<Cidade, Integer> {

    public CidadeDAO(ConnectionSource cs) throws SQLException {
        super(Cidade.class);
        setConnectionSource(cs);
        initialize();
    }

}
