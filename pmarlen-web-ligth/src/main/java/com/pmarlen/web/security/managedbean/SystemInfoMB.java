package com.pmarlen.web.security.managedbean;

import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.dao.UsuarioPerfilDAO;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.UsuarioPerfil;
import com.pmarlen.model.Constants;
import com.pmarlen.web.servlet.ContextAndSessionListener;
import com.pmarlen.web.servlet.SessionInfo;
import com.tracktopell.jdbc.DataSourceFacade;
import com.tracktopell.jdbc.WEBDataSourceFacade;
import java.io.IOException;
import java.io.Serializable;

import java.text.*;
import java.util.*;

import org.apache.log4j.Logger;
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
	private static String environment=null;
	private static final transient Logger logger = Logger.getLogger(SystemInfoMB.class.getSimpleName());
	private static final transient SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static String db        = null;
	private static String user      = null;
	private static String password  = null;

	private static Properties prop = new Properties();
	static{
		try {
			prop.load(WEBDataSourceFacade.class.getResourceAsStream("/env_vars.properties"));
		}catch(IOException ioe){
			logger.error("Properties not found:", ioe);
		}
	}

	public static String getDB() {
		if (db == null) {
			db   = prop.getProperty("db");			
		}
		return db;
	}
	
	public static String getUser() {
		if (user == null) {
			user  = prop.getProperty("user");			
		}
		return user;
	}
	
	public static String getPassword() {
		if (password == null) {
			password   = prop.getProperty("password");			
		}
		return password;
	}


	
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
		updateTime();
		return sessionDate;
	}
	
	public void updateTime(){
		sessionDate= sdf.format(new Date());
	}
	
	public String getEnvironment(){
		return getEnvironmentStaticlay();
	}
	
	public static String getEnvironmentStaticlay(){
		if(environment == null){
			environment=prop.getProperty("environment");
		}
		return environment;
	}


}
