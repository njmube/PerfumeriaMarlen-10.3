
package com.pmarlen.suempresa.realmmodel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class State {

    private String sortField;
    private String sortDirection;
    private Integer currentPage;
    private Integer itemsPerPage;
    private SearchFilters searchFilters;
    private Boolean forceShowSearch;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The sortField
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * 
     * @param sortField
     *     The sortField
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * 
     * @return
     *     The sortDirection
     */
    public String getSortDirection() {
        return sortDirection;
    }

    /**
     * 
     * @param sortDirection
     *     The sortDirection
     */
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    /**
     * 
     * @return
     *     The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The currentPage
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 
     * @return
     *     The itemsPerPage
     */
    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * 
     * @param itemsPerPage
     *     The itemsPerPage
     */
    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * 
     * @return
     *     The searchFilters
     */
    public SearchFilters getSearchFilters() {
        return searchFilters;
    }

    /**
     * 
     * @param searchFilters
     *     The searchFilters
     */
    public void setSearchFilters(SearchFilters searchFilters) {
        this.searchFilters = searchFilters;
    }

    /**
     * 
     * @return
     *     The forceShowSearch
     */
    public Boolean getForceShowSearch() {
        return forceShowSearch;
    }

    /**
     * 
     * @param forceShowSearch
     *     The forceShowSearch
     */
    public void setForceShowSearch(Boolean forceShowSearch) {
        this.forceShowSearch = forceShowSearch;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
