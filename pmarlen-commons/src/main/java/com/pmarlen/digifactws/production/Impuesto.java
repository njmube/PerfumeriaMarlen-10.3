
package com.pmarlen.digifactws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Impuesto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Impuesto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoImpuesto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tasa" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Importe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Impuesto", propOrder = {
    "tipoImpuesto",
    "tasa",
    "importe",
    "tipo"
})
public class Impuesto {

    @XmlElement(name = "TipoImpuesto")
    protected String tipoImpuesto;
    @XmlElement(name = "Tasa")
    protected double tasa;
    @XmlElement(name = "Importe")
    protected double importe;
    @XmlElement(name = "Tipo")
    protected String tipo;

    /**
     * Gets the value of the tipoImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    /**
     * Sets the value of the tipoImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoImpuesto(String value) {
        this.tipoImpuesto = value;
    }

    /**
     * Gets the value of the tasa property.
     * 
     */
    public double getTasa() {
        return tasa;
    }

    /**
     * Sets the value of the tasa property.
     * 
     */
    public void setTasa(double value) {
        this.tasa = value;
    }

    /**
     * Gets the value of the importe property.
     * 
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     * 
     */
    public void setImporte(double value) {
        this.importe = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

}
