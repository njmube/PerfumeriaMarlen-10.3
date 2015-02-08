
package com.pmarlen.digifactws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjetoX complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjetoX">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arregloString" type="{https://cfd.sicofi.com.mx}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="arregloDouble" type="{https://cfd.sicofi.com.mx}ArrayOfDouble" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjetoX", propOrder = {
    "arregloString",
    "arregloDouble"
})
public class ObjetoX {

    protected ArrayOfString arregloString;
    protected ArrayOfDouble arregloDouble;

    /**
     * Gets the value of the arregloString property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getArregloString() {
        return arregloString;
    }

    /**
     * Sets the value of the arregloString property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setArregloString(ArrayOfString value) {
        this.arregloString = value;
    }

    /**
     * Gets the value of the arregloDouble property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getArregloDouble() {
        return arregloDouble;
    }

    /**
     * Sets the value of the arregloDouble property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setArregloDouble(ArrayOfDouble value) {
        this.arregloDouble = value;
    }

}
