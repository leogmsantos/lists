package br.com.leo.minhalistadecompras.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.api.Rest;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.views.adapter.ItemBuyListAdapter;
import br.com.leo.minhalistadecompras.views.adapter.ItemMovieListSearchedAdapter;

public class MovieSearchActivity extends AppCompatActivity implements Rest.RecuperarFilmes, ItemMovieListSearchedAdapter.onClick{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<FilmeModel> filmes = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private ItemMovieListSearchedAdapter mAdapter;
    private Rest rest = new Rest(this);
    private String titulo, descricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        initializeComponents();
    }

    private void initializeComponents(){
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerMovieSearch);

        recyclerSetup();
        onSearch();
        getExtras();
    }

    private void recyclerSetup(){
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ItemMovieListSearchedAdapter(filmes, this);
        recyclerView.setAdapter(mAdapter);

    }

    private void getExtras(){
        titulo = getIntent().getStringExtra("titulo");
        descricao = getIntent().getStringExtra("descricao");
    }

    private void onSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rest.getMovies(newText);
                return false;
            }
        });
    }

    @Override
    public void getMovies(List<FilmeModel> filmes) {
        this.filmes = filmes;
        System.out.println(this.filmes);
        mAdapter.atualizarLista(filmes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickListener(FilmeModel filme) {
        FilmeModel filmeSelecionado = filme;
        filmeSelecionado.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        filmeSelecionado.setTitulo(titulo);
        filmeSelecionado.setDescricao(descricao);
        filmeSelecionado.saveMovieOnFirebase();
        filmes.add(filmeSelecionado);
        Intent i = new Intent();
        i.putExtra(AppGeral.ITEM_FILME, filme);
        setResult(RESULT_OK, i);
        finish();
    }


}
