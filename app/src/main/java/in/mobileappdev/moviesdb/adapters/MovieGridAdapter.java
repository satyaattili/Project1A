package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Movie;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovieDataSet;

    public MovieGridAdapter(Context mContext, ArrayList<Movie> mMovieDataSet) {
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
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie currentMovie = mMovieDataSet.get(position);
        holder.mName.setText(currentMovie.getMovieName());
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
}
