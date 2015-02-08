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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="systemInfoMB")
@RequestScoped
public class SystemInfoMB  implements Serializable{

	private final Logger logger = LoggerFactory.getLogger(SystemInfoMB.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

	public String getSystemVersion() {
		return Constants.getServerVersion();
	}
	
	public String getBuildTimestamp() {
		return Constants.getBuildTimestamp();	
	}

}
