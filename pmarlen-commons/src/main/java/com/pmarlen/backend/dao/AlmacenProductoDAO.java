/**
 * AlmacenProductoDAO
 *
 * Created 2015/02/07 21:02
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

import java.util.logging.Logger;
import java.util.logging.Level;

import com.pmarlen.backend.model.*;
import com.tracktopell.jdbc.DataSourceFacade;

/**
 * Class for AlmacenProductoDAO of Table ALMACEN_PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/02/07 21:02
 */

public class AlmacenProductoDAO {

	private final static Logger logger = Logger.getLogger(AlmacenProductoDAO.class.getName());

	/**
	*	Datasource for table ALMACEN_PRODUCTO simple CRUD operations.
	*   x common paramenter.
	*/

	private static AlmacenProductoDAO instance;

	private AlmacenProductoDAO(){	
		logger.fine("created AlmacenProductoDAO.");
	}

	public static AlmacenProductoDAO getInstance() {
		if(instance == null){
			instance = new AlmacenProductoDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public AlmacenProducto findBy(AlmacenProducto x) throws DAOException, EntityNotFoundException{
		AlmacenProducto r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,PRECIO,UBICACION FROM ALMACEN_PRODUCTO "+
					"WHERE PRODUCTO_CODIGO_BARRAS=?"
			);
			ps.setString(1, x.getProductoCodigoBarras());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new AlmacenProducto();
				r.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				r.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				r.setCantidad((Integer)rs.getObject("CANTIDAD"));
				r.setPrecio((Double)rs.getObject("PRECIO"));
				r.setUbicacion((String)rs.getObject("UBICACION"));
			} else {
				throw new EntityNotFoundException("ALMACEN_PRODUCTO NOT FOUND FOR PRODUCTO_CODIGO_BARRAS="+x.getProductoCodigoBarras());
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	}

    public ArrayList<AlmacenProducto> findAll() throws DAOException {
		ArrayList<AlmacenProducto> r = new ArrayList<AlmacenProducto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,PRECIO,UBICACION FROM ALMACEN_PRODUCTO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				AlmacenProducto x = new AlmacenProducto();
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setPrecio((Double)rs.getObject("PRECIO"));
				x.setUbicacion((String)rs.getObject("UBICACION"));
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	};
    
    public int insert(AlmacenProducto x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,PRECIO,UBICACION) "+
					" VALUES(?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getPrecio());
			ps.setObject(ci++,x.getUbicacion());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setProductoCodigoBarras((String)rsk.getObject(1));
				}
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{				
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

	public int update(AlmacenProducto x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE ALMACEN_PRODUCTO SET CANTIDAD=?,PRECIO=?,UBICACION=? "+
					" WHERE PRODUCTO_CODIGO_BARRAS=?");
			
			int ci=1;
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getPrecio());
			ps.setObject(ci++,x.getUbicacion());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

    public int delete(AlmacenProducto x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM ALMACEN_PRODUCTO WHERE PRODUCTO_CODIGO_BARRAS=?");
			ps.setObject(1, x.getProductoCodigoBarras());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

}
