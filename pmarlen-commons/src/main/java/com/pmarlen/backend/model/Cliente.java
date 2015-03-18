
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Cliente.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class Cliente implements java.io.Serializable {
    private static final long serialVersionUID = 336867571;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * rfc
    */
    private String rfc;
    
    /**
    * razon social
    */
    private String razonSocial;
    
    /**
    * nombre establecimiento
    */
    private String nombreEstablecimiento;
    
    /**
    * calle
    */
    private String calle;
    
    /**
    * num exterior
    */
    private String numExterior;
    
    /**
    * num interior
    */
    private String numInterior;
    
    /**
    * colonia
    */
    private String colonia;
    
    /**
    * municipio
    */
    private String municipio;
    
    /**
    * referencia
    */
    private String referencia;
    
    /**
    * ciudad
    */
    private String ciudad;
    
    /**
    * cp
    */
    private String cp;
    
    /**
    * estado
    */
    private String estado;
    
    /**
    * email
    */
    private String email;
    
    /**
    * telefonos
    */
    private String telefonos;
    
    /**
    * contacto
    */
    private String contacto;
    
    /**
    * observaciones
    */
    private String observaciones;
    
    /**
    * ubicacion lat
    */
    private Double ubicacionLat;
    
    /**
    * ubicacion lon
    */
    private Double ubicacionLon;
    
    /**
    * num cuenta
    */
    private String numCuenta;
    
    /**
    * banco
    */
    private String banco;
    
    /**
    * direccion facturacion
    */
    private String direccionFacturacion;

    /** 
     * Default Constructor
     */
    public Cliente() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Cliente( Integer id ) {
        this.id 	= 	id;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String v) {
        this.rfc = v;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String v) {
        this.razonSocial = v;
    }

    public String getNombreEstablecimiento() {
        return this.nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String v) {
        this.nombreEstablecimiento = v;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String v) {
        this.calle = v;
    }

    public String getNumExterior() {
        return this.numExterior;
    }

    public void setNumExterior(String v) {
        this.numExterior = v;
    }

    public String getNumInterior() {
        return this.numInterior;
    }

    public void setNumInterior(String v) {
        this.numInterior = v;
    }

    public String getColonia() {
        return this.colonia;
    }

    public void setColonia(String v) {
        this.colonia = v;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String v) {
        this.municipio = v;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public void setReferencia(String v) {
        this.referencia = v;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String v) {
        this.ciudad = v;
    }

    public String getCp() {
        return this.cp;
    }

    public void setCp(String v) {
        this.cp = v;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String v) {
        this.estado = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(String v) {
        this.telefonos = v;
    }

    public String getContacto() {
        return this.contacto;
    }

    public void setContacto(String v) {
        this.contacto = v;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String v) {
        this.observaciones = v;
    }

    public Double getUbicacionLat() {
        return this.ubicacionLat;
    }

    public void setUbicacionLat(Double v) {
        this.ubicacionLat = v;
    }

    public Double getUbicacionLon() {
        return this.ubicacionLon;
    }

    public void setUbicacionLon(Double v) {
        this.ubicacionLon = v;
    }

    public String getNumCuenta() {
        return this.numCuenta;
    }

    public void setNumCuenta(String v) {
        this.numCuenta = v;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String v) {
        this.banco = v;
    }

    public String getDireccionFacturacion() {
        return this.direccionFacturacion;
    }

    public void setDireccionFacturacion(String v) {
        this.direccionFacturacion = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (id != null ? id.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Cliente)) {
            return false;
        }

    	Cliente other = (Cliente ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return razonSocial;
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// Integer
		sb.append(this.id);
		sb.append(s);
		// String
		sb.append(this.rfc);
		sb.append(s);
		// String
		sb.append(this.razonSocial);
		sb.append(s);
		// String
		sb.append(this.nombreEstablecimiento);
		sb.append(s);
		// String
		sb.append(this.calle);
		sb.append(s);
		// String
		sb.append(this.numExterior);
		sb.append(s);
		// String
		sb.append(this.numInterior);
		sb.append(s);
		// String
		sb.append(this.colonia);
		sb.append(s);
		// String
		sb.append(this.municipio);
		sb.append(s);
		// String
		sb.append(this.referencia);
		sb.append(s);
		// String
		sb.append(this.ciudad);
		sb.append(s);
		// String
		sb.append(this.cp);
		sb.append(s);
		// String
		sb.append(this.estado);
		sb.append(s);
		// String
		sb.append(this.email);
		sb.append(s);
		// String
		sb.append(this.telefonos);
		sb.append(s);
		// String
		sb.append(this.contacto);
		sb.append(s);
		// String
		sb.append(this.observaciones);
		sb.append(s);
		// Double
		sb.append( df.format(this.ubicacionLat));
		sb.append(s);
		// Double
		sb.append( df.format(this.ubicacionLon));
		sb.append(s);
		// String
		sb.append(this.numCuenta);
		sb.append(s);
		// String
		sb.append(this.banco);
		sb.append(s);
		// String
		sb.append(this.direccionFacturacion);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// Integer
			this.id =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.rfc = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.razonSocial = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.nombreEstablecimiento = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.calle = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.numExterior = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.numInterior = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.colonia = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.municipio = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.referencia = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.ciudad = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.cp = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.estado = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.email = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.telefonos = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.contacto = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.observaciones = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Double
			this.ubicacionLat =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// Double
			this.ubicacionLon =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.numCuenta = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.banco = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.direccionFacturacion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
