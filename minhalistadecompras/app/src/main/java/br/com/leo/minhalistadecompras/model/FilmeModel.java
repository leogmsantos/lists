package br.com.leo.minhalistadecompras.model;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;

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
    private String id;
    private String idUsuario;
    private String titulo, descricao;

    public FilmeModel() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference movieRef = databaseReference.child("filmes");
        String idFilme = movieRef.push().getKey();
        setId(idFilme);
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean saveMovieOnFirebase(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference movieRef = firebaseRef.child("filmes").
                child(getIdUsuario())
                .child(getTitulo())
                .child(getId());
        movieRef.setValue(this);
        return true;
    }
}
