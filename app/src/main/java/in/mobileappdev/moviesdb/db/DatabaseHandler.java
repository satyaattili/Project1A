package in.mobileappdev.moviesdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {


  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "in.mobileappdev.md";
  private static final String TABLE_FAVORITES = "favorites";
  private static final String TAG = DatabaseHandler.class.getSimpleName();
  private static DatabaseHandler mInstance;

  // Contacts Table Columns names
  public static final String KEY_ID = "id";
  public static final String KEY_MOVIE_ID = "movie_id";
  public static final String KEY_MOVIE_NAME = "movie_title";
  public static final String KEY_MOVIE_POSTER = "movie_poster";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


  /**
   * Retrieves a thread-safe instance of the singleton object {@link DatabaseHandler} and opens the database
   * with writing permissions.
   *
   * @param context the context to set.
   * @return the singleton instance.
   */
  public static synchronized DatabaseHandler getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new DatabaseHandler(context);
    }
    return mInstance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_ID + " LONG UNIQUE,"  + KEY_MOVIE_NAME +
        " TEXT,"  + KEY_MOVIE_POSTER + " TEXT "+ ")";
   Log.d(TAG, "Create table : "+CREATE_CONTACTS_TABLE);
    db.execSQL(CREATE_CONTACTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
    onCreate(db);
  }

  /**
   * All CRUD(Create, Read, Update, Delete) Operations
   */

  // Adding new contact
  public void addToFavorites(long movieId, String title, String poster) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_MOVIE_ID, movieId);
    values.put(KEY_MOVIE_NAME, title);
    values.put(KEY_MOVIE_POSTER, poster);
    db.insert(TABLE_FAVORITES, null, values);
    db.close();
  }

  // Deleting movie
  public void deleteContact(long movieId) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_FAVORITES, KEY_MOVIE_ID + " = ?",
        new String[] { String.valueOf(movieId) });
    db.close();
  }
  String[] args = { "first string", "second@string.com" };

  public boolean isExistsInFavorites(long id) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_FAVORITES, new String[] { KEY_ID,
            KEY_MOVIE_ID }, KEY_MOVIE_ID + "=?",
        new String[] { String.valueOf(id) }, null, null, null, null);
    if(cursor != null && cursor.getCount()>0){
      Log.e(TAG, "Key Exists");
      return true;
    }
    return false;
  }

  public Cursor getAllFavoriteMovies() {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor =
        db.query(TABLE_FAVORITES, new String[]{KEY_MOVIE_ID, KEY_MOVIE_NAME, KEY_MOVIE_POSTER},
            null,
            null, null, null, null, null);
    Log.d(TAG, "Cursor count : " + cursor.getCount());
    return cursor;
  }


}