package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieOverViewFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieReviewsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieTrailersFragment;

public class MovieDetailViewActivity extends AppCompatActivity {

  private static final String TAG = MovieDetailViewActivity.class.getSimpleName();
  private CollapsingToolbarLayout collapsingToolbar;
  private ImageView mMoviePoster;
  private Toolbar toolbar;
  private long movieId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_movie_detail_view);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    collapsingToolbar =  (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle("");
    mMoviePoster = (ImageView) findViewById(R.id.backdrop);

    String movieName = null;
    Intent resultIntent = getIntent();
    if(resultIntent != null){
      movieId = resultIntent.getLongExtra("movie_id", -1);
      movieName = resultIntent.getStringExtra("movie_name");
      collapsingToolbar.setTitle(movieName);
    }

    MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(movieId, movieName);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, detailsFragment).commit();

  }

  public void setMovieToolbar(String imageurl, String name){
    collapsingToolbar.setTitle(name);
    Picasso.with(this).load(imageurl)
        .into
            (mMoviePoster);
  }


}
