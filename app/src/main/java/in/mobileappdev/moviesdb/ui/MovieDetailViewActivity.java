package in.mobileappdev.moviesdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
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
  //private SlideShowPagerAdapter mSlideShowAdapter;
 // private ImageView mMoviePoster;
  YouTubePlayerSupportFragment youTubePlayerView;
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

    /** Initializing YouTube player view **/
    youTubePlayerView = (YouTubePlayerSupportFragment) getSupportFragmentManager()
        .findFragmentById(R.id.youtube_player_toolbar);

    // mSlideShowViewPager = (AutoScrollViewPager) findViewById(R.id.image_slideshow_pager);

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

  public void loadYoutubeVideo(final String url){
    youTubePlayerView.initialize(Constants.YOUTUBE_VIDEO_API_KEY,
        new YouTubePlayer.OnInitializedListener() {
          @Override
          public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                              YouTubePlayer youTubePlayer,
                                              boolean b) {
            youTubePlayer.loadVideo(url);
            youTubePlayer.play();
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
          }

          @Override
          public void onInitializationFailure(YouTubePlayer.Provider provider,
                                              YouTubeInitializationResult youTubeInitializationResult) {

          }
        });

  }

  public void setMovieToolbar(String imageurl, String name){
    collapsingToolbar.setTitle(name);
    /*Picasso.with(this).load(imageurl)
        .into
            (mMoviePoster);*/
  }



}
