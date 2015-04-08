/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.AlmacenProducto;

/**
 *
 * @author alfredo
 */
public class AlmacenProductoQuickView extends AlmacenProducto{

	private String almacenTipoDescripcion;
	
	private String productoNombre;
	
	private String productoPresentacion;
	
	private String productoIndustria;

	private String productoMarca;
    
    private String productoLinea;
    
	private String productoContenido;
	
	private String productoUnidadMedida;
	
	private String productoUnidadEmpaque;
	
	private Integer rowId;

	public AlmacenProductoQuickView() {
	}

	
	/**
	 * @return the almacenTipoDescripcion
	 */
	public String getAlmacenTipoDescripcion() {
		return almacenTipoDescripcion;
	}

	/**
	 * @param almacenTipoDescripcion the almacenTipoDescripcion to set
	 */
	public void setAlmacenTipoDescripcion(String almacenTipoDescripcion) {
		this.almacenTipoDescripcion = almacenTipoDescripcion;
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
	 * @return the productoUnidadMedida
	 */
	public String getProductoUnidadMedida() {
		return productoUnidadMedida;
	}

	/**
	 * @param productoUnidadMedida the productoUnidadMedida to set
	 */
	public void setProductoUnidadMedida(String productoUnidadMedida) {
		this.productoUnidadMedida = productoUnidadMedida;
	}

	/**
	 * @return the productoUnidadEmpaque
	 */
	public String getProductoUnidadEmpaque() {
		return productoUnidadEmpaque;
	}

	/**
	 * @param productoUnidadEmpaque the productoUnidadEmpaque to set
	 */
	public void setProductoUnidadEmpaque(String productoUnidadEmpaque) {
		this.productoUnidadEmpaque = productoUnidadEmpaque;
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

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getRowId() {
		return rowId;
	}
	
	public String getUbicacionRenderedColor(){
		if(this.getUbicacion() != null){
			if(this.getUbicacion().startsWith("Rack A")){
				return "ubucacion-A";
			} else if(this.getUbicacion().startsWith("Tarimas B")){
				return "ubucacion-B";
			} else if(this.getUbicacion().startsWith("Tarimas C")){
				return "ubucacion-C";
			}
		}
		return"";
	}
	
}
