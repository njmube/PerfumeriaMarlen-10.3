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

@ManagedBean(name="editarEntradaSalidaMB")
@SessionScoped
public class EditarEntradaSalidaMB{	
	private static transient Logger logger = Logger.getLogger("EditarEntradaSalidaMB");
	private List<SelectItem> resultadoBusquedaList;
	private static final int LONG_MIN_DESC_CTE = 60;
	private static List<SelectItem> tipoAlmacenList;
	private ArrayList<EntradaSalidaDetalleQuickView> entityList;
	private EntradaSalidaDetalleQuickView selectedEntity;
	private EntradaSalida pedidoVenta;
	private EntradaSalidaFooter pedidoVentaFooter;
	private ArrayList<EntradaSalidaDetalleQuickView> resultadoBusqueda;
	private EntradaSalidaDetalleQuickView resultadoBusquedaSI;
	
	private String cadenaBusqueda;
	private int tipoAlmacen;
	private String resultadoBusquedaSelected;
	private int cantidadAgregarBusqueda;
	boolean conservarBusqueda;
	
	private int cantidadAgregarCodigo;
	private String codigo;
	private boolean autorizaDescuento = true;
	private boolean tablaExpandida = false;
	private boolean tableDraggableEnabled = false;
	private boolean crearPedido;
	private boolean hayCambios = false;
	@ManagedProperty(value = "#{sessionUserMB}")
	private SessionUserMB sessionUserMB;

	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}
	
	@PostConstruct
	public void init() {
		pedidoVenta = new EntradaSalida();
		pedidoVentaFooter= new EntradaSalidaFooter();
		entityList = new ArrayList<EntradaSalidaDetalleQuickView>();
		tipoAlmacen = Constants.ALMACEN_PRINCIPAL;
		cantidadAgregarBusqueda = 1;
		cantidadAgregarCodigo   = 1;
		
		clienteSeleccionado = null;
		crearPedido = false;	
		cadenaBusqueda = null;
		resultadoBusqueda = null;
		resultadoBusquedaList = null;
		conservarBusqueda = true;
		hayCambios = false;
		autorizaDescuento = true;
		tablaExpandida = false;
		tableDraggableEnabled = false;
		logger.info("OK init");
	}

	public String editar(int pedidoVentaID){
		logger.info("pedidoVentaID="+pedidoVentaID);		
		try {
			pedidoVenta = EntradaSalidaDAO.getInstance().findBy(new EntradaSalida(pedidoVentaID));
			logger.info("pedidoVenta="+pedidoVenta);
			entityList = EntradaSalidaDAO.getInstance().findAllESDByEntradaSalida(pedidoVentaID);

			logger.info("entityList:");
			for(EntradaSalidaDetalleQuickView pv:entityList){
				logger.info("\teditar:"+pv);
			}
		}catch(DAOException de){
			logger.severe(de.getMessage());
			pedidoVenta = new EntradaSalidaQuickView();
			pedidoVenta.setId(0);
			pedidoVentaFooter= new EntradaSalidaFooter();
			entityList = new ArrayList<EntradaSalidaDetalleQuickView>();
		}
		tipoAlmacen = Constants.ALMACEN_PRINCIPAL;
		cantidadAgregarBusqueda = 1;
		cantidadAgregarCodigo   = 1;
		
		getClientesList();
		onClienteListChange();
		
		getFormaDePagoList();
		onFormaDePagoListChange();
		
		getMetodoDePagoList();
		onMetodoDePagoListChange();
		
		getDescuentoEspacialList();
		
		crearPedido = false;	
		cadenaBusqueda = null;
		resultadoBusqueda = null;
		resultadoBusquedaList = null;
		conservarBusqueda = false;
		
		actualizarTotales();
		hayCambios = false;
		logger.fine("fin Editar");
		return "/pages/editarEntradaSalida";
	}

	public String reset() {
		logger.info("->EntradaSalidaDetalleMB: rest.");
		editar(this.pedidoVenta.getId());
		return "/pages/cliente";
	}
	
	public EntradaSalida getPedidoVenta() {
		return pedidoVenta;
	}

	public EntradaSalidaFooter getPedidoVentaFooter() {
		return pedidoVentaFooter;
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
				boolean modoExclusivo = false;
				if(cadenaBusqueda.matches("([ ]+)*\\((.)+\\)([ ]+)*")){
					modoExclusivo = true;
				}
				String cadenaBusquedaQuery = cadenaBusqueda.replace("(", "").replace(")", "");
				
				resultadoBusqueda = ProductoDAO.getInstance().findAllByDesc(this.tipoAlmacen, cadenaBusquedaQuery,modoExclusivo);
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
				hayCambios = true;
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
			hayCambios = true;
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
		hayCambios = true;
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
				hayCambios = true;
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
		pedidoVentaFooter.calculaTotalesDesde(pedidoVenta, entityList);
	}

	public void onClienteListChange() {
		logger.info("->onClienteListChange:clienteId="+pedidoVenta.getClienteId());
		clienteSeleccionado = null;
		for(Cliente c:getClientes()){
			if(c.getId().equals(pedidoVenta.getClienteId())){
				clienteSeleccionado = c;
				hayCambios = true;
				break;
			}
		}
	}
	
	public void seleccionaCliente(int clienteIdChoiced){
		logger.info("->seleccionaCliente:clienteIdChoiced="+clienteIdChoiced);
		for(Cliente c:getClientes()){
			if(c.getId().equals(clienteIdChoiced)){
				pedidoVenta.setClienteId(c.getId());
				clienteSeleccionado = c;
				hayCambios = true;
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
		logger.info("->onFormaDePagoListChange:entradaSalida.getFormaDePagoId()="+pedidoVenta.getFormaDePagoId());
		hayCambios = true;
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
		logger.info("->onMetodoDePagoListChange:entradaSalida.getMetodoDePagoId()="+pedidoVenta.getMetodoDePagoId());
		hayCambios = true;
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
		logger.info("->onDescuentoEspecialListChange:PorcentajeDescuentoExtra="+pedidoVenta.getPorcentajeDescuentoExtra());
		hayCambios = true;
		actualizarTotales();
	}

	public void setEntradaSalida(EntradaSalida entradaSalida) {
		this.pedidoVenta = entradaSalida;
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

	public boolean isTableDraggableEnabled() {
		return tableDraggableEnabled;
	}
	
	public void activarMover() {
		this.tableDraggableEnabled = true;
		FacesContext context = FacesContext.getCurrentInstance();         
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OPCIONES DE TABLA",  "AHORA PUEDE ARRASTRAR LOS RENGLONES PARA MOVER ENTRE ELEMENTOS DEL DETALLE") );			
	}

	public void desactivarMover() {
		this.tableDraggableEnabled = false;
		FacesContext context = FacesContext.getCurrentInstance();         
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OPCIONES DE TABLA",  "AHORA PUEDE SELECCIONAR EL TEXTO DE LOS RENGLONES DEL DETALLE DEL LOS ELEMENTOS DEL DETALLE") );			
	}

	public void comentariosChanged() {
		logger.info("->comentariosChanged:comentarios="+pedidoVenta.getComentarios());		
		hayCambios = true;
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
				pedidoVenta.getClienteId()!=null && pedidoVenta.getClienteId() > 0 && clienteSeleccionado!=null &&
				pedidoVenta.getFormaDePagoId()!=null && pedidoVenta.getFormaDePagoId()> 0 &&
				pedidoVenta.getMetodoDePagoId()!=null && pedidoVenta.getMetodoDePagoId()> 0 ){
			crearPedido=true;
		}
		
		return crearPedido;
	}
	
	private void validacion(){
		logger.info("->validacion");
		
	}
	
	public void guardar() {
		try{						
			logger.info("pedidoVenta.id:"+pedidoVenta.getId());
			logger.info("pedidoVenta.cfdVentaId:"+pedidoVenta.getCfdId());
			EntradaSalidaDAO.getInstance().update(pedidoVenta,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Guardar.");
			
			reset();
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"GUARDAR",  "SE ACTUALIZÓ CORRECTAMENTE EL PEDIDO #"+pedidoVenta.getId()+".") );
		}catch(Exception e){
			logger.log(Level.SEVERE, "->guardar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"GUARDAR",  "HUBO UN ERROR AL GUARDAR.") );
		}

	}
	
	public void verificar() {
		try{			
			logger.info("pedidoVenta.id:"+pedidoVenta.getId());
			EntradaSalidaDAO.getInstance().verificar(pedidoVenta,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Verificar.");
			
			reset();
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"VERIFICAR",  "SE VERIFICÓ CORRECTAMENTE EL PEDIDO #"+pedidoVenta.getId()+".") );
		}catch(Exception e){
			logger.log(Level.SEVERE, "->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"VERFICAR",  "HUBO UN ERROR AL VERIFICAR.") );
		}		
	}
	
	public void surtir() {
		try{			
			logger.info("pedidoVenta.id:"+pedidoVenta.getId());
			EntradaSalidaDAO.getInstance().surtir(pedidoVenta,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("OK Surtir.");

			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"SURTIR",  "SE SURTIÓ CORRECTAMENTE EL PEDIDO #"+pedidoVenta.getId()+".") );			
			reset();
		}catch(Exception e){
			logger.log(Level.SEVERE, "->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"SURTIR",  "HUBO UN ERROR AL SURTIR.") );
		}		
	}
	
	public void cancelar() {
		try{			
			logger.info("pedidoVenta.id:"+pedidoVenta.getId());
			//EntradaSalidaDAO.getInstance().surtir(pedidoVenta,entityList,sessionUserMB.getUsuarioAuthenticated());
			logger.info("CANCELAR NO IMPLEMENTADO, solo resetea");
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"CANCELAR",  "CANCELAR PENDIENTE PEDIDO #"+pedidoVenta.getId()+".") );			
			reset();
		}catch(Exception e){
			logger.log(Level.SEVERE, "->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"SURTIR",  "HUBO UN ERROR AL SURTIR.") );
		}		
	}
	
	public void cancelarCambios() {
		try{			
			logger.info("pedidoVenta.id:"+pedidoVenta.getId());
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"CANCELAR CAMBIOS",  "SE CANCELARON LOS CAMBIOS Y RECARGÓ EL PEDIDO #"+pedidoVenta.getId()+".") );
			reset();
		}catch(Exception e){
			logger.log(Level.SEVERE, "->verificar: Exception", e);
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"CANCELAR CAMBIOS",  "HUBO UN ERROR AL CANCELAR CAMBIOS.") );
		}		
	}

	
	public void cerrar() {
		logger.info("->cerrar");
		reset();
	}

	public boolean isHayCambios() {
		return hayCambios;
	}
	
	public void onComentariosChange() {
		logger.info("->onComentariosChange:comentarios="+pedidoVenta.getComentarios());
	}
	
	public void onCondicionesChange() {
		logger.info("->onCondicionesChange:CondicionesDePago="+pedidoVenta.getCondicionesDePago());
	}
	
	public String getImporteDesglosado(double f){
		return Constants.getImporteDesglosado(f);
	}

	public String getImporteMoneda(double f){
		return Constants.getImporteMoneda(f);
	}
	
	public boolean isAutorizaDescuento(){
		return this.autorizaDescuento;
	}
	
	public void setAutorizaDescuento(boolean autorizaDescuento){
		this.autorizaDescuento = autorizaDescuento;
	}
	
	public void onAutorizaDescuentoChange(){
		logger.info("autorizaDescuento="+this.autorizaDescuento);
		if(this.autorizaDescuento){
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"DESCUENTO",  " SE AUTORIZÓ LA POLITICA DE DESCUENTO") );
		} else {
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"DESCUENTO",  " SE BLOQUEÓ LA POLITICA DE DESCUENTO") );
		}
		pedidoVenta.setAutorizaDescuento(this.autorizaDescuento?1:0);
		if(!this.autorizaDescuento){
			this.pedidoVenta.setPorcentajeDescuentoExtra(0);
		}
		actualizarTotales();
	}
	
	public String getPoliticaDescuento(){
		if(this.autorizaDescuento){
			return "POLITICA: De 5,000 a 10,000 = -5%, > 10,000 = -10%";
		}else{
			return "NO HAY DESCUENTO";
		}
	}
	
	public String getCodigoTableWidth(){
		if(this.tablaExpandida)
			return "20%";
		else
			return "60%";
	}
	
	public boolean isVerificable(){		
		return pedidoVenta!=null && pedidoVenta.getEstadoId() <= Constants.ESTADO_SINCRONIZADO ;
	}
	
	public boolean isSurtible(){
		return pedidoVenta!=null && pedidoVenta.getEstadoId() == Constants.ESTADO_VERIFICADO ;
	}

	public boolean isFacturable(){
		return pedidoVenta!=null && pedidoVenta.getEstadoId() == Constants.ESTADO_SURTIDO ;
	}
	
	public boolean isCancelable(){
		return pedidoVenta!=null && pedidoVenta.getEstadoId() < Constants.ESTADO_CANCELADO ;
	}
	
	public int getAlturaExtraTabla() {
		logger.fine("->getAlturaExtraTabla: isVerificable()="+isVerificable()+", isSurtible()="+isSurtible());
		if(isVerificable() || isSurtible()){
			logger.info("->getAlturaExtraTabla: 0");
			return 0;
		} else {
			logger.info("->getAlturaExtraTabla: 180");
			return 180;
		}
	}

	public boolean getRenglonesMoviblesEnTabla() {
		if(isVerificable() || isSurtible()){
			return true;
		} else {
			return false;
		}
	}
	
	public String volverAlListado(){
		return "/pages/pedidosVenta";
	}

}
