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

public class RemoveItemDialog extends AppCompatDialogFragment {

    private Button btnCancelRemoveItem, btnConfirmRemoveItem;
    private View view;
    private RemoveItemDialog.DialogRemoveItemListener listener;
    private Context context;

    public RemoveItemDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_remove_item, null);
        builder.setView(view);

        initializeComponents();

        return builder.create();
    }

    private void initializeComponents(){
        btnCancelRemoveItem = view.findViewById(R.id.btnCancelRemoveItem);
        btnConfirmRemoveItem = view.findViewById(R.id.btnConfirmRemoveItem);

        removeItem();
    }

    private void removeItem(){
        btnCancelRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeItem(false);
            }
        });

        btnConfirmRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeItem(true);
            }
        });
    }

    public void setListener(DialogRemoveItemListener listener) {
        this.listener = listener;
    }

    public interface DialogRemoveItemListener{
        void removeItem(Boolean remove);
    }

}
