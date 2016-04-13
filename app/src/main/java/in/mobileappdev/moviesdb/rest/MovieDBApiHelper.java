package in.mobileappdev.moviesdb.rest;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import in.mobileappdev.moviesdb.utils.Utils;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by satyanarayana.avv on 09-03-2016.
 */
public class MovieDBApiHelper {

  private static CallMoviesAPI apiService;
  private static Context mContext;

  private static Retrofit getRetroFit() {

    File httpCacheDirectory = new File(mContext.getCacheDir(), "mymoviedbcache");
    Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor())
        .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).cache(cache).build();

    return new Retrofit.Builder()
        .client(httpClient)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static CallMoviesAPI getApiService(Context ctx) {
    mContext = ctx;
    if (apiService == null) {
      apiService = getRetroFit().create(CallMoviesAPI.class);
    }
    return apiService;
  }



  private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Response originalResponse = chain.proceed(chain.request());
      if (Utils.hasNetworkConnection(mContext)) {
        int maxAge = 60; // read from cache for 1 minute
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, max-age=" + maxAge)
            .build();
      } else {
        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
            .build();
      }
    }
  };

}
