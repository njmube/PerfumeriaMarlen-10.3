/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.security;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alfredo
 */
public class AnotherInfo implements Serializable{
	private static final long serialVersionUID = 1234567800;

	private int    intValue;
	private double doubleValue;
	private boolean booleanValue;

	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	/**
	 * @return the doubleValue
	 */
	public double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * @param doubleValue the doubleValue to set
	 */
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	/**
	 * @return the booleanValue
	 */
	public boolean isBooleanValue() {
		return booleanValue;
	}

	/**
	 * @param booleanValue the booleanValue to set
	 */
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	@Override
	public String toString() {
		return getClass().toString()+" { intValue="+intValue+", doubleValue="+doubleValue+", booleanValue="+booleanValue+" }";
	}

}
