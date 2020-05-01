package br.com.leo.minhalistadecompras.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.views.activity.ListActivity;

public class ItemMovieListSearchedAdapter extends RecyclerView.Adapter<ItemMovieListSearchedAdapter.MyViewHolder> {

    private List<FilmeModel> listaDeFilmes;
    private View itemView;
    private Context context;
    private onClick listener;


    public ItemMovieListSearchedAdapter(List<FilmeModel> listaDeFilmes, Context context) {
        this.listaDeFilmes = listaDeFilmes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme_novo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvMovieTitle.setText(listaDeFilmes.get(position).getTitle());
        holder.tvMovieDate.setText(listaDeFilmes.get(position).getRelreaseDate());
        Picasso.with(context).load(listaDeFilmes.get(position).getImage()).into(holder.ivCapa);
        holder.cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener = (onClick) context;
                listener.onItemClickListener(listaDeFilmes.get(position));
            }
        });


        //holder.tvMovieDescription.setText(listaDeFilmes.get(position).getOverview());
        //holder.tvMovieScore.setText("Score: " + listaDeFilmes.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return listaDeFilmes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMovieTitle, tvMovieDescription, tvMovieScore, tvMovieDate;
        private ImageView ivCapa;
        private CardView cardMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieDate = itemView.findViewById(R.id.tvMovieData);
            ivCapa = itemView.findViewById(R.id.ivCapa);
            cardMovie = itemView.findViewById(R.id.cardMovie);
            //tvMovieDescription = itemView.findViewById(R.id.tvMovieDescription);
            //tvMovieScore = itemView.findViewById(R.id.tvMovieScore);
        }
    }

    public void atualizarLista(List<FilmeModel> filmes){
        this.listaDeFilmes = filmes;
    }

    public interface onClick{
        void onItemClickListener(FilmeModel filme);
    }
}
