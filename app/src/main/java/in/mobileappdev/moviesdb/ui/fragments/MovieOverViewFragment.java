package in.mobileappdev.moviesdb.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.MovieDetailViewActivity;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
import in.mobileappdev.moviesdb.views.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieOverViewFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private long mMovieId;
  private static final String TAG = MovieOverViewFragment.class.getSimpleName();
  private CallMoviesAPI service;
  private CollapsingToolbarLayout collapsingToolbar;
  private ImageView mMoviePoster;
  private CircleImageView mThumbnailImage;
  private ProgressBar mLoading;
  private NestedScrollView mMovieContent;
  private TextView mOverView, mStatus, mVoting, mReleaseDate;
  private CardView mMovieStatusCard,mMovieVotingCard;

  @Bind(R.id.lb1) TextView leftButton1;
  @Bind(R.id.lb2) TextView leftButton2;
  @Bind(R.id.rb1) TextView rightButton1;
  @Bind(R.id.rb2) TextView rightButton2;


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
    BusProvider.getInstance().register(this);
    if (getArguments() != null) {
      mMovieId = getArguments().getLong(ARG_PARAM1);
    }
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
    initViews(view);
    return view;
  }


  /**
   * Intialise views
   * @param view
   */
  private void initViews(View view) {

    mOverView= (TextView) view.findViewById(R.id.movie_overview);
    mStatus= (TextView) view.findViewById(R.id.movie_status);
    mVoting= (TextView) view.findViewById(R.id.movie_voting);
    mReleaseDate = (TextView)view.findViewById(R.id.release_date);
    mLoading = (ProgressBar) view.findViewById(R.id.progressBar);
    mMovieContent = (NestedScrollView) view.findViewById(R.id.movie_content);
    mMovieStatusCard = (CardView) view.findViewById(R.id.status_card);
    mMovieVotingCard = (CardView) view.findViewById(R.id.voting_card);
    mThumbnailImage = (CircleImageView) view.findViewById(R.id.movie_thumbnail);
  }


  @Subscribe
  public void getOverViewResponse(MovieDetailsResponse movieDetails){
    displayMovieDetails(movieDetails);
  }


  /**
   * Display Movie Overview
   * @param movie
   */
  private void displayMovieDetails(MovieDetailsResponse movie){

    mLoading.setVisibility(View.GONE);
    mMovieContent.setVisibility(View.VISIBLE);

    leftButton1.setText(String.valueOf(movie.getPopularity()));
    leftButton2.setText(String.valueOf(movie.getRevenue()));

    rightButton1.setText(String.valueOf(movie.getVote_average()));
    rightButton2.setText(String.valueOf(movie.getRuntime()));

    String imageurl = Constants.IMAGE_BASE_URL+movie.getBackdrop_path();
    String posterUrl = Constants.IMAGE_BASE_URL+movie.getPoster_path();
    Picasso.with(getActivity()).load(posterUrl)
        .placeholder(R.drawable.default_movie).error(R.drawable.default_movie)
        .into(mThumbnailImage);
    if(getActivity() != null && getActivity() instanceof MovieDetailViewActivity){
     ((MovieDetailViewActivity) getActivity()).setMovieToolbar(imageurl,movie.getTitle());
    }

    mOverView.setText(movie.getOverview());
    mStatus.setText(movie.getStatus());
    mReleaseDate.setText(""+movie.getRelease_date());
    if(movie.getStatus().toLowerCase().equals("released")){
      mMovieStatusCard.setCardBackgroundColor(getResources().getColor(R.color.md_green_800));
    }else{
      mMovieStatusCard.setCardBackgroundColor(getResources().getColor(R.color.md_orange_800));
    }
    double voting = movie.getVote_average();
    mVoting.setText(String.valueOf(voting) + "/10");
    if(voting<= Constants.AVERAGE){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_red_800));
    }else if(voting>Constants.AVERAGE && voting<Constants.GOOD){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_orange_800));
    }else if(voting>=Constants.GOOD){
      mMovieVotingCard.setCardBackgroundColor(getResources().getColor(R.color.md_green_800));
    }

  }


}
