package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailViewActivity extends AppCompatActivity {

  private static final String TAG = MovieDetailViewActivity.class.getSimpleName();
  private CallMoviesAPI  service;
  private CollapsingToolbarLayout collapsingToolbar;
  private ImageView mMoviePoster;
  private ProgressBar mLoading;
  private NestedScrollView mMovieContent;
  private TextView mOverView, mStatus, mVoting;
  private CardView mMovieStatusCard,mMovieVotingCard;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_movie_detail_view);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    initViews();

    int movieId = -1;
    String movieName = null;
    Intent resultIntent = getIntent();
    if(resultIntent != null){
      movieId = resultIntent.getIntExtra("movie_id", -1);
      movieName = resultIntent.getStringExtra("movie_name");
      collapsingToolbar.setTitle(movieName);
    }

    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    service = retrofit.create(CallMoviesAPI.class);
    service.getMovieDetails(movieId, Constants.API_KEY).enqueue(
        new Callback<MovieDetailsResponse>() {
          @Override
          public void onResponse(Call<MovieDetailsResponse> call,
                                 Response<MovieDetailsResponse> response) {
            displayMovieDetails(response.body());
          }

          @Override
          public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
            Log.e(TAG, "OnFailure");
          }
        });
  }



  public void initViews(){

    collapsingToolbar =  (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    mMoviePoster = (ImageView) findViewById(R.id.backdrop);

    mOverView= (TextView) findViewById(R.id.movie_overview);
    mStatus= (TextView) findViewById(R.id.movie_status);
    mVoting= (TextView) findViewById(R.id.movie_voting);
    mLoading = (ProgressBar) findViewById(R.id.progressBar);
    mMovieContent = (NestedScrollView) findViewById(R.id.movie_content);
    mMovieStatusCard = (CardView) findViewById(R.id.status_card);
    mMovieVotingCard = (CardView) findViewById(R.id.voting_card);
  }

  private void displayMovieDetails(MovieDetailsResponse movie){

    mLoading.setVisibility(View.GONE);
    mMovieContent.setVisibility(View.VISIBLE);

    collapsingToolbar.setTitle(movie.getTitle());
    String imageurl = Constants.IMAGE_BASE_URL+movie.getBackdrop_path();
    Picasso.with(this).load(imageurl)
        .into
            (mMoviePoster);


    mOverView.setText(movie.getOverview());
    mStatus.setText(movie.getStatus());
    if(movie.getStatus().toLowerCase().equals("released")){
      mMovieStatusCard.setCardBackgroundColor(getResources().getColor(R.color.md_green_800));
    }else{
      mMovieStatusCard.setCardBackgroundColor(getResources().getColor(R.color.md_orange_800));
    }
    double voting = movie.getVote_average();
    mVoting.setText(String.valueOf(voting)+"/10");
    if(voting<=4){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_red_800));
    }else if(voting>4 && voting<7.5){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_orange_800));
    }else if(voting>=7.5){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_green_800));
    }

  }

}
