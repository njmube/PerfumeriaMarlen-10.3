
package com.pmarlen.digifactws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GeneraCFDXResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "generaCFDXResult"
})
@XmlRootElement(name = "GeneraCFDXResponse")
public class GeneraCFDXResponse {

    @XmlElement(name = "GeneraCFDXResult")
    protected String generaCFDXResult;

    /**
     * Gets the value of the generaCFDXResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneraCFDXResult() {
        return generaCFDXResult;
    }

    /**
     * Sets the value of the generaCFDXResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneraCFDXResult(String value) {
        this.generaCFDXResult = value;
    }

}
