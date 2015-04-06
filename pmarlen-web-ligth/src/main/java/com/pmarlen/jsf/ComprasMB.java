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
import java.util.logging.Level;
import java.util.logging.Logger;
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
	
	ArrayList<EntradaSalidaQuickView> compras;
	private int viewRows;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");
		try {
			compras = EntradaSalidaDAO.getInstance().findAllActiveDevs();
			logger.info("->refrescar:compras.size()="+compras.size());
			for(EntradaSalidaQuickView x:compras){
				logger.finer("->x="+x.getId());
			}
		}catch(DAOException de){
			compras = new ArrayList<EntradaSalidaQuickView>();
			logger.severe(de.getMessage());
		}
		viewRows = 25;
	}
	
	public void refrescar(){
		logger.info("->refrescar:");
		try {
			compras = EntradaSalidaDAO.getInstance().findAllActiveDevs();
			if(compras != null){
				logger.info("->refrescar:compras.size()="+compras.size());
				for(EntradaSalidaQuickView x:compras){
					logger.finer("->x="+x.getId());
				}
			}
		} catch (DAOException ex) {
			logger.severe(ex.getMessage());
		}
		
	}

	public void setEditarCompraMB(EditarCompraMB editarCompraMB) {
		this.editarCompraMB = editarCompraMB;
	}

	public ArrayList<EntradaSalidaQuickView> getCompras() {
		logger.fine("->getCompraes");
		return compras;
	}
	
	public String editarCompra(int pedidoVentaId){
		logger.config("->editarPedido:pedidoVentaId="+pedidoVentaId);
		editarCompraMB.editar(pedidoVentaId);
		return "/pages/editarCompra";
	}
	
	public void onEditarPedido(int pedidoVentaId){
		logger.config("->editarPedido:pedidoVentaId="+pedidoVentaId);
		editarCompraMB.editar(pedidoVentaId);
	}
	
	public int getSizeList(){
		logger.fine("->getSizeList()");
		return getCompras().size();
	}

	public int getViewRows() {
		logger.fine("->getViewRows()");
		return viewRows;
	}

	public void setViewRows(int viewRows) {
		logger.fine("->setViewRows("+viewRows+")");
		this.viewRows = viewRows;
	}
	public String getImporteMoneda(double f){
		return Constants.getImporteMoneda(f);
	}

}
