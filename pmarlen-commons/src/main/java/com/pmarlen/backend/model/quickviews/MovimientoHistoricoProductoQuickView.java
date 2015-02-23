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
	private int afectado;
	private int saldo;

	public MovimientoHistoricoProductoQuickView() {
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
