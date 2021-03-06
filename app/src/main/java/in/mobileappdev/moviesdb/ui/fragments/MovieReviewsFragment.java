package in.mobileappdev.moviesdb.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.ReviewListAdapter;
import in.mobileappdev.moviesdb.models.ReviewResponse;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieReviewsFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private static final String TAG = MovieReviewsFragment.class.getSimpleName();
  @Bind(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @Bind(R.id.progress_bar)
  ContentLoadingProgressBar mProgressBar;
  private long mMovieId;
  private CallMoviesAPI service;


  public MovieReviewsFragment() {
    // Required empty public constructor
  }

  public static MovieReviewsFragment newInstance(long param1) {
    MovieReviewsFragment fragment = new MovieReviewsFragment();
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
    View view = inflater.inflate(R.layout.fragment_movie_trailers, container, false);
    ButterKnife.bind(this, view);
    mProgressBar.setVisibility(View.VISIBLE);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    return view;
  }

  private void getVideos() {
    service = MovieDBApiHelper.getApiService(getActivity());
    service.getMovieReviews(mMovieId, Constants.API_KEY).enqueue(
        new Callback<ReviewResponse>() {
          @Override
          public void onResponse(Call<ReviewResponse> call,
                                 Response<ReviewResponse> response) {
            //Log.e(TAG, "" + response.body().getResults().size());

          }

          @Override
          public void onFailure(Call<ReviewResponse> call, Throwable t) {
            Log.e(TAG, "OnFailure");
          }
        });
  }

  private void intialisesReviews(ReviewResponse body) {
    if (body != null) {
      ReviewListAdapter adapter = new ReviewListAdapter(getActivity(), body.getResults());
      mRecyclerView.setAdapter(adapter);
      mProgressBar.setVisibility(View.GONE);
    }

  }

  @Subscribe
  public void reviewResponse(ReviewResponse response) {
    Log.e(TAG, "Subscripe review response : " + response);
    intialisesReviews(response);
  }

}
