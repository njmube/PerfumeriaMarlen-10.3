
package com.pmarlen.model.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.Collection;
import java.util.Collection;

// import com.pmarlen.model.beans.Linea;

/**
 * Class for mapping DTO Entity of Table Linea.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/09/11 12:55
 */

public class Linea implements java.io.Serializable {
    private static final long serialVersionUID = 2060447721;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * nombre
    */
    private String nombre;

    /** 
     * Default Constructor
     */
    public Linea() {
    }
	
    /** 
     * lazy Constructor just with IDs
     */
    public Linea( Integer id ) {
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Linea)) {
            return false;
        }

    	Linea other = (Linea ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
