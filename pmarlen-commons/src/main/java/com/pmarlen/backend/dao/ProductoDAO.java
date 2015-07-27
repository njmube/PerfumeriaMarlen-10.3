/**
 * ProductoDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import com.pmarlen.backend.model.*;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.ProductoQuickView;
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
 * Class for ProductoDAO of Table PRODUCTO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/02/07 21:02
 */

public class ProductoDAO {

	private final static Logger logger = Logger.getLogger(ProductoDAO.class.getName());

	/**
	*	Datasource for table PRODUCTO simple CRUD operations.
	*   x common paramenter.
	*/

	private static ProductoDAO instance;

	private ProductoDAO(){	
		logger.debug("created ProductoDAO.");
	}

	public static ProductoDAO getInstance() {
		if(instance == null){
			instance = new ProductoDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

	public Producto findBy(Producto x) throws DAOException, EntityNotFoundException {
		Producto r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,UNIDAD_EMPAQUE,COSTO,COSTO_VENTA FROM PRODUCTO "
					+ "WHERE CODIGO_BARRAS=?"
			);
			ps.setString(1, x.getCodigoBarras());
			
			rs = ps.executeQuery();
			if (rs.next()) {
				r = new Producto();
				r.setCodigoBarras((String) rs.getObject("CODIGO_BARRAS"));				
				r.setIndustria((String) rs.getObject("INDUSTRIA"));
				r.setLinea((String) rs.getObject("LINEA"));
				r.setMarca((String) rs.getObject("MARCA"));
				r.setNombre((String) rs.getObject("NOMBRE"));
				r.setPresentacion((String) rs.getObject("PRESENTACION"));
				r.setAbrebiatura((String) rs.getObject("ABREBIATURA"));
				r.setUnidadesXCaja((Integer) rs.getObject("UNIDADES_X_CAJA"));
				r.setContenido((String) rs.getObject("CONTENIDO"));
				r.setUnidadMedida((String) rs.getObject("UNIDAD_MEDIDA"));
				r.setUnidadEmpaque((String) rs.getObject("UNIDAD_EMPAQUE"));
				r.setCosto((Double) rs.getObject("COSTO"));
				r.setCostoVenta((Double) rs.getObject("COSTO_VENTA"));
			} else {
				throw new EntityNotFoundException("PRODUCTO NOT FOUND FOR CODIGO_BARRAS=" + x.getCodigoBarras());
			}
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:" + ex.getMessage());
				}
			}
		}
		return r;		
	}

	public EntradaSalidaDetalleQuickView findByCodigo(int almacenId, String codigo) throws DAOException {
		logger.info("->findAllExclusiveByCodigo");
		EntradaSalidaDetalleQuickView x = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String query = "SELECT P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.UNIDADES_X_CAJA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE,AP.PRECIO,AP.CANTIDAD,AP.ALMACEN_ID,A.TIPO_ALMACEN\n"
					+ "FROM   PRODUCTO P,ALMACEN_PRODUCTO AP,ALMACEN A \n"
					+ "WHERE  1=1\n"
					+ "AND    P.CODIGO_BARRAS=AP.PRODUCTO_CODIGO_BARRAS\n"
					+ "AND    AP.ALMACEN_ID=?\n"
					+ "AND    AP.ALMACEN_ID=A.ID\n"
					+ "AND    P.CODIGO_BARRAS=?\n"
					+ "ORDER BY P.NOMBRE,P.PRESENTACION,P.LINEA,P.MARCA";
			logger.info("->query:"+query);
			
			ps = conn.prepareStatement(query);
			/*

			 SELECT P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,AP.PRECIO,AP.CANTIDAD
			 FROM   PRODUCTO P,ALMACEN_PRODUCTO AP 
			 WHERE  1=1
			 AND    P.CODIGO_BARRAS=AP.PRODUCTO_CODIGO_BARRAS
			 AND    AP.ALMACEN_ID=1
			 AND    UPPER(CONCAT(P.CODIGO_BARRAS,'|',P.NOMBRE,'|',P.PRESENTACION,'|',P.INDUSTRIA,'|',P.MARCA,'|',P.LINEA,'|',P.CONTENIDO,'|',P.UNIDAD_MEDIDA,'|',AP.PRECIO,'|',AP.CANTIDAD)) REGEXP '.*ADIDAS.*'
			 AND    UPPER(CONCAT(P.CODIGO_BARRAS,'|',P.NOMBRE,'|',P.PRESENTACION,'|',P.INDUSTRIA,'|',P.MARCA,'|',P.LINEA,'|',P.CONTENIDO,'|',P.UNIDAD_MEDIDA,'|',AP.PRECIO,'|',AP.CANTIDAD)) REGEXP '.*ROLLON.*'			
			 ORDER BY P.NOMBRE,P.PRESENTACION,P.LINEA,P.MARCA;
			 */
			ps.setInt(1, almacenId);
			ps.setString(2, codigo);
			logger.debug("->findAllExclusiveByCodigo: set parameters, ok");

			rs = ps.executeQuery();
			if (rs.next()) {
				x = new EntradaSalidaDetalleQuickView();

				x.setAlmacenId(almacenId);
				x.setProductoCodigoBarras(rs.getString("CODIGO_BARRAS"));
				x.setProductoNombre(rs.getString("NOMBRE"));
				x.setProductoPresentacion(rs.getString("PRESENTACION"));
				x.setProductoIndustria(rs.getString("INDUSTRIA"));
				x.setProductoMarca(rs.getString("MARCA"));
				x.setProductoLinea(rs.getString("LINEA"));
				x.setProductoUnidadesPorCaja(rs.getObject("UNIDADES_X_CAJA").toString());
				
				x.setProductoContenido(rs.getString("CONTENIDO"));
				x.setProductoUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
				x.setProductoUnidadEmpaque(rs.getString("UNIDAD_EMPAQUE"));

				x.setApPrecio(rs.getDouble("PRECIO"));
				x.setApCantidad(rs.getInt("CANTIDAD"));
				x.setApTipoAlmacen(rs.getInt("TIPO_ALMACEN"));

				x.setAlmacenId(rs.getInt("ALMACEN_ID"));
				x.setCantidad(0);
				x.setPrecioVenta(x.getApPrecio());

			}
			logger.info("->findAllExclusiveByCodigo: x="+x);
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:" + ex.getMessage());
				}
			}
		}
		return x;
	}

	public ArrayList<EntradaSalidaDetalleQuickView> findAllByDesc(int almacenId, String desc,boolean exclusive) throws DAOException {
		logger.debug("almacenId="+almacenId+", desc="+desc+", exclusive="+exclusive);
		ArrayList<EntradaSalidaDetalleQuickView> r = new ArrayList<EntradaSalidaDetalleQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {

			String allWords[] = desc.split("[ ]+");
			StringBuffer regExpAll = new StringBuffer();
			int countWords=0;
			for (String w : allWords) {
				if(countWords>0){
					if(exclusive){
						regExpAll.append("AND");
					}else {
						regExpAll.append("OR");
					}
				}
				regExpAll.append(" UPPER(CONCAT(P.CODIGO_BARRAS,'|',P.NOMBRE,'|',P.PRESENTACION,'|',P.INDUSTRIA,'|',P.MARCA,'|',P.LINEA)) REGEXP '.*");
				regExpAll.append(w);
				regExpAll.append(".*'\n");
				countWords++;
			}

			conn = getConnection();
			String extendedQuery = "SELECT P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.UNIDADES_X_CAJA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDAD_EMPAQUE,AP.PRECIO,AP.CANTIDAD,AP.ALMACEN_ID,A.TIPO_ALMACEN\n"
					+ "FROM   PRODUCTO P,ALMACEN_PRODUCTO AP,ALMACEN A \n"
					+ "WHERE  1=1\n"
					+ "AND    P.CODIGO_BARRAS=AP.PRODUCTO_CODIGO_BARRAS\n"
					+ "AND    AP.ALMACEN_ID=?\n"
					+ "AND    AP.ALMACEN_ID=A.ID\n"
					+ "AND(\n"
					+ regExpAll
					+ ")\n"
					+ "ORDER BY P.NOMBRE,P.PRESENTACION,P.LINEA,P.MARCA";

			ps = conn.prepareStatement(extendedQuery);
			
			logger.debug("Query->"+extendedQuery+"<-");
			ps.setInt(1, almacenId);

			rs = ps.executeQuery();
			while (rs.next()) {
				EntradaSalidaDetalleQuickView x = new EntradaSalidaDetalleQuickView();

				x.setAlmacenId(almacenId);
				x.setProductoCodigoBarras(rs.getString("CODIGO_BARRAS"));				
				x.setProductoNombre(rs.getString("NOMBRE"));
				x.setProductoPresentacion(rs.getString("PRESENTACION"));
				x.setProductoIndustria(rs.getString("INDUSTRIA"));
				x.setProductoMarca(rs.getString("MARCA"));
				x.setProductoLinea(rs.getString("LINEA"));
				x.setProductoUnidadesPorCaja(rs.getObject("UNIDADES_X_CAJA").toString());
				x.setProductoContenido(rs.getString("CONTENIDO"));
				x.setProductoUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
				x.setProductoUnidadEmpaque(rs.getString("UNIDAD_EMPAQUE"));

				x.setApPrecio(rs.getDouble("PRECIO"));
				x.setApCantidad(rs.getInt("CANTIDAD"));
				x.setApTipoAlmacen(rs.getInt("TIPO_ALMACEN"));

				x.setAlmacenId(rs.getInt("ALMACEN_ID"));
				x.setCantidad(0);
				x.setPrecioVenta(x.getApPrecio());

				r.add(x);
			}
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:" + ex.getMessage());
				}
			}
		}
		return r;
	}

    public ArrayList<ProductoQuickView> findAll() throws DAOException {
		ArrayList<ProductoQuickView> r = new ArrayList<ProductoQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,UNIDAD_EMPAQUE,COSTO,COSTO_VENTA FROM PRODUCTO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				ProductoQuickView x = new ProductoQuickView();
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
				x.setCosto((Double)rs.getObject("COSTO"));
				x.setCostoVenta((Double)rs.getObject("COSTO_VENTA"));
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
    public ArrayList<ProductoQuickView> findAllForMultiediaShow(int almacenId) throws DAOException {
		ArrayList<ProductoQuickView> r = new ArrayList<ProductoQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT     CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,UNIDAD_EMPAQUE,COSTO,COSTO_VENTA,\n" +
										"          MULTIMEDIO_ID,M.MIME_TYPE,M.RUTA_CONTENIDO,M.SIZE_BYTES,M.NOMBRE_ARCHIVO,\n" +
										"          ALMACEN_ID,PRECIO\n" +
										"FROM      PRODUCTO P\n" +
										"LEFT JOIN PRODUCTO_MULTIMEDIO PM ON P.CODIGO_BARRAS  = PM.PRODUCTO_CODIGO_BARRAS\n" +
										"LEFT JOIN MULTIMEDIO          M  ON PM.MULTIMEDIO_ID = M.ID\n" +
										"LEFT JOIN ALMACEN_PRODUCTO    AP ON P.CODIGO_BARRAS  = AP.PRODUCTO_CODIGO_BARRAS\n" +
										"WHERE     1=1\n" +
										"AND       AP.ALMACEN_ID=? \n" +
										"ORDER BY  INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION");
			ps.setInt(1, almacenId);
			/*
SELECT    CODIGO_BARRAS,
          MULTIMEDIO_ID,M.MIME_TYPE,M.RUTA_CONTENIDO,M.SIZE_BYTES,M.NOMBRE_ARCHIVO,
          ALMACEN_ID,PRECIO
FROM      PRODUCTO P
LEFT JOIN PRODUCTO_MULTIMEDIO PM ON P.CODIGO_BARRAS  = PM.PRODUCTO_CODIGO_BARRAS
LEFT JOIN MULTIMEDIO          M  ON PM.MULTIMEDIO_ID = M.ID
LEFT JOIN ALMACEN_PRODUCTO    AP ON P.CODIGO_BARRAS  = AP.PRODUCTO_CODIGO_BARRAS
WHERE     1=1
AND       AP.ALMACEN_ID=1;
			
SELECT    CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,UNIDAD_EMPAQUE,COSTO,COSTO_VENTA,
          MULTIMEDIO_ID,M.MIME_TYPE,M.RUTA_CONTENIDO,M.SIZE_BYTES,M.NOMBRE_ARCHIVO
FROM      PRODUCTO P
LEFT JOIN PRODUCTO_MULTIMEDIO PM ON P.CODIGO_BARRAS  = PM.PRODUCTO_CODIGO_BARRAS
LEFT JOIN MULTIMEDIO          M  ON PM.MULTIMEDIO_ID = M.ID;
			
SELECT    CODIGO_BARRAS
          ALMACEN_ID,PRECIO
FROM      PRODUCTO P
LEFT JOIN ALMACEN_PRODUCTO    AP ON P.CODIGO_BARRAS  = AP.PRODUCTO_CODIGO_BARRAS
WHERE     1=1
AND       AP.ALMACEN_ID=1;
						
			*/
			rs = ps.executeQuery();
			ProductoQuickView x = null;
			while(rs.next()) {
				String cb=(String)rs.getObject("CODIGO_BARRAS");
				if(x!=null){
					if(x.getCodigoBarras().equalsIgnoreCase(cb)){
						// EL MISMO
					} else {
						// CAMBIO PRODUCTO
						r.add(x);
						x = new ProductoQuickView();
					}
				}else {
					x = new ProductoQuickView();
				}
				
				x.setCodigoBarras(cb);
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
				x.setCosto((Double)rs.getObject("COSTO"));
				x.setCostoVenta((Double)rs.getObject("COSTO_VENTA"));
				
				Integer multimedioID=(Integer)rs.getObject("MULTIMEDIO_ID");
				if(multimedioID != null){
					Multimedio m = new Multimedio();

					m.setId((Integer)rs.getObject("MULTIMEDIO_ID"));
					m.setMimeType((String)rs.getObject("MIME_TYPE"));
					m.setRutaContenido((String)rs.getObject("RUTA_CONTENIDO"));
					m.setSizeBytes((Integer)rs.getObject("SIZE_BYTES"));
					m.setNombreArchivo((String)rs.getObject("NOMBRE_ARCHIVO"));
					
					x.addMultimedio(m);
				}
				
				x.setAlmacenId(rs.getInt("ALMACEN_ID"));
				x.setPrecio(rs.getDouble("PRECIO"));
				
				//r.add(x);
			}
			if(x!=null){
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
    
    public int insert(Producto x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO PRODUCTO(CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,UNIDAD_EMPAQUE,COSTO,COSTO_VENTA) "+
					" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getCodigoBarras());
			ps.setObject(ci++,x.getIndustria());
			ps.setObject(ci++,x.getLinea());
			ps.setObject(ci++,x.getMarca());
			ps.setObject(ci++,x.getNombre());
			ps.setObject(ci++,x.getPresentacion());
			ps.setObject(ci++,x.getAbrebiatura());
			ps.setObject(ci++,x.getUnidadesXCaja());
			ps.setObject(ci++,x.getContenido());
			ps.setObject(ci++,x.getUnidadMedida());
			ps.setObject(ci++,x.getUnidadEmpaque());
			ps.setObject(ci++,x.getCosto());
			ps.setObject(ci++,x.getCostoVenta());

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

	public int update(Producto x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE PRODUCTO SET INDUSTRIA=?,LINEA=?,MARCA=?,NOMBRE=?,PRESENTACION=?,ABREBIATURA=?,UNIDADES_X_CAJA=?,CONTENIDO=?,UNIDAD_MEDIDA=?,UNIDAD_EMPAQUE=?,COSTO=?,COSTO_VENTA=? "+
					" WHERE CODIGO_BARRAS=?");
			
			int ci=1;
			ps.setObject(ci++,x.getIndustria());
			ps.setObject(ci++,x.getLinea());
			ps.setObject(ci++,x.getMarca());
			ps.setObject(ci++,x.getNombre());
			ps.setObject(ci++,x.getPresentacion());
			ps.setObject(ci++,x.getAbrebiatura());
			ps.setObject(ci++,x.getUnidadesXCaja());
			ps.setObject(ci++,x.getContenido());
			ps.setObject(ci++,x.getUnidadMedida());
			ps.setObject(ci++,x.getUnidadEmpaque());
			ps.setObject(ci++,x.getCosto());
			ps.setObject(ci++,x.getCostoVenta());
			ps.setObject(ci++,x.getCodigoBarras());
			
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

    public int delete(Producto x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM PRODUCTO WHERE CODIGO_BARRAS=?");
			ps.setObject(1, x.getCodigoBarras());
			
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
