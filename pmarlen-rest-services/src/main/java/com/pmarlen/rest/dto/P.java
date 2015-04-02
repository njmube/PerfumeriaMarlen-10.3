/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.rest.dto;

import java.io.Serializable;

/**
 *
 * @author alfredo
 */
public class P implements Serializable{
    
    /**
    * codigo barras
    */
    private String cb;
    
    /**
    * industria
    */
    private String i;
    
    /**
    * linea
    */
    private String l;
    
    /**
    * marca
    */
    private String m;
    
    /**
    * nombre
    */
    private String n;
    
    /**
    * presentacion
    */
    private String p;
    
    /**
    * abrebiatura
    */
    private String a;
    
    /**
    * unidades x caja
    */
    private int uc;
    
    /**
    * contenido
    */
    private String c;
    
    /**
    * unidad medida
    */
    private String um;
    
    /**
    * unidad empaque
    */
    private String ue;

	/**
	 * Almacen 1ra costo
	 */
    private int    a1c;
	
	/**
	 * Almacen 1ra precio
	 */
    private double a1p;
	
	/**
	 * Almacen 1ra ubicacion
	 */
    private String a1u;

	/**
	 * Almacen Opo costo
	 */
    private int    aOc;
	
	/**
	 * Almacen Opo precio
	 */
    private double aOp;
	
	/**
	 * Almacen Opo ubicacion
	 */
    private String aOu;

	/**
	 * Almacen Reg costo
	 */
    private int    aRc;
	
	/**
	 * Almacen Reg precio
	 */
    private double aRp;
	
	/**
	 * Almacen Reg ubicacion
	 */
    private String aRu;	
}
