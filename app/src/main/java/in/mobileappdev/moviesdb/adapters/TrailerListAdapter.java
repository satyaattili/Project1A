package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.repacked.apache.commons.io.IOUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.models.TrailerResult;
import in.mobileappdev.moviesdb.utils.Constants;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerViewHolder> {

  private static final String TAG = MovieGridAdapter.class.getSimpleName();
  private Context mContext;
  private List<TrailerResult> mMovieDataSet;
  private OnMovieClickListener mOnMovieClickListener;
  private OnTrailerSelectedListener mOnTrailerClickedListener;

  public TrailerListAdapter(Context mContext, List<TrailerResult> mMovieDataSet) {
    this.mContext = mContext;
    this.mMovieDataSet = mMovieDataSet;
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.
        from(parent.getContext()).
        inflate(R.layout.trailer_list_item, parent, false);

    return new TrailerViewHolder(itemView);
  }



  @Override
  public void onBindViewHolder(final TrailerViewHolder holder, int position) {

    final TrailerResult currentTrailer= mMovieDataSet.get(holder.getAdapterPosition());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    if(mOnTrailerClickedListener != null){
      mOnTrailerClickedListener.onTrailerClicked(holder.getAdapterPosition(), currentTrailer.getKey());
    }
      }
    });
    String url = Constants.VIDEO_THUMBNAIL+currentTrailer.getKey()+"/hqdefault.jpg";
    Picasso.with(mContext).load(url)
        .placeholder(R.drawable.default_video_thubnail ).into
        (holder.mThumbnail);

  }




  public void setOnTrailerClickedListener(OnTrailerSelectedListener onTrailerClickedListener){
    this.mOnTrailerClickedListener = onTrailerClickedListener;
  }

  @Override
  public int getItemCount() {
    return mMovieDataSet.size();
  }

  public class TrailerViewHolder  extends RecyclerView.ViewHolder{
    protected TextView mName;
    protected ImageView mThumbnail;

    public TrailerViewHolder(View v) {
      super(v);
      mName =  (TextView) v.findViewById(R.id.video_name);
      mThumbnail = (ImageView)  v.findViewById(R.id.thumbnail);

    }
  }

  public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener){
    this.mOnMovieClickListener =  onMovieClickListener;
  }

  public interface OnMovieClickListener{
    public void onMovieClick(String movieName, int movieId);
  }



  public interface OnTrailerSelectedListener{
        public void onTrailerClicked(int id, String vidioId);
  }
}
