package br.com.leo.minhalistadecompras.model;

import com.google.firebase.database.DatabaseReference;

import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;

public class ListaDeTarefasModel {

    private String nomeTarefa;
    private String dataConclusao;
    private String descricaoTarefa;
    private String statusTarefa;
    private String id;
    private String userId;
    private String titulo;

    public ListaDeTarefasModel() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference movieRef = databaseReference.child("tarefas");
        String idTarefas = movieRef.push().getKey();
        setId(idTarefas);
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public String getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(String statusTarefa) {
        this.statusTarefa = statusTarefa;
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

    public boolean saveBuyListOnFirebase(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference tarefasRef = firebaseRef.child("tarefas").
                child(getUserId())
                .child(getTitulo())
                .child(getId());
        tarefasRef.setValue(this);
        return true;
    }
}
