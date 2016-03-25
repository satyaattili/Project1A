package in.mobileappdev.moviesdb;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 * https://tausiq.wordpress.com/2013/01/27/android-make-use-of-android-application-class-as-singleton-object/
 */
public class MovieDBApplication extends Application{

  private static SharedPreferences mPref;
  private static MovieDBApplication sInstance;

  public static MovieDBApplication getInstance() {
    return sInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sInstance = this;
    sInstance.initializeInstance();
  }

  public void savePreference(String key, String value){
    mPref.edit().putString(key, value).apply();
  }

  public void savePreference(String key, int value){
    mPref.edit().putInt(key, value).apply();
  }

  public String getPreference(String key){
    return mPref.getString(key, null);
  }

  public int getIntPreference(String key){
    return mPref.getInt(key, 0);
  }

  private void initializeInstance() {
    // set application wise preference
    mPref = this.getApplicationContext().getSharedPreferences("pref_key", MODE_PRIVATE);
  }


  @Override
  public void onTerminate() {
    // Do your application wise Termination task
    super.onTerminate();
  }
}
