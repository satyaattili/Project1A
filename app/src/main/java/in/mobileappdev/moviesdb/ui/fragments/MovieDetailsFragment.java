package in.mobileappdev.moviesdb.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.mobileappdev.moviesdb.R;

public class MovieDetailsFragment extends Fragment {

  private static final String ARG_PARAM1 = "mid";
  private static final String ARG_PARAM2 = "mname";
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private ViewPagerAdapter adapter;

  private long mMovieId;
  private String mMovieName;

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
    if (getArguments() != null) {
      mMovieId = getArguments().getLong(ARG_PARAM1);
      mMovieName = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);
    initViews(view);
    return view;
  }

  private void initViews(View view) {
    viewPager = (ViewPager) view.findViewById(R.id.viewpager);
    viewPager.setOffscreenPageLimit(3);

    setupViewPager(viewPager,mMovieId);

    tabLayout = (TabLayout) view.findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);
  }

  public void updateArticleView(long position) {
      mMovieId = position;
      setupViewPager(viewPager, position);
    if(adapter != null){
      adapter.notifyDataSetChanged();
    }
  }

  private void setupViewPager(ViewPager viewPager, long mid) {
    adapter = new ViewPagerAdapter(getChildFragmentManager());
    adapter.addFragment(MovieOverViewFragment.newInstance(mid), "Overview");
    adapter.addFragment(MovieTrailersFragment.newInstance(mid), "Trailers");
    adapter.addFragment(MovieReviewsFragment.newInstance(mid), "Reviews");
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

 /* @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }*/


}
