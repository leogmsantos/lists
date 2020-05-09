package br.com.leo.minhalistadecompras.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth refrenciaAutenticacao;

    public static DatabaseReference getReferenciaFirebase(){
        if (referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getRefrenciaAutenticacao(){
        if(refrenciaAutenticacao == null){
            refrenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return  refrenciaAutenticacao;
    }



}
