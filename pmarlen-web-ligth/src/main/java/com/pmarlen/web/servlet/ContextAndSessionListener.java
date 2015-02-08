/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import com.pmarlen.model.Constants;
import com.tracktopell.jdbc.WEBDataSourceFacade;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 * @author aestrada
 */
public class ContextAndSessionListener implements ServletContextListener, HttpSessionListener {
    
    Logger logger = Logger.getLogger(ContextAndSessionListener.class.getName());
	
    private static SimpleDateFormat sdfDefault  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    private static SimpleDateFormat sdfExtended = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzzzzz (Z)");
	public static final Hashtable<String , SessionInfo> sessionInfoHT = new Hashtable<String , SessionInfo>();    
	
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Date dateSystem = new Date();
		logger.info("================++++++++++++++++++++++++++++++++++++++=============>>contextInitialized");
		logger.info("-->>contextInitialized: serverVersion="+Constants.getServerVersion());
		TimeZone defaultTZ = TimeZone.getDefault();
		SessionInfo.setApplicationContextCreationTime(System.currentTimeMillis());
        logger.info("-->>contextInitialized: TimeZone.getDefault()="+defaultTZ.getDisplayName()+", Time=defaultformat:"+sdfDefault.format(dateSystem)+", ExtendedFormat:"+sdfExtended.format(dateSystem));  
		logger.info("-->>contextInitialized: WEBDataSourceFacade.registerStrategy:");
        WEBDataSourceFacade.registerStrategy();		
		logger.info("-->>contextInitialized: ConnectionPoolKeepAliveService.getInstance().start:");
		ConnectionPoolKeepAliveService.getInstance().start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
		logger.info("-->>contextDestroyed: ConnectionPoolKeepAliveService.getInstance().stopRunning:");
		ConnectionPoolKeepAliveService.getInstance().stopRunning();
        logger.info("contextDestroyed<<================++++++++++++++++++++++++++++++++++++++=============");		
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
		final HttpSession session = hse.getSession();
		logger.info("-->>sessionCreated["+session.getId()+"] ==============================================>>");
		logger.info("-->>sessionCreated["+session.getId()+"] CreationTime          :"+sdfDefault.format(session.getCreationTime()));
		logger.info("-->>sessionCreated["+session.getId()+"] LastAccessedTime      :"+sdfDefault.format(session.getLastAccessedTime()));
		logger.info("-->>sessionCreated["+session.getId()+"] MaxInactiveInterval   :"+session.getMaxInactiveInterval()+" secs.");		
		logger.info("-->>sessionCreated["+session.getId()+"] new                   ?"+session.isNew());
		logger.info("-->>contextInitialized: =========================================================>>End of initialization");
	}

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
		final HttpSession session = hse.getSession();
        logger.info ("<<--["+session.getId()+"] sessionDestroyed");
		logger.info("<<--["+session.getId()+"] was new ? "+session.isNew());
		logger.info("<<--------------------------------------------------------------------------------");
		sessionInfoHT.remove(session.getId());
    }
}
