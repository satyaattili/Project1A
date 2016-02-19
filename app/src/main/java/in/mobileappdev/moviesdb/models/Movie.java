package in.mobileappdev.moviesdb.models;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class Movie {

    private long id;
    private boolean adult;
    private String backdrop_path;
    String original_language;
    String original_title;
    private String title;
    private String poster_path;

    public Movie(long id, String title, String poster_path) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieName() {
        return title;
    }

    public void setMovieName(String movieName) {
        this.title = movieName;
    }

    public String getMovieThumbnailUrl() {
        return poster_path;
    }

    public void setMovieThumbnailUrl(String movieThumbnailUrl) {
        this.poster_path = movieThumbnailUrl;
    }
}
