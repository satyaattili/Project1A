package in.mobileappdev.moviesdb.utils;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class Constants {
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public final static String API_KEY = "697bb28cf9449c50d8b51594edd8ff95";

    public static String POPULAR_MOVIES = BASE_URL+"movie/popular";
    public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    public static String VIDEO_THUMBNAIL = "http://img.youtube.com/vi/";

    public static String YOUTUBE_VIDEO_API_KEY = "AIzaSyD1cHQhUrl3VGAWN2CZCjmkXYlfLiGD6ts";
    public static String TITTLE = "https://www.googleapis" +
        ".com/youtube/v3/videos?part=id%2Csnippet&id=bUZfER6-dLA&key=AIzaSyD1cHQhUrl3VGAWN2CZCjmkXYlfLiGD6ts";

    public static String SORT_BY = "sort_by";

    public static double GOOD = 7.5;
    public static double AVERAGE = 5;
    public static double POOR = 2.5;

    public static String PREF_KEY_SELECTED_CATEGORY = "key_sel_category";
    public static int PREF_POPULAR_MOVIE = 1;
    public static int PREF_TOP_RATED_MOVIE = 2;
    public static int PREF_TOP_FAV_MOVIE = 3;

}
