package in.mobileappdev.moviesdb.services;

import java.util.List;

import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by satyanarayana.avv on 09-02-2016.
 */
public interface CallMoviesAPI {
    @Headers({
            "Accept: application/json",
            "api_key: "+ Constants.API_KEY
    })
    @GET("/movie/popular")
    Call<Movie> getMovies(Callback<List<Movie>> response);
}