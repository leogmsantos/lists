package br.com.leo.minhalistadecompras.api;

import java.util.List;

import br.com.leo.minhalistadecompras.model.FilmeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("/3/search/{category}")
    Call<ListaDeFilmesResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("query") String movie
    );

}
