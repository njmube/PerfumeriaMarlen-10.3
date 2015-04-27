/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.model.Constants;
import java.text.DecimalFormat;

/**
 *
 * @author alfredo
 */
public class EntradaSalidaDetalleQuickView extends EntradaSalidaDetalle implements Comparable<EntradaSalidaDetalleQuickView>{
	
	private long rowId;
	
	private String productoNombre;
	
	private String productoProveedor;
	
	private String productoPresentacion;
	
	private String productoContenido;
	
	private String productoUnidadMedida;
	
	private String productoUnidadEmpaque;
    
    private String productoIndustria;
    
    private String productoLinea;
    
    private String productoMarca;
	
	private String productoUnidadesPorCaja;
	
	private String apUbicacion;
	
	private int     apTipoAlmacen;
		    
    private int     apCantidad;
	
    private double  apPrecio;
	
	private int canPendteEnOtrosPV;

	
	public EntradaSalidaDetalleQuickView() {
		this.rowId = System.currentTimeMillis();
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public long getRowId() {
		return rowId;
	}
		
	public String toStringShorten() {
		StringBuilder sb= new StringBuilder(productoNombre);
		
		sb.append("/").
				append(productoPresentacion).
				append(" ").
				append(productoContenido).
				append(productoUnidadMedida).
				append("[").
				append(productoUnidadesPorCaja).
				append("]#").
				append(apCantidad);
		
		return  sb.toString();
	}

	/**
	 * @return the productoNombre
	 */
	public String getProductoNombre() {
		return productoNombre;
	}

	/**
	 * @param productoNombre the productoNombre to set
	 */
	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public String getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(String productoProveedor) {
		this.productoProveedor = productoProveedor;
	}
	
	/**
	 * @return the productoPresentacion
	 */
	public String getProductoPresentacion() {
		return productoPresentacion;
	}

	/**
	 * @param productoPresentacion the productoPresentacion to set
	 */
	public void setProductoPresentacion(String productoPresentacion) {
		this.productoPresentacion = productoPresentacion;
	}

	/**
	 * @return the productoContenido
	 */
	public String getProductoContenido() {
		return productoContenido;
	}

	/**
	 * @param productoContenido the productoContenido to set
	 */
	public void setProductoContenido(String productoContenido) {
		this.productoContenido = productoContenido;
	}

	/**
	 * @return the productoIndustria
	 */
	public String getProductoIndustria() {
		return productoIndustria;
	}

	/**
	 * @param productoIndustria the productoIndustria to set
	 */
	public void setProductoIndustria(String productoIndustria) {
		this.productoIndustria = productoIndustria;
	}

	/**
	 * @return the productoLinea
	 */
	public String getProductoLinea() {
		return productoLinea;
	}

	/**
	 * @param productoLinea the productoLinea to set
	 */
	public void setProductoLinea(String productoLinea) {
		this.productoLinea = productoLinea;
	}

	/**
	 * @return the productoMarca
	 */
	public String getProductoMarca() {
		return productoMarca;
	}

	/**
	 * @param productoMarca the productoMarca to set
	 */
	public void setProductoMarca(String productoMarca) {
		this.productoMarca = productoMarca;
	}

	public void setProductoUnidadesPorCaja(String productoUnidadesPorCaja) {
		this.productoUnidadesPorCaja = productoUnidadesPorCaja;
	}

	public String getProductoUnidadesPorCaja() {
		return productoUnidadesPorCaja;
	}

	public String getApUbicacion() {
		return apUbicacion;
	}

	public void setApUbicacion(String apUbicacion) {
		this.apUbicacion = apUbicacion;
	}
	
	/**
	 * @return the apTipoAlmacen
	 */
	public int getApTipoAlmacen() {
		return apTipoAlmacen;
	}

	/**
	 * @param apTipoAlmacen the apTipoAlmacen to set
	 */
	public void setApTipoAlmacen(int apTipoAlmacen) {
		this.apTipoAlmacen = apTipoAlmacen;
	}

	/**
	 * @return the apCantidad
	 */
	public int getApCantidad() {
		return apCantidad;
	}

	/**
	 * @param apCantidad the apCantidad to set
	 */
	public void setApCantidad(int apCantidad) {
		this.apCantidad = apCantidad;
	}

	/**
	 * @return the apPrecio
	 */
	public double getApPrecio() {
		return apPrecio;
	}

	/**
	 * @param apPrecio the apPrecio to set
	 */
	public void setApPrecio(double apPrecio) {
		this.apPrecio = apPrecio;
	}

	/**
	 * @return the productoUnidadMedida
	 */
	public String getProductoUnidadMedida() {
		return productoUnidadMedida;
	}

	/**
	 * @param productoUnidadDeMedida the productoUnidadMedida to set
	 */
	public void setProductoUnidadMedida(String productoUnidadDeMedida) {
		this.productoUnidadMedida = productoUnidadDeMedida;
	}

	public String getProductoUnidadEmpaque() {
		return productoUnidadEmpaque;
	}

	public void setProductoUnidadEmpaque(String productoUnidadEmpaque) {
		this.productoUnidadEmpaque = productoUnidadEmpaque;
	}
	
	

	public void setCanPendteEnOtrosPV(int canPendteEnOtrosPV) {
		this.canPendteEnOtrosPV = canPendteEnOtrosPV;
	}

	public int getCanPendteEnOtrosPV() {
		return canPendteEnOtrosPV;
	}
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	@Override
	public int compareTo(EntradaSalidaDetalleQuickView o) {
		if(		apUbicacion   != null && apUbicacion.trim().length()   > 1 &&
				o.apUbicacion != null && o.apUbicacion.trim().length() > 1 ){
			return apUbicacion.compareTo(o.apUbicacion);
		} else {
			return getId().compareTo(o.getId());
		}		
	}
}
