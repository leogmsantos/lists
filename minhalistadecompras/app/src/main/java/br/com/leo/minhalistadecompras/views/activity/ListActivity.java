package br.com.leo.minhalistadecompras.views.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.api.MoviesAPI;
import br.com.leo.minhalistadecompras.api.Rest;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.util.StringUtils;
import br.com.leo.minhalistadecompras.views.adapter.ItemBuyListAdapter;
import br.com.leo.minhalistadecompras.views.adapter.ItemMovieListAdapter;
import br.com.leo.minhalistadecompras.views.dialog.CreateItemDialog;
import br.com.leo.minhalistadecompras.views.dialog.LoadingDialog;

public class ListActivity extends AppCompatActivity implements CreateItemDialog.DialogItemListener, Rest.RecuperarFilmes {

    private TextView tvTitulo, tvDescricao, tvSemLista;
    private String titulo, descricao, categoria;
    private Button btnAdditem, btnSearch;
    private FloatingActionButton btnNewItem;

    private RecyclerView recyclerItens;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private LoadingDialog dialog;

    private List<ListaDeComprasModel> listaDeCompras = new ArrayList<>();
    private List<FilmeModel> listaDeFilmes = new ArrayList<>();

    private int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeComponents();
        initializeRetrofit();
    }

    private void initializeComponents(){
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescricao = findViewById(R.id.tvDescricao);
        recyclerItens = findViewById(R.id.recyclerItemList);
        tvSemLista = findViewById(R.id.tvSemItens);
        btnAdditem = findViewById(R.id.btnAdditem);
        btnNewItem = findViewById(R.id.btnNewItem);
        btnSearch = findViewById(R.id.btnSearch);

        getExtras();
        recyclerSetup();
        validateList();
        criarItem();

    }

    private void initializeRetrofit(){
        MoviesAPI.retrofitSetup();
    }

    private void getExtras(){
        titulo = getIntent().getExtras().getString(AppGeral.TITULO_LISTA);
        descricao = getIntent().getExtras().getString(AppGeral.DESCRICAO_LISTA);
        categoria = getIntent().getExtras().getString(AppGeral.CATEGORIA);


        if (titulo != null){
            tvTitulo.setText(StringUtils.toCamelCase(titulo));
        }else{
            Toast.makeText(this, "Favor inserir um título", Toast.LENGTH_SHORT).show();
        }

        if (!descricao.isEmpty()){
            tvDescricao.setText(StringUtils.toCamelCase(descricao));
        }else{
            tvDescricao.setText("");
        }

    }

    private void recyclerSetup(){
        switch (categoria){
            case AppGeral.LISTA_DE_COMRAS:
                recyclerBuyList();
                break;
            case AppGeral.LISTA_DE_FILMES:
                recyclerMovieList();
                break;
        }
    }

    private void recyclerBuyList(){
        layoutManager = new LinearLayoutManager(this);
        recyclerItens.setLayoutManager(layoutManager);
        recyclerItens.setHasFixedSize(true);
        mAdapter = new ItemBuyListAdapter(listaDeCompras);
        recyclerItens.setAdapter(mAdapter);
    }

    private void recyclerMovieList(){
        layoutManager = new LinearLayoutManager(this);
        recyclerItens.setLayoutManager(layoutManager);
        recyclerItens.setHasFixedSize(true);
        mAdapter = new ItemMovieListAdapter(listaDeFilmes);
        recyclerItens.setAdapter(mAdapter);
    }

    private void validateList(){
        if (!listaDeCompras.isEmpty()){
            tvSemLista.setVisibility(View.GONE);
            btnAdditem.setVisibility(View.GONE);
            recyclerItens.setVisibility(View.VISIBLE);
            btnNewItem.setVisibility(View.VISIBLE);
        }

        if (!listaDeFilmes.isEmpty()){
            tvSemLista.setVisibility(View.GONE);
            btnAdditem.setVisibility(View.GONE);
            recyclerItens.setVisibility(View.VISIBLE);
            btnNewItem.setVisibility(View.VISIBLE);
        }
    }

    private void criarItem(){
        btnAdditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (categoria){
                    case AppGeral.LISTA_DE_COMRAS:
                        openCreateItemDialog();
                        break;
                    case AppGeral.LISTA_DE_FILMES:
                        Intent i = new Intent(ListActivity.this, MovieSearchActivity.class);
                        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                        break;
                    case AppGeral.LISTA_DE_TAREFAS:
                        break;
                }
            }
        });

        btnNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (categoria){
                    case AppGeral.LISTA_DE_COMRAS:
                        openCreateItemDialog();
                        break;
                    case AppGeral.LISTA_DE_FILMES:
                        Intent i = new Intent(ListActivity.this, MovieSearchActivity.class);
                        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                        break;
                    case AppGeral.LISTA_DE_TAREFAS:
                        break;
                }
            }
        });
    }

    private void openCreateItemDialog() {
        CreateItemDialog dialog = new CreateItemDialog();
        dialog.show(getSupportFragmentManager(), "Dialog de Item");
    }

    private void openLoadingDialog(){
        dialog = new LoadingDialog();
        dialog.show(getSupportFragmentManager(), "Loading Dialog");
    }

    @Override
    public void criarProduto(String nomeProduto, String valorProduto) {
        ListaDeComprasModel listaDeComprasModel = new ListaDeComprasModel();
        listaDeComprasModel.setProduto(nomeProduto);
        listaDeComprasModel.setValor(Double.valueOf(valorProduto));
        listaDeCompras.add(listaDeComprasModel);
        mAdapter.notifyDataSetChanged();
        validateList();
    }


    private void dismissDialog(){
        if (dialog.isVisible()){
            dialog.dismiss();
        }
    }

    @Override
    public void getMovies(List<FilmeModel> filmes) {
        listaDeFilmes = filmes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY){
            if (resultCode == RESULT_OK){
                FilmeModel filme = (FilmeModel) data.getSerializableExtra(AppGeral.ITEM_FILME);
                listaDeFilmes.add(filme);
                mAdapter.notifyDataSetChanged();
                validateList();
            }
        }
    }
}
