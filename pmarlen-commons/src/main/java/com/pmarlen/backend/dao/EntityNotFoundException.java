/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.dao;

/**
 *
 * @author alfredo
 */
public class EntityNotFoundException extends DAOException{

	public EntityNotFoundException() {
		super("EntityNotFoundException");
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
}
