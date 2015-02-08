
package com.pmarlen.suempresa.realmmodel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SpaceUsage {

    private String legend;
    private Integer percent;
    private String usage;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The legend
     */
    public String getLegend() {
        return legend;
    }

    /**
     * 
     * @param legend
     *     The legend
     */
    public void setLegend(String legend) {
        this.legend = legend;
    }

    /**
     * 
     * @return
     *     The percent
     */
    public Integer getPercent() {
        return percent;
    }

    /**
     * 
     * @param percent
     *     The percent
     */
    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    /**
     * 
     * @return
     *     The usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * 
     * @param usage
     *     The usage
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
