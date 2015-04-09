/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.Sucursal;
import com.pmarlen.backend.model.Usuario;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class SyncDTOPackage {
	private List<InventarioSucursalQuickView> inventarioSucursalQVList;
	private List<Usuario> usuarioList;
	private List<ClienteQuickView> clienteList;
	private List<MetodoDePago> metodoDePagoList;
	private List<FormaDePago> formaDePagoList;
	private Sucursal sucursal;	

	/**
	 * @return the inventarioSucursalQVList
	 */
	public List<InventarioSucursalQuickView> getInventarioSucursalQVList() {
		return inventarioSucursalQVList;
	}

	/**
	 * @param inventarioSucursalQVList the inventarioSucursalQVList to set
	 */
	public void setInventarioSucursalQVList(List<InventarioSucursalQuickView> inventarioSucursalQVList) {
		this.inventarioSucursalQVList = inventarioSucursalQVList;
	}

	/**
	 * @return the usuarioList
	 */
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	/**
	 * @param usuarioList the usuarioList to set
	 */
	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	/**
	 * @return the clienteList
	 */
	public List<ClienteQuickView> getClienteList() {
		return clienteList;
	}

	/**
	 * @param clienteList the clienteList to set
	 */
	public void setClienteList(List<ClienteQuickView> clienteList) {
		this.clienteList = clienteList;
	}

	/**
	 * @return the metodoDePagoList
	 */
	public List<MetodoDePago> getMetodoDePagoList() {
		return metodoDePagoList;
	}

	/**
	 * @param metodoDePagoList the metodoDePagoList to set
	 */
	public void setMetodoDePagoList(List<MetodoDePago> metodoDePagoList) {
		this.metodoDePagoList = metodoDePagoList;
	}

	/**
	 * @return the formaDePagoList
	 */
	public List<FormaDePago> getFormaDePagoList() {
		return formaDePagoList;
	}

	/**
	 * @param formaDePagoList the formaDePagoList to set
	 */
	public void setFormaDePagoList(List<FormaDePago> formaDePagoList) {
		this.formaDePagoList = formaDePagoList;
	}

	/**
	 * @return the sucursal
	 */
	public Sucursal getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
}
