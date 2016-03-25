package in.mobileappdev.moviesdb.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.db.DatabaseHandler;
import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.models.ReviewResponse;
import in.mobileappdev.moviesdb.models.VideosResponse;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment {

  private static final String ARG_PARAM1 = "mid";
  private static final String ARG_PARAM2 = "mname";
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private ViewPagerAdapter adapter;
  private long mMovieId;
  private String mMovieName;
  private ProgressBar mProgressLoading;

  public static MovieDetailsFragment newInstance(long mid, String mname) {
    MovieDetailsFragment fragment = new MovieDetailsFragment();
    Bundle args = new Bundle();
    args.putLong(ARG_PARAM1, mid);
    args.putString(ARG_PARAM2, mname);
    fragment.setArguments(args);
    return fragment;
  }

  public MovieDetailsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    if (getArguments() != null) {
      mMovieId = getArguments().getLong(ARG_PARAM1);
      mMovieName = getArguments().getString(ARG_PARAM2);
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);
    initViews(view);
    getActivity().invalidateOptionsMenu();
    return view;
  }

  private void initViews(View view) {
    mProgressLoading = (ProgressBar) view.findViewById(R.id.overview_loading) ;
    viewPager = (ViewPager) view.findViewById(R.id.viewpager);
    viewPager.setOffscreenPageLimit(3);

    setupViewPager(viewPager, mMovieId);

    tabLayout = (TabLayout) view.findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);

  }

  public void updateArticleView(long position) {
      mMovieId = position;
      MovieDBApiHelper.getApiService(getActivity()).getMovieReviews(mMovieId, Constants.API_KEY);
      setupViewPager(viewPager, position);
    if(adapter != null){
      adapter.notifyDataSetChanged();
    }
  }

  private void setupViewPager(ViewPager viewPager, long mid) {
    MovieDBApiHelper.getApiService(getActivity()).getMovieDetails(mid, Constants.API_KEY).enqueue
        (overViewResponseCallback);
    adapter = new ViewPagerAdapter(getChildFragmentManager());
    viewPager.setAdapter(adapter);
  }

  class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    getActivity().getMenuInflater().inflate(R.menu.menu_movie_details, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    if(DatabaseHandler.getInstance(getActivity()).isExistsInFavorites(mMovieId)){
      menu.findItem(R.id.action_favorite).setChecked(true);
      menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_favorite);
    }
    super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_favorite) {

      if(item.isChecked()){
        item.setChecked(false);
        item.setIcon(R.drawable.ic_action_favorite_outline);
        DatabaseHandler.getInstance(getActivity()).deleteContact(mMovieId);
        Snackbar.make(getView(), "Deleted from Favourites", Snackbar.LENGTH_SHORT).show();
      }else{
        item.setChecked(true);
        item.setIcon(R.drawable.ic_action_favorite);
        DatabaseHandler.getInstance(getActivity()).addToFavorites(mMovieId);
        Snackbar.make(getView(), "Added to your Favourites", Snackbar.LENGTH_SHORT).show();
      }
    }else if(id==R.id.home){

      getActivity().onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }


  Callback<ReviewResponse> reviewResponseCallback = new Callback<ReviewResponse>() {
    @Override
    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
      if( response.body()!=null && response.body().getResults().size()>0){
        adapter.addFragment(MovieReviewsFragment.newInstance(mMovieId), "Reviews");
        adapter.notifyDataSetChanged();
        BusProvider.getInstance().post(response.body());
      }
    }

    @Override
    public void onFailure(Call<ReviewResponse> call, Throwable t) {

    }
  };

  Callback<VideosResponse> trailerResponseCallback = new Callback<VideosResponse>() {
    @Override
    public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
      if(response.body()!=null && response.body().getResults().size()>0){
        adapter.addFragment(MovieTrailersFragment.newInstance(mMovieId), "Trailers");
        adapter.notifyDataSetChanged();
        BusProvider.getInstance().post(response.body());
      }
    }

    @Override
    public void onFailure(Call<VideosResponse> call, Throwable t) {

    }
  };


  Callback<MovieDetailsResponse> overViewResponseCallback = new Callback<MovieDetailsResponse>() {
    @Override
    public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
      if( response.body()!=null){
        adapter.addFragment(MovieOverViewFragment.newInstance(mMovieId), "Overview");
        adapter.notifyDataSetChanged();
        MovieDBApiHelper.getApiService(getActivity()).getCredits(mMovieId,Constants.API_KEY).enqueue
            (creditsCallback);
        MovieDBApiHelper.getApiService(getActivity()).getMovieTrailers(mMovieId, Constants.API_KEY).enqueue(
            trailerResponseCallback);
        MovieDBApiHelper.getApiService(getActivity()).getMovieReviews(mMovieId, Constants.API_KEY).enqueue
            (reviewResponseCallback);
        BusProvider.getInstance().post(response.body());
        mProgressLoading.setVisibility(View.GONE);
      }
    }

    @Override
    public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

    }
  };

  Callback<Credits> creditsCallback = new Callback<Credits>() {
    @Override
    public void onResponse(Call<Credits> call, Response<Credits> response) {
      if( response.body()!=null && response.body().getCast().size()>0){
        adapter.addFragment(MovieCastAndCrewFragment.newInstance(mMovieId), "CAST");
        adapter.notifyDataSetChanged();
        BusProvider.getInstance().post(response.body());
      }
    }

    @Override
    public void onFailure(Call<Credits> call, Throwable t) {

    }
  };



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


}
