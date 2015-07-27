package com.pmarlen.jsf;

import com.pmarlen.backend.dao.AlmacenDAO;
import com.pmarlen.backend.dao.AlmacenProductoDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MovimientoHistoricoProductoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Almacen;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.AlmacenProductoQuickView;
import com.pmarlen.backend.model.quickviews.MovimientoHistoricoProductoQuickView;
import com.pmarlen.model.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name="inventarioMB")
@SessionScoped
public class InventarioMB  {
	private List<AlmacenProductoQuickView> entityList;
	private Integer viewRows;
	private int     almacenId;
	private int		sucursalId = 1;	
	private ArrayList<SelectItem> almacenList;
	private ArrayList<MovimientoHistoricoProductoQuickView> movsHisProducto;
	private transient static Logger logger = Logger.getLogger(InventarioMB.class.getName());
	private LineChartModel historicoMovsLCM;
	private String[] selectedIndustrias;  
    private List<String> industrias;
	private AlmacenProductoQuickView selected;
	@PostConstruct
    public void init() {				
		viewRows=10;
		getAlmacenList();
		getEntityList();
		movsHisProducto = new ArrayList<MovimientoHistoricoProductoQuickView>();
		historicoMovsLCM = new LineChartModel();
		industrias = new ArrayList<String>();
        industrias.add("San Francisco");
        industrias.add("London");
        industrias.add("Paris");
        industrias.add("Istanbul");
        industrias.add("Berlin");
        industrias.add("Barcelona");
        industrias.add("Rome");
        industrias.add("Sao Paulo");
        industrias.add("Amsterdam");
		viewRows = 10;
    }
	
	public String reset() {
        init();
		return "/pages/inventarioMB";
    }
	
	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}

	public int getAlmacenId() {
		return almacenId;
	}
	
	public List<AlmacenProductoQuickView> getEntityList() {
		if(entityList == null){
			try {
				entityList = AlmacenProductoDAO.getInstance().findAllByAlmacen(almacenId);				
			}catch(DAOException de){
				entityList = new ArrayList<AlmacenProductoQuickView>();
				logger.error(de.getMessage());
			}		
		}
		return entityList;
	}
	
	public int getSizeList(){
		return entityList.size();
	}
	
		
	public List<SelectItem> getAlmacenList() {
		if(almacenList==null){
			almacenList = new ArrayList<SelectItem>();
			List<Almacen> almacenes=null;
			try {
				almacenes=(List<Almacen>) AlmacenDAO.getInstance().findBySucursal(sucursalId);
			}catch(DAOException de){
				logger.error(de.getMessage());			
			}
			if(almacenes != null){
				//almacenList.add(new SelectItem(0,"--SELECCIONE--"));			
				for(Almacen a:almacenes){
					if(a.getTipoAlmacen() == Constants.ALMACEN_PRINCIPAL){
						almacenId = a.getId();
					}
					almacenList.add(new SelectItem(a.getId(),Constants.getDescripcionTipoAlmacen(a.getTipoAlmacen()).toUpperCase()));			
				}
			}
		}
		return almacenList;
	}
	public void updateMovsHisProductoX(){
		logger.info("--XX--");
	}
	
	public void updateMovsHisProducto(AlmacenProductoQuickView apSelected){
		logger.info("int almacenId="+apSelected.getAlmacenId()+"codigoBarras="+apSelected.getProductoCodigoBarras());
//		movsHisProducto = new ArrayList<MovimientoHistoricoProductoQuickView>(); 
		try {
			movsHisProducto = MovimientoHistoricoProductoDAO.getInstance().findAllByAlmacenAndProducto(apSelected.getAlmacenId(), apSelected.getProductoCodigoBarras());
			/*
			historicoMovsLCM = new LineChartModel();
 
			ChartSeries movsCS = new ChartSeries();
			
			movsCS.setLabel("HISTORICO");
			int min = 0;
			int max = 0;
			int cont= 1;
			for(MovimientoHistoricoProductoQuickView mhp: movsHisProducto){
				mhp.setRowId(cont++);
				movsCS.set(mhp.getRowId(), mhp.getSaldo());
				if(mhp.getSaldo()>max){
					max = mhp.getSaldo()+10;
				}
				if(mhp.getSaldo()<min){
					min = mhp.getSaldo()-10;
				}
			}
			
			historicoMovsLCM.addSeries(movsCS);
			historicoMovsLCM.setTitle("MOVIMIENTOS DE ENTRADA SALIDA");
			historicoMovsLCM.setLegendPosition("e");
			historicoMovsLCM.setShowPointLabels(true);
			historicoMovsLCM.getAxes().put(AxisType.X, new CategoryAxis("TIEMPO"));
			Axis yAxis = historicoMovsLCM.getAxis(AxisType.Y);				 
			yAxis.setLabel("CANTIDAD");
			yAxis.setMin(min);
			yAxis.setMax(max);
			*/
		}catch(DAOException de){
			logger.error(de.getMessage());			
		}
	}

	public LineChartModel getHistoricoMovsLCM() {
		return historicoMovsLCM;
	}
	
	public void onIndustriasChanged(){
		logger.info("selectedIndustrias={"+Arrays.asList(selectedIndustrias+"}"));
	}
	
	public ArrayList<MovimientoHistoricoProductoQuickView> getMovsHisProducto() {
		logger.info("->movsHisProducto.size"+movsHisProducto.size());
		return movsHisProducto;
	}
	
	public void onTipoAlmacenChange() {
		logger.info("almacenId="+almacenId);
		entityList = null;
	}
	
	public String[] getSelectedIndustrias() {
        return selectedIndustrias;
    }
 
    public void setSelectedIndustrias(String[] selectedIndustrias) {
        this.selectedIndustrias = selectedIndustrias;
    }

	public AlmacenProductoQuickView getSelected() {
		return selected;
	}
	
    public List<String> getIndustrias() {
        return industrias;
    }
	
	public void updateUbicacionProducto(AlmacenProductoQuickView selectedAP){
		selected = selectedAP;
		ubucacionEditar = selected.getUbicacion()!=null?selected.getUbicacion():"";
		
		logger.info("selected:"+selected.getProductoCodigoBarras()+" ["+selected.getAlmacenId()+"]: rowId="+selected.getRowId());
	}
	
	private String ubucacionEditar;

	public String getUbucacionEditar() {
		return ubucacionEditar;
	}

	public void setUbucacionEditar(String ubucacionEditar) {
		this.ubucacionEditar = ubucacionEditar;
	}
	
	public void aceptarEdicionUbicacion(){	
		selected.setUbicacion(ubucacionEditar);
		try {
			AlmacenProductoDAO.getInstance().update(selected);

			logger.info("selected:"+selected.getProductoCodigoBarras()+" ["+selected.getAlmacenId()+"]: rowId="+selected.getRowId()+", selected.ubicacion="+selected.getUbicacion());
			selected = null;
			this.ubucacionEditar = null;
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ACTUALIZAR UBICACIÓN",  "SE HA ACTUALIZADO LA UBICACIÓN DE ESTE PRODUCTO PARA TIPO DE ALMACEN SELECCIONADO") );			
		}catch(Exception e){
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ACTUALIZAR UBICACIÓN",  "OCURRIO UN ERROR AL ACTUALIZAR") );			
		}
	}
	
	public void cancelarEdicionUbicacion(){
		logger.info("cancelar");
		selected = null;
		ubucacionEditar = null;
	}
	
	public void edicionDirectaUbicacion(ValueChangeEvent e){
		logger.info("->selectedAP.ubicacion:"+e.getNewValue()+", source class="+e.getSource().getClass()+", component class"+e.getComponent().getClass());
	}

	
	private Double nuevoPrecio;
	private String comentarioNuevoPrecio;

	public void setNuevoPrecio(Double nuevoPrecio) {
		this.nuevoPrecio = nuevoPrecio;
	}

	public Double getNuevoPrecio() {
		return nuevoPrecio;
	}

	public void setComentarioNuevoPrecio(String comentarioNuevoPrecio) {
		this.comentarioNuevoPrecio = comentarioNuevoPrecio;
	}

	public String getComentarioNuevoPrecio() {
		return comentarioNuevoPrecio;
	}
	
	
	
	public void prepararParaCambioDePrecio(AlmacenProductoQuickView selectedAP){
		selected = selectedAP;
		nuevoPrecio = selected.getPrecio();
		comentarioNuevoPrecio = null;
		
		logger.info("selected:"+selected.getProductoCodigoBarras()+" ["+selected.getAlmacenId()+"]: nuevoPrecio="+nuevoPrecio);
	}
	
	public void aceptarCambioDePrecio(){	
		if(nuevoPrecio != selected.getPrecio()) {
			selected.setPrecio(nuevoPrecio);
			try {
				AlmacenProductoDAO.getInstance().update(selected);

				logger.info("selected:"+selected.getProductoCodigoBarras()+" ["+selected.getAlmacenId()+"]: rowId="+selected.getRowId()+", selected.precio="+selected.getPrecio());
				selected = null;
				this.ubucacionEditar = null;
				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ACTUALIZAR PRECIO",  "SE HA ACTUALIZADO EL PRECIO DE ESTE PRODUCTO PARA TIPO DE ALMACEN SELECCIONADO") );			
			}catch(Exception e){
				logger.error("en el DAO", e);
				FacesContext context = FacesContext.getCurrentInstance();         
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ACTUALIZAR PRECIO",  "OCURRIO UN ERROR AL ACTUALIZAR") );			
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();         
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ACTUALIZAR PRECIO",  "NO SE REALIZO CAMBIO, PUES ES EL MISMO VALOR") );			
		}
	}
	
	public void cancelarCambioDePrecio(){
		logger.info("cancelar");
		selected = null;
		ubucacionEditar = null;
	}	
	
	public Integer getViewRows() {
		return viewRows;
	}	
	
	public void setViewRows(Integer viewRows) {
		this.viewRows = viewRows;
	}

	public void setViewRows(int viewRows) {
		logger.debug("->setViewRows("+viewRows+")");
		this.viewRows = viewRows;
	}
	
	public void refresh(){
		this.entityList=null;
		init();
		//return "/pages/inventarios";
	}
}
