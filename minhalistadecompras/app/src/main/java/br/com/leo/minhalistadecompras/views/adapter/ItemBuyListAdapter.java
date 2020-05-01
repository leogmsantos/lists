package br.com.leo.minhalistadecompras.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;

public class ItemBuyListAdapter extends RecyclerView.Adapter<ItemBuyListAdapter.MyViewHolder> {

    private List<ListaDeComprasModel> listaDeCompras;
    private View itemView;

    public ItemBuyListAdapter(List<ListaDeComprasModel> listaDeCompras) {
        this.listaDeCompras = listaDeCompras;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_de_compras, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tituloProduto.setText(listaDeCompras.get(position).getProduto());
        holder.valorProduto.setText(Double.toString(listaDeCompras.get(position).getValor()));
    }

    @Override
    public int getItemCount() {
        return listaDeCompras.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloProduto, valorProduto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloProduto = itemView.findViewById(R.id.tvTituloProtudo);
            valorProduto = itemView.findViewById(R.id.tvValorProduto);
        }
    }
}
