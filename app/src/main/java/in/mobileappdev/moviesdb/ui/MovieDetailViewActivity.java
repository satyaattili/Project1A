package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.SlideShowPagerAdapter;
import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.MovieImages;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.ui.fragments.MovieCastAndCrewFragment;
import in.mobileappdev.moviesdb.ui.fragments.MovieDetailsFragment;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.views.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailViewActivity extends AppCompatActivity {

  private static final String TAG = MovieDetailViewActivity.class.getSimpleName();
  private CollapsingToolbarLayout collapsingToolbar;
  private AutoScrollViewPager mSlideShowViewPager;
  private SlideShowPagerAdapter mSlideShowAdapter;
 // private ImageView mMoviePoster;
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
    //mMoviePoster = (ImageView) findViewById(R.id.backdrop);

    mSlideShowViewPager = (AutoScrollViewPager) findViewById(R.id.image_slideshow_pager);

    String movieName = null;
    Intent resultIntent = getIntent();
    if(resultIntent != null){
      movieId = resultIntent.getLongExtra("movie_id", -1);
      movieName = resultIntent.getStringExtra("movie_name");
      collapsingToolbar.setTitle(movieName);
    }

    MovieDBApiHelper.getApiService().getMovieImages(movieId, Constants.API_KEY).enqueue(imagesCallback);

    MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(movieId, movieName);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, detailsFragment).commit();

  }

  public void setMovieToolbar(String imageurl, String name){
    collapsingToolbar.setTitle(name);
    /*Picasso.with(this).load(imageurl)
        .into
            (mMoviePoster);*/
  }

  Callback<MovieImages> imagesCallback = new Callback<MovieImages>() {
    @Override
    public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
      if(response.isSuccess() && response.body()!=null){
        mSlideShowAdapter = new SlideShowPagerAdapter(MovieDetailViewActivity.this, response.body
            ());
        mSlideShowViewPager.setAdapter(mSlideShowAdapter);
        mSlideShowViewPager.setAutoScrollDurationFactor(10);
        mSlideShowViewPager.setInterval(3000);
       //\ mSlideShowViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mSlideShowAdapter.notifyDataSetChanged();
        mSlideShowViewPager.startAutoScroll();
      }
    }

    @Override
    public void onFailure(Call<MovieImages> call, Throwable t) {

    }
  };

  public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
      int pageWidth = view.getWidth();
      int pageHeight = view.getHeight();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0);

      } else if (position <= 1) { // [-1,1]
        // Modify the default slide transition to shrink the page as well
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
        if (position < 0) {
          view.setTranslationX(horzMargin - vertMargin / 2);
        } else {
          view.setTranslationX(-horzMargin + vertMargin / 2);
        }

        // Scale the page down (between MIN_SCALE and 1)
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        // Fade the page relative to its size.
        view.setAlpha(MIN_ALPHA +
            (scaleFactor - MIN_SCALE) /
                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }

}
