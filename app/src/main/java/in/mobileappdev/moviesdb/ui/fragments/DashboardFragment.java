package in.mobileappdev.moviesdb.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

  @Bind(R.id.movie_cat_tabs)
  TabLayout mMoviewCategoryTabLayout;
  @Bind(R.id.dashboard_viewpager)
  ViewPager mCategoryViewPager;
  private ViewPagerAdapter adapter;

  private int[] tabIcons = {
      R.drawable.ic_action_favorite,
      R.drawable.ic_action_favorite,
      R.drawable.ic_action_favorite
  };

  public DashboardFragment() {
    // Required empty public constructor
  }

  public static DashboardFragment newInstance() {
    return new DashboardFragment();

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  private void initViews() {

    mMoviewCategoryTabLayout.setSmoothScrollingEnabled(true);
    adapter = new ViewPagerAdapter(getChildFragmentManager());
    mCategoryViewPager.setAdapter(adapter);
    adapter.addFragment(MovieListFragment.newInstance(1), getString(R.string.filter_popular));
    adapter.addFragment(MovieListFragment.newInstance(2), getString(R.string.filter_toprated));
    adapter.addFragment(FavoritesFragment.newInstance(), getString(R.string.filter_favorites));
    adapter.notifyDataSetChanged();
    mMoviewCategoryTabLayout.setupWithViewPager(mCategoryViewPager);
    //setupTabIcons();
  }


  /**
   * Not used
   */
  private void setupTabIcons() {
    mMoviewCategoryTabLayout.getTabAt(0).setIcon(tabIcons[0]);
    mMoviewCategoryTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    mMoviewCategoryTabLayout.getTabAt(2).setIcon(tabIcons[2]);
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
