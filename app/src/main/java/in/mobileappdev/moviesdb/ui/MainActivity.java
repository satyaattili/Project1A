package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
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
    private String mTitle;
    private LinearLayout mErrorLayout;
    private Button mRetryButton;
    private TextView mErrorMessage;
    private ActionBar mActionBar;
    private RecyclerView mMovieRecyclerView;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mErrorLayout = (LinearLayout) findViewById(R.id.error_layout);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        mRetryButton = (Button) findViewById(R.id.btn_retry);
        mMovieRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

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

        mMovieRecyclerView.setAdapter(mMovieAdapter);
        mProgressBar.setVisibility(View.VISIBLE);

        mApiService = MovieDBApiHelper.getApiService();

        mApiService.getPopularLatestMovies(1,Constants.API_KEY).enqueue(this);
        mTitle = getString(R.string.filter_popular);
        mActionBar.setTitle(mTitle);

        mRetryButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          hideErrorLayout();
          mApiService.getPopularLatestMovies(1,Constants.API_KEY).enqueue(MainActivity.this);
        }
      });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideErrorLayout();
        clearDataSet();
        int id = item.getItemId();
        if (id == R.id.action_popular) {
          mApiService.getPopularLatestMovies(1,Constants.API_KEY).enqueue(this);
          mTitle = getString(R.string.filter_popular);
        }else if(id==R.id.action_toprated){
          mApiService.getTopRatedtMovies(1,Constants.API_KEY).enqueue(this);
          mTitle = getString(R.string.filter_toprated);
        }
        mActionBar.setTitle(mTitle);
        return super.onOptionsItemSelected(item);
    }



  @Override
  public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
    if(response.body() != null){
      movies.clear();
      movies.addAll(response.body().getResults());
      hideErrorLayout();
      mMovieAdapter.notifyDataSetChanged();
    }
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(Call<MovieResponse> call, Throwable t) {
    clearDataSet();
    showErrorLayout();
  }

  /**
   * Showing error layout
   */
  private void showErrorLayout(){
    mMovieRecyclerView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.GONE);
    mErrorLayout.setVisibility(View.VISIBLE);
  }

  /**
   * Hiding error layout
   */
  private void hideErrorLayout(){
    mMovieRecyclerView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.VISIBLE);
    mErrorLayout.setVisibility(View.GONE);
  }


  /**
   * Clearing data set
   */
  private void clearDataSet(){
    movies.clear();
    mMovieAdapter.notifyDataSetChanged();
  }

}
