package br.com.leo.minhalistadecompras.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.views.dialog.RemoveItemDialog;

public class ItemMovieListAdapter extends RecyclerView.Adapter<ItemMovieListAdapter.MyViewHolder> implements RemoveItemDialog.DialogRemoveItemListener {

    private List<FilmeModel> listaDeFilmes;
    private View itemView;
    private Context context;
    private RemoveItemDialog dialog;
    private int itemPosition;
    public ItemMovieListAdapter(List<FilmeModel> listaDeFilmes, Context context) {
        this.listaDeFilmes = listaDeFilmes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_de_filmes, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (listaDeFilmes.size() != 0){
            holder.tvMovieItemTitle.setText(listaDeFilmes.get(position).getTitle());
        }

        holder.constraintMovie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemPosition = position;
                dialog = new RemoveItemDialog(itemView.getContext());
                dialog.setListener(ItemMovieListAdapter.this);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Remove Dialog");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeFilmes.size();
    }

    @Override
    public void removeItem(Boolean remove) {
        if (remove == true){
            listaDeFilmes.remove(itemPosition);
            notifyDataSetChanged();
            dialog.dismiss();
        }else{
            dialog.dismiss();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMovieItemTitle;
        private ConstraintLayout constraintMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieItemTitle = itemView.findViewById(R.id.tvMovieItemTitle);
            constraintMovie = itemView.findViewById(R.id.constraintMovie);
        }
    }


}
