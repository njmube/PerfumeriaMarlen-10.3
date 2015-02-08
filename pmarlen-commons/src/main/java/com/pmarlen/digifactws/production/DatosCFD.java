
package com.pmarlen.digifactws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DatosCFD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosCFD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipodeComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FormadePago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MetododePago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Folio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Subtotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Total" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Descuento" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="EmailMensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotivoDescuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Moneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoCambio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CondicionesDePago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatosAdicionales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MensajePDF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LugarDeExpedicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cuenta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TBN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusCFD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FolioFiscalOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SerieFolioFiscalOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaFolioFiscalOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MontoFolioFiscalOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Complementos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosCFD", propOrder = {
    "tipodeComprobante",
    "formadePago",
    "metododePago",
    "serie",
    "folio",
    "fecha",
    "subtotal",
    "total",
    "descuento",
    "emailMensaje",
    "motivoDescuento",
    "moneda",
    "tipoCambio",
    "condicionesDePago",
    "datosAdicionales",
    "mensajePDF",
    "lugarDeExpedicion",
    "cuenta",
    "tbn",
    "statusCFD",
    "folioFiscalOriginal",
    "serieFolioFiscalOriginal",
    "fechaFolioFiscalOriginal",
    "montoFolioFiscalOriginal",
    "complementos"
})
public class DatosCFD {

    @XmlElement(name = "TipodeComprobante")
    protected String tipodeComprobante;
    @XmlElement(name = "FormadePago")
    protected String formadePago;
    @XmlElement(name = "MetododePago")
    protected String metododePago;
    @XmlElement(name = "Serie")
    protected String serie;
    @XmlElement(name = "Folio")
    protected int folio;
    @XmlElement(name = "Fecha", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(name = "Subtotal")
    protected double subtotal;
    @XmlElement(name = "Total")
    protected double total;
    @XmlElement(name = "Descuento")
    protected double descuento;
    @XmlElement(name = "EmailMensaje")
    protected String emailMensaje;
    @XmlElement(name = "MotivoDescuento")
    protected String motivoDescuento;
    @XmlElement(name = "Moneda")
    protected String moneda;
    @XmlElement(name = "TipoCambio")
    protected double tipoCambio;
    @XmlElement(name = "CondicionesDePago")
    protected String condicionesDePago;
    @XmlElement(name = "DatosAdicionales")
    protected String datosAdicionales;
    @XmlElement(name = "MensajePDF")
    protected String mensajePDF;
    @XmlElement(name = "LugarDeExpedicion")
    protected String lugarDeExpedicion;
    @XmlElement(name = "Cuenta")
    protected String cuenta;
    @XmlElement(name = "TBN")
    protected String tbn;
    @XmlElement(name = "StatusCFD")
    protected String statusCFD;
    @XmlElement(name = "FolioFiscalOriginal")
    protected String folioFiscalOriginal;
    @XmlElement(name = "SerieFolioFiscalOriginal")
    protected String serieFolioFiscalOriginal;
    @XmlElement(name = "FechaFolioFiscalOriginal")
    protected String fechaFolioFiscalOriginal;
    @XmlElement(name = "MontoFolioFiscalOriginal")
    protected String montoFolioFiscalOriginal;
    @XmlElement(name = "Complementos")
    protected String complementos;

    /**
     * Gets the value of the tipodeComprobante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipodeComprobante() {
        return tipodeComprobante;
    }

    /**
     * Sets the value of the tipodeComprobante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipodeComprobante(String value) {
        this.tipodeComprobante = value;
    }

    /**
     * Gets the value of the formadePago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormadePago() {
        return formadePago;
    }

    /**
     * Sets the value of the formadePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormadePago(String value) {
        this.formadePago = value;
    }

    /**
     * Gets the value of the metododePago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetododePago() {
        return metododePago;
    }

    /**
     * Sets the value of the metododePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetododePago(String value) {
        this.metododePago = value;
    }

    /**
     * Gets the value of the serie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Sets the value of the serie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerie(String value) {
        this.serie = value;
    }

    /**
     * Gets the value of the folio property.
     * 
     */
    public int getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     * 
     */
    public void setFolio(int value) {
        this.folio = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the subtotal property.
     * 
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Sets the value of the subtotal property.
     * 
     */
    public void setSubtotal(double value) {
        this.subtotal = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(double value) {
        this.total = value;
    }

    /**
     * Gets the value of the descuento property.
     * 
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * Sets the value of the descuento property.
     * 
     */
    public void setDescuento(double value) {
        this.descuento = value;
    }

    /**
     * Gets the value of the emailMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailMensaje() {
        return emailMensaje;
    }

    /**
     * Sets the value of the emailMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailMensaje(String value) {
        this.emailMensaje = value;
    }

    /**
     * Gets the value of the motivoDescuento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    /**
     * Sets the value of the motivoDescuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoDescuento(String value) {
        this.motivoDescuento = value;
    }

    /**
     * Gets the value of the moneda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Sets the value of the moneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Gets the value of the tipoCambio property.
     * 
     */
    public double getTipoCambio() {
        return tipoCambio;
    }

    /**
     * Sets the value of the tipoCambio property.
     * 
     */
    public void setTipoCambio(double value) {
        this.tipoCambio = value;
    }

    /**
     * Gets the value of the condicionesDePago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondicionesDePago() {
        return condicionesDePago;
    }

    /**
     * Sets the value of the condicionesDePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondicionesDePago(String value) {
        this.condicionesDePago = value;
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
     * Gets the value of the mensajePDF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajePDF() {
        return mensajePDF;
    }

    /**
     * Sets the value of the mensajePDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajePDF(String value) {
        this.mensajePDF = value;
    }

    /**
     * Gets the value of the lugarDeExpedicion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarDeExpedicion() {
        return lugarDeExpedicion;
    }

    /**
     * Sets the value of the lugarDeExpedicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarDeExpedicion(String value) {
        this.lugarDeExpedicion = value;
    }

    /**
     * Gets the value of the cuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * Sets the value of the cuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuenta(String value) {
        this.cuenta = value;
    }

    /**
     * Gets the value of the tbn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTBN() {
        return tbn;
    }

    /**
     * Sets the value of the tbn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTBN(String value) {
        this.tbn = value;
    }

    /**
     * Gets the value of the statusCFD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCFD() {
        return statusCFD;
    }

    /**
     * Sets the value of the statusCFD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCFD(String value) {
        this.statusCFD = value;
    }

    /**
     * Gets the value of the folioFiscalOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioFiscalOriginal() {
        return folioFiscalOriginal;
    }

    /**
     * Sets the value of the folioFiscalOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioFiscalOriginal(String value) {
        this.folioFiscalOriginal = value;
    }

    /**
     * Gets the value of the serieFolioFiscalOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerieFolioFiscalOriginal() {
        return serieFolioFiscalOriginal;
    }

    /**
     * Sets the value of the serieFolioFiscalOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerieFolioFiscalOriginal(String value) {
        this.serieFolioFiscalOriginal = value;
    }

    /**
     * Gets the value of the fechaFolioFiscalOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFolioFiscalOriginal() {
        return fechaFolioFiscalOriginal;
    }

    /**
     * Sets the value of the fechaFolioFiscalOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFolioFiscalOriginal(String value) {
        this.fechaFolioFiscalOriginal = value;
    }

    /**
     * Gets the value of the montoFolioFiscalOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMontoFolioFiscalOriginal() {
        return montoFolioFiscalOriginal;
    }

    /**
     * Sets the value of the montoFolioFiscalOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMontoFolioFiscalOriginal(String value) {
        this.montoFolioFiscalOriginal = value;
    }

    /**
     * Gets the value of the complementos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplementos() {
        return complementos;
    }

    /**
     * Sets the value of the complementos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplementos(String value) {
        this.complementos = value;
    }

}
