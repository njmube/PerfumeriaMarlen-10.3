/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.security;

import java.io.Serializable;

/**
 *
 * @author alfredo
 */
public class OnlineSessionInfo implements Serializable {
	private static final long serialVersionUID = 1234567834;
	
	private String usuarioId;
	private int numCaja;
	private int sucursalId;
	private long lastTimeAlive;
	private boolean needSynchronize;

	/**
	 * @return the usuarioId
	 */
	public String getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the numCaja
	 */
	public int getNumCaja() {
		return numCaja;
	}

	/**
	 * @param numCaja the numCaja to set
	 */
	public void setNumCaja(int numCaja) {
		this.numCaja = numCaja;
	}

	/**
	 * @return the sucursalId
	 */
	public int getSucursalId() {
		return sucursalId;
	}

	/**
	 * @param sucursalId the sucursalId to set
	 */
	public void setSucursalId(int sucursalId) {
		this.sucursalId = sucursalId;
	}

	/**
	 * @return the lastTimeAlive
	 */
	public long getLastTimeAlive() {
		return lastTimeAlive;
	}

	/**
	 * @param lastTimeAlive the lastTimeAlive to set
	 */
	public void setLastTimeAlive(long lastTimeAlive) {
		this.lastTimeAlive = lastTimeAlive;
	}

	/**
	 * @return the needSynchronize
	 */
	public boolean isNeedSynchronize() {
		return needSynchronize;
	}

	/**
	 * @param needSynchronize the needSynchronize to set
	 */
	public void setNeedSynchronize(boolean needSynchronize) {
		this.needSynchronize = needSynchronize;
	}

	@Override
	public int hashCode() {
		int hash = 0;

		hash = (usuarioId != null ? usuarioId.hashCode() : 0) + numCaja + sucursalId;
		return hash;
	}

	public boolean equals(Object o) {

		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(o instanceof OnlineSessionInfo)) {
			return false;
		}

		OnlineSessionInfo other = (OnlineSessionInfo) o;
		if (((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId)))
				|| this.numCaja != other.numCaja
				|| this.sucursalId != other.sucursalId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pmarlen.security.OnlineSessionInfo[usuarioId = " + usuarioId + ", numCaja = " + numCaja + ", sucursalId = " + sucursalId + "]";
	}
}
