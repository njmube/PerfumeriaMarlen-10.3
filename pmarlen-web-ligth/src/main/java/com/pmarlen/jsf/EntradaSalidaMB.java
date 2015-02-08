package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.EntradaSalidaDetalleDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaFooter;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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

@ManagedBean(name="entradaSalidaMB")
@SessionScoped
public class EntradaSalidaMB implements Serializable{
	private static Logger logger = Logger.getLogger("EntradaSalidaMB");
	private List<SelectItem> resultadoBusquedaList;
	private static final int LONG_MIN_DESC_CTE = 60;
	private static List<SelectItem> tipoAlmacenList;
	private ArrayList<EntradaSalidaDetalleQuickView> entityList;
	private EntradaSalidaDetalleQuickView selectedEntity;
	private EntradaSalida entradaSalida;
	private EntradaSalidaFooter entradaSalidaFooter;
	private ArrayList<EntradaSalidaDetalleQuickView> resultadoBusqueda;
	private EntradaSalidaDetalleQuickView resultadoBusquedaSI;
	
	private String cadenaBusqueda;
	private int tipoAlmacen;
	private String resultadoBusquedaSelected;
	private int cantidadAgregarBusqueda;
	boolean conservarBusqueda;
	
	private int cantidadAgregarCodigo;
	private String codigo;
	
	private boolean tablaExpandida = false;
	private boolean crearPedido;
	private boolean pedidoFinalizado = false;
	
	@ManagedProperty(value = "#{sessionUserMB}")
	private SessionUserMB sessionUserMB;

	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}
	
	@PostConstruct
	public void init() {
		logger.info("->init.");
		entradaSalida = new EntradaSalida();
		entradaSalidaFooter= new EntradaSalidaFooter();
		entityList = new ArrayList<EntradaSalidaDetalleQuickView>();
		tipoAlmacen = Constants.ALMACEN_PRINCIPAL;
		cantidadAgregarBusqueda = 1;
		cantidadAgregarCodigo   = 1;
		
		clienteSeleccionado = null;
		crearPedido = false;	
		cadenaBusqueda = null;
		resultadoBusqueda = null;
		resultadoBusquedaList = null;
		conservarBusqueda = false;
		pedidoFinalizado = false;
	}

	public String reset() {
		logger.info("->EntradaSalidaDetalleMB: rest.");
		init();
		return "/pages/cliente";
	}
	
	public List<EntradaSalidaDetalleQuickView> getEntityList() {
		return entityList;
	}
	
	public int getSizeList(){
		if(entityList==null){
			return 0;
		}
		return entityList.size();
	}


	public int getTipoAlmacen() {
		return tipoAlmacen;
	}

	public String getCadenaBusqueda() {
		return cadenaBusqueda;
	}

	public void setCadenaBusqueda(String cadenaBusqueda) {
		this.cadenaBusqueda = cadenaBusqueda;
	}
	
	public boolean isPuedeBuscar(){
		if(cadenaBusqueda!=null && cadenaBusqueda.trim().length()>3) {
			return true;
		} else {
			return false;
		}
	}
	

	public void buscarXBoton() {
		buscarXCadena();
	}

	public void cadenaBusquedaChanged(ValueChangeEvent e) {
		logger.info("->cadenaBusquedaChanged: e:"+e.getNewValue());
	}
	public void codigoChanged(ValueChangeEvent e) {
		logger.info("->codigoChanged: e:"+e.getNewValue()+", cantidadAgregarCodigo="+cantidadAgregarCodigo);
	}
	
	public void buscarXCadena() {
		logger.info("->buscarXCadena:tipoAlmacen="+tipoAlmacen+", cadenaBusqueda="+cadenaBusqueda);
		if(cadenaBusqueda.trim().length()>3) {	
			try {
				resultadoBusqueda = ProductoDAO.getInstance().findAllExclusiveByDesc(this.tipoAlmacen, cadenaBusqueda);
				resultadoBusquedaSI = null;
				if(resultadoBusqueda != null && resultadoBusqueda.size()>0){
					FacesContext context = FacesContext.getCurrentInstance();         
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"BUSCAR PRODUCTOS",  "SE ENCONTRARÓN "+resultadoBusqueda.size()+" PRODUCTO"+(resultadoBusqueda.size()>1?"S":"") ));

					logger.info("->buscar:findAllExclusiveByDesc:OK, resultadoBusqueda.size()="+resultadoBusqueda.size());
					resultadoBusquedaSI = resultadoBusqueda.get(0);
				} else {
					FacesContext context = FacesContext.getCurrentInstance();         
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"BUSACAR PRODUCTOS",  "NO SE SE ENCONTRÓ EL NADA CON ESA DESCRIPCIÓN.") );		
				}
			}catch(DAOException de){
				logger.severe(de.getMessage());
				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"BUSACAR PRODUCTOS",  "OCURRIÓ UN ERROR AL BUSCAR") );		
			}
			
			resultadoBusquedaList = null;
			cantidadAgregarBusqueda = 1;	
			cantidadAgregarCodigo   = 1;
		}
	}
	
	public void buscarXCodigo() {
		logger.info("->buscarXCodigo:cantidadAgregarCodigo="+cantidadAgregarCodigo+", codigo="+codigo);
		EntradaSalidaDetalleQuickView dvpAdd = null;
		try {
			dvpAdd = ProductoDAO.getInstance().findByCodigo(tipoAlmacen,codigo);

			logger.info("->buscarXCodigo:dvpAdd="+dvpAdd);


			if(dvpAdd != null) {
				dvpAdd.setCantidad(cantidadAgregarCodigo);
				logger.info("->buscarXCodigo:OK +"+cantidadAgregarCodigo+" x "+dvpAdd);
				entityList.add(dvpAdd);

				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"AGREGAR PRODUCTO",  "SE ENCONTRÓ Y SE AGREGÓ "+cantidadAgregarCodigo+" x ["+codigo+"] AL DETALLE.") );

				if(!conservarBusqueda){
					resultadoBusqueda = null;
					resultadoBusquedaList = null;
					cadenaBusqueda = null;
				}
				codigo = "";
				cantidadAgregarBusqueda = 1;
				cantidadAgregarCodigo   = 1;
				actualizarTotales();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"AGREGAR PRODUCTO",  "NO SE SE ENCONTRÓ EL CODIGO ["+codigo+"].") );

				codigo = "";
				cantidadAgregarBusqueda = 1;
				cantidadAgregarCodigo   = 1;
			}
		}catch(DAOException de){
			logger.severe(de.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"AGREGAR PRODUCTO",  "OCURRIÓ UN ERROR AL BUSCAR.") );

			codigo = "";
			cantidadAgregarBusqueda = 1;
			cantidadAgregarCodigo   = 1;
		}
	}
	
	public void cantidadDetalleCambio(long cambioRowId){
		logger.info("->cantidadDetalleCambio:cambioRowId="+cambioRowId);
		actualizarTotales();
	}
	
	public void cantidadDetalleChanged(ValueChangeEvent event) {
		int cantidadChanged = (Integer) event.getNewValue();
		logger.info("->updateCantidad:cantidadChanged="+cantidadChanged);
		actualizarTotales();
	}
	
	public void deleteRow(long deleteRowId){
		logger.info("->deleteRow:deleteRowId="+deleteRowId);
		int i=0;
		int indexDelete=-1;
		int cantidadEliminada=0;
		String codigoEliminado="";
		for(EntradaSalidaDetalleQuickView pv:entityList){
			logger.info("->deleteRow:\tdelete? "+pv.getRowId()+"=="+deleteRowId);
			if(pv.getRowId()==deleteRowId){
				cantidadEliminada= pv.getCantidad();
				codigoEliminado = pv.getProductoCodigoBarras();
				indexDelete = i;
				break;
			}
			i++;
		}
		if(indexDelete >=0) {
			entityList.remove(indexDelete);
			logger.info("->deleteRow:delete index:"+indexDelete);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ELIMINAR PRODUCTO",  "SE ELIMINÓ CORRECTAMENTE "+cantidadEliminada+" x ["+codigoEliminado+"].") );
		
			if(!conservarBusqueda){
				resultadoBusqueda = null;
				resultadoBusquedaList = null;
				cadenaBusqueda = null;
			}
			codigo = "";
			cantidadAgregarBusqueda = 1;
			cantidadAgregarCodigo   = 1;
			actualizarTotales();
		}
	}
		
	public EntradaSalidaDetalleQuickView getResultadoBusquedaSI(){
		return resultadoBusquedaSI;
	}
		

	public void setResultadoBusquedaSelected(String resultadoBusquedaSelected) {
		logger.info("->setResultadoBusquedaSelected("+resultadoBusquedaSelected+")");
		this.resultadoBusquedaSelected = resultadoBusquedaSelected;
		
		for(EntradaSalidaDetalleQuickView x:resultadoBusqueda){
			if(x.getProductoCodigoBarras().equals(resultadoBusquedaSelected)){
				resultadoBusquedaSI = x;
				break;
			}
		}		
	}

	public String getResultadoBusquedaSelected() {
		return resultadoBusquedaSelected;
	}

	public void setCantidadAgregarBusqueda(int cantidadAgregarBusqueda) {
		this.cantidadAgregarBusqueda = cantidadAgregarBusqueda;
	}
	

	public int getCantidadAgregarBusqueda() {
		return cantidadAgregarBusqueda;
	}

	public boolean isConservarBusqueda() {
		return conservarBusqueda;
	}
	
	public void setConservarBusqueda(boolean conservarBusqueda){
		this.conservarBusqueda = conservarBusqueda;
	}
	
	public void conservarBusquedaChanged(){
		logger.info("->conservarBusquedaChanged:conservarBusqueda="+conservarBusqueda);
	}
	
	public void onRowReorder(ReorderEvent event) {
		logger.info("->onRowReorder:From: " + event.getFromIndex() + ", To:" + event.getToIndex());
		int i=0;
		for(EntradaSalidaDetalleQuickView d:entityList){
			logger.info("->onRowReorder["+(i++)+"]:\t + "+d.getCantidad()+" ["+d.getProductoCodigoBarras()+"]");
		}
        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public List<SelectItem> getResultadoBusqueda() {
		if (resultadoBusquedaList == null) {
			resultadoBusquedaList = new ArrayList<SelectItem>();
			if(resultadoBusqueda != null){
				for(EntradaSalidaDetalleQuickView pv:resultadoBusqueda){
					resultadoBusquedaList.add(new SelectItem(pv.getProductoCodigoBarras(),pv.toStringShorten()));			
				}
			}			
		}
		return resultadoBusquedaList;
	}
	
	ArrayList<Cliente> clientes;
	ArrayList<SelectItem> clientesList;
	Cliente clienteSeleccionado;

	public ArrayList<Cliente> getClientes() {
		if(clientes == null){
			try {
				clientes = ClienteDAO.getInstance().findAll();
			}catch(DAOException de){
				logger.severe(de.getMessage());
				clientes = new ArrayList<Cliente>();
			}
		}
		return clientes;
	}
	
	
	public List<SelectItem> getClientesList() {
		if(clientesList == null){
			clientesList = new ArrayList<SelectItem>();
			clientesList.add(new SelectItem(0,"--SELECCIONE--"));
			
		}
		return clientesList;
	}

	public int getCantidadAgregarCodigo() {
		return cantidadAgregarCodigo;
	}

	public void setCantidadAgregarCodigo(int cantidadAgregarCodigo) {
		logger.info("->setCantidadAgregarCodigo:cantidadAgregarCodigo("+this.cantidadAgregarCodigo+")="+cantidadAgregarCodigo);
		this.cantidadAgregarCodigo = cantidadAgregarCodigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void agregarCodigo() {
		logger.info("->agregarCodigo:cantidadAgregarCodigo="+cantidadAgregarCodigo+", codigo="+codigo);
		
		EntradaSalidaDetalleQuickView dvpAdd = null;
		try {
			dvpAdd = ProductoDAO.getInstance().findByCodigo(tipoAlmacen,codigo);
			if(dvpAdd != null){
				logger.info("->agregarCodigo:dvpAdd="+dvpAdd);

				logger.info("->agregarCodigo:OK +"+cantidadAgregarCodigo+" x "+dvpAdd);

				dvpAdd.setCantidad(cantidadAgregarCodigo);

				entityList.add(dvpAdd);

				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"AGREGAR PRODUCTO",  "SE ENCONTRÓ Y SE AGREGÓ "+cantidadAgregarCodigo+" x ["+codigo+"] AL DETALLE.") );

				if(!conservarBusqueda){
					resultadoBusqueda = null;
					resultadoBusquedaList = null;
					cadenaBusqueda = null;
				}
				codigo = "";
				cantidadAgregarBusqueda = 1;
				cantidadAgregarCodigo   = 1;
				actualizarTotales();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"AGREGAR PRODUCTO",  "NO SE ENCONTRÓ ["+codigo+"] ") );

				if(!conservarBusqueda){
					resultadoBusqueda = null;
					resultadoBusquedaList = null;
					cadenaBusqueda = null;
				}
				codigo = "";
				cantidadAgregarBusqueda = 1;
				cantidadAgregarCodigo   = 1;

			}
		}catch(DAOException de){
			logger.severe(de.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"AGREGAR PRODUCTO",  "OCURRIÓ UN ERROR AL BUSCAR.") );

			codigo = "";
			cantidadAgregarBusqueda = 1;
			cantidadAgregarCodigo   = 1;
		}
	}
	
	public void actualizarTotales(){
		logger.info("->actualizarTotales:");
		entradaSalidaFooter.calculaTotalesDesde(entradaSalida, entityList);
	}

	public void onClienteListChange() {
		logger.info("->onClienteListChange:clienteId="+entradaSalida.getClienteId());
		clienteSeleccionado = null;
		for(Cliente c:getClientes()){
			if(c.getId().equals(entradaSalida.getClienteId())){
				clienteSeleccionado = c;
				break;
			}
		}
	}
	
	public void seleccionaCliente(int clienteIdChoiced){
		logger.info("->seleccionaCliente:clienteIdChoiced="+clienteIdChoiced);
		for(Cliente c:getClientes()){
			if(c.getId().equals(clienteIdChoiced)){
				entradaSalida.setClienteId(c.getId());
				clienteSeleccionado = c;
				break;
			}
		}
	}
	
	public List<SelectItem> getFormaDePagoList() {
		ArrayList<SelectItem> formaDePagoList = new ArrayList<SelectItem>();
		ArrayList<FormaDePago> formaDePagos = null;
		try {
			formaDePagos = FormaDePagoDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.severe(de.getMessage());			
		}
		if(formaDePagos != null){
			formaDePagoList.add(new SelectItem(0,"--SELECCIONE--"));			
			for(FormaDePago fp:formaDePagos){
				formaDePagoList.add(new SelectItem(fp.getId(),fp.getDescripcion()));			
			}
		}
		return formaDePagoList;
	}

	public void onFormaDePagoListChange() {
		logger.info("->onFormaDePagoListChange:entradaSalida.getFormaDePagoId()="+entradaSalida.getFormaDePagoId());
	}
	
	public List<SelectItem> getMetodoDePagoList() {
		ArrayList<SelectItem> metodoDePagoList = new ArrayList<SelectItem>();
		ArrayList<MetodoDePago> metodoDePagos = null;
		try {
			metodoDePagos = MetodoDePagoDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.severe(de.getMessage());
		}
		if(metodoDePagos != null){
			metodoDePagoList.add(new SelectItem(0,"--SELECCIONE--"));			
			for(MetodoDePago fp:metodoDePagos){
				metodoDePagoList.add(new SelectItem(fp.getId(),fp.getDescripcion()));			
			}
		}
		return metodoDePagoList;
	}

	public void onMetodoDePagoListChange() {
		logger.info("->onMetodoDePagoListChange:entradaSalida.getMetodoDePagoId()="+entradaSalida.getMetodoDePagoId());
	}
	
	private ArrayList<SelectItem> descuentoEspecialList;
	public List<SelectItem> getDescuentoEspacialList() {
		if(descuentoEspecialList == null){
			descuentoEspecialList = new ArrayList<SelectItem>();
			Iterator<Integer> descuentosIterator = Constants.descuentos.keySet().iterator();
			while(descuentosIterator.hasNext()){
				Integer dn = descuentosIterator.next();
				descuentoEspecialList.add(new SelectItem(dn,Constants.descuentos.get(dn)));
			}		
		}
		return descuentoEspecialList;
	}

	public void onDescuentoEspecialListChange() {
		logger.info("->onDescuentoEspecialListChange:PorcentajeDescuentoExtra="+entradaSalida.getPorcentajeDescuentoExtra());
		actualizarTotales();
	}

	public EntradaSalida getEntradaSalida() {
		return entradaSalida;
	}

	public void setEntradaSalida(EntradaSalida entradaSalida) {
		this.entradaSalida = entradaSalida;
	}

	public void setTablaExpandida(boolean tablaExpandida) {
		this.tablaExpandida = tablaExpandida;
	}

	public boolean isTablaExpandida() {
		return tablaExpandida;
	}

	public void expandirTabla() {
		this.tablaExpandida = true;
	}

	public void contraerTabla() {
		this.tablaExpandida = false;
	}
	
	public void comentariosChanged() {
		logger.info("->comentariosChanged:comentarios="+entradaSalida.getComentarios());		
	}

	public void onResultadoBusquedaChange() {
		logger.info("->onResultadoBusquedaChange:resultadoBusquedaSelected="+resultadoBusquedaSelected);
	}
	
	public void agregarSeleccionadoDeBusqueda() {
		logger.info("->agregarSeleccionadoDeBusqueda:"+cantidadAgregarBusqueda+" x resultadoBusquedaSelected="+resultadoBusquedaSelected);
		EntradaSalidaDetalleQuickView dvpAdd=null;
		for(EntradaSalidaDetalleQuickView pv:resultadoBusqueda){
			if(pv.getProductoCodigoBarras().equals(resultadoBusquedaSelected)){
				try {
					dvpAdd = (EntradaSalidaDetalleQuickView) BeanUtils.cloneBean(pv);
				} catch (Exception ex) {
					logger.log(Level.SEVERE,"UPS, no se pudede clonar", ex);
				}
				dvpAdd.setRowId(System.currentTimeMillis());
				dvpAdd.setAlmacenId(tipoAlmacen);
				dvpAdd.setCantidad(cantidadAgregarBusqueda);
				break;
			}
		}
		if(dvpAdd != null) {
			logger.info("->agregarSeleccionadoDeBusqueda:OK +"+cantidadAgregarBusqueda+" x "+dvpAdd);
			entityList.add(dvpAdd);
			
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"AGREGAR PRODUCTO",  "SE AGREGÓ "+cantidadAgregarBusqueda+" x ["+resultadoBusquedaSelected+"] AL DETALLE.") );
			
			if(!conservarBusqueda){
				resultadoBusqueda = null;
				resultadoBusquedaList = null;
				cadenaBusqueda = null;
			}
			cantidadAgregarBusqueda = 1;
			cantidadAgregarCodigo   = 1;
			actualizarTotales();
			
		}
	}
	
	public void setTipoAlmacen(int tipoAlmacen) {
		logger.info("->setTipoAlmacen:tipoAlmacen="+tipoAlmacen);
		this.tipoAlmacen = tipoAlmacen;
	}

	
	public void onTipoAlmacenChange() {
		logger.info("->onTipoAlmacenChange:tipoAlmacen="+tipoAlmacen);
		cantidadAgregarBusqueda = 1;
		cantidadAgregarCodigo   = 1;
		cadenaBusqueda ="";
		resultadoBusqueda = null;
		resultadoBusquedaList = null;		
	}

	public List<SelectItem> getTipoAlmacenList() {
		if (tipoAlmacenList == null) {

			tipoAlmacenList = new ArrayList<SelectItem>();
			Iterator<Integer> almacentesIt = Constants.tipoAlmacen.keySet().iterator();
			while(almacentesIt.hasNext()){
				Integer almacenId = almacentesIt.next();
				tipoAlmacenList.add(new SelectItem(almacenId, Constants.tipoAlmacen.get(almacenId)));
			}
		}

		return tipoAlmacenList;
	}	

	
	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public boolean isCrearPedido() {
		crearPedido=false;
		
		if(entityList!=null && entityList.size()>0 &&
				entradaSalida.getClienteId()!=null && entradaSalida.getClienteId() > 0 && clienteSeleccionado!=null &&
				entradaSalida.getFormaDePagoId()!=null && entradaSalida.getFormaDePagoId()> 0 &&
				entradaSalida.getMetodoDePagoId()!=null && entradaSalida.getMetodoDePagoId()> 0 ){
			crearPedido=true;
		}
		
		return crearPedido;
	}
	
	private void validacion(){
		logger.info("->validacion");
		
	}
	
	public void guardar() {
		logger.info("->guardar:");
		try{
			entradaSalida.setCaja(1);
			entradaSalida.setFactorIva(Constants.IVA);
			entradaSalida.setNumDeCuenta(null);
			entradaSalida.setNumeroTicket("0");
			entradaSalida.setSucursalId(sessionUserMB.getSucursalId());
			entradaSalida.setUsuarioEmailCreo(sessionUserMB.getUsuarioAuthenticated().getEmail());	
						
			EntradaSalidaDAO.getInstance().insert(entradaSalida,entityList);
			logger.info("->guardar:entradaSalida.id:"+entradaSalida.getId());
			
			pedidoFinalizado = true;
			cadenaBusqueda = null;
			resultadoBusqueda = null;
			resultadoBusquedaList = null;
			conservarBusqueda = false;
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"GUARDAR",  "SE CREO EL PEDIDO #"+entradaSalida.getId()) );
		}catch(DAOException de){
			logger.severe(de.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"GUARDAR",  "OCURRIÓ UN ERROR AL GUARDAR.") );
		}

	}
	
	public void cancelar() {
		logger.info("->cancelar");
		reset();
	}
	
	public void cerrar() {
		logger.info("->cerrar");
		reset();
	}

	public boolean isPedidoFinalizado() {
		return pedidoFinalizado;
	}
	
	public void onComentariosChange() {
		logger.info("->onComentariosChange:comentarios="+entradaSalida.getComentarios());
	}
	
	public void onCondicionesChange() {
		logger.info("->onCondicionesChange:CondicionesDePago="+entradaSalida.getCondicionesDePago());
	}

	public EntradaSalidaFooter getEntradaSalidaFooter() {
		return entradaSalidaFooter;
	}
	
	public String getImporteDesglosado(double f){
		return Constants.getImporteDesglosado(f);
	}
}
