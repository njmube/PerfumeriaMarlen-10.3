
package com.pmarlen.suempresa.realmmodel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PagesInRange {

    private Integer _1;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _1
     */
    public Integer get1() {
        return _1;
    }

    /**
     * 
     * @param _1
     *     The 1
     */
    public void set1(Integer _1) {
        this._1 = _1;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
