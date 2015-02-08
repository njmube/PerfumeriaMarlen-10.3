
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
 *         &lt;element name="Usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Contrasena" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatosCFD" type="{https://cfd.sicofi.com.mx}DatosCFD" minOccurs="0"/>
 *         &lt;element name="Receptor" type="{https://cfd.sicofi.com.mx}Receptor" minOccurs="0"/>
 *         &lt;element name="Concepto" type="{https://cfd.sicofi.com.mx}Concepto" minOccurs="0"/>
 *         &lt;element name="Impuesto" type="{https://cfd.sicofi.com.mx}Impuesto" minOccurs="0"/>
 *         &lt;element name="XMLAddenda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "usuario",
    "contrasena",
    "datosCFD",
    "receptor",
    "concepto",
    "impuesto",
    "xmlAddenda"
})
@XmlRootElement(name = "GeneraCFD_1_Concepto")
public class GeneraCFD1Concepto {

    @XmlElement(name = "Usuario")
    protected String usuario;
    @XmlElement(name = "Contrasena")
    protected String contrasena;
    @XmlElement(name = "DatosCFD")
    protected DatosCFD datosCFD;
    @XmlElement(name = "Receptor")
    protected Receptor receptor;
    @XmlElement(name = "Concepto")
    protected Concepto concepto;
    @XmlElement(name = "Impuesto")
    protected Impuesto impuesto;
    @XmlElement(name = "XMLAddenda")
    protected String xmlAddenda;

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the contrasena property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Sets the value of the contrasena property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrasena(String value) {
        this.contrasena = value;
    }

    /**
     * Gets the value of the datosCFD property.
     * 
     * @return
     *     possible object is
     *     {@link DatosCFD }
     *     
     */
    public DatosCFD getDatosCFD() {
        return datosCFD;
    }

    /**
     * Sets the value of the datosCFD property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosCFD }
     *     
     */
    public void setDatosCFD(DatosCFD value) {
        this.datosCFD = value;
    }

    /**
     * Gets the value of the receptor property.
     * 
     * @return
     *     possible object is
     *     {@link Receptor }
     *     
     */
    public Receptor getReceptor() {
        return receptor;
    }

    /**
     * Sets the value of the receptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Receptor }
     *     
     */
    public void setReceptor(Receptor value) {
        this.receptor = value;
    }

    /**
     * Gets the value of the concepto property.
     * 
     * @return
     *     possible object is
     *     {@link Concepto }
     *     
     */
    public Concepto getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Concepto }
     *     
     */
    public void setConcepto(Concepto value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the impuesto property.
     * 
     * @return
     *     possible object is
     *     {@link Impuesto }
     *     
     */
    public Impuesto getImpuesto() {
        return impuesto;
    }

    /**
     * Sets the value of the impuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Impuesto }
     *     
     */
    public void setImpuesto(Impuesto value) {
        this.impuesto = value;
    }

    /**
     * Gets the value of the xmlAddenda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXMLAddenda() {
        return xmlAddenda;
    }

    /**
     * Sets the value of the xmlAddenda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXMLAddenda(String value) {
        this.xmlAddenda = value;
    }

}
