/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

/**
 *
 * @author alfredo
 */
public class UsuarioEXCELValue {
    String usuarioId;
    
    String nombreCompleto;
    
    String password;
    
    String email;
	
	String sucID;
	
	String roles;
	
	public UsuarioEXCELValue(String usuarioId, String nombreCompleto, String password, String email,String sucID) {
		this.usuarioId = usuarioId;
		this.nombreCompleto = nombreCompleto;
		this.password = password;
		this.email = email;
		this.sucID = sucID;
	}

	public UsuarioEXCELValue() {
		
	}	
}
