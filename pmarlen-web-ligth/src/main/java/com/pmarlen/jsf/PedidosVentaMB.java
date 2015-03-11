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

@ManagedBean(name="pedidosVentaMB")
@SessionScoped
public class PedidosVentaMB  {
	private transient static Logger logger = Logger.getLogger(PedidosVentaMB.class.getSimpleName());
	
	@ManagedProperty(value = "#{editarEntradaSalidaMB}")
	private EditarEntradaSalidaMB editarEntradaSalidaMB;	
	
	ArrayList<EntradaSalidaQuickView> pedidosVentas;
	private int viewRows;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");
		try {
			pedidosVentas = EntradaSalidaDAO.getInstance().findAllActive();
		}catch(DAOException de){
			pedidosVentas = new ArrayList<EntradaSalidaQuickView>();
			logger.severe(de.getMessage());
		}
		viewRows = 25;
	}
	
	public void refrescar(){
		logger.info("->refrescar:");
		try {
			pedidosVentas = EntradaSalidaDAO.getInstance().findAllActive();
			if(pedidosVentas != null){
				logger.config("->refrescar:pedidosVentas.size()="+pedidosVentas.size());
			}
		} catch (DAOException ex) {
			logger.severe(ex.getMessage());
		}
		
	}

	public void setEditarEntradaSalidaMB(EditarEntradaSalidaMB editarEntradaSalidaMB) {
		this.editarEntradaSalidaMB = editarEntradaSalidaMB;
	}

	public ArrayList<EntradaSalidaQuickView> getPedidosVentas() {
		logger.fine("->getPedidosVentas");
		return pedidosVentas;
	}
	
	public String editarPedido(int pedidoVentaId){
		logger.config("->editarPedido:pedidoVentaId="+pedidoVentaId);
		editarEntradaSalidaMB.editar(pedidoVentaId);
		return "/pages/editarEntradaSalida";
	}
	
	public void onEditarPedido(int pedidoVentaId){
		logger.config("->editarPedido:pedidoVentaId="+pedidoVentaId);
		editarEntradaSalidaMB.editar(pedidoVentaId);
	}
	
	public int getSizeList(){
		logger.fine("->getSizeList()");
		return getPedidosVentas().size();
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
