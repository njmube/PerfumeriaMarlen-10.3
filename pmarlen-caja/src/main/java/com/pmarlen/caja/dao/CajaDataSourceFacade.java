/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.caja.dao;

import com.tracktopell.jdbc.DataSourceFacade;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
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
	
	
	public Connection getConnection(){
		Connection conn = null;
		return conn;
		
	}
	
	public Connection getConnectionCommiteable(){
		Connection conn = null;
		return conn;
	}
	
}
