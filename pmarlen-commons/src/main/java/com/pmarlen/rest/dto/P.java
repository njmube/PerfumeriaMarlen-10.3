/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.rest.dto;

import java.io.Serializable;

/**
 *
 * @author alfredo
 */
public class P implements Serializable{
    
    /**
    * codigo barras
    */
    private String cb;
    
    /**
    * industria
    */
    private String i;
    
    /**
    * linea
    */
    private String l;
    
    /**
    * marca
    */
    private String m;
    
    /**
    * nombre
    */
    private String n;
    
    /**
    * presentacion
    */
    private String p;
    
    /**
    * abrebiatura
    */
    private String a;
    
    /**
    * unidades x caja
    */
    private int uc;
    
    /**
    * contenido
    */
    private String c;
    
    /**
    * unidad medida
    */
    private String um;
    
    /**
    * unidad empaque
    */
    private String ue;
	//==========================================================================
	/**
	 * Almacen 1ra cantidad
	 */
    private int    a1c;
	
	/**
	 * Almacen 1ra precio
	 */
    private double a1p;
	
	//==========================================================================
	/**
	 * Almacen Opo cantidad
	 */
    private int    aOc;
	
	/**
	 * Almacen Opo precio
	 */
    private double aOp;
	
	//==========================================================================
	/**
	 * Almacen Reg cantidad
	 */
    private int    aRc;
	
	/**
	 * Almacen Reg precio
	 */
    private double aRp;
	
	//==========================================================================

	/**
	 * @return the cb
	 */
	public String getCb() {
		return cb;
	}

	/**
	 * @param cb the cb to set
	 */
	public void setCb(String cb) {
		this.cb = cb;
	}

	/**
	 * @return the i
	 */
	public String getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 */
	public void setI(String i) {
		this.i = i;
	}

	/**
	 * @return the l
	 */
	public String getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 */
	public void setL(String l) {
		this.l = l;
	}

	/**
	 * @return the m
	 */
	public String getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(String m) {
		this.m = m;
	}

	/**
	 * @return the n
	 */
	public String getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(String n) {
		this.n = n;
	}

	/**
	 * @return the p
	 */
	public String getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(String p) {
		this.p = p;
	}

	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(String a) {
		this.a = a;
	}

	/**
	 * @return the uc
	 */
	public int getUc() {
		return uc;
	}

	/**
	 * @param uc the uc to set
	 */
	public void setUc(int uc) {
		this.uc = uc;
	}

	/**
	 * @return the c
	 */
	public String getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(String c) {
		this.c = c;
	}

	/**
	 * @return the um
	 */
	public String getUm() {
		return um;
	}

	/**
	 * @param um the um to set
	 */
	public void setUm(String um) {
		this.um = um;
	}

	/**
	 * @return the ue
	 */
	public String getUe() {
		return ue;
	}

	/**
	 * @param ue the ue to set
	 */
	public void setUe(String ue) {
		this.ue = ue;
	}

	/**
	 * @return the a1c
	 */
	public int getA1c() {
		return a1c;
	}

	/**
	 * @param a1c the a1c to set
	 */
	public void setA1c(int a1c) {
		this.a1c = a1c;
	}

	/**
	 * @return the a1p
	 */
	public double getA1p() {
		return a1p;
	}

	/**
	 * @param a1p the a1p to set
	 */
	public void setA1p(double a1p) {
		this.a1p = a1p;
	}

	/**
	 * @return the aOc
	 */
	public int getaOc() {
		return aOc;
	}

	/**
	 * @param aOc the aOc to set
	 */
	public void setaOc(int aOc) {
		this.aOc = aOc;
	}

	/**
	 * @return the aOp
	 */
	public double getaOp() {
		return aOp;
	}

	/**
	 * @param aOp the aOp to set
	 */
	public void setaOp(double aOp) {
		this.aOp = aOp;
	}

	/**
	 * @return the aRc
	 */
	public int getaRc() {
		return aRc;
	}

	/**
	 * @param aRc the aRc to set
	 */
	public void setaRc(int aRc) {
		this.aRc = aRc;
	}

	/**
	 * @return the aRp
	 */
	public double getaRp() {
		return aRp;
	}

	/**
	 * @param aRp the aRp to set
	 */
	public void setaRp(double aRp) {
		this.aRp = aRp;
	}
}
