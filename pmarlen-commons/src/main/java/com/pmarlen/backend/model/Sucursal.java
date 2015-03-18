
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Sucursal.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class Sucursal implements java.io.Serializable {
    private static final long serialVersionUID = 1273030789;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * id padre
    */
    private Integer idPadre;
    
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * direccion
    */
    private String direccion;
    
    /**
    * telefonos
    */
    private String telefonos;
    
    /**
    * usuario sicofi
    */
    private String usuarioSicofi;
    
    /**
    * password sicofi
    */
    private String passwordSicofi;
    
    /**
    * serie sicofi
    */
    private String serieSicofi;
    
    /**
    * comentarios
    */
    private String comentarios;
    
    /**
    * descuento mayoreo habilitado
    */
    private Integer descuentoMayoreoHabilitado;

    /** 
     * Default Constructor
     */
    public Sucursal() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Sucursal( Integer id ) {
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

    public Integer getIdPadre() {
        return this.idPadre;
    }

    public void setIdPadre(Integer v) {
        this.idPadre = v;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String v) {
        this.direccion = v;
    }

    public String getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(String v) {
        this.telefonos = v;
    }

    public String getUsuarioSicofi() {
        return this.usuarioSicofi;
    }

    public void setUsuarioSicofi(String v) {
        this.usuarioSicofi = v;
    }

    public String getPasswordSicofi() {
        return this.passwordSicofi;
    }

    public void setPasswordSicofi(String v) {
        this.passwordSicofi = v;
    }

    public String getSerieSicofi() {
        return this.serieSicofi;
    }

    public void setSerieSicofi(String v) {
        this.serieSicofi = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public Integer getDescuentoMayoreoHabilitado() {
        return this.descuentoMayoreoHabilitado;
    }

    public void setDescuentoMayoreoHabilitado(Integer v) {
        this.descuentoMayoreoHabilitado = v;
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
        if (!(o instanceof Sucursal)) {
            return false;
        }

    	Sucursal other = (Sucursal ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombre;
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
		// Integer
		sb.append(this.idPadre);
		sb.append(s);
		// String
		sb.append(this.nombre);
		sb.append(s);
		// String
		sb.append(this.direccion);
		sb.append(s);
		// String
		sb.append(this.telefonos);
		sb.append(s);
		// String
		sb.append(this.usuarioSicofi);
		sb.append(s);
		// String
		sb.append(this.passwordSicofi);
		sb.append(s);
		// String
		sb.append(this.serieSicofi);
		sb.append(s);
		// String
		sb.append(this.comentarios);
		sb.append(s);
		// Integer
		sb.append(this.descuentoMayoreoHabilitado);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// Integer
			this.id =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Integer
			this.idPadre =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.nombre = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.direccion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.telefonos = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.usuarioSicofi = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.passwordSicofi = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.serieSicofi = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.comentarios = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.descuentoMayoreoHabilitado =  Integer.parseInt(srcSpplited[nf]);
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
