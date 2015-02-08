/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

/**
 *
 * @author alfredo
 */
public class Direccionable {
	Integer id; 
    String  razonSocial;	
	String  cp;
	int     poblacionId;

	public Direccionable() {
	}

	public Direccionable(Integer id, String razonSocial, String cp, int poblacionId) {
		this.id = id;
		this.razonSocial = razonSocial;
		this.cp = cp;
		this.poblacionId = poblacionId;
	}
	
}
