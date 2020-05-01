package br.com.leo.minhalistadecompras.api;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.util.JsonURLCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rest{

    private Context context;
    private List<FilmeModel> filmes;
    private String CATEGORY = "movie";
    private RecuperarFilmes recuperarFilmes;

    public Rest(Context context) {
        this.context = context;
    }

    public void getMovies(String query){
        APIService service = MoviesAPI.retrofit.create(APIService.class);
        Call<ListaDeFilmesResponse> call = service.getMovies(CATEGORY, MoviesAPI.API_KEY, query);
        call.enqueue(new Callback<ListaDeFilmesResponse>() {
            @Override
            public void onResponse(Call<ListaDeFilmesResponse> call, Response<ListaDeFilmesResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    filmes = new ArrayList<>();
                    for (int i = 0; i < response.body().getFilmes().size(); i++){
                        filmes.add(response.body().getFilmes().get(i));
                    }
                    recuperarFilmes = (RecuperarFilmes) context;
                    recuperarFilmes.getMovies(filmes);
                }
            }

            @Override
            public void onFailure(Call<ListaDeFilmesResponse> call, Throwable t) {
                Log.d("FAILURE", t.getMessage());
            }
        });
    }

    public interface RecuperarFilmes{
        void getMovies(List<FilmeModel> filmes);
    }
}
