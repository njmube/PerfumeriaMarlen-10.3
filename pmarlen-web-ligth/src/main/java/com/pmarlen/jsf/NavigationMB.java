/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.jsf;

import org.apache.log4j.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author alfredo
 */
@ManagedBean(name="navigationMB")
@RequestScoped
public class NavigationMB {
	protected static transient Logger logger = Logger.getLogger(NavigationMB.class.getName());
	@ManagedProperty(value = "#{pedidosVentaMB}")
	PedidosVentaMB pedidosVentaMB;
	
	public NavigationMB() {
		logger.debug("->Cosntructor");
	}
	
	public String pedidosVenta() {
		logger.debug("->pedidosVenta");
		return "pedidosVenta";
	}

	public void setPedidosVentaMB(PedidosVentaMB pedidosVentaMB) {
		this.pedidosVentaMB = pedidosVentaMB;
	}
	
	
	
	
}
