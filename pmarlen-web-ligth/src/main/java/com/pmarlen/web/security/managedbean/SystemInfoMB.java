package com.pmarlen.web.security.managedbean;

import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.dao.UsuarioPerfilDAO;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.UsuarioPerfil;
import com.pmarlen.model.Constants;
import com.pmarlen.web.servlet.ContextAndSessionListener;
import com.pmarlen.web.servlet.SessionInfo;
import java.io.Serializable;

import java.text.*;
import java.util.*;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name="systemInfoMB")
@SessionScoped
public class SystemInfoMB  implements Serializable{

	private static final transient Logger logger = Logger.getLogger(SystemInfoMB.class.getSimpleName());
	private static final transient SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public String getSystemVersion() {
		return Constants.getServerVersion();
	}
	
	public String getBuildTimestamp() {
		return Constants.getBuildTimestamp();	
	}
	
	public String getGitSHA1() {
		return Constants.getGitSHA1();
	}
	
	private String sessionDate=sdf.format(new Date());

	public String getSessionDate() {
		return sessionDate;
	}
	
	public void updateTime(){
		sessionDate= sdf.format(new Date());
	}



}
