/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.caja.dao;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;
import com.tracktopell.jdbc.DataSourceFacade;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

/**
 *
 * @author alfredo
 */
public class CajaDataSourceFacade extends DataSourceFacade{
	
	public static void registerStrategy(){
		DataSourceFacade.setStrategy(new CajaDataSourceFacade());
	}
	private static MiniConnectionPoolManager poolMgr;
	
	public Connection getConnection(){
		Connection conn = null;
		try {
			if(poolMgr == null){
				ConnectionPoolDataSource dataSource = DataSourceFactory.createDataSource();
				poolMgr = new MiniConnectionPoolManager(dataSource, 10);
			}
			conn = poolMgr.getConnection();

			conn = null;
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally{
			return conn;
		}
	}
	
	public Connection getConnectionCommiteable(){
	
		Connection conn = null;
		try {    
			ConnectionPoolDataSource dataSource = DataSourceFactory.createDataSource();
			poolMgr = new MiniConnectionPoolManager(dataSource, 10);
			conn = poolMgr.getConnection();

			conn = null;
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally{
			return conn;
		}

	}
	
}
