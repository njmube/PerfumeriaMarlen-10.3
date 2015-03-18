
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Movimiento_operativo_almacen_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoOperativoAlmacenDetalle implements java.io.Serializable {
    private static final long serialVersionUID = 958544880;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * movimiento operativo almacen id
    */
    private int movimientoOperativoAlmacenId;
    
    /**
    * producto codigo barras
    */
    private String productoCodigoBarras;
    
    /**
    * cantidad
    */
    private int cantidad;
    
    /**
    * cantidad confirmada
    */
    private Integer cantidadConfirmada;

    /** 
     * Default Constructor
     */
    public MovimientoOperativoAlmacenDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoOperativoAlmacenDetalle( Integer id ) {
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

    public int getMovimientoOperativoAlmacenId() {
        return this.movimientoOperativoAlmacenId;
    }

    public void setMovimientoOperativoAlmacenId(int v) {
        this.movimientoOperativoAlmacenId = v;
    }

    public String getProductoCodigoBarras() {
        return this.productoCodigoBarras;
    }

    public void setProductoCodigoBarras(String v) {
        this.productoCodigoBarras = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public Integer getCantidadConfirmada() {
        return this.cantidadConfirmada;
    }

    public void setCantidadConfirmada(Integer v) {
        this.cantidadConfirmada = v;
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
        if (!(o instanceof MovimientoOperativoAlmacenDetalle)) {
            return false;
        }

    	MovimientoOperativoAlmacenDetalle other = (MovimientoOperativoAlmacenDetalle ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.MovimientoOperativoAlmacenDetalle[id = "+id+ "]";
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
		// int
		sb.append(this.movimientoOperativoAlmacenId);
		sb.append(s);
		// String
		sb.append(this.productoCodigoBarras);
		sb.append(s);
		// int
		sb.append(this.cantidad);
		sb.append(s);
		// Integer
		sb.append(this.cantidadConfirmada);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// Integer
			this.id =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.movimientoOperativoAlmacenId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.productoCodigoBarras = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// int
			this.cantidad =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Integer
			this.cantidadConfirmada =  Integer.parseInt(srcSpplited[nf]);
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
