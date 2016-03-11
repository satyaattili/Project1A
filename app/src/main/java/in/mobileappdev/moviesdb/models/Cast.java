
package in.mobileappdev.moviesdb.models;

public class Cast {

    private Integer cast_id;
    private String character;
    private String credit_id;
    private Integer id;
    private String name;
    private Integer order;
    private String profile_path;

    /**
     * 
     * @return
     *     The cast_id
     */
    public Integer getCastId() {
        return cast_id;
    }

    /**
     * 
     * @param cast_id
     *     The cast_id
     */
    public void setCastId(Integer cast_id) {
        this.cast_id = cast_id;
    }

    /**
     * 
     * @return
     *     The character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * 
     * @param character
     *     The character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * 
     * @return
     *     The credit_id
     */
    public String getCreditId() {
        return credit_id;
    }

    /**
     * 
     * @param credit_id
     *     The credit_id
     */
    public void setCreditId(String credit_id) {
        this.credit_id = credit_id;
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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The profile_path
     */
    public String getProfilePath() {
        return profile_path;
    }

    /**
     * 
     * @param profile_path
     *     The profile_path
     */
    public void setProfilePath(String profile_path) {
        this.profile_path = profile_path;
    }

}
