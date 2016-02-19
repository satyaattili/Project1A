
package in.mobileappdev.moviesdb.models;

import java.util.HashMap;
import java.util.Map;

public class SpokenLanguage {

    private String iso_639_1;
    private String name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The iso_639_1
     */
    public String getIso_639_1() {
        return iso_639_1;
    }

    /**
     * 
     * @param iso_639_1
     *     The iso_639_1
     */
    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
