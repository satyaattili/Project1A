package in.mobileappdev.moviesdb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
