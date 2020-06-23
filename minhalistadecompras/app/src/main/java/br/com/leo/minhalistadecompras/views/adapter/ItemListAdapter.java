package br.com.leo.minhalistadecompras.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.model.ListaDeTarefasModel;
import br.com.leo.minhalistadecompras.model.ListaModel;
import br.com.leo.minhalistadecompras.views.activity.ListActivity;
import br.com.leo.minhalistadecompras.views.dialog.RemoveItemDialog;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    private List<ListaModel> lista;
    private View itemView;
    private Context context;
    private RemoveItemDialog dialog;
    private int itemPosition;
    List<ListaDeComprasModel> listaDeCompras = new ArrayList<>();
    List<ListaDeTarefasModel> listaDeTarefas = new ArrayList<>();
    List<FilmeModel> listaDeFilmes = new ArrayList<>();

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

       holder.constraintList.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatabaseReference reference = ConfiguracaoFirebase.getReferenciaFirebase().child(lista.get(position).getCategoria()).child(ConfiguracaoFirebase.getRefrenciaAutenticacao().getUid()).child(lista.get(position).getTitulo());
               System.out.println(lista.get(position).getCategoria() + lista.get(position).getTitulo());
               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot ds : dataSnapshot.getChildren()){
                           switch (lista.get(position).getCategoria()){
                               case AppGeral.LISTA_DE_COMRAS:
                                   ListaDeComprasModel compras = ds.getValue(ListaDeComprasModel.class);
                                   listaDeCompras.add(compras);
                                   break;
                               case AppGeral.LISTA_DE_TAREFAS:
                                   ListaDeTarefasModel tarefas = ds.getValue(ListaDeTarefasModel.class);
                                   listaDeTarefas.add(tarefas);
                                    break;
                               case AppGeral.LISTA_DE_FILMES:
                                   FilmeModel filmes = ds.getValue(FilmeModel.class);
                                   listaDeFilmes.add(filmes);
                                    break;
                           }
                       }
                       switch (lista.get(position).getCategoria()){
                           case AppGeral.LISTA_DE_COMRAS:
                               Intent intentCompras = new Intent(context, ListActivity.class);
                               intentCompras.putExtra(AppGeral.TITULO_LISTA, listaDeCompras.get(0).getTitulo());
                               intentCompras.putExtra(AppGeral.DESCRICAO_LISTA, listaDeCompras.get(0).getDescricao());
                               intentCompras.putExtra(AppGeral.CATEGORIA, AppGeral.LISTA_DE_COMRAS);
                               intentCompras.putExtra(AppGeral.ACTIVITY, AppGeral.ITEM_LIST_ADAPTER);
                               intentCompras.putExtra(AppGeral.LISTA_DE_COMRAS, (Serializable) listaDeCompras);
                               context.startActivity(intentCompras);
                               listaDeCompras.clear();
                               break;
                           case AppGeral.LISTA_DE_TAREFAS:
                               Intent intentTarefas = new Intent(context, ListActivity.class);
                               intentTarefas.putExtra(AppGeral.TITULO_LISTA, listaDeTarefas.get(0).getTitulo());
                               intentTarefas.putExtra(AppGeral.DESCRICAO_LISTA, listaDeTarefas.get(0).getDescricaoTarefa());
                               intentTarefas.putExtra(AppGeral.CATEGORIA, AppGeral.LISTA_DE_TAREFAS);
                               intentTarefas.putExtra(AppGeral.ACTIVITY, AppGeral.ITEM_LIST_ADAPTER);
                               intentTarefas.putExtra(AppGeral.LISTA_DE_TAREFAS, (Serializable) listaDeTarefas);
                               context.startActivity(intentTarefas);
                               listaDeTarefas.clear();
                               break;
                           case AppGeral.LISTA_DE_FILMES:
                               Intent intentFilmes = new Intent(context, ListActivity.class);
                               intentFilmes.putExtra(AppGeral.TITULO_LISTA, listaDeFilmes.get(0).getTitulo());
                               intentFilmes.putExtra(AppGeral.DESCRICAO_LISTA, listaDeFilmes.get(0).getDescricao());
                               intentFilmes.putExtra(AppGeral.CATEGORIA, AppGeral.LISTA_DE_FILMES);
                               intentFilmes.putExtra(AppGeral.ACTIVITY, AppGeral.ITEM_LIST_ADAPTER);
                               intentFilmes.putExtra(AppGeral.LISTA_DE_FILMES, (Serializable) listaDeFilmes);
                               context.startActivity(intentFilmes);
                               listaDeFilmes.clear();
                               break;
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }
       });
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

    public void updateList(List<ListaModel> lista){
        this.lista = lista;
    }

}
