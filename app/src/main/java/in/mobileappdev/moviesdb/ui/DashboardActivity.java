package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieListFragment;

public class DashboardActivity extends AppCompatActivity implements
    MovieListFragment.OnMovieSelectedListener{

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

      MovieListFragment firstFragment = MovieListFragment.newInstance();

      firstFragment.setArguments(getIntent().getExtras());

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, firstFragment).commit();
    }
  }

  @Override
  public void onMovieSelected(long position) {

    MovieDetailsFragment articleFrag = (MovieDetailsFragment)
        getSupportFragmentManager().findFragmentById(R.id.article_fragment);


    if (articleFrag != null) {
      Bundle args = new Bundle();
      args.putLong("mid", position);
      args.putString("mname", null);
      //articleFrag.setArguments(args);
      articleFrag.updateArticleView(position);
    } else {
     /* MovieDetailsFragment newFragment = new MovieDetailsFragment();
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragment_container, newFragment);
      transaction.addToBackStack(null);
      transaction.commit();*/
        Intent detailsActivity = new Intent(DashboardActivity.this, MovieDetailViewActivity.class);
        detailsActivity.putExtra("movie_id", position);
        detailsActivity.putExtra("movie_name", "Movie Name");
        startActivity(detailsActivity);
    }
  }


}
