package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<MovieResponse>{

    private static final String TAG = MainActivity.class.getSimpleName() ;
    private ArrayList<Result> movies = new ArrayList<>();
    private ProgressBar mProgressBar;
    private MovieGridAdapter mMovieAdapter;
    private CallMoviesAPI  mApiService;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        RecyclerView movieList = (RecyclerView) findViewById(R.id.movie_list);
        movieList.setHasFixedSize(true);
        movieList.setLayoutManager(new GridLayoutManager(this, 2));

        mMovieAdapter= new MovieGridAdapter(this, movies);
        mMovieAdapter.setOnMovieClickListener(new MovieGridAdapter.OnMovieClickListener() {
          @Override
          public void onMovieClick(String movieName, int movieId) {
            Intent detailsActivity = new Intent(MainActivity.this, MovieDetailViewActivity.class);
            detailsActivity.putExtra("movie_id", movieId);
            detailsActivity.putExtra("movie_name", movieName);
            startActivity(detailsActivity);
          }
        });

        movieList.setAdapter(mMovieAdapter);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApiService = retrofit.create(CallMoviesAPI.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      mProgressBar.setVisibility(View.VISIBLE);
        int id = item.getItemId();
        if (id == R.id.action_popular) {
          mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);
          return true;
        }else if (id == R.id.action_latest){
          mApiService.getLatestMovies(Constants.API_KEY).enqueue(this);
          return true;
        }else if(id==R.id.action_toprated){
          mApiService.getTopRatedtMovies(Constants.API_KEY).enqueue(this);
          return true;
        }

        return super.onOptionsItemSelected(item);
    }



  @Override
  public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
    Log.e(TAG, "onResponse " + response.body().getResults().size());
    if(response.body() != null){
      movies.clear();
      movies.addAll(response.body().getResults());
      mMovieAdapter.notifyDataSetChanged();
    }
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(Call<MovieResponse> call, Throwable t) {
    Log.e(TAG, "onFailure");
    mProgressBar.setVisibility(View.GONE);
  }
}
