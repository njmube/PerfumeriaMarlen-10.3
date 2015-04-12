/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tracktopell.jdbc;

import com.tracktopell.jdbc.DataSourceFacade;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

/**
 *
 * @author alfredo
 */
public class WEBDataSourceFacade extends DataSourceFacade{
	
	private static Logger logger = Logger.getLogger(WEBDataSourceFacade.class.getName());
	private static Properties prop = new Properties();
	public static void registerStrategy(){
		try {
			prop.load(WEBDataSourceFacade.class.getResourceAsStream("/env_vars.properties"));
			DataSourceFacade.setStrategy(new WEBDataSourceFacade());
		}catch(IOException ioe){
			logger.log(Level.SEVERE, "Properties not found:", ioe);
		}
		
	}
	
	public Connection getConnection() {
		Context ctx = null;
		Connection conn = null;
        try {   
			logger.fine("->getConnection: 1)ds="+ds);
			if(ds == null) {
				ctx = new InitialContext();
				logger.finest("->getConnection: ctx="+ctx);
				ds = (DataSource) ctx.lookup("java:comp/env/"+prop.getProperty("jndi"));			
				                              
				logger.finest("->getConnection: 2)ds?"+ds);
			}
			conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally{
			return conn;
		}
		
	}

	public Connection getConnectionCommiteable() {
		Context ctx = null;
		Connection conn = null;
        try {    
			if(ds == null) {
				ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/"+prop.getProperty("jndi"));			
			}
			//logger.info("-->>DataSource class:"+ds.getClass()+" implements "+Arrays.asList(ds.getClass().getInterfaces()));
			conn = ds.getConnection();
			conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally{
			return conn;
		}
	}
	
}
