package com.pmarlen.jsf;

import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Producto;
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

@ManagedBean(name="productoCatSimple")
@SessionScoped
public class ProductoCatSimpleMB {
	List<Producto> entityList;
	Integer viewRows;
	Producto selectedEntity;
	private transient static Logger logger = Logger.getLogger(ProductoCatSimpleMB.class.getName());
	
	@PostConstruct
    public void init() {		
		try {
        entityList = ProductoDAO.getInstance().findAll();
		}catch(DAOException de){
			entityList = new ArrayList<Producto>();
			logger.severe(de.getMessage());
		}		
		viewRows = 10;
    }
	
	public String reset() {
		System.out.println("->ProductoMB: rest.");
        init();
		return "/pages/cliente";
    }
	
	public void selectEntity(ActionEvent event){
		System.out.println("->ProductoMB: selectProducto.");
	}
	
	public void setSelectedEntity(Producto selectedProducto) {
		System.out.println("->ProductoMB setSelectedProducto:"+selectedProducto);
		this.selectedEntity = selectedProducto;
	}
	
	public void save(){
		System.out.println("->ProductoMB: saveSelectedProducto:id:"+selectedEntity.getCodigoBarras());		
	}

	public List<Producto> getEntityList() {
		return entityList;
	}
	
	public Producto getSelectedEntity() {
		return selectedEntity;
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
}
