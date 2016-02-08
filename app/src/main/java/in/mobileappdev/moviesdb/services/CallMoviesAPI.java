package in.mobileappdev.moviesdb.services;

import in.mobileappdev.moviesdb.models.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by satyanarayana.avv on 09-02-2016.
 */
public interface CallMoviesAPI {
    @GET("/movie/popular")
    Call<Movie> getMovies(
            @Query("api_key") String key
    );
}