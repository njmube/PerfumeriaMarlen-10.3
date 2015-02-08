/**
 * MovimientoHistoricoProductoDAO
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
 * Class for MovimientoHistoricoProductoDAO of Table MOVIMIENTO_HISTORICO_PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/02/07 21:02
 */

public class MovimientoHistoricoProductoDAO {

	private final static Logger logger = Logger.getLogger(MovimientoHistoricoProductoDAO.class.getName());

	/**
	*	Datasource for table MOVIMIENTO_HISTORICO_PRODUCTO simple CRUD operations.
	*   x common paramenter.
	*/

	private static MovimientoHistoricoProductoDAO instance;

	private MovimientoHistoricoProductoDAO(){	
		logger.fine("created MovimientoHistoricoProductoDAO.");
	}

	public static MovimientoHistoricoProductoDAO getInstance() {
		if(instance == null){
			instance = new MovimientoHistoricoProductoDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public MovimientoHistoricoProducto findBy(MovimientoHistoricoProducto x) throws DAOException, EntityNotFoundException{
		MovimientoHistoricoProducto r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS FROM MOVIMIENTO_HISTORICO_PRODUCTO "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new MovimientoHistoricoProducto();
				r.setId((Integer)rs.getObject("ID"));
				r.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				r.setFecha((Timestamp)rs.getObject("FECHA"));
				r.setTipoMovimiento((Integer)rs.getObject("TIPO_MOVIMIENTO"));
				r.setCantidad((Integer)rs.getObject("CANTIDAD"));
				r.setCosto((Double)rs.getObject("COSTO"));
				r.setPrecio((Double)rs.getObject("PRECIO"));
				r.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				r.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
			} else {
				throw new EntityNotFoundException("MOVIMIENTO_HISTORICO_PRODUCTO NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<MovimientoHistoricoProducto> findAll() throws DAOException {
		ArrayList<MovimientoHistoricoProducto> r = new ArrayList<MovimientoHistoricoProducto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS FROM MOVIMIENTO_HISTORICO_PRODUCTO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				MovimientoHistoricoProducto x = new MovimientoHistoricoProducto();
				x.setId((Integer)rs.getObject("ID"));
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setFecha((Timestamp)rs.getObject("FECHA"));
				x.setTipoMovimiento((Integer)rs.getObject("TIPO_MOVIMIENTO"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setCosto((Double)rs.getObject("COSTO"));
				x.setPrecio((Double)rs.getObject("PRECIO"));
				x.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
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
    
    public int insert(MovimientoHistoricoProducto x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO(ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS) "+
					" VALUES(?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getTipoMovimiento());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getCosto());
			ps.setObject(ci++,x.getPrecio());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getProductoCodigoBarras());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setId((Integer)rsk.getObject(1));
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

	public int update(MovimientoHistoricoProducto x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE MOVIMIENTO_HISTORICO_PRODUCTO SET ALMACEN_ID=?,FECHA=?,TIPO_MOVIMIENTO=?,CANTIDAD=?,COSTO=?,PRECIO=?,USUARIO_EMAIL=?,PRODUCTO_CODIGO_BARRAS=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getTipoMovimiento());
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getCosto());
			ps.setObject(ci++,x.getPrecio());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getId());
			
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

    public int delete(MovimientoHistoricoProducto x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM MOVIMIENTO_HISTORICO_PRODUCTO WHERE ID=?");
			ps.setObject(1, x.getId());
			
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
