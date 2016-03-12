package in.mobileappdev.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.models.Cast;
import in.mobileappdev.moviesdb.models.Crew;
import in.mobileappdev.moviesdb.utils.Constants;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class MovieCrewAdapter extends RecyclerView.Adapter<MovieCrewAdapter.MovieViewHolder> {

    private static final String TAG = MovieCrewAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Crew> mCrewDataSet;
    private OnMovieClickListener mOnMovieClickListener;

    public MovieCrewAdapter(Context mContext, ArrayList<Crew> mCastDataSet) {
        this.mContext = mContext;
        this.mCrewDataSet = mCastDataSet;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cast_grid_item, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        final Crew cast = mCrewDataSet.get(position);
        holder.castName.setText(cast.getName());
        holder.castJob.setText(cast.getJob());
        holder.castDepartment.setVisibility(View.VISIBLE);
        holder.castDepartment.setText("("+cast.getDepartment()+")");
        String imageurl = Constants.IMAGE_BASE_URL+cast.getProfilePath();
        //holder.mName.setText(currentMovie.getTitle());
        Picasso.with(mContext).load(imageurl)
            .placeholder(R.drawable.default_movie ).into
            (holder.mProfilePic);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnMovieClickListener != null){
                    mOnMovieClickListener.onMovieClick(currentMovie.getTitle(),currentMovie.getId());
                }
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return mCrewDataSet.size();
    }

    public class MovieViewHolder  extends RecyclerView.ViewHolder{
        //protected TextView mName;
        @Bind(R.id.cProfilePic) ImageView mProfilePic;
        @Bind(R.id.cName) TextView castName;
        @Bind(R.id.cJob) TextView castJob;
        @Bind(R.id.cDepartment) TextView castDepartment;

        public MovieViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener){
        this.mOnMovieClickListener =  onMovieClickListener;
    }

    public interface OnMovieClickListener{
        public void onMovieClick(String movieName, int movieId);
    }
}
