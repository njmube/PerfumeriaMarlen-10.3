package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.EntradaSalidaDetalleDAO;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.dao.SucursalDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.Sucursal;
import com.pmarlen.backend.model.quickviews.ClienteQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaFooter;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.businesslogic.reports.GeneradorImpresionPedidoVenta;
import com.pmarlen.model.Constants;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import javax.servlet.ServletContext;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="editarCompraMB")
@SessionScoped
public class EditarCompraMB extends EditarPedidoVentaMB{
		
	@PostConstruct
	public void init() {
		super.init();
		logger.info("OK init");
	}

	
	public boolean isCrearCompra() {
		crearEntradaSalida=false;
		
		if(entityList!=null && entityList.size()>0 &&
				entradaSalida.getClienteId()!=null && entradaSalida.getClienteId() > 0 && clienteSeleccionado!=null ){
			crearEntradaSalida=true;
		}
		
		return crearEntradaSalida;
	}
	
	@Override
	public String editar(int compraID){
		logger.info("--------------------------------<<<<<< inicio Editar");
		validarSiEstabaEditandoOtro("COMPRA");
		logger.info("compraID="+compraID);		
		try {
			EntradaSalida es4Edit = new EntradaSalida(compraID);
			entradaSalida = EntradaSalidaDAO.getInstance().findBy(es4Edit);
			logger.info("compra="+entradaSalida);
			entityList = EntradaSalidaDAO.getInstance().findAllESDByEntradaSalida(compraID);
			EntradaSalidaDAO.getInstance().actualizaCantidadPendienteParaOtrosES(entityList);

			logger.info("entityList:--------->>>");
			for(EntradaSalidaDetalleQuickView pv:entityList){
				logger.info("\teditar: rowId="+pv.getRowId()+": "+pv.getCantidad()+" X ["+pv.getProductoCodigoBarras()+"]@"+pv.getAlmacenId());
			}
			logger.info("entityList:<<<---------");
		}catch(DAOException de){
			logger.error(de.getMessage());
			entradaSalida = new EntradaSalidaQuickView();
			entradaSalida.setId(0);
			entradaSalidaFooter= new EntradaSalidaFooter();
			entityList = new ArrayList<EntradaSalidaDetalleQuickView>();
		}
		tipoAlmacen = Constants.ALMACEN_PRINCIPAL;
		cantidadAgregarBusqueda = 1;
		cantidadAgregarCodigo   = 1;
		
		//getClientesList();
		onClienteListChange();
		
		getFormaDePagoList();
		onFormaDePagoListChange();
		
		getMetodoDePagoList();
		onMetodoDePagoListChange();
		
		getDescuentoEspacialList();
		
		crearEntradaSalida = false;	
		cadenaBusqueda = null;
		resultadoBusqueda = null;
		resultadoBusquedaList = null;
		conservarBusqueda = true;
		
		actualizarTotales();
		hayCambios = false;
		logger.info("fin Editar");
		logger.info("-------------------------------->>>>>>> fin Editar");
		return "/pages/editarCompra";
	}

	public EntradaSalidaQuickView getCompra() {
		return entradaSalida;
	}
	
	public void guardar() {
		try{						
			logger.info("entradaSalida.id:"+entradaSalida.getId());
			logger.info("entradaSalida.cfdVentaId:"+entradaSalida.getCfdId());
			EntradaSalidaDAO.getInstance().update(entradaSalida,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Guardar.");
			
			reset();
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"GUARDAR",  "SE ACTUALIZÓ CORRECTAMENTE LA COMPRA #"+entradaSalida.getId()+".") );
		}catch(Exception e){
			logger.error("->guardar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"GUARDAR",  "HUBO UN ERROR AL GUARDAR.") );
		}

	}
	
	public void verificar() {
		try{			
			logger.info("entradaSalida.id:"+entradaSalida.getId());
			EntradaSalidaDAO.getInstance().verificar(entradaSalida,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Verificar.");
			
			reset();
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"VERIFICAR",  "SE VERIFICÓ CORRECTAMENTE LA COMPRA #"+entradaSalida.getId()+".") );
		}catch(Exception e){
			logger.error("->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"VERFICAR",  "HUBO UN ERROR AL VERIFICAR.") );
		}		
	}
	
	public void surtir() {
		try{			
			logger.info("entradaSalida.id:"+entradaSalida.getId());
			EntradaSalidaDAO.getInstance().surtir(entradaSalida,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Surtir.");

			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"SURTIR",  "SE SURTIÓ CORRECTAMENTE LA COMPRA #"+entradaSalida.getId()+".") );			
			reset();
		}catch(Exception e){
			logger.error("->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"SURTIR",  "HUBO UN ERROR AL SURTIR.") );
		}		
	}
	
	public void cancelar() {
		try{			
			logger.info("entradaSalida.id:"+entradaSalida.getId());
			//EntradaSalidaDAO.getInstance().surtir(entradaSalida,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("CANCELAR NO IMPLEMENTADO, solo resetea");
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"CANCELAR",  "CANCELAR PENDIENTE COMPRA #"+entradaSalida.getId()+".") );			
			reset();
		}catch(Exception e){
			logger.error("->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"SURTIR",  "HUBO UN ERROR AL CANCELAR.") );
		}		
	}
	
	public void cancelarCambios() {
		try{			
			logger.info("entradaSalida.id:"+entradaSalida.getId());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"CANCELAR CAMBIOS",  "SE CANCELARON LOS CAMBIOS Y RECARGÓ LA COMPRA #"+entradaSalida.getId()+".") );
			reset();
		}catch(Exception e){
			logger.error("->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"CANCELAR CAMBIOS",  "HUBO UN ERROR AL CANCELAR CAMBIOS.") );
		}		
	}

	public EntradaSalidaFooter getCompraFooter() {
		return super.getPedidoVentaFooter(); //To change body of generated methods, choose Tools | Templates.
	}
	
}
	