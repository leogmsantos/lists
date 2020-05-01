package br.com.leo.minhalistadecompras.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListaDeFilmesRequest implements Serializable {

    ListaDeFilmesRequestBean listaDeFilmesRequestBean;

    public ListaDeFilmesRequest(ListaDeFilmesRequestBean listaDeFilmesRequestBean) {
        this.listaDeFilmesRequestBean = listaDeFilmesRequestBean;
    }

    public ListaDeFilmesRequestBean getListaDeFilmesRequestBean() {
        return listaDeFilmesRequestBean;
    }

    public void setListaDeFilmesRequestBean(ListaDeFilmesRequestBean listaDeFilmesRequestBean) {
        this.listaDeFilmesRequestBean = listaDeFilmesRequestBean;
    }
}
