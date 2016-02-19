package in.mobileappdev.moviesdb.services;

import java.util.List;

import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by satyanarayana.avv on 09-02-2016.
 */
public interface CallMoviesAPI {
    //@GET("discover/movie?sort_by=popularity.desc")
    @GET("movie/popular")
    Call<MovieResponse> getPopularLatestMovies(@Query("api_key") String apiKey);

    @GET("movie/latest")
    Call<MovieResponse> getLatestMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedtMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int movieId, @Query("api_key") String
        apiKey);


}