package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.EntradaSalidaDetalleDAO;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.ClienteQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaFooter;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.model.Constants;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.print.attribute.standard.Severity;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@ManagedBean(name="nuevaCompraMB")
@SessionScoped
public class NuevaCompraMB extends PedidoVentaMB{	
	
	@ManagedProperty(value = "#{sessionUserMB}")
	private SessionUserMB sessionUserMB;
		
	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}	
	
	@PostConstruct
	@Override
	public void init() {
		super.init();
		autorizaDescuento = false;
	}

	@Override
	public String reset() {
		logger.debug("->EntradaSalidaDetalleMB: rest.");
		init();
		return "/pages/nuevaDevolucion";
	}
	
	public EntradaSalida getNuevaCompra() {
		return entradaSalida;
	}

	public EntradaSalidaFooter getNuevaCompraFooter() {
		return entradaSalidaFooter;
	}

	public boolean isCrearCompra() {
		crearES=false;
		
		if(entityList!=null && entityList.size()>0 ){
			crearES=true;
		}
		
		return crearES;
	}
	
	@Override
	public void guardar() {
		logger.info("->guardar:");
		try{
			entradaSalida.setCaja(1);
			entradaSalida.setFactorIva(Constants.IVA);
			entradaSalida.setNumDeCuenta(null);
			entradaSalida.setNumeroTicket("0");
			entradaSalida.setSucursalId(sessionUserMB.getSucursalId());
			entradaSalida.setUsuarioEmailCreo(sessionUserMB.getUsuarioAuthenticated().getEmail());
			entradaSalida.setAutorizaDescuento(autorizaDescuento?1:0);
						
			EntradaSalidaDAO.getInstance().insertDevolucionVenta(entradaSalida,entityList);
			logger.info("->guardar:entradaSalida.id:"+entradaSalida.getId());
			
			esFinalizado = true;
			cadenaBusqueda = null;
			resultadoBusqueda = null;
			resultadoBusquedaList = null;
			conservarBusqueda = false;
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"GUARDAR",  "SE CREO LA COMPRA #"+entradaSalida.getId()) );
		}catch(DAOException de){
			logger.error(de.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"GUARDAR",  "OCURRIÓ UN ERROR AL GUARDAR.") );
		}
	}
	
	public boolean isCompraFinalizada() {
		return esFinalizado;
	}
}
