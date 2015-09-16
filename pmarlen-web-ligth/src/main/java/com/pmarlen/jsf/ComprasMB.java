package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Cliente;
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

import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.ReorderEvent;

@ManagedBean(name="comprasMB")
@SessionScoped
public class ComprasMB  {
	private transient static Logger logger = Logger.getLogger(ComprasMB.class.getSimpleName());
	
	@ManagedProperty(value = "#{editarCompraMB}")
	private EditarCompraMB editarCompraMB;
	
	private EntradaSalidaLazyDataModel lazyModel;	private int viewRows;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");
		lazyModel = new EntradaSalidaLazyDataModel(Constants.TIPO_MOV_ENTRADA_ALMACEN_COMPRA,1,true);
		viewRows = 25;
	}
	
	public void refrescar(){
		logger.info("->refrescar:");
		lazyModel = new EntradaSalidaLazyDataModel(Constants.TIPO_MOV_ENTRADA_ALMACEN_COMPRA,1,true);
	}

	public void setEditarCompraMB(EditarCompraMB editarCompraMB) {
		this.editarCompraMB = editarCompraMB;
	}
	
	public void editar(int id){
		logger.info("->editar:id="+id);
		editarCompraMB.editar(id);
	}
	public String editarPedidoAction(int pedidoVentaId){
		logger.info("->editarPedidoAction:pedidoVentaId="+pedidoVentaId);
		return editarCompraMB.editar(pedidoVentaId);
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
