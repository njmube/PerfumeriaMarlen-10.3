/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.MovimientoHistoricoProducto;
import com.pmarlen.model.Constants;

/**
 *
 * @author alfredo
 */
public class MovimientoHistoricoProductoQuickView extends MovimientoHistoricoProducto {
	private int rowId;
	private int afectado;
	private int saldo;
	private String tipoMovDesc;

	public MovimientoHistoricoProductoQuickView() {
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getRowId() {
		return rowId;
	}

	
	/**
	 * @return the afectado
	 */
	public int getAfectado() {
		return afectado;
	}

	/**
	 * @param afectado the afectado to set
	 */
	public void setAfectado(int afectado) {
		this.afectado = afectado;
	}

	public String getTipoMovDesc() {
		return tipoMovDesc;
	}

	public void setTipoMovDesc(String tipoMovDesc) {
		this.tipoMovDesc = tipoMovDesc;
	}

	/**
	 * @return the saldo
	 */
	public int getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	public boolean isEntrada(){
		return this.getTipoMovimiento() >= Constants.TIPO_MOV_ENTRADA_ALMACEN_COMPRA && this.getTipoMovimiento() < Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA;
	}
	
	public boolean isSalida(){
		return this.getTipoMovimiento() >= Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA && this.getTipoMovimiento() < Constants.TIPO_MOV_OTRO;
	}
	
}
