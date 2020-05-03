package br.com.leo.minhalistadecompras.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import br.com.leo.minhalistadecompras.R;

public class CreateItemTaskDialog extends AppCompatDialogFragment {

    private EditText tituloTarefa;
    private Button btnSalvarTarefa;
    private View view;
    private DialogItemListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_task_item, null);
        builder.setView(view);

        initializeComponents();

        return builder.create();
    }

    private void initializeComponents(){
        tituloTarefa = view.findViewById(R.id.edtItemNomeTarefa);
        btnSalvarTarefa = view.findViewById(R.id.btnCriarTarefa);

        criarLista();
    }

    private void criarLista(){
        btnSalvarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloTarefa.getText().toString();
                listener.criarTarefa(titulo);
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogItemListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    public interface DialogItemListener{
        void criarTarefa(String nomeTarefa);
   }
}
