package br.com.leo.minhalistadecompras.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListaDeFilmesRequestBean implements Serializable {
    @SerializedName("api_key")
    private String key;

    @SerializedName("query")
    private String movie;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
