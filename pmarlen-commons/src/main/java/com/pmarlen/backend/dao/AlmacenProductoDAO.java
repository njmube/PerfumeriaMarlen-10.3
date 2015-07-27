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
import com.pmarlen.backend.model.quickviews.InventarioSucursalQuickView;
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
		logger.debug("created AlmacenProductoDAO.");
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
			
SELECT    P.CODIGO_BARRAS AS CB,AP.ALMACEN_ID AS AP_AID,AP.PRODUCTO_CODIGO_BARRAS AP_CB
FROM      PRODUCTO P
LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=1
WHERE     1=1
ORDER BY  P.NOMBRE,P.PRESENTACION

SELECT   SUM(1), AP.ALMACEN_ID
FROM     ALMACEN_PRODUCTO AP
GROUP BY AP.ALMACEN_ID;			
			*/
			ps = conn.prepareStatement(
					"SELECT    AP.ALMACEN_ID,AP.PRODUCTO_CODIGO_BARRAS,AP.CANTIDAD,AP.PRECIO,AP.UBICACION,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE,A.TIPO_ALMACEN\n" +
					"FROM      ALMACEN A,PRODUCTO P\n" +
					"LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=?\n" +
					"WHERE     1=1\n" +					
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

    public ArrayList<InventarioSucursalQuickView> findAllBySucursal(int sucursalId) throws DAOException {
		ArrayList<InventarioSucursalQuickView> r = new ArrayList<InventarioSucursalQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			/*
			
SELECT    P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE,
          R1.CANTIDAD,R1.PRECIO,
          R2.CANTIDAD,R2.PRECIO,
          R3.CANTIDAD,R3.PRECIO
FROM      PRODUCTO P,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 1
            AND    A.SUCURSAL_ID  = 1
          )
) AS R1,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 2
            AND    A.SUCURSAL_ID  = 1
          )
) AS R2,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 3
            AND    A.SUCURSAL_ID  = 1
          )
) AS R3
WHERE 1                         = 1
AND   P.CODIGO_BARRAS           = R1.PRODUCTO_CODIGO_BARRAS
AND   R1.PRODUCTO_CODIGO_BARRAS = R2.PRODUCTO_CODIGO_BARRAS
AND   R2.PRODUCTO_CODIGO_BARRAS = R3.PRODUCTO_CODIGO_BARRAS


SELECT    P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.ABREBIATURA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDADES_X_CAJA,P.UNIDAD_EMPAQUE,
          R1.CANTIDAD,R1.PRECIO,
          R2.CANTIDAD,R2.PRECIO,
          R3.CANTIDAD,R3.PRECIO
FROM      PRODUCTO P,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 1
            AND    A.SUCURSAL_ID  = ?
          )
) AS R1,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 2
            AND    A.SUCURSAL_ID  = ?
          )
) AS R2,
(
SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS
FROM      PRODUCTO P
          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(
            SELECT A.ID FROM ALMACEN A 
            WHERE  A.TIPO_ALMACEN = 3
            AND    A.SUCURSAL_ID  = ?
          )
) AS R3
WHERE 1                         = 1
AND   P.CODIGO_BARRAS           = R1.PRODUCTO_CODIGO_BARRAS
AND   R1.PRODUCTO_CODIGO_BARRAS = R2.PRODUCTO_CODIGO_BARRAS
AND   R2.PRODUCTO_CODIGO_BARRAS = R3.PRODUCTO_CODIGO_BARRAS
ORDER BY P.NOMBRE,P.PRESENTACION
			
			
			
			*/
			ps = conn.prepareStatement(
					"SELECT    P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.ABREBIATURA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDADES_X_CAJA,P.UNIDAD_EMPAQUE,\n" +
					"          R1.CANTIDAD A1_C,R1.PRECIO A1_P,\n" +
					"          R2.CANTIDAD AO_C,R2.PRECIO AO_P,\n" +
					"          R3.CANTIDAD AR_C,R3.PRECIO AR_P\n" +
					"FROM      PRODUCTO P,\n" +
					"(\n" +
					"SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS\n" +
					"FROM      PRODUCTO P\n" +
					"          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(\n" +
					"            SELECT A.ID FROM ALMACEN A \n" +
					"            WHERE  A.TIPO_ALMACEN = 1\n" +
					"            AND    A.SUCURSAL_ID  = ?\n" +
					"          )\n" +
					") AS R1,\n" +
					"(\n" +
					"SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS\n" +
					"FROM      PRODUCTO P\n" +
					"          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(\n" +
					"            SELECT A.ID FROM ALMACEN A \n" +
					"            WHERE  A.TIPO_ALMACEN = 2\n" +
					"            AND    A.SUCURSAL_ID  = ?\n" +
					"          )\n" +
					") AS R2,\n" +
					"(\n" +
					"SELECT    AP.CANTIDAD, AP.PRECIO, AP.PRODUCTO_CODIGO_BARRAS\n" +
					"FROM      PRODUCTO P\n" +
					"          LEFT JOIN ALMACEN_PRODUCTO AP  ON P.CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=(\n" +
					"            SELECT A.ID FROM ALMACEN A \n" +
					"            WHERE  A.TIPO_ALMACEN = 3\n" +
					"            AND    A.SUCURSAL_ID  = ?\n" +
					"          )\n" +
					") AS R3\n" +
					"WHERE 1                         = 1\n" +
					"AND   P.CODIGO_BARRAS           = R1.PRODUCTO_CODIGO_BARRAS\n" +
					"AND   R1.PRODUCTO_CODIGO_BARRAS = R2.PRODUCTO_CODIGO_BARRAS\n" +
					"AND   R2.PRODUCTO_CODIGO_BARRAS = R3.PRODUCTO_CODIGO_BARRAS\n" +
					"ORDER BY P.NOMBRE,P.PRESENTACION");
			ps.setInt(1, sucursalId);
			ps.setInt(2, sucursalId);
			ps.setInt(3, sucursalId);
			
			rs = ps.executeQuery();			
			while(rs.next()) {
				InventarioSucursalQuickView x = new InventarioSucursalQuickView();
				
				x.setCodigoBarras((String)rs.getObject("CODIGO_BARRAS"));
				x.setIndustria((String)rs.getObject("INDUSTRIA"));
				x.setLinea((String)rs.getObject("LINEA"));
				x.setMarca((String)rs.getObject("MARCA"));
				x.setNombre((String)rs.getObject("NOMBRE"));
				x.setPresentacion((String)rs.getObject("PRESENTACION"));
				x.setAbrebiatura((String)rs.getObject("ABREBIATURA"));
				x.setUnidadesXCaja((Integer)rs.getObject("UNIDADES_X_CAJA"));
				x.setContenido((String)rs.getObject("CONTENIDO"));
				x.setUnidadMedida((String)rs.getObject("UNIDAD_MEDIDA"));
				x.setUnidadEmpaque((String)rs.getObject("UNIDAD_EMPAQUE"));
				x.setCosto(0.0);
				x.setCostoVenta(0.0);
				
				x.setA1c((Integer)rs.getObject("A1_C"));
				x.setaOc((Integer)rs.getObject("AO_C"));
				x.setaRc((Integer)rs.getObject("AR_C"));
				
				x.setA1p((Double)rs.getObject("A1_P"));
				x.setaOp((Double)rs.getObject("AO_P"));
				x.setaRp((Double)rs.getObject("AR_P"));
				
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
