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
	}
	
	public void calculaTotalesDesde(EntradaSalida pv,ArrayList<? extends EntradaSalidaDetalle> dvpList){
		reset();
		double importeReg = 0.0;
		double importeRegNG = 0.0;
		for(EntradaSalidaDetalle dvp: dvpList){
			importeReg         = dvp.getCantidad()*dvp.getPrecioVenta();
			importeIVA        += importeReg - (importeReg / Constants.MAS_IVA);
			importeRegNG       = (importeReg / Constants.MAS_IVA);
			subTotalNoGrabado += importeRegNG;
			subTotalBruto     += importeReg;
			
		}
		descuentoCalculado = 0;
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
	 * @param subTotalNoGrabado the subTotalNoGrabado to set
	 */
	public void setSubTotalNoGrabado(Double subTotalNoGrabado) {
		this.subTotalNoGrabado = subTotalNoGrabado;
	}

	/**
	 * @return the subTotalBruto
	 */
	public Double getSubTotalBruto() {
		return subTotalBruto;
	}

	/**
	 * @param subTotalBruto the subTotalBruto to set
	 */
	public void setSubTotalBruto(Double subTotalBruto) {
		this.subTotalBruto = subTotalBruto;
	}

	/**
	 * @return the descuentoCalculado
	 */
	public Integer getDescuentoCalculado() {
		return descuentoCalculado;
	}

	/**
	 * @param descuentoCalculado the descuentoCalculado to set
	 */
	public void setDescuentoCalculado(Integer descuentoCalculado) {
		this.descuentoCalculado = descuentoCalculado;
	}

	/**
	 * @return the descuentoExtra
	 */
	public Integer getDescuentoExtra() {
		return descuentoExtra;
	}

	/**
	 * @param descuentoExtra the descuentoExtra to set
	 */
	public void setDescuentoExtra(Integer descuentoExtra) {
		this.descuentoExtra = descuentoExtra;
	}

	/**
	 * @return the descuentoAplicado
	 */
	public Integer getDescuentoAplicado() {
		return descuentoAplicado;
	}

	/**
	 * @param descuentoAplicado the descuentoAplicado to set
	 */
	public void setDescuentoAplicado(Integer descuentoAplicado) {
		this.descuentoAplicado = descuentoAplicado;
	}

	/**
	 * @return the importeDescuentoCalculado
	 */
	public Double getImporteDescuentoCalculado() {
		return importeDescuentoCalculado;
	}

	/**
	 * @param importeDescuentoCalculado the importeDescuentoCalculado to set
	 */
	public void setImporteDescuentoCalculado(Double importeDescuentoCalculado) {
		this.importeDescuentoCalculado = importeDescuentoCalculado;
	}

	/**
	 * @return the importeDescuentoExtra
	 */
	public Double getImporteDescuentoExtra() {
		return importeDescuentoExtra;
	}

	/**
	 * @param importeDescuentoExtra the importeDescuentoExtra to set
	 */
	public void setImporteDescuentoExtra(Double importeDescuentoExtra) {
		this.importeDescuentoExtra = importeDescuentoExtra;
	}

	/**
	 * @return the importeDescuentoAplicado
	 */
	public Double getImporteDescuentoAplicado() {
		return importeDescuentoAplicado;
	}

	/**
	 * @param importeDescuentoAplicado the importeDescuentoAplicado to set
	 */
	public void setImporteDescuentoAplicado(Double importeDescuentoAplicado) {
		this.importeDescuentoAplicado = importeDescuentoAplicado;
	}

	/**
	 * @return the importeIVA
	 */
	public Double getImporteIVA() {
		return importeIVA;
	}

	/**
	 * @param importeIVA the importeIVA to set
	 */
	public void setImporteIVA(Double importeIVA) {
		this.importeIVA = importeIVA;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

}
