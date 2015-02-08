
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
 * @date 2015/02/07 21:02
 */

public class Cliente implements java.io.Serializable {
    private static final long serialVersionUID = 1162133812;
    
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
    * contacto
    */
    private String contacto;
    
    /**
    * telefonos
    */
    private String telefonos;
    
    /**
    * email
    */
    private String email;
    
    /**
    * observaciones
    */
    private String observaciones;
    
    /**
    * direccion
    */
    private String direccion;

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

    public String getContacto() {
        return this.contacto;
    }

    public void setContacto(String v) {
        this.contacto = v;
    }

    public String getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(String v) {
        this.telefonos = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String v) {
        this.observaciones = v;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String v) {
        this.direccion = v;
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
		sb.append(this.contacto);
		sb.append(s);
		// String
		sb.append(this.telefonos);
		sb.append(s);
		// String
		sb.append(this.email);
		sb.append(s);
		// String
		sb.append(this.observaciones);
		sb.append(s);
		// String
		sb.append(this.direccion);

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
			this.contacto = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.telefonos = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.email = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.observaciones = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.direccion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
