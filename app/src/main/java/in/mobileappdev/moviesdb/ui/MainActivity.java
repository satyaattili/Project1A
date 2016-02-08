package in.mobileappdev.moviesdb.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import in.mobileappdev.moviesdb.R;
import in.mobileappdev.moviesdb.adapters.MovieGridAdapter;
import in.mobileappdev.moviesdb.models.Movie;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView movieList = (RecyclerView) findViewById(R.id.movie_list);
        movieList.setHasFixedSize(true);
        movieList.setLayoutManager(new GridLayoutManager(this, 2));
        ArrayList<Movie> movies = new ArrayList<>();
        for(int i=1;i<=20;i++){
            Movie m = new Movie(i,"Movie - "+i, null);
            movies.add(m);
        }

        MovieGridAdapter adapter = new MovieGridAdapter(this, movies);
        movieList.setAdapter(adapter);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CallMoviesAPI  service = retrofit.create(CallMoviesAPI.class);
        service.getMovies(Constants.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.e(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
