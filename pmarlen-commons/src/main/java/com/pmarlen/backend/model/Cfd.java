
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table CFD.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class Cfd implements java.io.Serializable {
    private static final long serialVersionUID = 321962844;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * ultima actualizacion
    */
    private java.sql.Timestamp ultimaActualizacion;
    
    /**
    * contenido original xml
    */
    private byte[] contenidoOriginalXml;
    
    /**
    * calling error result
    */
    private String callingErrorResult;
    
    /**
    * num cfd
    */
    private String numCfd;
    
    /**
    * tipo
    */
    private String tipo;

    /** 
     * Default Constructor
     */
    public Cfd() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Cfd( Integer id ) {
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

    public java.sql.Timestamp getUltimaActualizacion() {
        return this.ultimaActualizacion;
    }

    public void setUltimaActualizacion(java.sql.Timestamp v) {
        this.ultimaActualizacion = v;
    }

    public byte[] getContenidoOriginalXml() {
        return this.contenidoOriginalXml;
    }

    public void setContenidoOriginalXml(byte[] v) {
        this.contenidoOriginalXml = v;
    }

    public String getCallingErrorResult() {
        return this.callingErrorResult;
    }

    public void setCallingErrorResult(String v) {
        this.callingErrorResult = v;
    }

    public String getNumCfd() {
        return this.numCfd;
    }

    public void setNumCfd(String v) {
        this.numCfd = v;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String v) {
        this.tipo = v;
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
        if (!(o instanceof Cfd)) {
            return false;
        }

    	Cfd other = (Cfd ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.Cfd[id = "+id+ "]";
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
		sb.append(this.ultimaActualizacion==null?"null":sdf.format(this.ultimaActualizacion));
		sb.append(s);
		// byte[]
		sb.append(this.contenidoOriginalXml==null?"null":javax.xml.bind.DatatypeConverter.printBase64Binary((this.contenidoOriginalXml)));
		sb.append(s);
		// String
		sb.append(this.callingErrorResult);
		sb.append(s);
		// String
		sb.append(this.numCfd);
		sb.append(s);
		// String
		sb.append(this.tipo);

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
			this.ultimaActualizacion =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// byte[]
			this.contenidoOriginalXml =  srcSpplited[nf].equals("null")?null:javax.xml.bind.DatatypeConverter.parseBase64Binary(srcSpplited[nf]);
			nf++;
			// String
			this.callingErrorResult = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.numCfd = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.tipo = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
