
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Entrada_Salida_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalidaDetalle implements java.io.Serializable {
    private static final long serialVersionUID = 8505448;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * entrada salida id
    */
    private int entradaSalidaId;
    
    /**
    * producto codigo barras
    */
    private String productoCodigoBarras;
    
    /**
    * almacen id
    */
    private int almacenId;
    
    /**
    * cantidad
    */
    private int cantidad;
    
    /**
    * precio venta
    */
    private double precioVenta;

    /** 
     * Default Constructor
     */
    public EntradaSalidaDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EntradaSalidaDetalle( Integer id ) {
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

    public int getEntradaSalidaId() {
        return this.entradaSalidaId;
    }

    public void setEntradaSalidaId(int v) {
        this.entradaSalidaId = v;
    }

    public String getProductoCodigoBarras() {
        return this.productoCodigoBarras;
    }

    public void setProductoCodigoBarras(String v) {
        this.productoCodigoBarras = v;
    }

    public int getAlmacenId() {
        return this.almacenId;
    }

    public void setAlmacenId(int v) {
        this.almacenId = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public double getPrecioVenta() {
        return this.precioVenta;
    }

    public void setPrecioVenta(double v) {
        this.precioVenta = v;
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
        if (!(o instanceof EntradaSalidaDetalle)) {
            return false;
        }

    	EntradaSalidaDetalle other = (EntradaSalidaDetalle ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.EntradaSalidaDetalle[id = "+id+ "]";
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
		sb.append(this.entradaSalidaId);
		sb.append(s);
		// String
		sb.append(this.productoCodigoBarras);
		sb.append(s);
		// int
		sb.append(this.almacenId);
		sb.append(s);
		// int
		sb.append(this.cantidad);
		sb.append(s);
		// double
		sb.append( df.format(this.precioVenta));

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
			this.entradaSalidaId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.productoCodigoBarras = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// int
			this.almacenId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.cantidad =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// double
			this.precioVenta =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
