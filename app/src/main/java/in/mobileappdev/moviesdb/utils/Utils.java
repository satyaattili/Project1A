package in.mobileappdev.moviesdb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import in.mobileappdev.moviesdb.services.CallMoviesAPI;
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

  public static String getFormattedTime(long minutes){
    long hours = TimeUnit.MINUTES.toHours(120);
    long remainMinute = minutes - TimeUnit.HOURS.toMinutes(hours);
    String result = String.format(Locale.getDefault(),"%02d", hours) + ":"
        + String.format(Locale.getDefault(),"%02d", remainMinute);

    return result;
  }

  public static boolean isEmpty(String string){
    return string != null && !string.equals("");
  }



}
