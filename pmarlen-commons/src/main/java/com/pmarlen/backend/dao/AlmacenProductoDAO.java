/**
 * AlmacenProductoDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import com.pmarlen.backend.model.*;
import com.pmarlen.backend.model.quickviews.AlmacenProductoQuickView;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for AlmacenProductoDAO of Table ALMACEN_PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
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

    public ArrayList<AlmacenProductoQuickView> findAllByAlmacen(int almacenId) throws DAOException {
		ArrayList<AlmacenProductoQuickView> r = new ArrayList<AlmacenProductoQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			/*
SELECT    AP.ALMACEN_ID,AP.PRODUCTO_CODIGO_BARRAS,AP.CANTIDAD,AP.PRECIO,AP.UBICACION,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE
FROM      PRODUCTO P
LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS
WHERE     1=1
AND       AP.ALMACEN_ID=1
ORDER BY  P.NOMBRE,P.PRESENTACION
			
SELECT    AP.ALMACEN_ID,AP.PRODUCTO_CODIGO_BARRAS,AP.CANTIDAD,P.NOMBRE
FROM      PRODUCTO P
LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS
WHERE     1=1
AND       AP.ALMACEN_ID=1
ORDER BY  P.NOMBRE,P.PRESENTACION
			
SELECT   * 
FROM     MOVIMIENTO_HISTORICO_PRODUCTO 
WHERE    1=1
AND      ALMACEN_ID=1 
AND     PRODUCTO_CODIGO_BARRAS='7891024136089'
			
			
SELECT   ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,COUNT(1) 
FROM     MOVIMIENTO_HISTORICO_PRODUCTO 
WHERE    1=1
AND      ALMACEN_ID=1 
GROUP BY ALMACEN_ID,PRODUCTO_CODIGO_BARRAS
			
			*/
			ps = conn.prepareStatement(
					"SELECT    AP.ALMACEN_ID,AP.PRODUCTO_CODIGO_BARRAS,AP.CANTIDAD,AP.PRECIO,AP.UBICACION,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE,A.TIPO_ALMACEN\n" +
					"FROM      ALMACEN A,PRODUCTO P\n" +
					"LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS\n" +
					"WHERE     1=1\n" +
					"AND       AP.ALMACEN_ID=?\n" +
					"AND       AP.ALMACEN_ID=A.ID\n" +
					"ORDER BY  P.NOMBRE,P.PRESENTACION");
			ps.setInt(1, almacenId);
			
			rs = ps.executeQuery();
			int rowId=0;
			while(rs.next()) {
				AlmacenProductoQuickView x = new AlmacenProductoQuickView();
				
				x.setRowId(rowId++);
				
				x.setAlmacenId((Integer)rs.getObject("ALMACEN_ID"));
				x.setProductoCodigoBarras((String)rs.getObject("PRODUCTO_CODIGO_BARRAS"));
				x.setCantidad((Integer)rs.getObject("CANTIDAD"));
				x.setPrecio((Double)rs.getObject("PRECIO"));
				x.setUbicacion((String)rs.getObject("UBICACION"));
				
				x.setProductoNombre(rs.getString("NOMBRE"));
				x.setProductoPresentacion(rs.getString("PRESENTACION"));
				x.setProductoIndustria(rs.getString("INDUSTRIA"));
				x.setProductoMarca(rs.getString("MARCA"));
				x.setProductoLinea(rs.getString("LINEA"));
				x.setProductoContenido(rs.getString("CONTENIDO"));
				x.setProductoUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
				x.setProductoUnidadEmpaque(rs.getString("UNIDAD_EMPAQUE"));
				
				x.setAlmacenTipoDescripcion(Constants.getDescripcionTipoAlmacen(rs.getInt("TIPO_ALMACEN")).toUpperCase());
				
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
			ps = conn.prepareStatement("UPDATE ALMACEN_PRODUCTO SET CANTIDAD=?,PRECIO=?,UBICACION=? WHERE PRODUCTO_CODIGO_BARRAS=? AND ALMACEN_ID=?");
			
			int ci=1;
			
			ps.setObject(ci++,x.getCantidad());
			ps.setObject(ci++,x.getPrecio());
			ps.setObject(ci++,x.getUbicacion());
			ps.setObject(ci++,x.getProductoCodigoBarras());
			ps.setObject(ci++,x.getAlmacenId());
			
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
