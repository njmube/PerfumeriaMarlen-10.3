/**
 * OfertaProductoDAO
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
 * Class for OfertaProductoDAO of Table OFERTA_PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class OfertaProductoDAO {

	private final static Logger logger = Logger.getLogger(OfertaProductoDAO.class.getName());

	/**
	*	Datasource for table OFERTA_PRODUCTO simple CRUD operations.
	*   x common paramenter.
	*/

	private static OfertaProductoDAO instance;

	private OfertaProductoDAO(){	
		logger.debug("created OfertaProductoDAO.");
	}

	public static OfertaProductoDAO getInstance() {
		if(instance == null){
			instance = new OfertaProductoDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public OfertaProducto findBy(OfertaProducto x) throws DAOException, EntityNotFoundException{
		OfertaProducto r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,TIPO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,ACTIVA,MARCA,LINEA,LEMA FROM OFERTA_PRODUCTO "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new OfertaProducto();
				r.setId((Integer)rs.getObject("ID"));
				r.setTipo((Integer)rs.getObject("TIPO"));
				r.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				r.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				r.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				r.setActiva((Integer)rs.getObject("ACTIVA"));
				r.setMarca((String)rs.getObject("MARCA"));
				r.setLinea((String)rs.getObject("LINEA"));
				r.setLema((String)rs.getObject("LEMA"));
			} else {
				throw new EntityNotFoundException("OFERTA_PRODUCTO NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<OfertaProducto> findAll() throws DAOException {
		ArrayList<OfertaProducto> r = new ArrayList<OfertaProducto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,TIPO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,ACTIVA,MARCA,LINEA,LEMA FROM OFERTA_PRODUCTO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				OfertaProducto x = new OfertaProducto();
				x.setId((Integer)rs.getObject("ID"));
				x.setTipo((Integer)rs.getObject("TIPO"));
				x.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setActiva((Integer)rs.getObject("ACTIVA"));
				x.setMarca((String)rs.getObject("MARCA"));
				x.setLinea((String)rs.getObject("LINEA"));
				x.setLema((String)rs.getObject("LEMA"));
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
    
    public int insert(OfertaProducto x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO OFERTA_PRODUCTO(TIPO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,ACTIVA,MARCA,LINEA,LEMA) "+
					" VALUES(?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getTipo());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getActiva());
			ps.setObject(ci++,x.getMarca());
			ps.setObject(ci++,x.getLinea());
			ps.setObject(ci++,x.getLema());

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

	public int update(OfertaProducto x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE OFERTA_PRODUCTO SET TIPO=?,USUARIO_EMAIL=?,PRODUCTO_CODIGO_BARRAS=?,ALMACEN_ID=?,ACTIVA=?,MARCA=?,LINEA=?,LEMA=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getTipo());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getAlmacenId());
			ps.setObject(ci++,x.getActiva());
			ps.setObject(ci++,x.getMarca());
			ps.setObject(ci++,x.getLinea());
			ps.setObject(ci++,x.getLema());
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

    public int delete(OfertaProducto x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM OFERTA_PRODUCTO WHERE ID=?");
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
