/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.model.dao;

import com.pmarlen.model.dto.Linea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


/**
 *
 * @author alfredo
 */
public class LineaDAO	extends BaseDAO 
						implements EntityDAO<Linea>{
	LineaDAO(){
	}
	
	@Override
	public void create(Linea entity) throws PreexistingEntityException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void update(Linea entity) throws NonexistentEntityException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void delete(Linea entity) throws NonexistentEntityException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Linea> listAll() {
		List<Linea> resultX = new ArrayList<Linea>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("SELECT ID,NOMBRE FROM LINEA");
			rs = ps.executeQuery();
			while( rs.next()) {
				Linea x =  new Linea();
				x.setId     (rs.getInt("ID"));
				x.setNombre (rs.getString("NOMBRE"));
				resultX.add(x);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch(SQLException se){
			se.printStackTrace(System.err);
		}
		return resultX;
	}
	
}
