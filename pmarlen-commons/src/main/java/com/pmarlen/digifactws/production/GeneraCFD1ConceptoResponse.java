
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
 *         &lt;element name="GeneraCFD_1_ConceptoResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "generaCFD1ConceptoResult"
})
@XmlRootElement(name = "GeneraCFD_1_ConceptoResponse")
public class GeneraCFD1ConceptoResponse {

    @XmlElement(name = "GeneraCFD_1_ConceptoResult")
    protected String generaCFD1ConceptoResult;

    /**
     * Gets the value of the generaCFD1ConceptoResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneraCFD1ConceptoResult() {
        return generaCFD1ConceptoResult;
    }

    /**
     * Sets the value of the generaCFD1ConceptoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneraCFD1ConceptoResult(String value) {
        this.generaCFD1ConceptoResult = value;
    }

}
