/**
 * MovimientoHistoricoProductoDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import com.pmarlen.backend.model.*;
import com.pmarlen.backend.model.quickviews.MovimientoHistoricoProductoQuickView;
import com.pmarlen.model.Constants;
import com.tracktopell.jdbc.DataSourceFacade;

import java.io.ByteArrayInputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;	
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;

/**
 * Class for MovimientoHistoricoProductoDAO of Table MOVIMIENTO_HISTORICO_PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoHistoricoProductoDAO {

	private final static Logger logger = Logger.getLogger(MovimientoHistoricoProductoDAO.class.getName());

	/**
	*	Datasource for table MOVIMIENTO_HISTORICO_PRODUCTO simple CRUD operations.
	*   x common paramenter.
	*/

	private static MovimientoHistoricoProductoDAO instance;

	private MovimientoHistoricoProductoDAO(){	
		logger.debug("created MovimientoHistoricoProductoDAO.");
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
				r.setEntradaSalidaId((Integer)rs.getObject("ENTRADA_SALIDA_ID"));
			} else {
				throw new EntityNotFoundException("MOVIMIENTO_HISTORICO_PRODUCTO NOT FOUND FOR ID="+x.getId());
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
    
	public ArrayList<String> findAllIndustrias() throws DAOException {
		ArrayList<String> r = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT DISTINCT(INDUSTRIA) FROM PRODUCTO ORDER BY INDUSTRIA");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				r.add((String)rs.getObject("INDUSTRIA"));				
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

	public ArrayList<String> findAllLineas() throws DAOException {
		ArrayList<String> r = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT DISTINCT(LINEA) FROM PRODUCTO ORDER BY LINEA");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				r.add((String)rs.getObject("LINEA"));				
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
	
	public ArrayList<String> findAllMarcas() throws DAOException {
		ArrayList<String> r = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT DISTINCT(MARCA) FROM PRODUCTO ORDER BY MARCA");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				r.add((String)rs.getObject("MARCA"));				
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

	public ArrayList<MovimientoHistoricoProductoQuickView> findAllByAlmacenAndProducto(int almacenId,String codigoBarras) throws DAOException {
		ArrayList<MovimientoHistoricoProductoQuickView> r = new ArrayList<MovimientoHistoricoProductoQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			/*
SELECT ID,ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,IF(TIPO_MOVIMIENTO>=20 AND TIPO_MOVIMIENTO<30,CANTIDAD,IF(TIPO_MOVIMIENTO>=30 AND TIPO_MOVIMIENTO<40,-1*CANTIDAD,0)) AS AFECTADO 
FROM   MOVIMIENTO_HISTORICO_PRODUCTO
WHERE  1=1
AND    ALMACEN_ID=1 
AND    PRODUCTO_CODIGO_BARRAS='7891024136089'
			
			
SELECT R1.ID,R1.ALMACEN_ID,R1.FECHA,R1.TIPO_MOVIMIENTO,R1.CANTIDAD,R1.COSTO,R1.PRECIO,R1.USUARIO_EMAIL,R1.PRODUCTO_CODIGO_BARRAS,R1.X1,SUM(R1.X1) 
FROM
(SELECT ID,ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,IF(TIPO_MOVIMIENTO>=20 AND TIPO_MOVIMIENTO<30,CANTIDAD,IF(TIPO_MOVIMIENTO>=30 AND TIPO_MOVIMIENTO<40,-1*CANTIDAD,0)) AS X1 
FROM   MOVIMIENTO_HISTORICO_PRODUCTO
WHERE  1=1
AND    ALMACEN_ID=1 
AND    PRODUCTO_CODIGO_BARRAS='7891024136089') R1;
			
			*/
			
			ps = conn.prepareStatement(
					"SELECT ID,ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ENTRADA_SALIDA_ID,IF(TIPO_MOVIMIENTO>=20 AND TIPO_MOVIMIENTO<30,CANTIDAD,"+
					"IF(TIPO_MOVIMIENTO>=30 AND TIPO_MOVIMIENTO<40,-1*CANTIDAD,0)) AS AFECTADO \n" +
					"FROM   MOVIMIENTO_HISTORICO_PRODUCTO\n" +
					"WHERE  1=1\n" +
					"AND    ALMACEN_ID=1 \n" +
					"AND    ALMACEN_ID=? AND PRODUCTO_CODIGO_BARRAS=?");
			
			ps.setInt   (1, almacenId);
			ps.setString(2, codigoBarras);
			
			rs = ps.executeQuery();
			int saldo=0;
			while(rs.next()) {
				MovimientoHistoricoProductoQuickView x = new MovimientoHistoricoProductoQuickView();
				
				x.setId((Integer)rs.getObject("ID"));
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setFecha((Timestamp)rs.getObject("FECHA"));
				x.setTipoMovimiento((Integer)rs.getObject("TIPO_MOVIMIENTO"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setCosto((Double)rs.getObject("COSTO"));
				x.setPrecio((Double)rs.getObject("PRECIO"));
				x.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setEntradaSalidaId((Integer)rs.getObject("ENTRADA_SALIDA_ID"));
				
				x.setAfectado(rs.getInt("AFECTADO"));
				x.setTipoMovDesc(Constants.getDescripcionTipoMov(x.getTipoMovimiento()));
				
				saldo +=x.getAfectado();
				x.setSaldo(saldo);
				
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
    
    public int insert(MovimientoHistoricoProducto x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO(ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ENTRADA_SALIDA_ID) "+
					" VALUES(?,?,?,?,?,?,?,?,?)"
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
			ps.setObject(ci++,x.getEntradaSalidaId());

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

	public int update(MovimientoHistoricoProducto x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE MOVIMIENTO_HISTORICO_PRODUCTO SET ALMACEN_ID=?,FECHA=?,TIPO_MOVIMIENTO=?,CANTIDAD=?,COSTO=?,PRECIO=?,USUARIO_EMAIL=?,PRODUCTO_CODIGO_BARRAS=?,ENTRADA_SALIDA_ID=? "+
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
			ps.setObject(ci++,x.getEntradaSalidaId());
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
