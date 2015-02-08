/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 *
 * @author alfredo
 */
@WebFault(name="UpdateBugFixingException")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateBugFixingException extends Exception{
	
	public UpdateBugFixingException(String message){
		super(message);
	}
}
