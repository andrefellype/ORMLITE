package br.edu.ifnmg.app_ormlite.entidade;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Ruth on 09/05/2016.
 */
@DatabaseTable(tableName = "cidade")
public class Cidade implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int codmun;
    @DatabaseField
    private String nomemunic;
    @DatabaseField
    private int coduf;
    @DatabaseField
    private String uf;
    @DatabaseField
    private int populacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodmun() {
        return codmun;
    }

    public void setCodmun(int codmun) {
        this.codmun = codmun;
    }

    public String getNomemunic() {
        return nomemunic;
    }

    public void setNomemunic(String nomemunic) {
        this.nomemunic = nomemunic;
    }

    public int getCoduf() {
        return coduf;
    }

    public void setCoduf(int coduf) {
        this.coduf = coduf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }
}
