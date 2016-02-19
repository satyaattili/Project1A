
package in.mobileappdev.moviesdb.models;

import java.util.HashMap;
import java.util.Map;


public class ProductionCountry {

    private String iso_3166_1;
    private String name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The iso_3166_1
     */
    public String getIso_3166_1() {
        return iso_3166_1;
    }

    /**
     * 
     * @param iso_3166_1
     *     The iso_3166_1
     */
    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
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
