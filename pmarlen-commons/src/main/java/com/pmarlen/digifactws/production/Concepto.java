
package com.pmarlen.digifactws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Concepto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Concepto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cantidad" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Precio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="NumDocAduanero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaExpAduana" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FechaAduanera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Aduana" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CuentaPredial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatosAdicionales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Importe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="NoIdentificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Complemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Concepto", propOrder = {
    "cantidad",
    "unidad",
    "descripcion",
    "precio",
    "numDocAduanero",
    "fechaExpAduana",
    "fechaAduanera",
    "aduana",
    "cuentaPredial",
    "datosAdicionales",
    "importe",
    "noIdentificacion",
    "complemento"
})
public class Concepto {

    @XmlElement(name = "Cantidad")
    protected double cantidad;
    @XmlElement(name = "Unidad")
    protected String unidad;
    @XmlElement(name = "Descripcion")
    protected String descripcion;
    @XmlElement(name = "Precio")
    protected double precio;
    @XmlElement(name = "NumDocAduanero")
    protected String numDocAduanero;
    @XmlElement(name = "FechaExpAduana", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaExpAduana;
    @XmlElement(name = "FechaAduanera")
    protected String fechaAduanera;
    @XmlElement(name = "Aduana")
    protected String aduana;
    @XmlElement(name = "CuentaPredial")
    protected String cuentaPredial;
    @XmlElement(name = "DatosAdicionales")
    protected String datosAdicionales;
    @XmlElement(name = "Importe")
    protected double importe;
    @XmlElement(name = "NoIdentificacion")
    protected String noIdentificacion;
    @XmlElement(name = "Complemento")
    protected String complemento;

    /**
     * Gets the value of the cantidad property.
     * 
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Sets the value of the cantidad property.
     * 
     */
    public void setCantidad(double value) {
        this.cantidad = value;
    }

    /**
     * Gets the value of the unidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Sets the value of the unidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidad(String value) {
        this.unidad = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the precio property.
     * 
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Sets the value of the precio property.
     * 
     */
    public void setPrecio(double value) {
        this.precio = value;
    }

    /**
     * Gets the value of the numDocAduanero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDocAduanero() {
        return numDocAduanero;
    }

    /**
     * Sets the value of the numDocAduanero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDocAduanero(String value) {
        this.numDocAduanero = value;
    }

    /**
     * Gets the value of the fechaExpAduana property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaExpAduana() {
        return fechaExpAduana;
    }

    /**
     * Sets the value of the fechaExpAduana property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaExpAduana(XMLGregorianCalendar value) {
        this.fechaExpAduana = value;
    }

    /**
     * Gets the value of the fechaAduanera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAduanera() {
        return fechaAduanera;
    }

    /**
     * Sets the value of the fechaAduanera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAduanera(String value) {
        this.fechaAduanera = value;
    }

    /**
     * Gets the value of the aduana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAduana() {
        return aduana;
    }

    /**
     * Sets the value of the aduana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAduana(String value) {
        this.aduana = value;
    }

    /**
     * Gets the value of the cuentaPredial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaPredial() {
        return cuentaPredial;
    }

    /**
     * Sets the value of the cuentaPredial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaPredial(String value) {
        this.cuentaPredial = value;
    }

    /**
     * Gets the value of the datosAdicionales property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatosAdicionales() {
        return datosAdicionales;
    }

    /**
     * Sets the value of the datosAdicionales property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatosAdicionales(String value) {
        this.datosAdicionales = value;
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
     * Gets the value of the noIdentificacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    /**
     * Sets the value of the noIdentificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoIdentificacion(String value) {
        this.noIdentificacion = value;
    }

    /**
     * Gets the value of the complemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Sets the value of the complemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplemento(String value) {
        this.complemento = value;
    }

}
