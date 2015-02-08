
package com.pmarlen.suempresa.realmmodel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Name {

    private String searchText;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The searchText
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * 
     * @param searchText
     *     The searchText
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
