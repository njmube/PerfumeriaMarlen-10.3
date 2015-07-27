/**
 * AlmacenDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import java.util.ArrayList;

import java.io.ByteArrayInputStream;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Blob;
import java.sql.Timestamp;	

import org.apache.log4j.Logger;

import com.pmarlen.backend.model.*;
import com.tracktopell.jdbc.DataSourceFacade;

/**
 * Class for AlmacenDAO of Table ALMACEN.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class AlmacenDAO {

	private final static Logger logger = Logger.getLogger(AlmacenDAO.class.getName());

	/**
	*	Datasource for table ALMACEN simple CRUD operations.
	*   x common paramenter.
	*/

	private static AlmacenDAO instance;

	private AlmacenDAO(){	
		logger.debug("created AlmacenDAO.");
	}

	public static AlmacenDAO getInstance() {
		if(instance == null){
			instance = new AlmacenDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    
	public ArrayList<Almacen> findBySucursal(int sucursalId) throws DAOException {
		ArrayList<Almacen> r = new ArrayList<Almacen>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,TIPO_ALMACEN,SUCURSAL_ID FROM ALMACEN WHERE SUCURSAL_ID=?");
			ps.setInt(1, sucursalId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Almacen x = new Almacen();
				x.setId((Integer)rs.getObject("ID"));
				x.setTipoAlmacen((Integer)rs.getObject("TIPO_ALMACEN"));
				x.setSucursalId((Integer)rs.getObject("SUCURSAL_ID"));
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.error( "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error( "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	};


    public ArrayList<Almacen> findAll() throws DAOException {
		ArrayList<Almacen> r = new ArrayList<Almacen>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,TIPO_ALMACEN,SUCURSAL_ID FROM ALMACEN");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Almacen x = new Almacen();
				x.setId((Integer)rs.getObject("ID"));
				x.setTipoAlmacen((Integer)rs.getObject("TIPO_ALMACEN"));
				x.setSucursalId((Integer)rs.getObject("SUCURSAL_ID"));
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.error( "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error( "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	};
    
    public int insert(Almacen x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO ALMACEN(TIPO_ALMACEN,SUCURSAL_ID) "+
					" VALUES(?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getTipoAlmacen());
			ps.setObject(ci++,x.getSucursalId());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setId((Integer)rsk.getObject(1));
				}
			}
		}catch(SQLException ex) {
			logger.error( "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{				
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error( "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

	public int update(Almacen x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE ALMACEN SET TIPO_ALMACEN=?,SUCURSAL_ID=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getTipoAlmacen());
			ps.setObject(ci++,x.getSucursalId());
			ps.setObject(ci++,x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.error( "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error( "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

    public int delete(Almacen x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM ALMACEN WHERE ID=?");
			ps.setObject(1, x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.error( "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error( "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

}
