
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Movimiento_Historico_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoHistoricoProducto implements java.io.Serializable {
    private static final long serialVersionUID = 2006133574;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * almacen id
    */
    private int almacenId;
    
    /**
    * fecha
    */
    private java.sql.Timestamp fecha;
    
    /**
    * tipo movimiento
    */
    private int tipoMovimiento;
    
    /**
    * cantidad
    */
    private int cantidad;
    
    /**
    * costo
    */
    private Double costo;
    
    /**
    * precio
    */
    private Double precio;
    
    /**
    * usuario email
    */
    private String usuarioEmail;
    
    /**
    * producto codigo barras
    */
    private String productoCodigoBarras;
    
    /**
    * entrada salida id
    */
    private Integer entradaSalidaId;

    /** 
     * Default Constructor
     */
    public MovimientoHistoricoProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoHistoricoProducto( Integer id ) {
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

    public int getAlmacenId() {
        return this.almacenId;
    }

    public void setAlmacenId(int v) {
        this.almacenId = v;
    }

    public java.sql.Timestamp getFecha() {
        return this.fecha;
    }

    public void setFecha(java.sql.Timestamp v) {
        this.fecha = v;
    }

    public int getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(int v) {
        this.tipoMovimiento = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public Double getCosto() {
        return this.costo;
    }

    public void setCosto(Double v) {
        this.costo = v;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double v) {
        this.precio = v;
    }

    public String getUsuarioEmail() {
        return this.usuarioEmail;
    }

    public void setUsuarioEmail(String v) {
        this.usuarioEmail = v;
    }

    public String getProductoCodigoBarras() {
        return this.productoCodigoBarras;
    }

    public void setProductoCodigoBarras(String v) {
        this.productoCodigoBarras = v;
    }

    public Integer getEntradaSalidaId() {
        return this.entradaSalidaId;
    }

    public void setEntradaSalidaId(Integer v) {
        this.entradaSalidaId = v;
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
        if (!(o instanceof MovimientoHistoricoProducto)) {
            return false;
        }

    	MovimientoHistoricoProducto other = (MovimientoHistoricoProducto ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.MovimientoHistoricoProducto[id = "+id+ "]";
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
		sb.append(this.almacenId);
		sb.append(s);
		// java.sql.Timestamp
		sb.append(this.fecha==null?"null":sdf.format(this.fecha));
		sb.append(s);
		// int
		sb.append(this.tipoMovimiento);
		sb.append(s);
		// int
		sb.append(this.cantidad);
		sb.append(s);
		// Double
		sb.append( df.format(this.costo));
		sb.append(s);
		// Double
		sb.append( df.format(this.precio));
		sb.append(s);
		// String
		sb.append(this.usuarioEmail);
		sb.append(s);
		// String
		sb.append(this.productoCodigoBarras);
		sb.append(s);
		// Integer
		sb.append(this.entradaSalidaId);

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
			this.almacenId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// java.sql.Timestamp
			this.fecha =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// int
			this.tipoMovimiento =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.cantidad =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Double
			this.costo =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// Double
			this.precio =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.usuarioEmail = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.productoCodigoBarras = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.entradaSalidaId =  Integer.parseInt(srcSpplited[nf]);
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
