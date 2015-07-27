package com.pmarlen.jsf;

import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.quickviews.UsuarioQuickView;
import com.pmarlen.model.Constants;
import com.pmarlen.web.security.managedbean.EmailChecker;
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

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioMB  {
	private transient final Logger logger = Logger.getLogger(UsuarioMB.class.getSimpleName());
	List<UsuarioQuickView> entityList;
	Integer viewRows;
	UsuarioQuickView selectedEntity;
	String dialogTitle;
	boolean checkSameEmailPassword;
	Integer checkSameEmailPasswordState;
	int saveMode=0;
	
	@PostConstruct
    public void init() {
		logger.info("->UsuarioMB: init.");
        try{
			entityList = UsuarioDAO.getInstance().findAll();
		}catch(DAOException de){
			logger.error(de.getMessage());
			entityList = new ArrayList<UsuarioQuickView>();
		}
		logger.info("->UsuarioMB: init:entityList size="+entityList.size());
		viewRows = 10;
		dialogTitle ="USUARIO";
		saveMode=0;
		checkSameEmailPasswordState=null;
    }
	
	public String reset() {
		logger.info("->UsuarioMB: rest.");
        init();
		return "/pages/usuario";
    }

	public String getDialogTitle() {
		return dialogTitle;
	}
	
	public void selectEntity(ActionEvent event){
		logger.info("->UsuarioMB: selectUsuario.");
	}
	
	public void actionX(ActionEvent event){
		logger.info("->UsuarioMB: actionX.");
	}
	
	public void prepareForNew() {
		logger.info("->UsuarioMB prepareForNew");
		dialogTitle ="AGREGAR NUEVO USUARIO";
		this.selectedEntity = new UsuarioQuickView();
		checkSameEmailPassword=false;
		checkSameEmailPasswordState=null;
		saveMode=1;
	}
	
	public void refresh() {
		init();
	}
	
	public void setSelectedEntity(UsuarioQuickView selectedUsuario) {
		logger.info("->UsuarioMB setSelectedUsuario.email="+selectedUsuario.getEmail());
		dialogTitle ="EDITAR USUARIO:"+selectedUsuario.getEmail();
		this.selectedEntity = selectedUsuario;
		checkSameEmailPassword=false;
		checkSameEmailPasswordState=null;
		saveMode=2;
	}

	public boolean isCheckSameEmailPassword() {
		return checkSameEmailPassword;
	}

	public Integer getCheckSameEmailPasswordState() {
		return checkSameEmailPasswordState;
	}
	
	public void checkPassword(){
		checkSameEmailPasswordState=1;
		logger.info("->checkPassword="+this.selectedEntity.getEmail()+", password->"+this.selectedEntity.getPassword()+"<-");
		checkSameEmailPassword = EmailChecker.isSameEmailPassword(this.selectedEntity.getEmail(), this.selectedEntity.getPassword());
		logger.info("->checkSameEmailPassword="+checkSameEmailPassword);
		if(checkSameEmailPassword){
			checkSameEmailPasswordState=2;
		}
	}
	
	public void save(){
		logger.info("->UsuarioMB: save selectedEntity:email:"+selectedEntity.getEmail()+", roles:"+selectedEntity.getRoleList());
		
		try{
			
			selectedEntity.setPassword(Constants.getMD5Encrypted(selectedEntity.getPassword()));
			
			int u=-1;			
			if(saveMode == 1){
				logger.info("->UsuarioMB:insert");
				u=UsuarioDAO.getInstance().insert(selectedEntity);			
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE CREÓ CORRECTAMENTE NUEVO CLIENTE"));			
			} else if(saveMode == 2){
				logger.info("->UsuarioMB:update");
				u=UsuarioDAO.getInstance().update(selectedEntity);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, dialogTitle, "SE ACTUALIZARÓN CORRECTAMENTE LOS DATOS DEL CLIENTE"));			
			}
			reset();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, dialogTitle, "OCURRIO UN ERROR AL GUARDAR"));
			FacesContext.getCurrentInstance().validationFailed();
		}
		
	}

	public UsuarioQuickView getSelectedEntity() {
		return selectedEntity;
	}
	
    public void onEntityChosen(SelectEvent event) {
        Integer entityId = (Integer) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entity Selected", "Id:" + entityId);
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	public List<UsuarioQuickView> getEntityList() {
//		logger.info("just return:---------->>");
//		for(UsuarioQuickView uqv: entityList){
//			logger.info("->:"+uqv.getEmail()+","+uqv.getNombreCompleto()+","+uqv.getAbilitado());
//		}
//		logger.info("<<----------");
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
