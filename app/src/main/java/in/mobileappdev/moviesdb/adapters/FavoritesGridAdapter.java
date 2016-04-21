package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.db.DatabaseHandler;
import in.mobileappdev.moviesdb.models.Result;
import in.mobileappdev.moviesdb.utils.Constants;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class FavoritesGridAdapter extends CursorRecyclerViewAdapter<FavoritesGridAdapter.MovieViewHolder> {

    private static final String TAG = FavoritesGridAdapter.class.getSimpleName();
    private Context mContext;
    private OnMovieClickListener mOnMovieClickListener;
    private Cursor mCursor;

    public FavoritesGridAdapter(Context mContext, Cursor cursor) {
        super(mContext,cursor);
        this.mContext = mContext;
        this.mCursor = cursor;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.movie_grid_item, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, Cursor cursor) {
        String thumnail = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_MOVIE_POSTER));
        final String title = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_MOVIE_NAME));
        final int id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_MOVIE_ID));
        final String imageurl = Constants.IMAGE_BASE_URL+thumnail;
        viewHolder.mName.setText(title);
        Picasso.with(mContext).load(imageurl)
            .placeholder(R.drawable.thumbnail_placeholder ).into
            (viewHolder.mThumbnail);
        viewHolder.mFavorite.setVisibility(View.VISIBLE);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnMovieClickListener != null){
                    mOnMovieClickListener.onMovieClick(title,id, imageurl);
                }
            }
        });
    }

    public class MovieViewHolder  extends RecyclerView.ViewHolder{
        protected TextView mName;
        protected ImageView mThumbnail;
        protected ImageView mFavorite;

        public MovieViewHolder(View v) {
            super(v);
            mName =  (TextView) v.findViewById(R.id.movie_name);
            mThumbnail = (ImageView)  v.findViewById(R.id.movie_thumbnail);
            mFavorite = (ImageView) v.findViewById(R.id.fav_icon);
        }
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener){
        this.mOnMovieClickListener =  onMovieClickListener;
    }

    public interface OnMovieClickListener{
        public void onMovieClick(String movieName, int movieId, String poster);
    }
}
