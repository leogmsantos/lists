package br.com.leo.minhalistadecompras.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;

public class ItemMovieListAdapter extends RecyclerView.Adapter<ItemMovieListAdapter.MyViewHolder> {

    private List<FilmeModel> listaDeFilmes;
    private View itemView;

    public ItemMovieListAdapter(List<FilmeModel> listaDeFilmes) {
        this.listaDeFilmes = listaDeFilmes;
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
    }

    @Override
    public int getItemCount() {
        return listaDeFilmes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMovieItemTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieItemTitle = itemView.findViewById(R.id.tvMovieItemTitle);
        }
    }
}
