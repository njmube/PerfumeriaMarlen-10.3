
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Usuario_Perfil.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class UsuarioPerfil implements java.io.Serializable {
    private static final long serialVersionUID = 1773046069;
    
    /**
    * email
    */
    private String email;
    
    /**
    * perfil
    */
    private String perfil;

    /** 
     * Default Constructor
     */
    public UsuarioPerfil() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public UsuarioPerfil( String email, String perfil ) {
        this.email 	= 	email;
        this.perfil 	= 	perfil;

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

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String v) {
        this.perfil = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (perfil != null ? perfil.hashCode() : 0 ) + 
			 (email != null ? email.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof UsuarioPerfil)) {
            return false;
        }

    	UsuarioPerfil other = (UsuarioPerfil ) o;
        if ( (this.perfil == null && other.perfil != null) || (this.perfil != null && !this.perfil.equals(other.perfil)) || 
             (this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.UsuarioPerfil[perfil = "+perfil + ", email = "+email+ "]";
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
		// String
		sb.append(this.perfil);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// String
			this.email = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.perfil = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
