
package com.pmarlen.suempresa.realmmodel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Pager {

    private Integer pageCount;
    private Integer itemCountPerPage;
    private Integer first;
    private Integer current;
    private Integer last;
    private PagesInRange pagesInRange;
    private Integer firstPageInRange;
    private Integer lastPageInRange;
    private Integer currentItemCount;
    private Integer totalItemCount;
    private Integer firstItemNumber;
    private Integer lastItemNumber;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The pageCount
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 
     * @param pageCount
     *     The pageCount
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 
     * @return
     *     The itemCountPerPage
     */
    public Integer getItemCountPerPage() {
        return itemCountPerPage;
    }

    /**
     * 
     * @param itemCountPerPage
     *     The itemCountPerPage
     */
    public void setItemCountPerPage(Integer itemCountPerPage) {
        this.itemCountPerPage = itemCountPerPage;
    }

    /**
     * 
     * @return
     *     The first
     */
    public Integer getFirst() {
        return first;
    }

    /**
     * 
     * @param first
     *     The first
     */
    public void setFirst(Integer first) {
        this.first = first;
    }

    /**
     * 
     * @return
     *     The current
     */
    public Integer getCurrent() {
        return current;
    }

    /**
     * 
     * @param current
     *     The current
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * 
     * @return
     *     The last
     */
    public Integer getLast() {
        return last;
    }

    /**
     * 
     * @param last
     *     The last
     */
    public void setLast(Integer last) {
        this.last = last;
    }

    /**
     * 
     * @return
     *     The pagesInRange
     */
    public PagesInRange getPagesInRange() {
        return pagesInRange;
    }

    /**
     * 
     * @param pagesInRange
     *     The pagesInRange
     */
    public void setPagesInRange(PagesInRange pagesInRange) {
        this.pagesInRange = pagesInRange;
    }

    /**
     * 
     * @return
     *     The firstPageInRange
     */
    public Integer getFirstPageInRange() {
        return firstPageInRange;
    }

    /**
     * 
     * @param firstPageInRange
     *     The firstPageInRange
     */
    public void setFirstPageInRange(Integer firstPageInRange) {
        this.firstPageInRange = firstPageInRange;
    }

    /**
     * 
     * @return
     *     The lastPageInRange
     */
    public Integer getLastPageInRange() {
        return lastPageInRange;
    }

    /**
     * 
     * @param lastPageInRange
     *     The lastPageInRange
     */
    public void setLastPageInRange(Integer lastPageInRange) {
        this.lastPageInRange = lastPageInRange;
    }

    /**
     * 
     * @return
     *     The currentItemCount
     */
    public Integer getCurrentItemCount() {
        return currentItemCount;
    }

    /**
     * 
     * @param currentItemCount
     *     The currentItemCount
     */
    public void setCurrentItemCount(Integer currentItemCount) {
        this.currentItemCount = currentItemCount;
    }

    /**
     * 
     * @return
     *     The totalItemCount
     */
    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * 
     * @param totalItemCount
     *     The totalItemCount
     */
    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    /**
     * 
     * @return
     *     The firstItemNumber
     */
    public Integer getFirstItemNumber() {
        return firstItemNumber;
    }

    /**
     * 
     * @param firstItemNumber
     *     The firstItemNumber
     */
    public void setFirstItemNumber(Integer firstItemNumber) {
        this.firstItemNumber = firstItemNumber;
    }

    /**
     * 
     * @return
     *     The lastItemNumber
     */
    public Integer getLastItemNumber() {
        return lastItemNumber;
    }

    /**
     * 
     * @param lastItemNumber
     *     The lastItemNumber
     */
    public void setLastItemNumber(Integer lastItemNumber) {
        this.lastItemNumber = lastItemNumber;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
