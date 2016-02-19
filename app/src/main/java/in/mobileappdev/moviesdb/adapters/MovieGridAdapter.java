package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.utils.Constants;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private static final String TAG = MovieGridAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Result> mMovieDataSet;
    private OnMovieClickListener mOnMovieClickListener;

    public MovieGridAdapter(Context mContext, ArrayList<Result> mMovieDataSet) {
        this.mContext = mContext;
        this.mMovieDataSet = mMovieDataSet;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.movie_grid_item, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        final Result currentMovie = mMovieDataSet.get(position);
        String imageurl = Constants.IMAGE_BASE_URL+currentMovie.getPosterPath();
        holder.mName.setText(currentMovie.getTitle());
        Picasso.with(mContext).load(imageurl)
            .placeholder(R.drawable.default_movie ).into
            (holder.mThumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnMovieClickListener != null){
                    mOnMovieClickListener.onMovieClick(currentMovie.getTitle(),currentMovie.getId());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mMovieDataSet.size();
    }

    public class MovieViewHolder  extends RecyclerView.ViewHolder{
        protected TextView mName;
        protected ImageView mThumbnail;

        public MovieViewHolder(View v) {
            super(v);
            mName =  (TextView) v.findViewById(R.id.movie_name);
            mThumbnail = (ImageView)  v.findViewById(R.id.movie_thumbnail);
        }
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener){
        this.mOnMovieClickListener =  onMovieClickListener;
    }

    public interface OnMovieClickListener{
        public void onMovieClick(String movieName, int movieId);
    }
}
