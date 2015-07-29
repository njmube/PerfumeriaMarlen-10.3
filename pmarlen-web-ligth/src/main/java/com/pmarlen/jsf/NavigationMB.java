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
	
	@ManagedProperty(value = "#{historicoPedidosVentaMB}")
	HistoricoPedidosVentaMB historicoPedidosVentaMB;
	
	@ManagedProperty(value = "#{pedidoVentaMB}")
	PedidoVentaMB pedidoVentaMB;
	
	@ManagedProperty(value = "#{comprasMB}")
	ComprasMB comprasMB;
	
	@ManagedProperty(value = "#{historicoComprasMB}")
	HistoricoComprasMB historicoComprasMB;
	
	@ManagedProperty(value = "#{nuevaCompraMB}")
	NuevaCompraMB nuevaCompraMB;
	
	@ManagedProperty(value = "#{nuevaCompraMB}")
	DevolucionesVentaMB devolucionesVentaMB;
	
	@ManagedProperty(value = "#{nuevaCompraMB}")
	HistoricoDevolucionesVentaMB historicoDevolucionesVentaMB;
	
	@ManagedProperty(value = "#{nuevaDevolucionMB}")
	NuevaDevolucionMB nuevaDevolucionMB;
	
	public NavigationMB() {
		logger.debug("->Cosntructor");
	}
	
	public String pedidosVenta() {
		logger.debug("->pedidosVenta");
		pedidosVentaMB.refrescar();
		return "pedidosVenta";
	}
	
	public String historicoPedidosVenta() {
		logger.debug("->historicoPedidosVenta");
		historicoPedidosVentaMB.refrescar();
		return "historicoPedidosVenta";
	}
	
	public String pedidoVenta() {
		logger.debug("->pedidoVenta");
		return "pedidoVenta";
	}
	
	
	public String compras() {
		logger.debug("->compras");
		pedidosVentaMB.refrescar();
		return "pedidosVenta";
	}
	
	public String historicoCompras() {
		logger.debug("->historicoCompras");
		historicoPedidosVentaMB.refrescar();
		return "historicoCompras";
	}
	
	public String nuevaCompra() {
		logger.debug("->nuevaCompra");
		return "nuevaCompra";
	}	
	
	public void setPedidosVentaMB(PedidosVentaMB pedidosVentaMB) {
		this.pedidosVentaMB = pedidosVentaMB;
	}

	public void setHistoricoPedidosVentaMB(HistoricoPedidosVentaMB historicoPedidosVentaMB) {
		this.historicoPedidosVentaMB = historicoPedidosVentaMB;
	}

	public void setPedidoVentaMB(PedidoVentaMB pedidoVentaMB) {
		this.pedidoVentaMB = pedidoVentaMB;
	}

	public void setComprasMB(ComprasMB comprasMB) {
		this.comprasMB = comprasMB;
	}

	public void setHistoricoComprasMB(HistoricoComprasMB historicoComprasMB) {
		this.historicoComprasMB = historicoComprasMB;
	}

	public void setNuevaCompraMB(NuevaCompraMB nuevaCompraMB) {
		this.nuevaCompraMB = nuevaCompraMB;
	}
	
	
	
}
