/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author alfredo
 */
public class BaseDAO {
	DataSource ds;

	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	
	public static LineaDAO getLineaDAO(){
		LineaDAO d = new LineaDAO();
		d.setDs(getMySQLDataSource());
		return d;
	}
	
	public static DataSource getMySQLDataSource() {
        Context ctx = null;
		DataSource dsX = null;
        try {    
			ctx = new InitialContext();
			dsX = (DataSource) ctx.lookup("java:comp/env/jdbc/PMARLEN_BACKEND_DS");
			
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return dsX;
    }
}
