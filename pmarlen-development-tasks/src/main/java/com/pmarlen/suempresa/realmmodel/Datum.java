
package com.pmarlen.suempresa.realmmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Datum {

    private String id;
    private String name;
    private String domainName;
    private String domainId;
    private Object userId;
    private Object userContactName;
    private List<Object> aliases = new ArrayList<Object>();
    private List<Object> redirects = new ArrayList<Object>();
    private Boolean autoresponderEnabled;
    private String webmailUrl;
    private Boolean isMailBox;
    private SpaceUsage spaceUsage;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
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
     *     The domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * 
     * @param domainName
     *     The domainName
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * 
     * @return
     *     The domainId
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * 
     * @param domainId
     *     The domainId
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public Object getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The userContactName
     */
    public Object getUserContactName() {
        return userContactName;
    }

    /**
     * 
     * @param userContactName
     *     The userContactName
     */
    public void setUserContactName(Object userContactName) {
        this.userContactName = userContactName;
    }

    /**
     * 
     * @return
     *     The aliases
     */
    public List<Object> getAliases() {
        return aliases;
    }

    /**
     * 
     * @param aliases
     *     The aliases
     */
    public void setAliases(List<Object> aliases) {
        this.aliases = aliases;
    }

    /**
     * 
     * @return
     *     The redirects
     */
    public List<Object> getRedirects() {
        return redirects;
    }

    /**
     * 
     * @param redirects
     *     The redirects
     */
    public void setRedirects(List<Object> redirects) {
        this.redirects = redirects;
    }

    /**
     * 
     * @return
     *     The autoresponderEnabled
     */
    public Boolean getAutoresponderEnabled() {
        return autoresponderEnabled;
    }

    /**
     * 
     * @param autoresponderEnabled
     *     The autoresponderEnabled
     */
    public void setAutoresponderEnabled(Boolean autoresponderEnabled) {
        this.autoresponderEnabled = autoresponderEnabled;
    }

    /**
     * 
     * @return
     *     The webmailUrl
     */
    public String getWebmailUrl() {
        return webmailUrl;
    }

    /**
     * 
     * @param webmailUrl
     *     The webmailUrl
     */
    public void setWebmailUrl(String webmailUrl) {
        this.webmailUrl = webmailUrl;
    }

    /**
     * 
     * @return
     *     The isMailBox
     */
    public Boolean getIsMailBox() {
        return isMailBox;
    }

    /**
     * 
     * @param isMailBox
     *     The isMailBox
     */
    public void setIsMailBox(Boolean isMailBox) {
        this.isMailBox = isMailBox;
    }

    /**
     * 
     * @return
     *     The spaceUsage
     */
    public SpaceUsage getSpaceUsage() {
        return spaceUsage;
    }

    /**
     * 
     * @param spaceUsage
     *     The spaceUsage
     */
    public void setSpaceUsage(SpaceUsage spaceUsage) {
        this.spaceUsage = spaceUsage;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
