/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.model;

import com.pmarlen.backend.model.PedidoVentaDetalle;
import com.pmarlen.backend.model.Producto;

/**
 *
 * @author alfredo
 */
public class PedidoVentaDetalleTableItem {
	private Producto producto;
	private PedidoVentaDetalle pvd;

	public PedidoVentaDetalleTableItem(Producto producto, PedidoVentaDetalle pvd) {
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
		return producto.getCodigoBarras();
	}

	public String getNombre() {
		return producto.getNombre();
	}
	
	public String getLinea() {
		return producto.getLinea();
	}
	
	public String getMarca() {
		return producto.getMarca();
	}

	public Double getPrecioVenta() {
		return 0.0;
	}
	
	public Double getImporete() {
		return pvd.getCantidad() * 0.0;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	public PedidoVentaDetalle getPvd() {
		return pvd;
	}

}
