package com.pmarlen.web.security.managedbean;

import com.pmarlen.backend.dao.DAOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name="sessionUserMB")
@SessionScoped
public class SessionUserMB implements Serializable{

	private final Logger logger = Logger.getLogger(SessionUserMB.class.getSimpleName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
	private UsuarioDAO usuarioDAO;
	private UsuarioPerfilDAO usarioPerfilDAO;
	private long timeOutSession;
	private Usuario usuarioAuthenticated;
	private ArrayList<UsuarioPerfil> usuarioPerfiles;
	
	@PostConstruct
    public void init() {
 		logger.info("------------->");
    }

	public Usuario getUsuarioAuthenticated() {
		if (usuarioAuthenticated == null) {
			if(this.usuarioDAO == null) {
				this.usuarioDAO = UsuarioDAO.getInstance();
			}
			
			if(this.usarioPerfilDAO == null){
				this.usarioPerfilDAO = UsuarioPerfilDAO.getInstance();
			}
			FacesContext fCtx = FacesContext.getCurrentInstance();
			ExternalContext externalContext = fCtx.getExternalContext();
			HttpSession session = (HttpSession) externalContext.getSession(false);
			String sessionId = session.getId();
			
			String remoteUser      = externalContext.getRemoteUser();
			String userPrincipal   = externalContext.getUserPrincipal().getName();
			
			logger.info("==>> getUsuarioAuthenticated : Session[" + sessionId + "] first enter: remoteUser=" + remoteUser+", userPrincipal=" + userPrincipal);
				
			if (remoteUser != null) {
				
				Date creationTime = new Date(session.getCreationTime());

				logger.info("==>> UsuarioAuthenticated : Session[" + sessionId + "] created            :" + sdf.format(creationTime));
				logger.info("==>> UsuarioAuthenticated : Session[" + sessionId + "] new                ?" + session.isNew());
				
				timeOutSession = session.getMaxInactiveInterval()*1000 - 5000;
				try {
					usuarioAuthenticated = this.usuarioDAO.findBy(new Usuario(remoteUser));
					if (!session.isNew()) {
						logger.info("\t==>> usuarioAuthenticated : Session[" + sessionId + "] True enter :" + usuarioAuthenticated);
						usuarioPerfiles = this.usarioPerfilDAO.findBy(usuarioAuthenticated);
						SessionInfo si = ContextAndSessionListener.sessionInfoHT.get(sessionId);
						if (si != null) {
							si.setUserName(remoteUser);
						} else {
							si = new SessionInfo(session, usuarioAuthenticated.getNombreCompleto());
							ContextAndSessionListener.sessionInfoHT.put(sessionId, si);
						}
					}
				} catch(DAOException de){
					logger.severe(de.getMessage());
				}
				
			}
		}
		return usuarioAuthenticated;
	}
	
	
	
	public void onIdle() {
		logger.info("onIdle, timeOutSession="+(timeOutSession/1000)+" secs., then Logout !");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "No activity.", "What are you doing over there?"));
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Welcome Back", "Well, that's a long coffee break!"));
    }

	public long getTimeOutSession() {
		return timeOutSession;
	}
	
	public String getSessionTimeOutMinus3SecsToDisplay() {
		HttpServletRequest r = null;

		r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		r.getSession().getMaxInactiveInterval();

		return String.valueOf(r.getSession().getMaxInactiveInterval() - 3);
	}

	public void updateLastVisitedPage(String page) {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fCtx.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		HttpServletRequest request = (HttpServletRequest) (externalContext.getRequest());
		String sessionId = session.getId();
		String userAgent = request.getHeader("User-Agent");
		String remoteAdrrInfo = request.getRemoteAddr() + "(" + request.getRemoteHost() + ")";
		logger.info("==>> updateLastVisitedPage : page"+page+" from : "+ remoteAdrrInfo +" using:"+userAgent);
		SessionInfo si = ContextAndSessionListener.sessionInfoHT.get(sessionId);
		if(si != null) {
			si.setLastVisitedPage(page);
			si.setRemoteAddr(remoteAdrrInfo);
			if(userAgent != null){
				si.setUserAgent(userAgent);
			}
		}
	}

	public boolean isRootUser() {
		if (usuarioAuthenticated != null) {
			for(UsuarioPerfil up: usuarioPerfiles){
				if(up.getPerfil().equals(Constants.PERFIL_ROOT)){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public boolean isAdminUser() {
		if (usuarioAuthenticated != null) {
			if(isRootUser()){
				return true;
			}
			for(UsuarioPerfil up: usuarioPerfiles){
				if(up.getPerfil().equals(Constants.PERFIL_ADMIN)){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public boolean isSalesUser() {
		if (usuarioAuthenticated != null) {
			if(isRootUser()){
				return true;
			}
			for(UsuarioPerfil up: usuarioPerfiles){
				if(up.getPerfil().equals(Constants.PERFIL_SALES)){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public boolean isStockUser() {
		if (usuarioAuthenticated != null) {
			if(isRootUser()){
				return true;
			}
			for(UsuarioPerfil up: usuarioPerfiles){
				if(up.getPerfil().equals(Constants.PERFIL_STOCK)){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public int getSucursalId() {
		return 1;
	}
}
