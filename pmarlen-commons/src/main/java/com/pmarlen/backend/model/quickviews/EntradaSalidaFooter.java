package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.model.Constants;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author alfredo
 */
public class EntradaSalidaFooter implements Serializable{
	private Double subTotalNoGrabado;
	private Double subTotalBruto;
	private Integer descuentoCalculado;
	private Integer descuentoExtra;
	private Integer descuentoAplicado;
	private Double importeDescuentoCalculado;
	private Double importeDescuentoExtra;
	private Double importeDescuentoAplicado;
	private Double importeIVA;
	private Double total;
	private Integer totalUnidades;

	public EntradaSalidaFooter() {
		reset();
	}

	public void reset(){
		this.subTotalNoGrabado = 0.0;
		this.subTotalBruto = 0.0;
		this.descuentoCalculado=0;
		this.descuentoExtra=0;
		this.descuentoAplicado=0;
		this.importeDescuentoCalculado = 0.0;
		this.importeDescuentoExtra = 0.0;
		this.importeDescuentoAplicado = 0.0;
		this.importeIVA = 0.0;
		this.total = 0.0;
		this.totalUnidades = 0;
	}
	
	public void calculaTotalesDesde(EntradaSalida pv,ArrayList<? extends EntradaSalidaDetalle> dvpList){
		reset();
		double importeReg = 0.0;
		double importeRegNG = 0.0;
		totalUnidades = 0;
		for(EntradaSalidaDetalle dvp: dvpList){
			totalUnidades     += dvp.getCantidad();
			importeReg         = dvp.getCantidad()*dvp.getPrecioVenta();
			importeIVA        += importeReg - (importeReg / Constants.MAS_IVA);
			importeRegNG       = (importeReg / Constants.MAS_IVA);
			subTotalNoGrabado += importeRegNG;
			subTotalBruto     += importeReg;
			
		}
		descuentoCalculado = 0;
		descuentoExtra = 0;
		descuentoAplicado =0;
		importeDescuentoExtra = 0.0;
		importeDescuentoAplicado = 0.0;
		if(pv.getAutorizaDescuento()!=null && pv.getAutorizaDescuento().intValue()==1){			
			if (subTotalBruto >= 5000 && subTotalBruto < 10000) {				
				descuentoCalculado = 5;
				importeDescuentoCalculado = (subTotalBruto * descuentoCalculado)/100.0;
			} else if (subTotalBruto >= 10000) {
				descuentoCalculado = 10;
				importeDescuentoCalculado = (subTotalBruto * descuentoCalculado)/100.0;
			}
			descuentoExtra = pv.getPorcentajeDescuentoExtra();
			if(descuentoExtra == null){
				descuentoExtra = 0;
			}
			importeDescuentoExtra    = (subTotalBruto * descuentoExtra)/100.0;
			descuentoAplicado        = descuentoCalculado + descuentoExtra;		
			importeDescuentoAplicado = importeDescuentoCalculado + importeDescuentoExtra;
		} 
		pv.setPorcentajeDescuentoCalculado(descuentoCalculado);
		importeDescuentoExtra = (subTotalBruto * descuentoExtra)/100.0;

		total = subTotalNoGrabado + importeIVA - importeDescuentoAplicado ;		
	}

	/**
	 * @return the subTotalNoGrabado
	 */
	public Double getSubTotalNoGrabado() {
		return subTotalNoGrabado;
	}

	/**
	 * @return the subTotalBruto
	 */
	public Double getSubTotalBruto() {
		return subTotalBruto;
	}

	/**
	 * @return the descuentoCalculado
	 */
	public Integer getDescuentoCalculado() {
		return descuentoCalculado;
	}

	/**
	 * @return the descuentoExtra
	 */
	public Integer getDescuentoExtra() {
		return descuentoExtra;
	}

	/**
	 * @return the descuentoAplicado
	 */
	public Integer getDescuentoAplicado() {
		return descuentoAplicado;
	}

	/**
	 * @return the importeDescuentoCalculado
	 */
	public Double getImporteDescuentoCalculado() {
		return importeDescuentoCalculado;
	}

	/**
	 * @return the importeDescuentoExtra
	 */
	public Double getImporteDescuentoExtra() {
		return importeDescuentoExtra;
	}

	/**
	 * @return the importeDescuentoAplicado
	 */
	public Double getImporteDescuentoAplicado() {
		return importeDescuentoAplicado;
	}

	/**
	 * @return the importeIVA
	 */
	public Double getImporteIVA() {
		return importeIVA;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @return the totalUnidades
	 */
	public Integer getTotalUnidades() {
		return totalUnidades;
	}

}
