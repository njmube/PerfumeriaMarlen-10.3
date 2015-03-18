
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Corte_Caja.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class CorteCaja implements java.io.Serializable {
    private static final long serialVersionUID = 2090437641;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * fecha
    */
    private java.sql.Timestamp fecha;
    
    /**
    * sucursal id
    */
    private int sucursalId;
    
    /**
    * caja
    */
    private int caja;
    
    /**
    * usuario email
    */
    private String usuarioEmail;
    
    /**
    * total
    */
    private double total;
    
    /**
    * comentarios
    */
    private String comentarios;

    /** 
     * Default Constructor
     */
    public CorteCaja() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public CorteCaja( Integer id ) {
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

    public java.sql.Timestamp getFecha() {
        return this.fecha;
    }

    public void setFecha(java.sql.Timestamp v) {
        this.fecha = v;
    }

    public int getSucursalId() {
        return this.sucursalId;
    }

    public void setSucursalId(int v) {
        this.sucursalId = v;
    }

    public int getCaja() {
        return this.caja;
    }

    public void setCaja(int v) {
        this.caja = v;
    }

    public String getUsuarioEmail() {
        return this.usuarioEmail;
    }

    public void setUsuarioEmail(String v) {
        this.usuarioEmail = v;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double v) {
        this.total = v;
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
        if (!(o instanceof CorteCaja)) {
            return false;
        }

    	CorteCaja other = (CorteCaja ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.CorteCaja[id = "+id+ "]";
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
		// java.sql.Timestamp
		sb.append(this.fecha==null?"null":sdf.format(this.fecha));
		sb.append(s);
		// int
		sb.append(this.sucursalId);
		sb.append(s);
		// int
		sb.append(this.caja);
		sb.append(s);
		// String
		sb.append(this.usuarioEmail);
		sb.append(s);
		// double
		sb.append( df.format(this.total));
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
			// java.sql.Timestamp
			this.fecha =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// int
			this.sucursalId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.caja =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.usuarioEmail = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// double
			this.total =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.comentarios = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
