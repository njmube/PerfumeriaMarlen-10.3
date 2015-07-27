package com.pmarlen.rest.servlet;

import com.tracktopell.jdbc.WEBDataSourceFacade;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import org.apache.log4j.Logger;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
		logger.info("================++++++++++++++++++++++++++++++++++++++=============>>contextInitialized");
        WEBDataSourceFacade.registerStrategy();		
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
		logger.info("-->>contextDestroyed: ConnectionPoolKeepAliveService.getInstance().stopRunning:");
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
    }
}
