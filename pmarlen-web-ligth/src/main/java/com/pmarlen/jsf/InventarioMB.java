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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
	
	@PostConstruct
    public void init() {				
		getAlmacenList();
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
				logger.severe(de.getMessage());
			}		
		}
		return entityList;
	}
	
	public int getSizeList(){
		return entityList.size();
	}
	
	public void setViewRows(Integer viewRows) {
		this.viewRows = viewRows;
	}

	public Integer getViewRows() {
		return viewRows;
	}
	
		
	public List<SelectItem> getAlmacenList() {
		if(almacenList==null){
			almacenList = new ArrayList<SelectItem>();
			List<Almacen> almacenes=null;
			try {
				almacenes=(List<Almacen>) AlmacenDAO.getInstance().findBySucursal(sucursalId);
			}catch(DAOException de){
				logger.severe(de.getMessage());			
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
	
	public void updateMovsHisProducto(int almacenId,String codigoBarras){
		logger.info("int almacenId="+almacenId+"codigoBarras="+codigoBarras);
//		movsHisProducto = new ArrayList<MovimientoHistoricoProductoQuickView>(); 
		try {
			movsHisProducto = MovimientoHistoricoProductoDAO.getInstance().findAllByAlmacenAndProducto(almacenId, codigoBarras);
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
			logger.severe(de.getMessage());			
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
 
    public List<String> getIndustrias() {
        return industrias;
    }

}
