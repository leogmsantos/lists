package br.com.leo.minhalistadecompras.model;

import com.google.firebase.database.DatabaseReference;

import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;

public class ListaDeComprasModel {

    private String id;
    private String userId;
    private String produto;
    private double valor;
    private String titulo, descricao;

    public ListaDeComprasModel() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference movieRef = databaseReference.child("compras");
        String idCompras = movieRef.push().getKey();
        setId(idCompras);
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean saveBuyListOnFirebase(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference buyRef = firebaseRef.child("compras").
                child(getUserId())
                .child(getTitulo())
                .child(getId());
        buyRef.setValue(this);
        return true;
    }
}
