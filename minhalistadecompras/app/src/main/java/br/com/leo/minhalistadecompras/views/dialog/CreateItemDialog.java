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

public class CreateItemDialog extends AppCompatDialogFragment {

    private EditText tituloItemProduto, valorItemProduto;
    private Button btnSalvarProduto;
    private View view;
    private DialogItemListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(view);

        initializeComponents();

        return builder.create();
    }

    private void initializeComponents(){
        tituloItemProduto = view.findViewById(R.id.edtItemNomeProduto);
        valorItemProduto = view.findViewById(R.id.edtValorItemProduto);
        btnSalvarProduto = view.findViewById(R.id.btnCriarItemProduto);

        criarLista();
    }

    private void criarLista(){
        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloItemProduto.getText().toString();
                String valor = valorItemProduto.getText().toString();

                listener.criarProduto(titulo,valor);
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
        void criarProduto(String nomeProduto, String valorProduto);
   }
}
