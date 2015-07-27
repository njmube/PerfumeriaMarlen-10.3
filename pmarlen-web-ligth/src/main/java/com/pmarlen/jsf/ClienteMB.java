package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.quickviews.ClienteQuickView;
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

@ManagedBean(name="clienteMB")
@SessionScoped
public class ClienteMB  {
	private transient final Logger logger = Logger.getLogger(ClienteMB.class.getSimpleName());
	List<ClienteQuickView> entityList;
	Integer viewRows;
	ClienteQuickView selectedEntity;
	String dialogTitle;
	
	@PostConstruct
    public void init() {
		logger.info("->ClienteMB: init.");
        try{
			entityList = ClienteDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.error(de.getMessage());
			entityList = new ArrayList<ClienteQuickView>();
		}
		logger.info("->ClienteMB: init:entityList="+entityList);
		viewRows = 10;
		dialogTitle ="DATOS DEL CLIENTE";
    }
	
	public String reset() {
		logger.info("->ClienteMB: rest.");
        init();
		return "/pages/cliente";
    }

	public String getDialogTitle() {
		return dialogTitle;
	}
	
	
	
	public void selectEntity(ActionEvent event){
		logger.info("->ClienteMB: selectCliente.");
	}
	
	public void actionX(ActionEvent event){
		logger.info("->ClienteMB: actionX.");
	}
	
	public void prepareForNew() {
		logger.info("->ClienteMB prepareForNew");
		//dialogTitle ="AGREGAR NUEVO CLIETE";
		this.selectedEntity = new ClienteQuickView();
	}
	
	public void setSelectedEntity(ClienteQuickView selectedCliente) {
		logger.info("->ClienteMB setSelectedCliente.id="+selectedCliente.getId());
		//dialogTitle ="INFORMACIÓN DEL CLEINTE";
		this.selectedEntity = selectedCliente;
		if(this.selectedEntity.getDireccionFacturacion()== null){
			this.selectedEntity.setDireccionFacturacion(this.selectedEntity.getDireccion());
		}
	}
	
	public void save(){
		logger.info("->ClienteMB: saveSelectedCliente:id:"+selectedEntity.getId());
		
		try{
//			if(selectedEntity.getTelefonos().contains("0000")){
//				throw new Exception("Telephone 0000 not allowed.");
//			}
			int u=-1;			
			if(selectedEntity.getId()==null){
				u=ClienteDAO.getInstance().insert(selectedEntity);			
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE CREÓ CORRECTAMENTE NUEVO CLIENTE"));			
			} else{
				u=ClienteDAO.getInstance().update(selectedEntity);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE ACTUALIZARÓN CORRECTAMENTE LOS DATOS DEL CLIENTE"));			
			}
			reset();
		} catch(Exception e){
			logger.error("AL GUARDAR CLIENTE:",e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, dialogTitle, "OCURRIO UN ERROR AL GUARDAR"));
			FacesContext.getCurrentInstance().validationFailed();
		}
		
	}

	public Cliente getSelectedEntity() {
		return selectedEntity;
	}
	
    public void onEntityChosen(SelectEvent event) {
        Integer entityId = (Integer) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entity Selected", "Id:" + entityId);
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	public List<ClienteQuickView> getEntityList() {
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
}
