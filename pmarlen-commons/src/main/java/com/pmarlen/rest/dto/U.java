/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.rest.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class U {
    private String e;
    
    /**
    * abilitado
    */
    private int a;
    
    /**
    * nombre completo
    */
    private String nc;
    
    /**
    * password
    */
    private String p;
	
	/**
	 * Roles List
	 */
	private LinkedHashSet<String> rl;

	public U() {
		rl=new LinkedHashSet<String>();
	}
	
	public void addRole(String r){
		rl.add(r);
	}
	
	public List<String> getRl(){
		Iterator<String> irs=rl.iterator();
		List rls=new ArrayList<String>();
		while(irs.hasNext()){
			rls.add(irs.next());
		}
		return rls;
	}

	/**
	 * @return the e
	 */
	public String getE() {
		return e;
	}

	/**
	 * @param e the e to set
	 */
	public void setE(String e) {
		this.e = e;
	}

	/**
	 * @return the a
	 */
	public int getA() {
		return a;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(int a) {
		this.a = a;
	}

	/**
	 * @return the nc
	 */
	public String getNc() {
		return nc;
	}

	/**
	 * @param nc the nc to set
	 */
	public void setNc(String nc) {
		this.nc = nc;
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

}
