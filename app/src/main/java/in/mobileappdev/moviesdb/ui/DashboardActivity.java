package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieListFragment;


/**
 * Satya Attili
 */
public class DashboardActivity extends AppCompatActivity implements
    MovieListFragment.OnMovieSelectedListener{

  MovieDetailsFragment articleFrag;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (findViewById(R.id.fragment_container) != null) {
      if (savedInstanceState != null) {
        return;
      }

      MovieListFragment listFragment = MovieListFragment.newInstance();
      listFragment.setArguments(getIntent().getExtras());

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, listFragment).commit();
    }else{
      articleFrag = (MovieDetailsFragment)
          getSupportFragmentManager().findFragmentById(R.id.article_fragment);
    }
  }

  @Override
  public void onMovieSelected(long mid, String movieName) {

    if (articleFrag != null) {
        articleFrag.updateArticleView(mid);
    } else {
        Intent detailsActivity = new Intent(DashboardActivity.this, MovieDetailViewActivity.class);
        detailsActivity.putExtra("movie_id", mid);
        detailsActivity.putExtra("movie_name", movieName);
        startActivity(detailsActivity);
    }
  }

}