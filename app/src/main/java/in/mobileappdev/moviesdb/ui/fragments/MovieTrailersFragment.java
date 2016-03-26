package in.mobileappdev.moviesdb.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeIntents;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.TrailerListAdapter;
import in.mobileappdev.moviesdb.models.VideosResponse;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.BusProvider;



public class MovieTrailersFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private static final String TAG = MovieTrailersFragment.class.getSimpleName();
  private long mMovieId;
  private CallMoviesAPI service;
  @Bind(R.id.recycler_view) RecyclerView mRecyclerView;


  public MovieTrailersFragment() {
    // Required empty public constructor
  }

  public static MovieTrailersFragment newInstance(long param1) {
    MovieTrailersFragment fragment = new MovieTrailersFragment();
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
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    return view;
  }

  private void intialisesTrailers(VideosResponse body) {
    TrailerListAdapter adapter = new TrailerListAdapter(getActivity(), body.getResults());
    mRecyclerView.setAdapter(adapter);
    adapter.setOnTrailerClickedListener(new TrailerListAdapter.OnTrailerSelectedListener() {
      @Override
      public void onTrailerClicked(int id, String vidioId) {
        Intent intent =
            YouTubeIntents.createPlayVideoIntentWithOptions(getActivity().getApplicationContext(),
                vidioId, false,
                false);
        startActivity(intent);
      }
    });
  }



  @Subscribe
  public void getTrailerResponse(VideosResponse trailers) {
    intialisesTrailers(trailers);
  }

}
