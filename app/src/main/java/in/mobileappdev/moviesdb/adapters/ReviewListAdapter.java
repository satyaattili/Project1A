package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.ReviewResult;


/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.TrailerViewHolder> {

  private static final String TAG = MovieGridAdapter.class.getSimpleName();
  private Context mContext;
  private List<ReviewResult> mMovieDataSet;

  public ReviewListAdapter(Context mContext, List<ReviewResult> mMovieDataSet) {
    this.mContext = mContext;
    this.mMovieDataSet = mMovieDataSet;
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.
        from(parent.getContext()).
        inflate(R.layout.review_list_item, parent, false);

    return new TrailerViewHolder(itemView);
  }



  @Override
  public void onBindViewHolder(TrailerViewHolder holder, final int position) {

    final ReviewResult review = mMovieDataSet.get(position);
    holder.mReview.setText(review.getContent());
    holder.mReviewer.setText(review.getAuthor());


  }


  @Override
  public int getItemCount() {
    return mMovieDataSet.size();
  }

  public class TrailerViewHolder  extends RecyclerView.ViewHolder{
    protected TextView mReviewer;
    protected TextView mReview;

    public TrailerViewHolder(View v) {
      super(v);
      mReviewer =  (TextView) v.findViewById(R.id.reviewer);
      mReview = (TextView)  v.findViewById(R.id.review);

    }
  }


}
