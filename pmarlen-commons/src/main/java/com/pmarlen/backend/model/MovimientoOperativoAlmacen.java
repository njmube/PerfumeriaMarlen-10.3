
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Movimiento_operativo_almacen.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoOperativoAlmacen implements java.io.Serializable {
    private static final long serialVersionUID = 1433980130;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * motivo
    */
    private String motivo;
    
    /**
    * usuario genero
    */
    private String usuarioGenero;
    
    /**
    * fecha inicio
    */
    private java.sql.Timestamp fechaInicio;
    
    /**
    * almacen origen
    */
    private int almacenOrigen;
    
    /**
    * almacen destino
    */
    private int almacenDestino;
    
    /**
    * tipo movimiento
    */
    private int tipoMovimiento;
    
    /**
    * usuario confirmo
    */
    private String usuarioConfirmo;
    
    /**
    * fecha confirmacion
    */
    private java.sql.Timestamp fechaConfirmacion;
    
    /**
    * comentarios
    */
    private String comentarios;

    /** 
     * Default Constructor
     */
    public MovimientoOperativoAlmacen() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoOperativoAlmacen( Integer id ) {
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

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String v) {
        this.motivo = v;
    }

    public String getUsuarioGenero() {
        return this.usuarioGenero;
    }

    public void setUsuarioGenero(String v) {
        this.usuarioGenero = v;
    }

    public java.sql.Timestamp getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(java.sql.Timestamp v) {
        this.fechaInicio = v;
    }

    public int getAlmacenOrigen() {
        return this.almacenOrigen;
    }

    public void setAlmacenOrigen(int v) {
        this.almacenOrigen = v;
    }

    public int getAlmacenDestino() {
        return this.almacenDestino;
    }

    public void setAlmacenDestino(int v) {
        this.almacenDestino = v;
    }

    public int getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(int v) {
        this.tipoMovimiento = v;
    }

    public String getUsuarioConfirmo() {
        return this.usuarioConfirmo;
    }

    public void setUsuarioConfirmo(String v) {
        this.usuarioConfirmo = v;
    }

    public java.sql.Timestamp getFechaConfirmacion() {
        return this.fechaConfirmacion;
    }

    public void setFechaConfirmacion(java.sql.Timestamp v) {
        this.fechaConfirmacion = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
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
        if (!(o instanceof MovimientoOperativoAlmacen)) {
            return false;
        }

    	MovimientoOperativoAlmacen other = (MovimientoOperativoAlmacen ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.MovimientoOperativoAlmacen[id = "+id+ "]";
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
		sb.append(this.motivo);
		sb.append(s);
		// String
		sb.append(this.usuarioGenero);
		sb.append(s);
		// java.sql.Timestamp
		sb.append(this.fechaInicio==null?"null":sdf.format(this.fechaInicio));
		sb.append(s);
		// int
		sb.append(this.almacenOrigen);
		sb.append(s);
		// int
		sb.append(this.almacenDestino);
		sb.append(s);
		// int
		sb.append(this.tipoMovimiento);
		sb.append(s);
		// String
		sb.append(this.usuarioConfirmo);
		sb.append(s);
		// java.sql.Timestamp
		sb.append(this.fechaConfirmacion==null?"null":sdf.format(this.fechaConfirmacion));
		sb.append(s);
		// String
		sb.append(this.comentarios);

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
			this.motivo = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.usuarioGenero = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// java.sql.Timestamp
			this.fechaInicio =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// int
			this.almacenOrigen =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.almacenDestino =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.tipoMovimiento =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.usuarioConfirmo = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// java.sql.Timestamp
			this.fechaConfirmacion =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// String
			this.comentarios = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
