package br.com.leo.minhalistadecompras.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import br.com.leo.minhalistadecompras.model.FilmeModel;

class ListaDeFilmesResponse implements Serializable {

    @SerializedName("results")
    private  List<FilmeModel> filmes;

    public List<FilmeModel> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<FilmeModel> filmes) {
        this.filmes = filmes;
    }
}
