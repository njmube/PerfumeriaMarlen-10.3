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
import com.pmarlen.rest.dto.P;
import com.pmarlen.rest.dto.U;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class SyncDTOPackage {
	private List<P> inventarioSucursalQVList;
	private List<U> usuarioList;
	private List<ClienteQuickView> clienteList;
	private List<MetodoDePago> metodoDePagoList;
	private List<FormaDePago> formaDePagoList;
	private Sucursal sucursal;	

	/**
	 * @return the inventarioSucursalQVList
	 */
	public List<P> getInventarioSucursalQVList() {
		return inventarioSucursalQVList;
	}

	/**
	 * @param inventarioSucursalQVList the inventarioSucursalQVList to set
	 */
	public void setInventarioSucursalQVList(List<P> inventarioSucursalQVList) {
		this.inventarioSucursalQVList = inventarioSucursalQVList;
	}

	/**
	 * @return the usuarioList
	 */
	public List<U> getUsuarioList() {
		return usuarioList;
	}

	/**
	 * @param usuarioList the usuarioList to set
	 */
	public void setUsuarioList(List<U> usuarioList) {
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

	@Override
	public String toString() {
		return "inventarioSucursalQVList.length="+inventarioSucursalQVList.size()+",usuarioList.length="+usuarioList.size()+
				",clienteList="+clienteList.size()+",metodoDePagoList.length="+metodoDePagoList.size()+
				",formaDePagoList="+formaDePagoList.size()+",sucursal="+sucursal;
	}
}
