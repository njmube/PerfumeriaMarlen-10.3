/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

/**
 *
 * @author alfredo
 */
public class PoblacionesVO {
	int id;
	String nombre;

	public PoblacionesVO(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
