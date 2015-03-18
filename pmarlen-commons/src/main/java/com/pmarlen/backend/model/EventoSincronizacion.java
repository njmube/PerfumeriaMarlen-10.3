
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Evento_Sincronizacion.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EventoSincronizacion implements java.io.Serializable {
    private static final long serialVersionUID = 1159908271;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * fecha evento
    */
    private java.sql.Timestamp fechaEvento;
    
    /**
    * tipo evento
    */
    private int tipoEvento;
    
    /**
    * afectacion global
    */
    private int afectacionGlobal;
    
    /**
    * sucursal id afectada
    */
    private int sucursalIdAfectada;
    
    /**
    * tabla
    */
    private String tabla;
    
    /**
    * campos llave
    */
    private String camposLlave;
    
    /**
    * valores llave
    */
    private String valoresLlave;

    /** 
     * Default Constructor
     */
    public EventoSincronizacion() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EventoSincronizacion( Integer id ) {
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

    public java.sql.Timestamp getFechaEvento() {
        return this.fechaEvento;
    }

    public void setFechaEvento(java.sql.Timestamp v) {
        this.fechaEvento = v;
    }

    public int getTipoEvento() {
        return this.tipoEvento;
    }

    public void setTipoEvento(int v) {
        this.tipoEvento = v;
    }

    public int getAfectacionGlobal() {
        return this.afectacionGlobal;
    }

    public void setAfectacionGlobal(int v) {
        this.afectacionGlobal = v;
    }

    public int getSucursalIdAfectada() {
        return this.sucursalIdAfectada;
    }

    public void setSucursalIdAfectada(int v) {
        this.sucursalIdAfectada = v;
    }

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String v) {
        this.tabla = v;
    }

    public String getCamposLlave() {
        return this.camposLlave;
    }

    public void setCamposLlave(String v) {
        this.camposLlave = v;
    }

    public String getValoresLlave() {
        return this.valoresLlave;
    }

    public void setValoresLlave(String v) {
        this.valoresLlave = v;
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
        if (!(o instanceof EventoSincronizacion)) {
            return false;
        }

    	EventoSincronizacion other = (EventoSincronizacion ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.EventoSincronizacion[id = "+id+ "]";
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
		sb.append(this.fechaEvento==null?"null":sdf.format(this.fechaEvento));
		sb.append(s);
		// int
		sb.append(this.tipoEvento);
		sb.append(s);
		// int
		sb.append(this.afectacionGlobal);
		sb.append(s);
		// int
		sb.append(this.sucursalIdAfectada);
		sb.append(s);
		// String
		sb.append(this.tabla);
		sb.append(s);
		// String
		sb.append(this.camposLlave);
		sb.append(s);
		// String
		sb.append(this.valoresLlave);

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
			this.fechaEvento =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// int
			this.tipoEvento =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.afectacionGlobal =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.sucursalIdAfectada =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.tabla = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.camposLlave = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.valoresLlave = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
