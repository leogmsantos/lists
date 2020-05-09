package br.com.leo.minhalistadecompras.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaModel;
import br.com.leo.minhalistadecompras.views.dialog.RemoveItemDialog;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    private List<ListaModel> lista;
    private View itemView;
    private Context context;
    private RemoveItemDialog dialog;
    private int itemPosition;
    public ItemListAdapter(List<ListaModel> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvListTitle.setText(lista.get(position).getTitulo());
       switch (lista.get(position).getCategoria()){
           case AppGeral.LISTA_DE_COMRAS:
               holder.ivListIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_shopping_cart_white_24dp));
               break;
           case AppGeral.LISTA_DE_FILMES:
               holder.ivListIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_movie_white_24dp));
               break;
           case AppGeral.LISTA_DE_TAREFAS:
               holder.ivListIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_build_white_24dp));
               break;
       }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivListIcon;
        private TextView tvListTitle;
        private ConstraintLayout constraintList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivListIcon = itemView.findViewById(R.id.ivListIcon);
            tvListTitle = itemView.findViewById(R.id.tvListTitle);
            constraintList = itemView.findViewById(R.id.constraintList);
        }
    }
}
