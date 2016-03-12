package in.mobileappdev.moviesdb.rest;

import in.mobileappdev.moviesdb.models.MovieResponse;
import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import in.mobileappdev.moviesdb.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by satyanarayana.avv on 09-03-2016.
 */
public class MovieDBApiHelper {

  private static CallMoviesAPI apiService;

  private static Retrofit getRetroFit() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
    return new Retrofit.Builder()
        .client(httpClient)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static CallMoviesAPI getApiService() {
    if (apiService == null) {
      apiService = getRetroFit().create(CallMoviesAPI.class);
    }
    return apiService;
  }




}
