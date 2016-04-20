package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.SlideShowPagerAdapter;
import in.mobileappdev.moviesdb.models.MovieImages;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.views.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailViewActivity extends AppCompatActivity {

  private static final String TAG = MovieDetailViewActivity.class.getSimpleName();
  @Bind(R.id.toolbar)
  Toolbar mToolBar;
  @Bind(R.id.collapsing_toolbar)
  CollapsingToolbarLayout mCollapsingToolBarLayout;
  @Bind(R.id.image_slideshow_pager)
  AutoScrollViewPager mSlideShowViewPager;
  Callback<MovieImages> imagesCallback = new Callback<MovieImages>() {
    @Override
    public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
      if (response.body() != null) {
        SlideShowPagerAdapter mSlideShowAdapter =
            new SlideShowPagerAdapter(MovieDetailViewActivity.this, response.body
                ());
        mSlideShowViewPager.setAdapter(mSlideShowAdapter);
        mSlideShowViewPager.setAutoScrollDurationFactor(10);
        mSlideShowViewPager.setInterval(3000);
        mSlideShowAdapter.notifyDataSetChanged();
        mSlideShowViewPager.startAutoScroll();
      }
    }

    @Override
    public void onFailure(Call<MovieImages> call, Throwable t) {

    }
  };
  private long movieId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_movie_detail_view);
    ButterKnife.bind(this);

    setSupportActionBar(mToolBar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mCollapsingToolBarLayout.setTitle("");
    }


    String movieName = null;
    Intent resultIntent = getIntent();
    if (resultIntent != null) {
      movieId = resultIntent.getLongExtra("movie_id", -1);
      movieName = resultIntent.getStringExtra("movie_name");
      mCollapsingToolBarLayout.setTitle(movieName);
    }

    MovieDBApiHelper.getApiService(this).getMovieImages(movieId, Constants.API_KEY).enqueue
        (imagesCallback);

    MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(movieId, movieName);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, detailsFragment).commit();

  }

  public void setMovieToolbar(String imageurl, String name) {
    mCollapsingToolBarLayout.setTitle(name);
  }
}
