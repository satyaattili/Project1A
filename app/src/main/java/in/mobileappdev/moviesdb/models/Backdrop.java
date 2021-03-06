
package in.mobileappdev.moviesdb.models;

public class Backdrop {

    private String file_path = "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg";
    private Integer width;
    private Integer height;
    private Object iso6391;
    private Float aspectRatio;
    private Float voteAverage;
    private Integer voteCount;

    /**
     * 
     * @return
     *     The filePath
     */
    public String getFilePath() {
        return file_path;
    }

    /**
     * 
     * @param filePath
     *     The file_path
     */
    public void setFilePath(String filePath) {
        this.file_path = filePath;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The iso6391
     */
    public Object getIso6391() {
        return iso6391;
    }

    /**
     * 
     * @param iso6391
     *     The iso_639_1
     */
    public void setIso6391(Object iso6391) {
        this.iso6391 = iso6391;
    }

    /**
     * 
     * @return
     *     The aspectRatio
     */
    public Float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * 
     * @param aspectRatio
     *     The aspect_ratio
     */
    public void setAspectRatio(Float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * 
     * @return
     *     The voteAverage
     */
    public Float getVoteAverage() {
        return voteAverage;
    }

    /**
     * 
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * 
     * @return
     *     The voteCount
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * 
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

}
