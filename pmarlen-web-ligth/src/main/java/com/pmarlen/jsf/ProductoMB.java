package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.ProductoQuickView;
import com.pmarlen.model.Constants;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name="productoMB")
@SessionScoped
public class ProductoMB  {
	private transient final Logger logger = Logger.getLogger(ProductoMB.class.getSimpleName());
	List<ProductoQuickView> entityList;
	Integer viewRows;
	ProductoQuickView selectedEntity;
	String dialogTitle;
	int almacenId=1;
	boolean nuevoProducto=false;
	@PostConstruct
    public void init() {
		logger.info("->ProductoMB: init.");
		nuevoProducto=false;
        try{
			entityList = ProductoDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.error(de.getMessage());
			entityList = new ArrayList<ProductoQuickView>();
		}
		//logger.info("->ProductoMB: init:entityList="+entityList);
		viewRows = 10;
		dialogTitle ="PRODUCTO";
    }
	
	public String reset() {
		logger.info("->ProductoMB: rest.");
        init();
		return "/pages/producto";
    }

	public String getDialogTitle() {
		return dialogTitle;
	}
	
	public void selectEntity(ActionEvent event){
		logger.info("->ProductoMB: selectProducto.");
	}
	
	public void actionX(ActionEvent event){
		logger.info("->ProductoMB: actionX.");
	}
	
	public void prepareForNew() {
		nuevoProducto=true;
		logger.info("->ProductoMB prepareForNew");
		dialogTitle ="DATOS DEL PRODUCTO";
		this.selectedEntity = new ProductoQuickView();
		this.selectedEntity.setUnidadEmpaque("PZ");
		this.selectedEntity.setUnidadesXCaja(12);		
	}
	
	public void setSelectedEntity(ProductoQuickView selectedProducto) {
		nuevoProducto=false;
		logger.info("->ProductoMB setSelectedProducto.id="+selectedProducto.getCodigoBarras());
		dialogTitle ="EDITAR PRODUCTO ID #"+selectedProducto.getCodigoBarras();
		this.selectedEntity = selectedProducto;
	}
	
	public void save(){
		logger.info("->ProductoMB: saveSelectedProducto:codigoBarras:"+selectedEntity.getCodigoBarras());
		
		try{
			int u=-1;			
			if(nuevoProducto){
				u=ProductoDAO.getInstance().insert(selectedEntity);			
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE CREÓ CORRECTAMENTE NUEVO PRODUCTO"));			
			} else{
				u=ProductoDAO.getInstance().update(selectedEntity);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE ACTUALIZARÓN CORRECTAMENTE LOS DATOS DEL PRODUCTO ["+selectedEntity.getCodigoBarras()+"]"));			
			}
			reset();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, dialogTitle, "OCURRIO UN ERROR AL GUARDAR:"+e.getLocalizedMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		
	}

	public Producto getSelectedEntity() {
		return selectedEntity;
	}
	
    public void onEntityChosen(SelectEvent event) {
        Integer entityId = (Integer) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entity Selected", "Id:" + entityId);
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	public List<ProductoQuickView> getEntityList() {
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
	
	public String getImporteMoneda(double f){
		return Constants.getImporteMoneda(f);
	}
}
