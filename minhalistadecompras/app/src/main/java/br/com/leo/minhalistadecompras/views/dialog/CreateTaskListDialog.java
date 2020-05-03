package br.com.leo.minhalistadecompras.views.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.views.activity.ListActivity;

public class CreateTaskListDialog extends AppCompatDialogFragment {

    private EditText tituloLista, descricaoLista;
    private Button btnCriarLista;
    private View view;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_list, null);
        builder.setView(view);

        initializeComponents();

        return builder.create();
    }

    private void initializeComponents(){
        tituloLista = view.findViewById(R.id.edtTituloLista);
        descricaoLista = view.findViewById(R.id.edtDescricaoLista);
        btnCriarLista = view.findViewById(R.id.btnCriarLista);

        criarLista();
    }

    private void criarLista(){
        btnCriarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloLista.getText().toString();
                String descricao = descricaoLista.getText().toString();
                Intent i = new Intent(getContext(), ListActivity.class);
                i.putExtra(AppGeral.TITULO_LISTA, titulo);
                i.putExtra(AppGeral.DESCRICAO_LISTA, descricao);
                i.putExtra(AppGeral.CATEGORIA, AppGeral.LISTA_DE_TAREFAS);
                startActivity(i);
            }
        });
    }

    /*public interface dialogInteface(){

    }*/
}
