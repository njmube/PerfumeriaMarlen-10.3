package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.model.Cliente;
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

@ManagedBean(name="clienteMB")
@SessionScoped
public class ClienteMB  {
	private transient final Logger logger = Logger.getLogger(ClienteMB.class.getSimpleName());
	List<Cliente> entityList;
	Integer viewRows;
	Cliente selectedEntity;
	
	@PostConstruct
    public void init() {
		System.out.println("->ClienteMB: init.");
        try{
			entityList = ClienteDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.severe(de.getMessage());
			entityList = new ArrayList<Cliente>();
		}
		System.out.println("->ClienteMB: init:entityList="+entityList);
		viewRows = 10;
    }
	
	public String reset() {
		System.out.println("->ClienteMB: rest.");
        init();
		return "/pages/cliente";
    }
	
	public void selectEntity(ActionEvent event){
		System.out.println("->ClienteMB: selectCliente.");
	}
	
	public void actionX(ActionEvent event){
		System.out.println("->ClienteMB: actionX.");
	}
	
	public void prepareForNew() {
		System.out.println("->ClienteMB prepareForNew");
		this.selectedEntity = new Cliente();
	}
	
	public void setSelectedEntity(Cliente selectedCliente) {
		System.out.println("->ClienteMB setSelectedCliente:"+selectedCliente);
		this.selectedEntity = selectedCliente;
	}
	
	public void save(){
		System.out.println("->ClienteMB: saveSelectedCliente:id:"+selectedEntity.getId());
		try{
			if(selectedEntity.getTelefonos().contains("0000")){
				throw new Exception("Telephone 0000 not allowed.");
			}
			
			int u=ClienteDAO.getInstance().update(selectedEntity);
			System.out.println("->ClienteMB: saveSelectedCliente: u="+u);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardar", "Ok se guardó correctamente"));
			reset();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Guardar", "Eror al guardar:"+e.getLocalizedMessage()));
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
	public List<Cliente> getEntityList() {
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
