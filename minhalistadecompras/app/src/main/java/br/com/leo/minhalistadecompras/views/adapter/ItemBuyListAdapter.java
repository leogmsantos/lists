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
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.views.dialog.RemoveItemDialog;

public class ItemBuyListAdapter extends RecyclerView.Adapter<ItemBuyListAdapter.MyViewHolder> implements RemoveItemDialog.DialogRemoveItemListener{

    private List<ListaDeComprasModel> listaDeCompras;
    private View itemView;
    private RemoveItemDialog dialog;
    private Context context;
    private int itemPosition;

    public ItemBuyListAdapter(List<ListaDeComprasModel> listaDeCompras, Context context) {
        this.listaDeCompras = listaDeCompras;
        this.context = context;
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

        holder.constraintBuy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemPosition = position;
                dialog = new RemoveItemDialog(itemView.getContext());
                dialog.setListener(ItemBuyListAdapter.this);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Remove Dialog");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeCompras.size();
    }

    @Override
    public void removeItem(Boolean remove) {
        if (remove == true){
            listaDeCompras.remove(itemPosition);
            notifyDataSetChanged();
            dialog.dismiss();
        }else{
            dialog.dismiss();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloProduto, valorProduto;
        private ConstraintLayout constraintBuy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloProduto = itemView.findViewById(R.id.tvTituloProtudo);
            valorProduto = itemView.findViewById(R.id.tvValorProduto);
            constraintBuy = itemView.findViewById(R.id.constraintBuy);
        }
    }
}
