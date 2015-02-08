
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
 *         &lt;element name="CambiaStatusFactResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "cambiaStatusFactResult"
})
@XmlRootElement(name = "CambiaStatusFactResponse")
public class CambiaStatusFactResponse {

    @XmlElement(name = "CambiaStatusFactResult")
    protected String cambiaStatusFactResult;

    /**
     * Gets the value of the cambiaStatusFactResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCambiaStatusFactResult() {
        return cambiaStatusFactResult;
    }

    /**
     * Sets the value of the cambiaStatusFactResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCambiaStatusFactResult(String value) {
        this.cambiaStatusFactResult = value;
    }

}
