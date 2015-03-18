
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Metodo_De_Pago.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MetodoDePago implements java.io.Serializable {
    private static final long serialVersionUID = 1912977276;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * descripcion
    */
    private String descripcion;

    /** 
     * Default Constructor
     */
    public MetodoDePago() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MetodoDePago( Integer id ) {
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String v) {
        this.descripcion = v;
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
        if (!(o instanceof MetodoDePago)) {
            return false;
        }

    	MetodoDePago other = (MetodoDePago ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return descripcion;
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
		sb.append(this.descripcion);

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
			this.descripcion = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
