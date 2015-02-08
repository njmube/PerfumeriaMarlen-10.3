/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import javax.swing.JTextField;

/**
 *
 * @author alfredo
 */
class ValidacionCamposException extends Exception{
	private JTextField source;
	
	public ValidacionCamposException(String message,JTextField source) {
		super(message);
		this.source = source;
	}

	/**
	 * @return the source
	 */
	public JTextField getSource() {
		return source;
	}
	
}
