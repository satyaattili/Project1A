package in.mobileappdev.moviesdb.models;

/**
 * Created by satyanarayana.avv on 08-02-2016.
 */
public class Movie {

    private long id;
    private String movieName;
    private String movieThumbnailUrl;

    public Movie(long id, String movieName, String movieThumbnailUrl) {
        this.id = id;
        this.movieName = movieName;
        this.movieThumbnailUrl = movieThumbnailUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieThumbnailUrl() {
        return movieThumbnailUrl;
    }

    public void setMovieThumbnailUrl(String movieThumbnailUrl) {
        this.movieThumbnailUrl = movieThumbnailUrl;
    }
}
