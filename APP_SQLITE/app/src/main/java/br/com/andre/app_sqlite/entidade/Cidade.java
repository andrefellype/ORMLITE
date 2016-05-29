package br.com.andre.app_sqlite.entidade;

import java.io.Serializable;

/**
 * Created by Ruth on 25/05/2016.
 */
public class Cidade implements Serializable{

    private int id;
    private int codmun;
    private String nomemunic;
    private int coduf;
    private String uf;
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
