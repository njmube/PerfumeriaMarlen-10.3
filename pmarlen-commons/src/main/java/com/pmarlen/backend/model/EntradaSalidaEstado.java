
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Entrada_Salida_Estado.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalidaEstado implements java.io.Serializable {
    private static final long serialVersionUID = 2025018324;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * entrada salida id
    */
    private int entradaSalidaId;
    
    /**
    * estado id
    */
    private int estadoId;
    
    /**
    * fecha
    */
    private java.sql.Timestamp fecha;
    
    /**
    * usuario email
    */
    private String usuarioEmail;
    
    /**
    * comentarios
    */
    private String comentarios;

    /** 
     * Default Constructor
     */
    public EntradaSalidaEstado() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EntradaSalidaEstado( Integer id ) {
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

    public int getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(int v) {
        this.estadoId = v;
    }

    public java.sql.Timestamp getFecha() {
        return this.fecha;
    }

    public void setFecha(java.sql.Timestamp v) {
        this.fecha = v;
    }

    public String getUsuarioEmail() {
        return this.usuarioEmail;
    }

    public void setUsuarioEmail(String v) {
        this.usuarioEmail = v;
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
        if (!(o instanceof EntradaSalidaEstado)) {
            return false;
        }

    	EntradaSalidaEstado other = (EntradaSalidaEstado ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.EntradaSalidaEstado[id = "+id+ "]";
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
		// int
		sb.append(this.estadoId);
		sb.append(s);
		// java.sql.Timestamp
		sb.append(this.fecha==null?"null":sdf.format(this.fecha));
		sb.append(s);
		// String
		sb.append(this.usuarioEmail);
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
			// int
			this.entradaSalidaId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.estadoId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// java.sql.Timestamp
			this.fecha =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// String
			this.usuarioEmail = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.comentarios = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
