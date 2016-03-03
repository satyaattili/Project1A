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
import in.mobileappdev.moviesdb.ui.fragments.MovieOverViewFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieReviewsFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieTrailersFragment;

public class MovieDetailViewActivity extends AppCompatActivity {

  private static final String TAG = MovieDetailViewActivity.class.getSimpleName();
  private CollapsingToolbarLayout collapsingToolbar;
  private ImageView mMoviePoster;
  private Toolbar toolbar;
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private int movieId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_movie_detail_view);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(null);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    collapsingToolbar =  (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(null);
    mMoviePoster = (ImageView) findViewById(R.id.backdrop);

    String movieName = null;
    Intent resultIntent = getIntent();
    if(resultIntent != null){
      movieId = resultIntent.getIntExtra("movie_id", -1);
      movieName = resultIntent.getStringExtra("movie_name");
      collapsingToolbar.setTitle(movieName);
    }

    viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setOffscreenPageLimit(3);
    setupViewPager(viewPager);

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);
  }

  public void setMovieToolbar(String imageurl){
    Picasso.with(this).load(imageurl)
        .into
            (mMoviePoster);
  }

  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(MovieOverViewFragment.newInstance(movieId), "Overview");
    adapter.addFragment(MovieTrailersFragment.newInstance(movieId), "Trailers");
    adapter.addFragment(MovieReviewsFragment.newInstance(movieId), "Reviews");
    viewPager.setAdapter(adapter);
  }

  class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }


}
