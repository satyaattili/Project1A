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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.TrailerListAdapter;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.models.VideosResponse;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.DashboardActivity;
import in.mobileappdev.moviesdb.ui.MovieDetailViewActivity;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieTrailersFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieID";
  private static final String TAG = MovieTrailersFragment.class.getSimpleName();
  private long mMovieId;
  private CallMoviesAPI service;
  private RecyclerView mRecyclerView;


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
    View view =  inflater.inflate(R.layout.fragment_movie_trailers, container, false);
    initViews(view);
    return view;
  }


  private void intialisesTrailers(VideosResponse body) {
    TrailerListAdapter adapter = new TrailerListAdapter(getActivity(), body.getResults());
    mRecyclerView.setAdapter(adapter);
    adapter.setOnTrailerClickedListener(new TrailerListAdapter.OnTrailerSelectedListener() {
      @Override
      public void onTrailerClicked(int id, String vidioId) {
        Intent intent =
            YouTubeIntents.createPlayVideoIntentWithOptions(getActivity(), vidioId, false,
                false);
        startActivity(intent);
      }
    });
  }

  private void initViews(View view) {
    // Initialize recycler view
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }


  @Subscribe
  public void getTrailerResponse(VideosResponse trailers){
    intialisesTrailers(trailers);
    ((MovieDetailViewActivity) getActivity()).loadYoutubeVideo(trailers.getResults().get(0).getKey());
  }

}
