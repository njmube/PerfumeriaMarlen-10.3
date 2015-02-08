/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tracktopell.jdbc;

import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author alfredo
 */
public abstract class DataSourceFacade {
	protected static DataSource ds;
	private   static DataSourceFacade strategy;

	public static void setStrategy(DataSourceFacade strategy) {
		DataSourceFacade.strategy = strategy;
	}

	public static DataSourceFacade getStrategy() {
		return strategy;
	}
	
	public abstract Connection getConnection();
	
	public abstract Connection getConnectionCommiteable();
	
}
