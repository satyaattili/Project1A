package in.mobileappdev.moviesdb.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.db.DatabaseHandler;
import in.mobileappdev.moviesdb.models.Credits;
import in.mobileappdev.moviesdb.models.MovieDetailsResponse;
import in.mobileappdev.moviesdb.models.ReviewResponse;
import in.mobileappdev.moviesdb.models.VideosResponse;
import in.mobileappdev.moviesdb.rest.MovieDBApiHelper;
import in.mobileappdev.moviesdb.utils.BusProvider;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment {

  private static final String ARG_PARAM1 = "mid";
  private static final String ARG_PARAM2 = "mname";
  private static final String TAG = MovieDetailsFragment.class.getSimpleName();
  private static final String ARG_PARAM3 = "poster";
  @Bind(R.id.overview_loading)
  ProgressBar mProgressLoading;
  @Bind(R.id.viewpager)
  ViewPager viewPager;
  @Bind(R.id.tabs)
  TabLayout tabLayout;
  private ViewPagerAdapter adapter;
  private long mMovieId;
  Callback<ReviewResponse> reviewResponseCallback = new Callback<ReviewResponse>() {
    @Override
    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
      if (isAdded()) {
        if (response.body() != null && response.body().getResults().size() > 0) {
          adapter.addFragment(MovieReviewsFragment.newInstance(mMovieId),
              getString(R.string.reviews));
          adapter.notifyDataSetChanged();
          BusProvider.getInstance().post(response.body());
        }
      }

    }

    @Override
    public void onFailure(Call<ReviewResponse> call, Throwable t) {

    }
  };
  Callback<VideosResponse> trailerResponseCallback = new Callback<VideosResponse>() {
    @Override
    public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
      if (response.body() != null && response.body().getResults().size() > 0 && isAdded()) {
        adapter.addFragment(MovieTrailersFragment.newInstance(mMovieId),
            getString(R.string.trailers));
        adapter.notifyDataSetChanged();
        BusProvider.getInstance().post(response.body());
      }
    }

    @Override
    public void onFailure(Call<VideosResponse> call, Throwable t) {

    }
  };
  Callback<Credits> creditsCallback = new Callback<Credits>() {
    @Override
    public void onResponse(Call<Credits> call, Response<Credits> response) {
      if (response.body() != null && response.body().getCast().size() > 0 && isAdded()) {
        adapter.addFragment(MovieCastAndCrewFragment.newInstance(mMovieId),
            getString(R.string.cast));
        adapter.notifyDataSetChanged();
        BusProvider.getInstance().post(response.body());
      }
    }

    @Override
    public void onFailure(Call<Credits> call, Throwable t) {

    }
  };
  Callback<MovieDetailsResponse> overViewResponseCallback = new Callback<MovieDetailsResponse>() {
    @Override
    public void onResponse(Call<MovieDetailsResponse> call,
                           Response<MovieDetailsResponse> response) {
      adapter.clearFragments();
      if (response.body() != null && isAdded()) {
        Log.d(TAG, "" + response.body().getTitle());
        adapter.addFragment(MovieOverViewFragment.newInstance(mMovieId),
            getString(R.string.overview));
        adapter.notifyDataSetChanged();
        MovieDBApiHelper.getApiService(getActivity())
            .getCredits(mMovieId, Constants.API_KEY)
            .enqueue
                (creditsCallback);
        MovieDBApiHelper.getApiService(getActivity())
            .getMovieTrailers(mMovieId, Constants.API_KEY)
            .enqueue(
                trailerResponseCallback);
        MovieDBApiHelper.getApiService(getActivity())
            .getMovieReviews(mMovieId, Constants.API_KEY)
            .enqueue
                (reviewResponseCallback);
        mProgressLoading.setVisibility(View.GONE);
        BusProvider.getInstance().post(response.body());
        tabLayout.setupWithViewPager(viewPager);

      }
    }

    @Override
    public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

    }
  };
  private String mMovieName;
  private String mMoviePoster;

  public MovieDetailsFragment() {
    // Required empty public constructor
  }

  public static MovieDetailsFragment newInstance(long mid, String mname, String poster) {
    MovieDetailsFragment fragment = new MovieDetailsFragment();
    Bundle args = new Bundle();
    args.putLong(ARG_PARAM1, mid);
    args.putString(ARG_PARAM2, mname);
    args.putString(ARG_PARAM3, poster);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    if (getArguments() != null) {
      mMovieId = getArguments().getLong(ARG_PARAM1);
      mMovieName = getArguments().getString(ARG_PARAM2);
      mMoviePoster = getArguments().getString(ARG_PARAM3);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
    ButterKnife.bind(this, view);
    initViews(view);
    getActivity().invalidateOptionsMenu();
    return view;
  }

  private void initViews(View view) {
    viewPager.setOffscreenPageLimit(3);
    setupViewPager(viewPager, mMovieId);
    tabLayout.setSmoothScrollingEnabled(true);

  }

  public void updateArticleView(long position, String title, String poster) {
    mMovieId = position;
    mMovieName = title;
    mMoviePoster = poster;
    adapter.clearFragments();
    setupViewPager(viewPager, position);
    if (adapter != null) {
      adapter.notifyDataSetChanged();
    }
  }

  private void setupViewPager(ViewPager viewPager, long mid) {
    MovieDBApiHelper.getApiService(getActivity()).getMovieDetails(mid, Constants.API_KEY).enqueue
        (overViewResponseCallback);
    adapter = new ViewPagerAdapter(getChildFragmentManager());
    viewPager.setAdapter(adapter);
    getActivity().invalidateOptionsMenu();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    getActivity().getMenuInflater().inflate(R.menu.menu_movie_details, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    if (DatabaseHandler.getInstance(getActivity()).isExistsInFavorites(mMovieId)) {
      menu.findItem(R.id.action_favorite).setChecked(true);
      menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_favorite);
    }
    super.onPrepareOptionsMenu(menu);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_favorite) {
      if (item.isChecked()) {
        item.setChecked(false);
        item.setIcon(R.drawable.ic_action_favorite_outline);
        DatabaseHandler.getInstance(getActivity()).deleteContact(mMovieId);
        Snackbar.make(getView(), R.string.deleted_from_fav, Snackbar.LENGTH_SHORT).show();
        BusProvider.getInstance().post(false);
      } else {
        item.setChecked(true);
        item.setIcon(R.drawable.ic_action_favorite);
        DatabaseHandler.getInstance(getActivity())
            .addToFavorites(mMovieId, mMovieName, mMoviePoster);
        Snackbar.make(getView(), R.string.added_to_fav, Snackbar.LENGTH_SHORT).show();
        BusProvider.getInstance().post(true);
      }
    } else if (id == android.R.id.home) {
      getActivity().onBackPressed();
    }
    return true;
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

    public void clearFragments() {
      mFragmentList.clear();
      mFragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }


}
