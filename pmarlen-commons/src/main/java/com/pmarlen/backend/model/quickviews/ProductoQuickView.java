/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.Multimedio;
import com.pmarlen.backend.model.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class ProductoQuickView extends Producto {
	private List<Multimedio> multimedios;
	private int almacenId;
	private double precio;
	private Multimedio firstMultimedio;
	
	public ProductoQuickView() {
		multimedios = new ArrayList<Multimedio>();
	}

	public List<Multimedio> getMultimedios() {
		return multimedios;
	}

	/**
	 * @return the almacenId
	 */
	public int getAlmacenId() {
		return almacenId;
	}

	/**
	 * @param almacenId the almacenId to set
	 */
	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public void addMultimedio(Multimedio m){
		if(this.multimedios.size() == 0){
			firstMultimedio = m;
		}
		multimedios.add(m);
	}

	public Multimedio getFirstMultimedio() {
		return firstMultimedio;
	}
	
	
}
