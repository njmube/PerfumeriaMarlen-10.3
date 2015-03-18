
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Usuario.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 1818292199;
    
    /**
    * email
    */
    private String email;
    
    /**
    * abilitado
    */
    private int abilitado;
    
    /**
    * nombre completo
    */
    private String nombreCompleto;
    
    /**
    * password
    */
    private String password;

    /** 
     * Default Constructor
     */
    public Usuario() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Usuario( String email ) {
        this.email 	= 	email;

    }
    
    /**
     * Getters and Setters
     */
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public int getAbilitado() {
        return this.abilitado;
    }

    public void setAbilitado(int v) {
        this.abilitado = v;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String v) {
        this.nombreCompleto = v;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String v) {
        this.password = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (email != null ? email.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Usuario)) {
            return false;
        }

    	Usuario other = (Usuario ) o;
        if ( (this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// String
		sb.append(this.email);
		sb.append(s);
		// int
		sb.append(this.abilitado);
		sb.append(s);
		// String
		sb.append(this.nombreCompleto);
		sb.append(s);
		// String
		sb.append(this.password);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// String
			this.email = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// int
			this.abilitado =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.nombreCompleto = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.password = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
