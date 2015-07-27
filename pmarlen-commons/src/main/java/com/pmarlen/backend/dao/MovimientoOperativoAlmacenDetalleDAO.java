/**
 * MovimientoOperativoAlmacenDetalleDAO
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
 * Class for MovimientoOperativoAlmacenDetalleDAO of Table MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoOperativoAlmacenDetalleDAO {

	private final static Logger logger = Logger.getLogger(MovimientoOperativoAlmacenDetalleDAO.class.getName());

	/**
	*	Datasource for table MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE simple CRUD operations.
	*   x common paramenter.
	*/

	private static MovimientoOperativoAlmacenDetalleDAO instance;

	private MovimientoOperativoAlmacenDetalleDAO(){	
		logger.debug("created MovimientoOperativoAlmacenDetalleDAO.");
	}

	public static MovimientoOperativoAlmacenDetalleDAO getInstance() {
		if(instance == null){
			instance = new MovimientoOperativoAlmacenDetalleDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public MovimientoOperativoAlmacenDetalle findBy(MovimientoOperativoAlmacenDetalle x) throws DAOException, EntityNotFoundException{
		MovimientoOperativoAlmacenDetalle r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MOVIMIENTO_OPERATIVO_ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,CANTIDAD_CONFIRMADA FROM MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new MovimientoOperativoAlmacenDetalle();
				r.setId((Integer)rs.getObject("ID"));
				r.setMovimientoOperativoAlmacenId((Integer)rs.getObject("MOVIMIENTO_OPERATIVO_ALMACEN_ID"));
				r.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				r.setCantidad((Integer)rs.getObject("CANTIDAD"));
				r.setCantidadConfirmada((Integer)rs.getObject("CANTIDAD_CONFIRMADA"));
			} else {
				throw new EntityNotFoundException("MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<MovimientoOperativoAlmacenDetalle> findAll() throws DAOException {
		ArrayList<MovimientoOperativoAlmacenDetalle> r = new ArrayList<MovimientoOperativoAlmacenDetalle>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MOVIMIENTO_OPERATIVO_ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,CANTIDAD_CONFIRMADA FROM MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				MovimientoOperativoAlmacenDetalle x = new MovimientoOperativoAlmacenDetalle();
				x.setId((Integer)rs.getObject("ID"));
				x.setMovimientoOperativoAlmacenId((Integer)rs.getObject("MOVIMIENTO_OPERATIVO_ALMACEN_ID"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setCantidadConfirmada((Integer)rs.getObject("CANTIDAD_CONFIRMADA"));
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
    
    public int insert(MovimientoOperativoAlmacenDetalle x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE(MOVIMIENTO_OPERATIVO_ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,CANTIDAD_CONFIRMADA) "+
					" VALUES(?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMovimientoOperativoAlmacenId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getCantidadConfirmada());

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

	public int update(MovimientoOperativoAlmacenDetalle x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE SET MOVIMIENTO_OPERATIVO_ALMACEN_ID=?,PRODUCTO_CODIGO_BARRAS=?,CANTIDAD=?,CANTIDAD_CONFIRMADA=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMovimientoOperativoAlmacenId());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getCantidadConfirmada());
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

    public int delete(MovimientoOperativoAlmacenDetalle x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE WHERE ID=?");
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
