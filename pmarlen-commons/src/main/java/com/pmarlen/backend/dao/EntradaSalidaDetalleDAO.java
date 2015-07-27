/**
 * EntradaSalidaDetalleDAO
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
 * Class for EntradaSalidaDetalleDAO of Table ENTRADA_SALIDA_DETALLE.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalidaDetalleDAO {

	private final static Logger logger = Logger.getLogger(EntradaSalidaDetalleDAO.class.getName());

	/**
	*	Datasource for table ENTRADA_SALIDA_DETALLE simple CRUD operations.
	*   x common paramenter.
	*/

	private static EntradaSalidaDetalleDAO instance;

	private EntradaSalidaDetalleDAO(){	
		logger.debug("created EntradaSalidaDetalleDAO.");
	}

	public static EntradaSalidaDetalleDAO getInstance() {
		if(instance == null){
			instance = new EntradaSalidaDetalleDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public EntradaSalidaDetalle findBy(EntradaSalidaDetalle x) throws DAOException, EntityNotFoundException{
		EntradaSalidaDetalle r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA FROM ENTRADA_SALIDA_DETALLE "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new EntradaSalidaDetalle();
				r.setId((Integer)rs.getObject("ID"));
				r.setEntradaSalidaId((Integer)rs.getObject("ENTRADA_SALIDA_ID"));
				r.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				r.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				r.setCantidad((Integer)rs.getObject("CANTIDAD"));
				r.setPrecioVenta((Double)rs.getObject("PRECIO_VENTA"));
			} else {
				throw new EntityNotFoundException("ENTRADA_SALIDA_DETALLE NOT FOUND FOR ID="+x.getId());
			}
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	}

    public ArrayList<EntradaSalidaDetalle> findAll() throws DAOException {
		ArrayList<EntradaSalidaDetalle> r = new ArrayList<EntradaSalidaDetalle>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA FROM ENTRADA_SALIDA_DETALLE");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				EntradaSalidaDetalle x = new EntradaSalidaDetalle();
				x.setId((Integer)rs.getObject("ID"));
				x.setEntradaSalidaId((Integer)rs.getObject("ENTRADA_SALIDA_ID"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setPrecioVenta((Double)rs.getObject("PRECIO_VENTA"));
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	};
    
    public int insert(EntradaSalidaDetalle x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_DETALLE(ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA) "+
					" VALUES(?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getEntradaSalidaId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getPrecioVenta());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setId((Integer)rsk.getObject(1));
				}
			}
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{				
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

	public int update(EntradaSalidaDetalle x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA_DETALLE SET ENTRADA_SALIDA_ID=?,PRODUCTO_CODIGO_BARRAS=?,ALMACEN_ID=?,CANTIDAD=?,PRECIO_VENTA=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getEntradaSalidaId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getPrecioVenta());
			ps.setObject(ci++,x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

    public int delete(EntradaSalidaDetalle x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM ENTRADA_SALIDA_DETALLE WHERE ID=?");
			ps.setObject(1, x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

}
