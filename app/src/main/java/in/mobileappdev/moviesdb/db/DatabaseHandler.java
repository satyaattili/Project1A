package in.mobileappdev.moviesdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {


  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "in.mobileappdev.md";
  private static final String TABLE_FAVORITES = "favorites";
  private static final String TAG = DatabaseHandler.class.getSimpleName();
  private static DatabaseHandler mInstance;

  // Contacts Table Columns names
  private static final String KEY_ID = "id";
  private static final String KEY_MOVIE_ID = "name";

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
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_ID + " LONG UNIQUE" + ")";
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
  public void addToFavorites(long movieId) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_MOVIE_ID, movieId);
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


}