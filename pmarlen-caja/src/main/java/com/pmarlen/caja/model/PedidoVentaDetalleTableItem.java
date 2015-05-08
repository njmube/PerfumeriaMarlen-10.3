/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.model;

import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.rest.dto.P;

/**
 *
 * @author alfredo
 */
public class PedidoVentaDetalleTableItem {
	private P producto;
	private EntradaSalidaDetalle pvd;

	public PedidoVentaDetalleTableItem(P producto, EntradaSalidaDetalle pvd) {
		this.producto = producto;
		this.pvd = pvd;
		this.pvd.setCantidad(1);
	}
	
	
	public int getCantidad() {
		return pvd.getCantidad();
	}

	public void setCantidad(int cantidad) {
		this.pvd.setCantidad(cantidad);
	}
	
	public String getCodigo() {
		return producto.getCb();
	}

	public String getShortDesc() {
		return producto.getCb()+" "+producto.getN()+"/"+producto.getP()+"("+producto.getC()+producto.getUm()+")";
	}
	
	public Double getPrecioVenta() {
		return producto.getA1p();
	}
	
	public Double getImporete() {
		return pvd.getCantidad() * producto.getA1p();
	}

	public P getProducto() {
		return producto;
	}

	public EntradaSalidaDetalle getPvd() {
		return pvd;
	}

}
