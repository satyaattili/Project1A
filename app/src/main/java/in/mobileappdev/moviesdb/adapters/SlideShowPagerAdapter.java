package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.models.MovieImages;
import in.mobileappdev.moviesdb.utils.Constants;

/**
 * Created by satyanarayana.avv on 12-03-2016.
 */
public class SlideShowPagerAdapter extends PagerAdapter {

  Context mContext;
  LayoutInflater mLayoutInflater;
  MovieImages movieImages;

  public SlideShowPagerAdapter(Context context, MovieImages movieImages) {
    this.mContext = context;
    this.movieImages = movieImages;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    int size = movieImages.getBackdrops().size();
    return  size >5 ? 5 : size;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == ((LinearLayout) object);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View itemView = mLayoutInflater.inflate(R.layout.slideshow_pager_item, container, false);
   // String imageUrl = Constants.IMAGE_BASE_URL+"/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg";
    //if(movieImages.getBackdrops().get(position).getFilePath() != null){
      String imageUrl = Constants.IMAGE_BASE_URL+movieImages.getBackdrops().get(position).getFilePath();
    //}
    ImageView imageView = (ImageView) itemView.findViewById(R.id.slide_image);
    Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.ic_launcher)
        .into
            (imageView);

    container.addView(itemView);

    return itemView;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((LinearLayout) object);
  }
}