
package in.mobileappdev.moviesdb.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovieDetailsResponse {

    private Boolean adult;
    private String backdrop_path;
    private Object belongs_to_collection;
    private Integer budget;
    private List<Genre> genres = new ArrayList<Genre>();
    private String homepage;
    private Integer id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies = new ArrayList<ProductionCompany>();
    private List<ProductionCountry> production_countries = new ArrayList<ProductionCountry>();
    private String release_date;
    private Integer revenue;
    private Integer runtime;
    private List<SpokenLanguage> spoken_languages = new ArrayList<SpokenLanguage>();
    private String status;
    private String tagline;
    private String title;
    private Boolean video;
    private Double vote_average;
    private Integer vote_count;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * 
     * @param adult
     *     The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     * 
     * @return
     *     The backdrop_path
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }

    /**
     * 
     * @param backdrop_path
     *     The backdrop_path
     */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     * 
     * @return
     *     The belongs_to_collection
     */
    public Object getBelongs_to_collection() {
        return belongs_to_collection;
    }

    /**
     * 
     * @param belongs_to_collection
     *     The belongs_to_collection
     */
    public void setBelongs_to_collection(Object belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    /**
     * 
     * @return
     *     The budget
     */
    public Integer getBudget() {
        return budget;
    }

    /**
     * 
     * @param budget
     *     The budget
     */
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    /**
     * 
     * @return
     *     The genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * 
     * @param genres
     *     The genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * 
     * @return
     *     The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * 
     * @param homepage
     *     The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The imdb_id
     */
    public String getImdb_id() {
        return imdb_id;
    }

    /**
     * 
     * @param imdb_id
     *     The imdb_id
     */
    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    /**
     * 
     * @return
     *     The original_language
     */
    public String getOriginal_language() {
        return original_language;
    }

    /**
     * 
     * @param original_language
     *     The original_language
     */
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    /**
     * 
     * @return
     *     The original_title
     */
    public String getOriginal_title() {
        return original_title;
    }

    /**
     * 
     * @param original_title
     *     The original_title
     */
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    /**
     * 
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * 
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     * 
     * @return
     *     The poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * 
     * @param poster_path
     *     The poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * 
     * @return
     *     The production_companies
     */
    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    /**
     * 
     * @param production_companies
     *     The production_companies
     */
    public void setProduction_companies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    /**
     * 
     * @return
     *     The production_countries
     */
    public List<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    /**
     * 
     * @param production_countries
     *     The production_countries
     */
    public void setProduction_countries(List<ProductionCountry> production_countries) {
        this.production_countries = production_countries;
    }

    /**
     * 
     * @return
     *     The release_date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * 
     * @param release_date
     *     The release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * 
     * @return
     *     The revenue
     */
    public Integer getRevenue() {
        return revenue;
    }

    /**
     * 
     * @param revenue
     *     The revenue
     */
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    /**
     * 
     * @return
     *     The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * 
     * @param runtime
     *     The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     * 
     * @return
     *     The spoken_languages
     */
    public List<SpokenLanguage> getSpoken_languages() {
        return spoken_languages;
    }

    /**
     * 
     * @param spoken_languages
     *     The spoken_languages
     */
    public void setSpoken_languages(List<SpokenLanguage> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * 
     * @param tagline
     *     The tagline
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     * 
     * @return
     *     The vote_average
     */
    public Double getVote_average() {
        return vote_average;
    }

    /**
     * 
     * @param vote_average
     *     The vote_average
     */
    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    /**
     * 
     * @return
     *     The vote_count
     */
    public Integer getVote_count() {
        return vote_count;
    }

    /**
     * 
     * @param vote_count
     *     The vote_count
     */
    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
