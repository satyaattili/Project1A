package in.mobileappdev.moviesdb.ui.fragments;


import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.FavoritesGridAdapter;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.db.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

  private static final String TAG = "FavoritesFragment";
  @Bind(R.id.progressBar)
  ProgressBar mProgressBar;
  @Bind(R.id.error_layout)
  LinearLayout mErrorLayout;
  @Bind(R.id.btn_retry)
  Button mRetryButton;
  @Bind(R.id.error_message)
  TextView mErrorMessage;
  @Bind(R.id.movie_list)
  RecyclerView mMovieRecyclerView;
  @Bind(R.id.error_image)
  ImageView errorImageView;
  private MovieListFragment.OnMovieSelectedListener mOnMovieSelectedListener;
  private FavoritesGridAdapter adapter;


  public FavoritesFragment() {
    // Required empty public constructor
  }

  public static FavoritesFragment newInstance() {
    return new FavoritesFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  private void initViews() {
    mMovieRecyclerView.setHasFixedSize(true);
    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    mMovieRecyclerView.setLayoutManager(gridLayoutManager);

    //animation
    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    itemAnimator.setAddDuration(3000);
    itemAnimator.setRemoveDuration(3000);
    itemAnimator.setChangeDuration(3000);
    itemAnimator.setMoveDuration(3000);
    mMovieRecyclerView.setItemAnimator(itemAnimator);


  }

  @Override
  public void onResume() {
    super.onResume();
    Cursor favMovieCursor = DatabaseHandler.getInstance(getActivity()).getAllFavoriteMovies();
    adapter = new FavoritesGridAdapter(getActivity(), favMovieCursor);
    if(favMovieCursor != null && favMovieCursor.getCount()>0)
    {
      hideErrorLayout();
      mMovieRecyclerView.setAdapter(adapter);
      adapter.notifyDataSetChanged();

      adapter.setOnMovieClickListener(new FavoritesGridAdapter.OnMovieClickListener() {
        @Override
        public void onMovieClick(String movieName, int movieId, String posterPath) {
          mOnMovieSelectedListener.onMovieSelected(movieId, movieName, posterPath);
        }
      });
    }else {
      showErrorLayout();
    }

  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mOnMovieSelectedListener = (MovieListFragment.OnMovieSelectedListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement mOnMovieSelectedListener");
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    } else {
      mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }
  }

  /**
   * Showing error layout
   */
  private void showErrorLayout() {
    mMovieRecyclerView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.GONE);
    mErrorLayout.setVisibility(View.VISIBLE);
    mErrorMessage.setVisibility(View.VISIBLE);
    mErrorMessage.setText(R.string.fav_error_msg);
    errorImageView.setImageResource(R.drawable.fav_error_icon);
    mRetryButton.setVisibility(View.GONE);
  }

  /**
   * Hiding error layout
   */
  private void hideErrorLayout() {
    mMovieRecyclerView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.GONE);
    mErrorLayout.setVisibility(View.GONE);
  }

}
