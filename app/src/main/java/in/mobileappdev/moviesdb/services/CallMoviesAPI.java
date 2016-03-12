package in.mobileappdev.moviesdb.services;

import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.models.MovieImages;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.models.ReviewResponse;
import in.mobileappdev.moviesdb.models.VideosResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by satyanarayana.avv on 09-02-2016.
 */
public interface CallMoviesAPI {
    //@GET("discover/movie?sort_by=popularity.desc")
    @GET("movie/popular")
    Call<MovieResponse> getPopularLatestMovies(@Query("page") int pageId,@Query("api_key") String
        apiKey);

    @GET("movie/latest")
    Call<MovieResponse> getLatestMovies(@Query("page") int pageId,@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedtMovies(@Query("page") int pageId,@Query("api_key") String
        apiKey);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") long movieId, @Query("api_key") String
        apiKey);

    @GET("movie/{id}/videos")
    Call<VideosResponse> getMovieTrailers(@Path("id") long movieId, @Query("api_key") String
        apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("id") long movieId, @Query("api_key") String
        apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getRequestToken(@Path("id") long movieId, @Query("api_key") String
        apiKey);

    @GET("movie/{id}/credits")
    Call<Credits> getCredits(@Path("id") long movieId, @Query("api_key") String
        apiKey);

    @GET("movie/{id}/images")
    Call<MovieImages> getMovieImages(@Path("id") long movieId, @Query("api_key") String
        apiKey);



}