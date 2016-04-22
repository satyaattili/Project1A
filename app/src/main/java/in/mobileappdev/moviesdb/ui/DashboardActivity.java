package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.ui.fragments.DashboardFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieListFragment;
import in.mobileappdev.moviesdb.utils.Utils;


/**
 * Satya Attili
 */
public class DashboardActivity extends AppCompatActivity implements
    MovieListFragment.OnMovieSelectedListener {

  @Bind(R.id.toolbar)
  Toolbar mToolBar;
  long mMovieSelected = -1;
  private MovieDetailsFragment articleFrag;
  private DashboardFragment listFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    ButterKnife.bind(this);

    setSupportActionBar(mToolBar);

    if (findViewById(R.id.fragment_container) != null) {
      if (savedInstanceState != null) {
        return;
      }

      listFragment = DashboardFragment.newInstance();
      listFragment.setArguments(getIntent().getExtras());

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, listFragment).commit();
    } else {
      articleFrag = (MovieDetailsFragment)
          getSupportFragmentManager().findFragmentById(R.id.article_fragment);
    }
  }

  @Override
  public void onMovieSelected(long mid, String movieName, String posterPath) {

/*    if(!Utils.hasNetworkConnection(this)){
      Snackbar.make(findViewById(R.id.parentlayout), R.string.no_network_msg, Snackbar
          .LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
      }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light )).show();
      return;
    }*/

    if (articleFrag != null) {
      if (mMovieSelected != mid) {
        mMovieSelected = mid;
        articleFrag.updateArticleView(mid, movieName, posterPath);
        mToolBar.setTitle(movieName);
      }
    } else {
      if (Utils.hasNetworkConnection(this)) {
        Intent detailsActivity = new Intent(DashboardActivity.this, MovieDetailViewActivity.class);
        detailsActivity.putExtra("movie_id", mid);
        detailsActivity.putExtra("movie_name", movieName);
        detailsActivity.putExtra("movie_poster", posterPath);
        startActivity(detailsActivity);
      } else {
        Snackbar.make(findViewById(R.id.parentlayout), R.string.no_network_msg, Snackbar
            .LENGTH_LONG).show();
      }
    }
  }
}
