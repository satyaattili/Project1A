package in.mobileappdev.moviesdb.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieCastAdapter;
import in.mobileappdev.moviesdb.adapters.MovieCrewAdapter;
import in.mobileappdev.moviesdb.models.Cast;
import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.Crew;
import in.mobileappdev.moviesdb.utils.BusProvider;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieCastAndCrewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieCastAndCrewFragment extends Fragment {

  private static final String ARG_PARAM1 = "movieId";
  private long mMovieID;
  @Bind(R.id.cast_list) RecyclerView mCastRecyclerView;
  @Bind(R.id.crew_list) RecyclerView mCrewRecyclerView;
  MovieCastAdapter movieCastAdapter;
  ArrayList<Cast> mCastDataSet = new ArrayList<Cast>();
  MovieCrewAdapter mMovieCrewAdapter;
  ArrayList<Crew> mCrewDataSet = new ArrayList<Crew>();


  public MovieCastAndCrewFragment() {
    // Required empty public constructor
  }

  public static MovieCastAndCrewFragment newInstance(long param1) {
    MovieCastAndCrewFragment fragment = new MovieCastAndCrewFragment();
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
      mMovieID = getArguments().getLong(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_cast_and_crew, container, false);
    ButterKnife.bind(this, view);
    mCastRecyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    mCastRecyclerView.setLayoutManager(layoutManager);
    movieCastAdapter = new MovieCastAdapter(getActivity(), mCastDataSet);
    mCastRecyclerView.setAdapter(movieCastAdapter);

    //crew
    mCrewRecyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager1
        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    mCrewRecyclerView.setLayoutManager(layoutManager1);
    mMovieCrewAdapter = new MovieCrewAdapter(getActivity(), mCrewDataSet);
    mCrewRecyclerView.setAdapter(mMovieCrewAdapter);
    return view;
  }

  @Subscribe
  public void getCastDetails(Credits credits){
    mCastDataSet.addAll(credits.getCast());
    movieCastAdapter.notifyDataSetChanged();
    mCrewDataSet.addAll(credits.getCrew());
    mMovieCrewAdapter.notifyDataSetChanged();
  }

  @Override
  public void onStop() {
    super.onStop();
    BusProvider.getInstance().unregister(this);
  }
}
