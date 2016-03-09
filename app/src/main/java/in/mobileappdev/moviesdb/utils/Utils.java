package in.mobileappdev.moviesdb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.mobileappdev.moviesdb.services.CallMoviesAPI;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by satyanarayana.avv on 03-03-2016.
 */
public class Utils {

  public static boolean hasNetworkConnection(Context context) {
    boolean hasConnectedWifi = false;
    boolean hasConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    for (NetworkInfo ni : netInfo) {
      if (ni.getTypeName().equalsIgnoreCase("WIFI"))
        if (ni.isConnected())
          hasConnectedWifi = true;
      if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
        if (ni.isConnected())
          hasConnectedMobile = true;
    }
    return hasConnectedWifi || hasConnectedMobile;
  }



}
