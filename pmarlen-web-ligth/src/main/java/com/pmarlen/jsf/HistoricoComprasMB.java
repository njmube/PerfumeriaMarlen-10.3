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

@ManagedBean(name="historicoComprasMB")
@SessionScoped
public class HistoricoComprasMB  {
	private transient static Logger logger = Logger.getLogger(HistoricoComprasMB.class.getSimpleName());
	
	@ManagedProperty(value = "#{editarCompraMB}")
	private EditarCompraMB editarCompraMB;
	
	ArrayList<EntradaSalidaQuickView> compras;
	private int viewRows;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");
		viewRows = 25;
	}
	
	public void refrescar(){
		logger.info("->refrescar:");
		compras = null;		
	}

	public void setEditarCompraMB(EditarCompraMB editarCompraMB) {
		this.editarCompraMB = editarCompraMB;
	}

	public ArrayList<EntradaSalidaQuickView> getCompras() {
		logger.debug("->getCompras");
		if(compras == null){
			try {
				compras = EntradaSalidaDAO.getInstance().findAllHistoricoCompras();
				logger.info("->refrescar:compras.size()="+compras.size());
				if(compras != null){
					logger.debug("compras.size()="+compras.size());				 
				}
			}catch(DAOException de){
				compras = new ArrayList<EntradaSalidaQuickView>();
				logger.error(de.getMessage());
			}
		}
		return compras;
	}
	
	public void editarCompra(int pedidoVentaId){
		logger.debug("->editarPedido:pedidoVentaId="+pedidoVentaId);
		editarCompraMB.editar(pedidoVentaId);
	}
	
	public int getSizeList(){
		logger.debug("->getSizeList()");
		return getCompras().size();
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

}
