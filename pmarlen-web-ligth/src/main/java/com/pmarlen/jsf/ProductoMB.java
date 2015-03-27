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
import java.util.logging.Logger;
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
	@PostConstruct
    public void init() {
		System.out.println("->ProductoMB: init.");
        try{
			entityList = ProductoDAO.getInstance().findAllForMultiediaShow(almacenId);
		}catch(DAOException de){
			logger.severe(de.getMessage());
			entityList = new ArrayList<ProductoQuickView>();
		}
		System.out.println("->ProductoMB: init:entityList="+entityList);
		viewRows = 10;
		dialogTitle ="CLIETE";
    }
	
	public String reset() {
		System.out.println("->ProductoMB: rest.");
        init();
		return "/pages/producto";
    }

	public String getDialogTitle() {
		return dialogTitle;
	}
	
	
	
	public void selectEntity(ActionEvent event){
		System.out.println("->ProductoMB: selectProducto.");
	}
	
	public void actionX(ActionEvent event){
		System.out.println("->ProductoMB: actionX.");
	}
	
	public void prepareForNew() {
		System.out.println("->ProductoMB prepareForNew");
		dialogTitle ="AGREGAR NUEVO CLIETE";
		this.selectedEntity = new ProductoQuickView();
	}
	
	public void setSelectedEntity(ProductoQuickView selectedProducto) {
		System.out.println("->ProductoMB setSelectedProducto.id="+selectedProducto.getCodigoBarras());
		dialogTitle ="EDITAR PRODUCTO ID #"+selectedProducto.getCodigoBarras();
		this.selectedEntity = selectedProducto;
	}
	
	public void save(){
		System.out.println("->ProductoMB: saveSelectedProducto:codigoBarras:"+selectedEntity.getCodigoBarras());
		
		try{
			int u=-1;			
			if(selectedEntity.getCodigoBarras()==null){
				u=ProductoDAO.getInstance().insert(selectedEntity);			
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE CREÓ CORRECTAMENTE NUEVO PRODUCTO"));			
			} else{
				u=ProductoDAO.getInstance().update(selectedEntity);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE ACTUALIZARÓN CORRECTAMENTE LOS DATOS DEL PRODUCTO"));			
			}
			reset();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, dialogTitle, "OCURRIO UN ERROR AL GUARDAR"));
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
