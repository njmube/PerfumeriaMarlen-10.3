
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Almacen_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class AlmacenProducto implements java.io.Serializable {
    private static final long serialVersionUID = 1667420337;
    private Integer almacenId;
    private String productoCodigoBarras;
    private int cantidad;
    private double precio;
    private String ubicacion;

    /** 
     * Default Constructor
     */
    public AlmacenProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public AlmacenProducto( Integer almacenId, String productoCodigoBarras ) {
        this.almacenId 	= 	almacenId;
        this.productoCodigoBarras 	= 	productoCodigoBarras;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getAlmacenId() {
        return this.almacenId;
    }

    public void setAlmacenId(Integer v) {
        this.almacenId = v;
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

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double v) {
        this.precio = v;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(String v) {
        this.ubicacion = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (almacenId != null ? almacenId.hashCode() : 0 ) + 
			 (productoCodigoBarras != null ? productoCodigoBarras.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof AlmacenProducto)) {
            return false;
        }

    	AlmacenProducto other = (AlmacenProducto ) o;
        if ( (this.almacenId == null && other.almacenId != null) || (this.almacenId != null && !this.almacenId.equals(other.almacenId)) || 
             (this.productoCodigoBarras == null && other.productoCodigoBarras != null) || (this.productoCodigoBarras != null && !this.productoCodigoBarras.equals(other.productoCodigoBarras))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.AlmacenProducto[almacenId = "+almacenId + ", productoCodigoBarras = "+productoCodigoBarras+ "]";
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// Integer
		sb.append(this.almacenId);
		sb.append(s);
		// String
		sb.append(this.productoCodigoBarras);
		sb.append(s);
		// int
		sb.append(this.cantidad);
		sb.append(s);
		// double
		sb.append( df.format(this.precio));
		sb.append(s);
		// String
		sb.append(this.ubicacion);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// Integer
			this.almacenId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.productoCodigoBarras = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// int
			this.cantidad =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// double
			this.precio =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.ubicacion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
