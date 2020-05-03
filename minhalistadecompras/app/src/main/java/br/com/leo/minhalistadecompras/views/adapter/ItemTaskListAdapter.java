package br.com.leo.minhalistadecompras.views.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.model.ListaDeTarefasModel;
import br.com.leo.minhalistadecompras.views.dialog.RemoveItemDialog;

public class ItemTaskListAdapter extends RecyclerView.Adapter<ItemTaskListAdapter.MyViewHolder> implements RemoveItemDialog.DialogRemoveItemListener {

    private List<ListaDeTarefasModel> listaDeTarefas;
    private View itemView;
    private Context context;
    private TaskListener listener;
    private int itemPosition;
    private RemoveItemDialog dialog;

    public ItemTaskListAdapter(List<ListaDeTarefasModel> listaDeTarefas, Context context) {
        this.listaDeTarefas = listaDeTarefas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_de_tarefas, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tituloTarefa.setText(listaDeTarefas.get(position).getNomeTarefa());

        holder.finalizarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.finalizarTarefa.isActivated() == true){
                    holder.constraintTask.setBackground(ContextCompat.getDrawable(itemView.getContext(),R.drawable.shape));
                    holder.finalizarTarefa.setChecked(false);
                    holder.finalizarTarefa.setActivated(false);
                    listener.isChecked(false);
                }else{
                    holder.constraintTask.setBackground(ContextCompat.getDrawable(itemView.getContext(),R.drawable.shape_green));
                    holder.finalizarTarefa.setChecked(true);
                    holder.finalizarTarefa.setActivated(true);
                    listener = (TaskListener) context;
                    listener.isChecked(true);
                }
            }
        });

        holder.constraintTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemPosition = position;
                dialog = new RemoveItemDialog(itemView.getContext());
                dialog.setListener(ItemTaskListAdapter.this);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Remove Dialog");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeTarefas.size();
    }

    @Override
    public void removeItem(Boolean remove) {
        if (remove == true){
            listaDeTarefas.remove(itemPosition);
            notifyDataSetChanged();
            dialog.dismiss();
        }else{
            dialog.dismiss();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloTarefa;
        private RadioButton finalizarTarefa;
        private ConstraintLayout constraintTask;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTarefa = itemView.findViewById(R.id.tvTituloTarefa);
            finalizarTarefa = itemView.findViewById(R.id.finalizarTarefa);
            constraintTask = itemView.findViewById(R.id.constraintTask);
            finalizarTarefa.setChecked(false);
        }
    }

    public interface TaskListener{
        void isChecked(Boolean listener);
    }
}
