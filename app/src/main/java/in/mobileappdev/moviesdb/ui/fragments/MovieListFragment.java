package in.mobileappdev.moviesdb.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.Toast;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.views.EndlessRecyclerViewScrollListener;
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
  private int currentPage;
  //private ActionBar mActionBar;
  private RecyclerView mMovieRecyclerView;
  private OnMovieSelectedListener mOnMovieSelectedListener;
  private boolean isPopular = false;

  private boolean loading = true;
  int pastVisiblesItems, visibleItemCount, totalItemCount;

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
    mApiService = MovieDBApiHelper.getApiService();
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
    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
    mMovieRecyclerView.setLayoutManager(gridLayoutManager);

    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    itemAnimator.setAddDuration(3000);
    itemAnimator.setRemoveDuration(3000);
    itemAnimator.setChangeDuration(3000);
    itemAnimator.setMoveDuration(3000);
    mMovieRecyclerView.setItemAnimator(itemAnimator);

    mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) //check for scroll down
        {
          visibleItemCount = gridLayoutManager.getChildCount();
          totalItemCount = gridLayoutManager.getItemCount();
          pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

          if (loading) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              loading = false;
              Log.v("...", "Last Item Wow !");
              getMore(currentPage + 1);
              Toast.makeText(getActivity(), movies.size() + " Movies found", Toast.LENGTH_SHORT)
                  .show();
            }
          }
        }
      }
    });

    mMovieAdapter = new MovieGridAdapter(getActivity(), movies);
    mMovieAdapter.setOnMovieClickListener(new MovieGridAdapter.OnMovieClickListener() {
      @Override
      public void onMovieClick(String movieName, int movieId) {
        mOnMovieSelectedListener.onMovieSelected(movieId, movieName);
      }
    });

    mMovieRecyclerView.setAdapter(mMovieAdapter);
    mProgressBar.setVisibility(View.VISIBLE);

    mApiService.getPopularLatestMovies(1,Constants.API_KEY).enqueue(this);
    isPopular = true;
    mTitle = getString(R.string.filter_popular);

    mRetryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideErrorLayout();
        //mApiService.getPopularLatestMovies(Constants.API_KEY).enqueue(this);
      }
    });
  }

  private void getMore(int page) {
    if(isPopular){
      mApiService.getPopularLatestMovies(page,Constants.API_KEY).enqueue(this);
    }else{
      mApiService.getTopRatedtMovies(page,Constants.API_KEY).enqueue(this);
    }

  }


  @Override
  public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
    if(response.body() != null){
      movies.addAll(response.body().getResults());
      currentPage = response.body().getPage();
      hideErrorLayout();
      mMovieAdapter.notifyDataSetChanged();
    }
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(Call<MovieResponse> call, Throwable t) {
    if(movies.size()==0){
      showErrorLayout();
    }

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
      isPopular= true;
      mApiService.getPopularLatestMovies(1,Constants.API_KEY).enqueue(this);
      mTitle = getString(R.string.filter_popular);
    }else if(id==R.id.action_toprated){
      isPopular = false;
      mApiService.getTopRatedtMovies(1,Constants.API_KEY).enqueue(this);
      mTitle = getString(R.string.filter_toprated);
    }
    //mActionBar.setTitle(mTitle);
    return super.onOptionsItemSelected(item);
  }

  public interface OnMovieSelectedListener {
    public void onMovieSelected(long mId ,String mName);
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



}
