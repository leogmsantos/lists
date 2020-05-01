package br.com.leo.minhalistadecompras.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilmeModel implements Serializable {

    @SerializedName("poster_path")
    private String image;
    @SerializedName("original_title")
    private String title;
    @SerializedName("vote_average")
    private Double score;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String relreaseDate;

    private String imagePath = "https://image.tmdb.org/t/p/w220_and_h330_face/";

    public String getImage() {
        return  imagePath + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelreaseDate() {
        return relreaseDate;
    }

    public void setRelreaseDate(String relreaseDate) {
        this.relreaseDate = relreaseDate;
    }
}
