package in.mobileappdev.moviesdb.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.ui.MovieDetailViewActivity;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment implements Callback<MovieResponse> {

  private static final String TAG = MovieListFragment.class.getSimpleName() ;
  private ArrayList<Result> movies = new ArrayList<>();
  private ProgressBar mProgressBar;
  private MovieGridAdapter mMovieAdapter;
  private CallMoviesAPI mApiService;
  private String mTitle;
  private LinearLayout mErrorLayout;
  private Button mRetryButton;
  private TextView mErrorMessage;
  //private ActionBar mActionBar;
  private RecyclerView mMovieRecyclerView;
  private OnMovieSelectedListener mOnMovieSelectedListener;

  public static MovieListFragment newInstance() {
    return new MovieListFragment();
  }

  public MovieListFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    mApiService = Utils.getRertrofitApiServce();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_movie_list, container, false);
    initviews(view);
    return view;
  }

  private void initviews(View view) {
    mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

    mErrorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
    mErrorMessage = (TextView) view.findViewById(R.id.error_message);
    mRetryButton = (Button) view.findViewById(R.id.btn_retry);
    mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_list);
    mMovieRecyclerView.setHasFixedSize(true);
    mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    mMovieAdapter= new MovieGridAdapter(getActivity(), movies);
    mMovieAdapter.setOnMovieClickListener(new MovieGridAdapter.OnMovieClickListener() {
      @Override
      public void onMovieClick(String movieName, int movieId) {
        /*Intent detailsActivity = new Intent(getActivity(), MovieDetailViewActivity.class);
        detailsActivity.putExtra("movie_id", movieId);
        detailsActivity.putExtra("movie_name", movieName);
        startActivity(detailsActivity);*/
        mOnMovieSelectedListener.onMovieSelected(movieId);
      }
    });

    mMovieRecyclerView.setAdapter(mMovieAdapter);
    mProgressBar.setVisibility(View.VISIBLE);

    mApiService = Utils.getRertrofitApiServce();

    mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);
    mTitle = getString(R.string.filter_popular);

    mRetryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideErrorLayout();
        //mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);
      }
    });
  }


  @Override
  public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
    if(response.body() != null){
      movies.clear();
      movies.addAll(response.body().getResults());
      hideErrorLayout();
      mMovieAdapter.notifyDataSetChanged();
    }
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(Call<MovieResponse> call, Throwable t) {
    clearDataSet();
    showErrorLayout();
  }

  /**
   * Showing error layout
   */
  private void showErrorLayout(){
    mMovieRecyclerView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.GONE);
    mErrorLayout.setVisibility(View.VISIBLE);
  }

  /**
   * Hiding error layout
   */
  private void hideErrorLayout(){
    mMovieRecyclerView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.VISIBLE);
    mErrorLayout.setVisibility(View.GONE);
  }


  /**
   * Clearing data set
   */
  private void clearDataSet(){
    movies.clear();
    mMovieAdapter.notifyDataSetChanged();
  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    hideErrorLayout();
    clearDataSet();
    int id = item.getItemId();
    if (id == R.id.action_popular) {
      mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);
      mTitle = getString(R.string.filter_popular);
    }else if(id==R.id.action_toprated){
      mApiService.getTopRatedtMovies(Constants.API_KEY).enqueue(this);
      mTitle = getString(R.string.filter_toprated);
    }
    //mActionBar.setTitle(mTitle);
    return super.onOptionsItemSelected(item);
  }

  public interface OnMovieSelectedListener {
    public void onMovieSelected(long position);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception.
    try {
      mOnMovieSelectedListener = (OnMovieSelectedListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement OnHeadlineSelectedListener");
    }
  }


  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if(newConfig.getLayoutDirection() == Configuration.ORIENTATION_LANDSCAPE){
      mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }else{
      mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }
  }
}
