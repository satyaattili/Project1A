package in.mobileappdev.moviesdb.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Genre;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.MovieDetailViewActivity;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
import in.mobileappdev.moviesdb.views.CircleImageView;


public class MovieOverViewFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private long mMovieId;
  private static final String TAG = MovieOverViewFragment.class.getSimpleName();
  private CallMoviesAPI service;
  @Bind(R.id.movie_thumbnail) CircleImageView mThumbnailImage;
  @Bind(R.id.progressBar) ProgressBar mLoading;
  @Bind(R.id.movie_content) NestedScrollView mMovieContent;
  @Bind(R.id.movie_overview) TextView mOverView;
  @Bind(R.id.tag_line) TextView mTagline;
  @Bind(R.id.generes_parent) LinearLayout mMovieGeneres;

  @Bind(R.id.lb1) TextView leftButton1;
  @Bind(R.id.lb2) TextView leftButton2;
  @Bind(R.id.rb1) TextView rightButton1;
  @Bind(R.id.rb2) TextView rightButton2;

  @Bind(R.id.vote_avg_card) CardView votingCard;


  public MovieOverViewFragment() {
    // Required empty public constructor
  }

  public static MovieOverViewFragment newInstance(long param1) {
    MovieOverViewFragment fragment = new MovieOverViewFragment();
    Bundle args = new Bundle();
    args.putLong(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mMovieId = getArguments().getLong(ARG_PARAM1);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    BusProvider.getInstance().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    BusProvider.getInstance().unregister(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_over_view, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Subscribe
  public void getOverViewResponse(MovieDetailsResponse movieDetails){
    displayMovieDetails(movieDetails);
  }


  /**
   * Display Movie Overview
   * @param movie
   */
  @TargetApi(Build.VERSION_CODES.M)
  private void displayMovieDetails(MovieDetailsResponse movie){

    mLoading.setVisibility(View.GONE);
    mMovieContent.setVisibility(View.VISIBLE);

    leftButton1.setText(String.valueOf(movie.getImdb_id()));
    leftButton2.setText(movie.getRelease_date());

    rightButton1.setText(String.valueOf(movie.getVote_average()));
    rightButton2.setText(Utils.getFormattedTime(movie.getRuntime()));

    String imageurl = Constants.IMAGE_BASE_URL+movie.getBackdrop_path();
    String posterUrl = Constants.IMAGE_BASE_URL+movie.getPoster_path();
    Picasso.with(getActivity()).load(posterUrl)
        .placeholder(R.drawable.default_movie).error(R.drawable.default_movie)
        .into(mThumbnailImage);
    if(getActivity() != null && getActivity() instanceof MovieDetailViewActivity){
     ((MovieDetailViewActivity) getActivity()).setMovieToolbar(imageurl,movie.getTitle());
    }

    mOverView.setText(movie.getOverview());
    mTagline.setText(movie.getTagline());
    double voting = movie.getVote_average();
    if(voting<= Constants.AVERAGE){
      votingCard.setCardBackgroundColor(getResources().getColor(R.color.md_red_800));
    }else if(voting>Constants.AVERAGE && voting<Constants.GOOD){
      votingCard.setCardBackgroundColor(getResources().getColor(R.color.md_orange_800));
    }else if(voting>=Constants.GOOD){
      votingCard.setCardBackgroundColor(getResources().getColor(R.color.md_green_800));
    }


    //generes
    List<Genre> generes = movie.getGenres();
    for(Genre genre : generes){
      TextView genreString = new TextView(getActivity());
      genreString.setText(genre.getName());
      genreString.setPadding(20,20,20,20);
//      genreString.setTextAppearance(R.style.GenreText);
      genreString.setBackgroundResource(R.drawable.rounded_bg);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT
      );
      params.setMargins(10, 10,10,10);
      genreString.setLayoutParams(params);
      mMovieGeneres.addView(genreString);

    }
  }


}
