package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.jsf.model.EntradaSalidaLazyDataModel;
import com.pmarlen.model.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import org.primefaces.event.ReorderEvent;

@ManagedBean(name="historicoPedidosVentaMB")
@SessionScoped
public class HistoricoPedidosVentaMB {
	private transient static Logger logger = Logger.getLogger(HistoricoPedidosVentaMB.class.getName());
	
	private EntradaSalidaLazyDataModel lazyModel;
	
	@ManagedProperty(value = "#{editarPedidoVentaMB}")
	private EditarPedidoVentaMB editarPedidoVentaMB;	
	
	//ArrayList<EntradaSalidaQuickView> pedidosVentas;
	private int viewRows;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");
		lazyModel = new EntradaSalidaLazyDataModel(Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA,1,false);
		viewRows = 25;
	}
	
	public void refrescar(){
		logger.info("->refrescar:");
		lazyModel = new EntradaSalidaLazyDataModel(Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA,1,false);
	}

	public void setEditarPedidoVentaMB(EditarPedidoVentaMB editarPedidoVentaMB) {
		this.editarPedidoVentaMB = editarPedidoVentaMB;
	}
	
	public void editar(int id){
		logger.info("->editar:id="+id);
		editarPedidoVentaMB.editar(id);
	}
	public String editarPedidoAction(int pedidoVentaId){
		logger.info("->editarPedidoAction:pedidoVentaId="+pedidoVentaId);
		return editarPedidoVentaMB.editar(pedidoVentaId);
	}
		
	public int getSizeList(){
		logger.debug("->getSizeList()");
		return lazyModel.getRowCount();
	}

	public int getViewRows() {
		logger.debug("->getViewRows()");
		return viewRows;
	}

	public void setViewRows(int viewRows) {
		logger.debug("->setViewRows("+viewRows+")");
		this.viewRows = viewRows;
	}

	public String getImporteMoneda(double f){
		return Constants.getImporteMoneda(f);
	}

	public EntradaSalidaLazyDataModel getLazyModel() {
		return lazyModel;
	}
	
}
