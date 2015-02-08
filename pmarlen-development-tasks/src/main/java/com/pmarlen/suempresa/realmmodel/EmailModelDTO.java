
package com.pmarlen.suempresa.realmmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmailModelDTO {

    private Pager pager;
    private State state;
    private List<Datum> data = new ArrayList<Datum>();
    private Locale locale;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The pager
     */
    public Pager getPager() {
        return pager;
    }

    /**
     * 
     * @param pager
     *     The pager
     */
    public void setPager(Pager pager) {
        this.pager = pager;
    }

    /**
     * 
     * @return
     *     The state
     */
    public State getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 
     * @param locale
     *     The locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
