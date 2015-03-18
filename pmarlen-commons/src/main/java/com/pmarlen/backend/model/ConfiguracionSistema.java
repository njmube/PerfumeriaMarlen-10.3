
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Configuracion_Sistema.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class ConfiguracionSistema implements java.io.Serializable {
    private static final long serialVersionUID = 1122088620;
    
    /**
    * llave
    */
    private String llave;
    
    /**
    * valor
    */
    private String valor;

    /** 
     * Default Constructor
     */
    public ConfiguracionSistema() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public ConfiguracionSistema( String llave ) {
        this.llave 	= 	llave;

    }
    
    /**
     * Getters and Setters
     */
    public String getLlave() {
        return this.llave;
    }

    public void setLlave(String v) {
        this.llave = v;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String v) {
        this.valor = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (llave != null ? llave.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof ConfiguracionSistema)) {
            return false;
        }

    	ConfiguracionSistema other = (ConfiguracionSistema ) o;
        if ( (this.llave == null && other.llave != null) || (this.llave != null && !this.llave.equals(other.llave))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.ConfiguracionSistema[llave = "+llave+ "]";
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// String
		sb.append(this.llave);
		sb.append(s);
		// String
		sb.append(this.valor);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// String
			this.llave = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.valor = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
