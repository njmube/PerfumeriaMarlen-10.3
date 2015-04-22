
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Producto.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class Producto implements java.io.Serializable {
    private static final long serialVersionUID = 973246391;
    
    /**
    * codigo barras
    */
    private String codigoBarras;

	/**
    * proveedor
    */
    private String proveedor;
    
    /**
    * industria
    */
    private String industria;
    
    /**
    * linea
    */
    private String linea;
    
    /**
    * marca
    */
    private String marca;
    
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * presentacion
    */
    private String presentacion;
    
    /**
    * abrebiatura
    */
    private String abrebiatura;
    
    /**
    * unidades x caja
    */
    private int unidadesXCaja;
    
    /**
    * contenido
    */
    private String contenido;
    
    /**
    * unidad medida
    */
    private String unidadMedida;
    
    /**
    * unidad empaque
    */
    private String unidadEmpaque;
    
    /**
    * costo
    */
    private double costo;
    
    /**
    * costo venta
    */
    private double costoVenta;

    /** 
     * Default Constructor
     */
    public Producto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Producto( String codigoBarras ) {
        this.codigoBarras 	= 	codigoBarras;

    }
    
    /**
     * Getters and Setters
     */
    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    public void setCodigoBarras(String v) {
        this.codigoBarras = v;
    }

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
    public String getIndustria() {
        return this.industria;
    }

    public void setIndustria(String v) {
        this.industria = v;
    }

    public String getLinea() {
        return this.linea;
    }

    public void setLinea(String v) {
        this.linea = v;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String v) {
        this.marca = v;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public String getPresentacion() {
        return this.presentacion;
    }

    public void setPresentacion(String v) {
        this.presentacion = v;
    }

    public String getAbrebiatura() {
        return this.abrebiatura;
    }

    public void setAbrebiatura(String v) {
        this.abrebiatura = v;
    }

    public int getUnidadesXCaja() {
        return this.unidadesXCaja;
    }

    public void setUnidadesXCaja(int v) {
        this.unidadesXCaja = v;
    }

    public String getContenido() {
        return this.contenido;
    }

    public void setContenido(String v) {
        this.contenido = v;
    }

    public String getUnidadMedida() {
        return this.unidadMedida;
    }

    public void setUnidadMedida(String v) {
        this.unidadMedida = v;
    }

    public String getUnidadEmpaque() {
        return this.unidadEmpaque;
    }

    public void setUnidadEmpaque(String v) {
        this.unidadEmpaque = v;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double v) {
        this.costo = v;
    }

    public double getCostoVenta() {
        return this.costoVenta;
    }

    public void setCostoVenta(double v) {
        this.costoVenta = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (codigoBarras != null ? codigoBarras.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Producto)) {
            return false;
        }

    	Producto other = (Producto ) o;
        if ( (this.codigoBarras == null && other.codigoBarras != null) || (this.codigoBarras != null && !this.codigoBarras.equals(other.codigoBarras))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombre + ", " + presentacion;
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// String
		sb.append(this.codigoBarras);
		sb.append(s);
		// String
		sb.append(this.proveedor);
		sb.append(s);
		// String
		sb.append(this.industria);
		sb.append(s);
		// String
		sb.append(this.linea);
		sb.append(s);
		// String
		sb.append(this.marca);
		sb.append(s);
		// String
		sb.append(this.nombre);
		sb.append(s);
		// String
		sb.append(this.presentacion);
		sb.append(s);
		// String
		sb.append(this.abrebiatura);
		sb.append(s);
		// int
		sb.append(this.unidadesXCaja);
		sb.append(s);
		// String
		sb.append(this.contenido);
		sb.append(s);
		// String
		sb.append(this.unidadMedida);
		sb.append(s);
		// String
		sb.append(this.unidadEmpaque);
		sb.append(s);
		// double
		sb.append( df.format(this.costo));
		sb.append(s);
		// double
		sb.append( df.format(this.costoVenta));

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// String
			this.codigoBarras = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.proveedor = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.industria = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.linea = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.marca = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.nombre = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.presentacion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.abrebiatura = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// int
			this.unidadesXCaja =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.contenido = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.unidadMedida = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.unidadEmpaque = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// double
			this.costo =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// double
			this.costoVenta =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
