package br.com.leo.minhalistadecompras.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;

public class User {

    private String id, email, senha;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void saveUserOnDatabase(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child(getId());
        usuariosRef.setValue(this);
    }
}
