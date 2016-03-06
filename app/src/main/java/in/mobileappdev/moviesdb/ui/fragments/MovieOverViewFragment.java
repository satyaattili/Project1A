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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.MovieDetailViewActivity;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieOverViewFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private long mMovieId;
  private static final String TAG = MovieOverViewFragment.class.getSimpleName();
  private CallMoviesAPI service;
  private CollapsingToolbarLayout collapsingToolbar;
  private ImageView mMoviePoster;
  private ProgressBar mLoading;
  private NestedScrollView mMovieContent;
  private TextView mOverView, mStatus, mVoting, mReleaseDate;
  private CardView mMovieStatusCard,mMovieVotingCard;

  public MovieOverViewFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment MovieOverViewFragment.
   */
  // TODO: Rename and change types and number of parameters
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
      Log.e(TAG, "Movie ID in Overview : "+mMovieId);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_over_view, container, false);
    initViews(view);
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    service = retrofit.create(CallMoviesAPI.class);
    service.getMovieDetails(mMovieId, Constants.API_KEY).enqueue(
        new Callback<MovieDetailsResponse>() {
          @Override
          public void onResponse(Call<MovieDetailsResponse> call,
                                 Response<MovieDetailsResponse> response) {
            if(response.body()!=null){
              displayMovieDetails(response.body());
            }

          }

          @Override
          public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
            Log.e(TAG, "OnFailure");
          }
        });
    return view;
  }

  private void initViews(View view) {

    mOverView= (TextView) view.findViewById(R.id.movie_overview);
    mStatus= (TextView) view.findViewById(R.id.movie_status);
    mVoting= (TextView) view.findViewById(R.id.movie_voting);
    mReleaseDate = (TextView)view.findViewById(R.id.release_date);
    mLoading = (ProgressBar) view.findViewById(R.id.progressBar);
    mMovieContent = (NestedScrollView) view.findViewById(R.id.movie_content);
    mMovieStatusCard = (CardView) view.findViewById(R.id.status_card);
    mMovieVotingCard = (CardView) view.findViewById(R.id.voting_card);
  }

  private void displayMovieDetails(MovieDetailsResponse movie){

    mLoading.setVisibility(View.GONE);
    mMovieContent.setVisibility(View.VISIBLE);

    String imageurl = Constants.IMAGE_BASE_URL+movie.getBackdrop_path();
    if(getActivity() != null){
     // ((MovieDetailViewActivity) getActivity()).setMovieToolbar(imageurl);
    }

    mOverView.setText(movie.getOverview());
    mStatus.setText(movie.getStatus());
    mReleaseDate.setText(getString(R.string.release_date).concat(" : ").concat(movie.getRelease_date
        ()));
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
