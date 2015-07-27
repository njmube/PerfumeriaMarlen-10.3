/**
 * EntradaSalidaDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import com.pmarlen.backend.model.Cfd;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.EntradaSalidaEstado;
import com.pmarlen.backend.model.Sucursal;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaEstadoQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.digifactws.production.client.DigifactClient;
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
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Class for EntradaSalidaDAO of Table ENTRADA_SALIDA.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalidaDAO {

	private final static Logger logger = Logger.getLogger(EntradaSalidaDAO.class.getName());

	/**
	*	Datasource for table ENTRADA_SALIDA simple CRUD operations.
	*   x common paramenter.
	*/

	private static EntradaSalidaDAO instance;

	private EntradaSalidaDAO(){	
		logger.debug("created EntradaSalidaDAO.");
	}

	public static EntradaSalidaDAO getInstance() {
		if(instance == null){
			instance = new EntradaSalidaDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

	public void actualizaCantidadPendienteParaOtrosES(ArrayList<EntradaSalidaDetalleQuickView> pvdList) throws DAOException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			
			HashSet<String> codigos = new HashSet<String>();
			int pedidoVentaId = 0;
			for (EntradaSalidaDetalleQuickView pvd : pvdList) {
				pedidoVentaId = pvd.getId();
				codigos.add(pvd.getProductoCodigoBarras());
			}

			int ncb = 0;
			StringBuffer sbCB = new StringBuffer();
			String codigosBuscar = null;
			for (String cb : codigos) {
				if (ncb == 0) {
					sbCB.append("'");
					sbCB.append(cb);
					sbCB.append("'");
			} else {
					sbCB.append(",'");
					sbCB.append(cb);
					sbCB.append("'");
			}
				ncb++;
			}
			codigosBuscar = sbCB.toString();

			logger.info("pedidoVentaId=" + pedidoVentaId + ", codigosBuscar= ->" + codigosBuscar + "<-");

			ps = conn.prepareStatement(
					"SELECT AP.CANTIDAD,ESD.PRODUCTO_CODIGO_BARRAS,ESD.ALMACEN_ID,SUM(ESD.CANTIDAD) TOT_CANTIDAD \n"
					+ "FROM   ENTRADA_SALIDA_DETALLE ESD,ENTRADA_SALIDA ES,ALMACEN_PRODUCTO AP\n"
					+ "WHERE  1 = 1 \n"
					+ "AND    ESD.PRODUCTO_CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS\n"
					+ "AND    ESD.ALMACEN_ID = AP.ALMACEN_ID\n"
					+ "AND    ESD.ENTRADA_SALIDA_ID = ES.ID \n"
					+ "AND    ES.ESTADO_ID IN (1,2,4)\n"
					+ "AND    ES.ID <> ? \n"
					+ "AND    ESD.PRODUCTO_CODIGO_BARRAS IN ("
					+ codigosBuscar
					+ ")\n"
					+ "GROUP BY ESD.PRODUCTO_CODIGO_BARRAS,ESD.ALMACEN_ID\n"
					+ "ORDER BY ESD.PRODUCTO_CODIGO_BARRAS,ESD.ALMACEN_ID,ESD.ID");

			
			ps.setInt(1, pedidoVentaId);

			rs = ps.executeQuery();
			while (rs.next()) {
				int axi = rs.getInt("ALMACEN_ID");
				String cbxi = rs.getString("PRODUCTO_CODIGO_BARRAS");
				int tcxi = rs.getInt("TOT_CANTIDAD");
				int apcxi = rs.getInt("CANTIDAD");

				logger.info("Iteration:\t" + axi + "," + cbxi + ", " + tcxi + ", " + apcxi);

				for (EntradaSalidaDetalleQuickView pvd : pvdList) {
					if (pvd.getProductoCodigoBarras().equals(cbxi) && pvd.getAlmacenId() == axi) {
						pvd.setCanPendteEnOtrosPV(tcxi);
						pvd.setApCantidad(apcxi);
						break;
					}
				}
			}
			logger.info("->OK, actualizado");

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
					logger.error("findAll:clossing:", ex);
				}
			}
		}
	}

	public EntradaSalidaQuickView findBy(EntradaSalida p) throws DAOException, EntityNotFoundException {
		EntradaSalidaQuickView x = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement psESE = null;
		ResultSet rsESE = null;

		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT	ES.ID,ES.TIPO_MOV,ES.SUCURSAL_ID,ES.ESTADO_ID,ES.FECHA_CREO,ES.USUARIO_EMAIL_CREO,ES.CLIENTE_ID,ES.FORMA_DE_PAGO_ID,ES.METODO_DE_PAGO_ID,ES.FACTOR_IVA,ES.COMENTARIOS,ES.CFD_ID,ES.NUMERO_TICKET,ES.CAJA,ES.IMPORTE_RECIBIDO,ES.APROBACION_VISA_MASTERCARD,ES.PORCENTAJE_DESCUENTO_CALCULADO,ES.PORCENTAJE_DESCUENTO_EXTRA,ES.CONDICIONES_DE_PAGO,ES.NUM_DE_CUENTA,ES.AUTORIZA_DESCUENTO,\n"
					+ "CFD.ID AS CFD_ID,\n"
					+ "S.NOMBRE AS SUCURSAL_NOMBRE,\n"
					+ "E.DESCRIPCION AS E_DESCRIPCION,\n"
					+ "U.NOMBRE_COMPLETO AS U_NOMBRE_COMPLETO,\n"
					+ "C.RFC AS C_RFC,\n"
					+ "C.RAZON_SOCIAL AS C_RAZON_SOCIAL,\n"
					+ "C.NOMBRE_ESTABLECIMIENTO AS C_NOMBRE_ESTABLECIMIENTO,\n"
					+ "FP.DESCRIPCION AS FP_DESCRIPCION,\n"
					+ "MP.DESCRIPCION AS MP_DESCRIPCION,\n"
					+ "CFD.NUM_CFD AS CFD_NUM_CFD,\n"
					+ "COUNT(1) NUM_ELEMENTOS, \n"
					+ "SUM(ESD.CANTIDAD * ESD.PRECIO_VENTA) AS IMPORTE_BRUTO\n"
					+ "FROM      ENTRADA_SALIDA_DETALLE ESD,\n"
					+ "          ENTRADA_SALIDA         ES\n"
					+ "LEFT JOIN CFD            CFD ON ES.CFD_ID            = CFD.ID\n"
					+ "LEFT JOIN SUCURSAL       S   ON ES.SUCURSAL_ID       = S.ID\n"
					+ "LEFT JOIN ESTADO         E   ON ES.ESTADO_ID         = E.ID\n"
					+ "LEFT JOIN USUARIO        U   ON ES.USUARIO_EMAIL_CREO= U.EMAIL\n"
					+ "LEFT JOIN CLIENTE        C   ON ES.CLIENTE_ID        = C.ID\n"
					+ "LEFT JOIN FORMA_DE_PAGO  FP  ON ES.FORMA_DE_PAGO_ID  = FP.ID\n"
					+ "LEFT JOIN METODO_DE_PAGO MP  ON ES.METODO_DE_PAGO_ID = MP.ID\n"
					+ "WHERE     1=1\n"
					+ "AND       ES.ID = ?\n"					
					+ "AND       ES.ID        = ESD.ENTRADA_SALIDA_ID\n"
					+ "GROUP BY  ESD.ENTRADA_SALIDA_ID\n"
					+ "ORDER BY  ES.ID DESC");

			ps.setInt(1, p.getId());

			rs = ps.executeQuery();
			while (rs.next()) {
				x = new EntradaSalidaQuickView();
				x.setId((Integer) rs.getObject("ID"));
				x.setTipoMov((Integer) rs.getObject("TIPO_MOV"));
				x.setSucursalId((Integer) rs.getObject("SUCURSAL_ID"));
				x.setEstadoId((Integer) rs.getObject("ESTADO_ID"));
				x.setFechaCreo((Timestamp) rs.getObject("FECHA_CREO"));
				x.setUsuarioEmailCreo((String) rs.getObject("USUARIO_EMAIL_CREO"));
				x.setClienteId((Integer) rs.getObject("CLIENTE_ID"));
				x.setFormaDePagoId((Integer) rs.getObject("FORMA_DE_PAGO_ID"));
				x.setMetodoDePagoId((Integer) rs.getObject("METODO_DE_PAGO_ID"));
				x.setFactorIva((Double) rs.getObject("FACTOR_IVA"));
				x.setComentarios((String) rs.getObject("COMENTARIOS"));
				x.setCfdId((Integer) rs.getObject("CFD_ID"));
				x.setNumeroTicket((String) rs.getObject("NUMERO_TICKET"));
				x.setCaja((Integer) rs.getObject("CAJA"));
				x.setImporteRecibido((Double) rs.getObject("IMPORTE_RECIBIDO"));
				x.setAprobacionVisaMastercard((String) rs.getObject("APROBACION_VISA_MASTERCARD"));
				x.setPorcentajeDescuentoCalculado((Integer) rs.getObject("PORCENTAJE_DESCUENTO_CALCULADO"));
				x.setPorcentajeDescuentoExtra((Integer) rs.getObject("PORCENTAJE_DESCUENTO_EXTRA"));
				x.setCondicionesDePago((String) rs.getObject("CONDICIONES_DE_PAGO"));
				x.setNumDeCuenta((String) rs.getObject("NUM_DE_CUENTA"));
				x.setAutorizaDescuento((Integer) rs.getObject("AUTORIZA_DESCUENTO"));

				x.setSucursalNombre((String) rs.getObject("SUCURSAL_NOMBRE"));
				x.setEstadoDescripcion((String) rs.getObject("E_DESCRIPCION"));
				x.setUsuarioNombreCompleto((String) rs.getObject("U_NOMBRE_COMPLETO"));
				x.setClienteRFC((String) rs.getObject("C_RFC"));
				x.setClienteRazonSocial((String) rs.getObject("C_RAZON_SOCIAL"));
				x.setClienteNombreEstablecimiento((String) rs.getObject("C_NOMBRE_ESTABLECIMIENTO"));
				x.setMetodoDePagoDescripcion((String) rs.getObject("MP_DESCRIPCION"));
				x.setFormaDePagoDescripcion((String) rs.getObject("FP_DESCRIPCION"));
				x.setCdfNumCFD((String) rs.getObject("CFD_NUM_CFD"));

				x.setNumElementos(rs.getInt("NUM_ELEMENTOS"));
				x.setImporteBruto(rs.getDouble("IMPORTE_BRUTO"));

				x.setImporteNoGravado(x.getImporteBruto() / (1.0 + x.getFactorIva()));
				x.setImporteIVA(x.getImporteBruto() - x.getImporteNoGravado());
				if(x.getImporteBruto() !=null && x.getPorcentajeDescuentoCalculado()!=null && x.getPorcentajeDescuentoExtra()!=null){
					x.setImporteDescuento((x.getImporteBruto() * (x.getPorcentajeDescuentoCalculado()+x.getPorcentajeDescuentoExtra()))/100.0);
					x.setImporteTotal(x.getImporteBruto() - x.getImporteDescuento());
				} else {
					x.setImporteDescuento(0.0);
					x.setImporteTotal(x.getImporteBruto() );
				}
			}

			ArrayList<EntradaSalidaEstadoQuickView> pveList = new ArrayList<EntradaSalidaEstadoQuickView>();
			/*
			 SELECT ESE.ID,ESE.ENTRADA_SALIDA_ID,ESE.ESTADO_ID,E.DESCRIPCION,ESE.FECHA,ESE.USUARIO_EMAIL,U.NOMBRE_COMPLETO,ESE.COMENTARIOS 
			 FROM   ENTRADA_SALIDA_ESTADO ESE,ESTADO E,USUARIO U
			 WHERE  1=1
			 AND    ESE.ESTADO_ID=E.ID
			 AND    ESE.USUARIO_EMAIL = U.EMAIL
			 AND    ESE.ENTRADA_SALIDA_ID=2548
			 ORDER BY ESE.FECHA;
			 */

			psESE = conn.prepareStatement(
					"SELECT ESE.ID,ESE.ENTRADA_SALIDA_ID,ESE.ESTADO_ID,E.DESCRIPCION,ESE.FECHA,ESE.USUARIO_EMAIL,U.NOMBRE_COMPLETO,ESE.COMENTARIOS \n"
					+ "FROM   ENTRADA_SALIDA_ESTADO ESE,ESTADO E,USUARIO U\n"
					+ "WHERE  1=1\n"
					+ "AND    ESE.ESTADO_ID=E.ID\n"
					+ "AND    ESE.USUARIO_EMAIL = U.EMAIL\n"
					+ "AND    ESE.ENTRADA_SALIDA_ID=?\n"
					+ "ORDER BY ESE.FECHA DESC");
			psESE.setInt(1, p.getId());

			rsESE = psESE.executeQuery();
			EntradaSalidaEstadoQuickView z = null;
			while (rsESE.next()) {
				z = new EntradaSalidaEstadoQuickView();

				z.setId(rsESE.getInt("ID"));
				z.setEntradaSalidaId(rsESE.getInt("ENTRADA_SALIDA_ID"));
				z.setEstadoId(rsESE.getInt("ESTADO_ID"));
				z.setEstadoDescripcion(rsESE.getString("DESCRIPCION"));
				z.setFecha(rsESE.getTimestamp("FECHA"));
				z.setUsuarioEmail(rsESE.getString("USUARIO_EMAIL"));
				z.setUsuarioNombreCompleto(rsESE.getString("NOMBRE_COMPLETO"));
				z.setComentarios(rsESE.getString("COMENTARIOS"));

				pveList.add(z);
			}
			x.setPveList(pveList);
			
			if(z != null){
				x.setEstadoActualFecha(z.getFecha());
				x.setEstadoActualUsuarioEmail(z.getUsuarioEmail());
				x.setEstadoActualUsuarioNombreCompleto(z.getUsuarioNombreCompleto());
			}

		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
					ps.close();

					rsESE.close();
					psESE.close();

					conn.close();
				} catch (SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:" + ex.getMessage());
				}
			}
		}
		return x;
	}

	public ArrayList<EntradaSalidaDetalleQuickView> findAllESDByEntradaSalida(int pedidoVentaId) throws DAOException {
		ArrayList<EntradaSalidaDetalleQuickView> r = new ArrayList<EntradaSalidaDetalleQuickView>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			
			ps = conn.prepareStatement(
					"SELECT   P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,P.INDUSTRIA,P.MARCA,P.LINEA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.UNIDADES_X_CAJA,P.UNIDAD_EMPAQUE,AP.PRECIO,AP.CANTIDAD,AP.UBICACION,ESD.ID AS ESD_ID,A.ID AS ALMACEN_ID,A.TIPO_ALMACEN,ESD.CANTIDAD AS CANTIDAD_ESD,ESD.PRECIO_VENTA\n"
					+ "FROM   ENTRADA_SALIDA ES,\n"
					+ "       ENTRADA_SALIDA_DETALLE ESD,\n"
					+ "       PRODUCTO P,\n"
					+ "       ALMACEN_PRODUCTO AP,\n"
					+ "       ALMACEN A\n"
					+ "WHERE  1=1\n"
					+ "AND    ES.ID                      = ?\n"
					+ "AND    ES.ID                      = ESD.ENTRADA_SALIDA_ID\n"
					+ "AND    ESD.PRODUCTO_CODIGO_BARRAS = P.CODIGO_BARRAS\n"
					+ "AND    ESD.PRODUCTO_CODIGO_BARRAS = AP.PRODUCTO_CODIGO_BARRAS\n"
					+ "AND    AP.ALMACEN_ID=A.ID\n"
					+ "AND    A.ID=ESD.ALMACEN_ID\n"
					+ "ORDER BY ESD.ID");

			ps.setInt(1, pedidoVentaId);

			rs = ps.executeQuery();
			long ct=System.currentTimeMillis();
			while (rs.next()) {
				EntradaSalidaDetalleQuickView x = new EntradaSalidaDetalleQuickView();
				x.setEntradaSalidaId(pedidoVentaId);
				x.setProductoCodigoBarras(rs.getString("CODIGO_BARRAS"));				
				x.setProductoNombre(rs.getString("NOMBRE"));
				x.setProductoPresentacion(rs.getString("PRESENTACION"));
				x.setProductoIndustria(rs.getString("INDUSTRIA"));
				x.setProductoMarca(rs.getString("MARCA"));
				x.setProductoLinea(rs.getString("LINEA"));
				x.setProductoContenido(rs.getString("CONTENIDO"));
				x.setProductoUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
				x.setPrecioVenta(rs.getDouble("PRECIO_VENTA"));
				x.setProductoUnidadEmpaque(rs.getString("UNIDAD_EMPAQUE"));
				x.setProductoUnidadesPorCaja(rs.getString("UNIDADES_X_CAJA"));
				x.setCantidad(rs.getInt("CANTIDAD_ESD"));
				x.setId(rs.getInt("ESD_ID"));
				x.setAlmacenId(rs.getInt("ALMACEN_ID"));
				x.setApTipoAlmacen(rs.getInt("TIPO_ALMACEN"));
				x.setApCantidad(rs.getInt("CANTIDAD"));
				x.setApUbicacion(rs.getString("UBICACION"));

				x.setRowId(ct++);
				
				logger.info("\t==>>"+x.getCantidad()+" X ["+x.getProductoCodigoBarras()+"] @ "+x.getAlmacenId()+" ("+x.getProductoUnidadEmpaque()+":"+x.getProductoContenido()+" "+x.getProductoUnidadMedida()+")["+x.getProductoUnidadesPorCaja()+"]");
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
    
	public ArrayList<EntradaSalidaQuickView> findAllActivePendidos() throws DAOException {
		return findAllActive(Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA,1);
	}
	
	public ArrayList<EntradaSalidaQuickView> findAllActiveDevs() throws DAOException {
		return findAllActive(Constants.TIPO_MOV_ENTRADA_ALMACEN_DEVOLUCION,1);
	}
	
	public ArrayList<EntradaSalidaQuickView> findAllActiveCompras() throws DAOException {
		return findAllActive(Constants.TIPO_MOV_ENTRADA_ALMACEN_COMPRA,1);
	}
	
	private ArrayList<EntradaSalidaQuickView> findAllActive(int tipoMov,int sucursalId) throws DAOException {
		return findAllActive(tipoMov,sucursalId,true);
	}
	
	private ArrayList<EntradaSalidaQuickView> findAllActive(int tipoMov,int sucursalId,boolean active) throws DAOException {
		logger.info("->findAllActive(tipoMov="+tipoMov+",sucursalId="+sucursalId+",active="+active+")");
		ArrayList<EntradaSalidaQuickView> r = new ArrayList<EntradaSalidaQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			
			String q="SELECT	ES.ID ES_ID,ES.TIPO_MOV,ES.SUCURSAL_ID,ES.ESTADO_ID,ES.FECHA_CREO,ES.USUARIO_EMAIL_CREO,ES.CLIENTE_ID,ES.FORMA_DE_PAGO_ID,ES.METODO_DE_PAGO_ID,ES.FACTOR_IVA,ES.COMENTARIOS,ES.CFD_ID,ES.NUMERO_TICKET,ES.CAJA,ES.IMPORTE_RECIBIDO,ES.APROBACION_VISA_MASTERCARD,ES.PORCENTAJE_DESCUENTO_CALCULADO,ES.PORCENTAJE_DESCUENTO_EXTRA,ES.CONDICIONES_DE_PAGO,ES.NUM_DE_CUENTA,ES.AUTORIZA_DESCUENTO,\n"
					+ "CFD.ID AS CFD_ID,\n"
					+ "S.NOMBRE AS SUCURSAL_NOMBRE,\n"
					+ "E.DESCRIPCION AS E_DESCRIPCION,\n"
					+ "U.NOMBRE_COMPLETO AS U_NOMBRE_COMPLETO,\n"
					+ "C.RFC AS C_RFC,\n"
					+ "C.RAZON_SOCIAL AS C_RAZON_SOCIAL,\n"
					+ "C.NOMBRE_ESTABLECIMIENTO AS C_NOMBRE_ESTABLECIMIENTO,\n"
					+ "FP.DESCRIPCION AS FP_DESCRIPCION,\n"
					+ "MP.DESCRIPCION AS MP_DESCRIPCION,\n"
					+ "CFD.NUM_CFD AS CFD_NUM_CFD,\n"
					+ "COUNT(1) NUM_ELEMENTOS, \n"
					+ "SUM(ESD.CANTIDAD * ESD.PRECIO_VENTA) AS IMPORTE_BRUTO, \n"
					+ "ESE.FECHA AS FECHA_ACTUALIZO, \n"		
					+ "ESE.USUARIO_EMAIL AS USUARIO_ACTUALIZO\n"
					+ "FROM      ENTRADA_SALIDA_ESTADO  ESE,\n"
					+ "          ENTRADA_SALIDA_DETALLE ESD,\n"		
					+ "          ENTRADA_SALIDA         ES\n"
					+ "LEFT JOIN CFD            CFD ON ES.CFD_ID      = CFD.ID\n"
					+ "LEFT JOIN SUCURSAL       S   ON ES.SUCURSAL_ID       = S.ID\n"
					+ "LEFT JOIN ESTADO         E   ON ES.ESTADO_ID         = E.ID\n"
					+ "LEFT JOIN USUARIO        U   ON ES.USUARIO_EMAIL_CREO= U.EMAIL\n"
					+ "LEFT JOIN CLIENTE        C   ON ES.CLIENTE_ID        = C.ID\n"
					+ "LEFT JOIN FORMA_DE_PAGO  FP  ON ES.FORMA_DE_PAGO_ID  = FP.ID\n"
					+ "LEFT JOIN METODO_DE_PAGO MP  ON ES.METODO_DE_PAGO_ID = MP.ID\n"
					+ "WHERE     1=1\n"
					+ (active ? "AND       ES.ESTADO_ID IN (1,2,4)\n":
							    "AND       ES.ESTADO_ID >  4\n" )
					+ "AND       ES.ID        = ESD.ENTRADA_SALIDA_ID\n"
					+ "AND       ES.ID        = ESE.ENTRADA_SALIDA_ID\n"
					+ "AND       ES.ESTADO_ID = ESE.ESTADO_ID\n"		
					+ "AND       ES.TIPO_MOV  = ?\n"
					+ "AND       ES.SUCURSAL_ID= ?\n"
					+ "GROUP BY  ESD.ENTRADA_SALIDA_ID\n"
					+ "ORDER BY  ES.ID DESC";
			
			logger.info("->QUERY :"+q);
			
			ps = conn.prepareStatement(q);
			ps.setInt(1, tipoMov);
			ps.setInt(2, sucursalId);
			
			rs = ps.executeQuery();
			rs.last();
			int size = rs.getRow();
			rs.beforeFirst();
			logger.info("->rs.last(): rs.getRow()="+size);
			
			while (rs.next()) {
				EntradaSalidaQuickView x = new EntradaSalidaQuickView();				
				x.setId((Integer) rs.getObject("ES_ID"));
				x.setTipoMov((Integer) rs.getObject("TIPO_MOV"));
				x.setSucursalId((Integer) rs.getObject("SUCURSAL_ID"));
				x.setEstadoId((Integer) rs.getObject("ESTADO_ID"));
				x.setFechaCreo((Timestamp) rs.getObject("FECHA_CREO"));
				x.setUsuarioEmailCreo((String) rs.getObject("USUARIO_EMAIL_CREO"));
				x.setClienteId((Integer) rs.getObject("CLIENTE_ID"));
				x.setFormaDePagoId((Integer) rs.getObject("FORMA_DE_PAGO_ID"));
				x.setMetodoDePagoId((Integer) rs.getObject("METODO_DE_PAGO_ID"));
				x.setFactorIva((Double) rs.getObject("FACTOR_IVA"));
				x.setComentarios((String) rs.getObject("COMENTARIOS"));
				x.setCfdId((Integer) rs.getObject("CFD_ID"));
				x.setNumeroTicket((String) rs.getObject("NUMERO_TICKET"));
				x.setCaja((Integer) rs.getObject("CAJA"));
				x.setImporteRecibido((Double) rs.getObject("IMPORTE_RECIBIDO"));
				x.setAprobacionVisaMastercard((String) rs.getObject("APROBACION_VISA_MASTERCARD"));
				x.setPorcentajeDescuentoCalculado((Integer) rs.getObject("PORCENTAJE_DESCUENTO_CALCULADO"));
				x.setPorcentajeDescuentoExtra((Integer) rs.getObject("PORCENTAJE_DESCUENTO_EXTRA"));
				x.setCondicionesDePago((String) rs.getObject("CONDICIONES_DE_PAGO"));
				x.setNumDeCuenta((String) rs.getObject("NUM_DE_CUENTA"));
				x.setAutorizaDescuento((Integer) rs.getObject("AUTORIZA_DESCUENTO"));

				x.setSucursalNombre((String) rs.getObject("SUCURSAL_NOMBRE"));
				x.setEstadoDescripcion((String) rs.getObject("E_DESCRIPCION"));
				x.setUsuarioNombreCompleto((String) rs.getObject("U_NOMBRE_COMPLETO"));
				x.setClienteRFC((String) rs.getObject("C_RFC"));
				x.setClienteRazonSocial((String) rs.getObject("C_RAZON_SOCIAL"));
				x.setClienteNombreEstablecimiento((String) rs.getObject("C_NOMBRE_ESTABLECIMIENTO"));
				x.setMetodoDePagoDescripcion((String) rs.getObject("MP_DESCRIPCION"));
				x.setFormaDePagoDescripcion((String) rs.getObject("FP_DESCRIPCION"));
				x.setCdfNumCFD((String) rs.getObject("CFD_NUM_CFD"));

				x.setNumElementos(rs.getInt("NUM_ELEMENTOS"));
				x.setImporteBruto(rs.getDouble("IMPORTE_BRUTO"));

				x.setImporteNoGravado(x.getImporteBruto() / (1.0 + x.getFactorIva()));	
				//logger.info("========================");
				//logger.info("PEDIDO ID:        :\t"+x.getId());
				//logger.info("IMPORTE BRUTO     :\t"+x.getImporteBruto());
				//logger.info("IMPORTE NO GRABADO:\t"+x.getImporteNoGravado());
				if(x.getImporteBruto() !=null && x.getPorcentajeDescuentoCalculado()!=null && x.getPorcentajeDescuentoExtra()!=null){
					x.setImporteDescuento((x.getImporteNoGravado()* (x.getPorcentajeDescuentoCalculado()+x.getPorcentajeDescuentoExtra()))/100.0);															
				} else {
					x.setImporteDescuento(0.0);					
				}
				x.setImporteIVA((x.getImporteNoGravado() - x.getImporteDescuento())*Constants.IVA);
				x.setImporteTotal(x.getImporteNoGravado()- x.getImporteDescuento() + x.getImporteIVA());
				//logger.info("% DESCUENTOS      :\t"+x.getPorcentajeDescuentoCalculado()+"% + "+x.getPorcentajeDescuentoExtra());
				//logger.info("I.V.A.            :\t"+x.getImporteIVA());
				//logger.info("    T O T A L     :\t"+x.getImporteTotal());
				
				x.setEstadoActualFecha((Timestamp) rs.getObject("FECHA_ACTUALIZO"));
				x.setEstadoActualUsuarioEmail((String) rs.getObject("USUARIO_ACTUALIZO"));

				r.add(x);
			}
			logger.info("------------------------------");
			logger.info("->FOUND :"+r.size()+" RECORDS.");
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
					logger.error("findAll:clossing:", ex);
				}
			}
		}
		return r;
	}
	
	public ArrayList<EntradaSalidaQuickView> findAllHistoricoPedidos() throws DAOException {
		return findAllHistorico(Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA,1);
	}
	
	public ArrayList<EntradaSalidaQuickView> findAllHistoricoDevs() throws DAOException {
		return findAllHistorico(Constants.TIPO_MOV_ENTRADA_ALMACEN_DEVOLUCION,1);
	}
	
	public ArrayList<EntradaSalidaQuickView> findAllHistoricoCompras() throws DAOException {
		return findAllHistorico(Constants.TIPO_MOV_ENTRADA_ALMACEN_DEVOLUCION,1);
	}
	
	private ArrayList<EntradaSalidaQuickView> findAllHistorico(int tipoMov,int sucursalId) throws DAOException {
		return findAllActive(tipoMov,sucursalId,false);
	}
    
	public int insertPedidoVenta(EntradaSalida x, ArrayList<? extends EntradaSalidaDetalle> pvdList) throws DAOException {
		return insert(Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA,x,pvdList);
	}
	
	public int insertDevolucionVenta(EntradaSalida x, ArrayList<? extends EntradaSalidaDetalle> pvdList) throws DAOException {
		return insert(Constants.TIPO_MOV_ENTRADA_ALMACEN_DEVOLUCION,x,pvdList);
	}
	
	private int insert(int tipoMov,EntradaSalida x, ArrayList<? extends EntradaSalidaDetalle> pvdList) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement psESE = null;
		PreparedStatement psESD = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();

			Timestamp now = new Timestamp(System.currentTimeMillis());
			ps = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA(TIPO_MOV,SUCURSAL_ID,ESTADO_ID,FECHA_CREO,USUARIO_EMAIL_CREO,CLIENTE_ID,FORMA_DE_PAGO_ID,METODO_DE_PAGO_ID,FACTOR_IVA,COMENTARIOS,CFD_ID,NUMERO_TICKET,CAJA,IMPORTE_RECIBIDO,APROBACION_VISA_MASTERCARD,PORCENTAJE_DESCUENTO_CALCULADO,PORCENTAJE_DESCUENTO_EXTRA,CONDICIONES_DE_PAGO,NUM_DE_CUENTA,AUTORIZA_DESCUENTO) "
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			int ci = 1;
			ps.setObject(ci++, tipoMov);
			ps.setObject(ci++, x.getSucursalId());
			ps.setObject(ci++, Constants.ESTADO_SINCRONIZADO);
			ps.setObject(ci++, now);
			ps.setObject(ci++, x.getUsuarioEmailCreo());
			ps.setObject(ci++, x.getClienteId());
			ps.setObject(ci++, x.getFormaDePagoId());
			ps.setObject(ci++, x.getMetodoDePagoId());
			ps.setObject(ci++, x.getFactorIva());
			ps.setObject(ci++, x.getComentarios());
			ps.setObject(ci++, x.getCfdId());
			ps.setObject(ci++, x.getNumeroTicket());
			ps.setObject(ci++, x.getCaja());
			ps.setObject(ci++, x.getImporteRecibido());
			ps.setObject(ci++, x.getAprobacionVisaMastercard());
			ps.setObject(ci++, x.getPorcentajeDescuentoCalculado());
			ps.setObject(ci++, x.getPorcentajeDescuentoExtra());
			ps.setObject(ci++, x.getCondicionesDePago());
			ps.setObject(ci++, x.getNumDeCuenta());
			ps.setObject(ci++, x.getAutorizaDescuento());
			logger.info("->EntradaSalida before Insert:"+x.getId());
			r = ps.executeUpdate();					
			
			ResultSet rsk = ps.getGeneratedKeys();
			if (rsk != null) {
				while (rsk.next()) {
					x.setId(rsk.getInt(1));
				}
			}
			logger.info("->EntradaSalida after Insert:"+x.getId());
			psESD = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_DETALLE(ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA) "
					+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			int rESD = 0;
			for (EntradaSalidaDetalle pvd : pvdList) {
				int ciESD = 1;

				psESD.clearParameters();
				psESD.clearWarnings();

				psESD.setInt(ciESD++, x.getId());
				if (pvd.getProductoCodigoBarras() == null) {
					psESD.setObject(ciESD++, null);
				} else {
					psESD.setString(ciESD++, pvd.getProductoCodigoBarras());
				}
				psESD.setInt(ciESD++, pvd.getAlmacenId());
				psESD.setInt(ciESD++, pvd.getCantidad());
				psESD.setDouble(ciESD++, pvd.getPrecioVenta());

				rESD += psESD.executeUpdate();
			}

			psESE = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS) "
					+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			int[] estados = new int[]{Constants.ESTADO_CAPTURADO, Constants.ESTADO_SINCRONIZADO};
			int ciESE = 1;

			int rESE = -1;
			for (int j = 0; j < 2; j++) {
				psESE.clearParameters();
				ciESE = 1;

				psESE.setInt(ciESE++, x.getId());
				psESE.setInt(ciESE++, estados[j]);
				psESE.setTimestamp(ciESE++, now);
				psESE.setString(ciESE++, x.getUsuarioEmailCreo());
				psESE.setString(ciESE++, "--NORMAL--");

				rESE += psESE.executeUpdate();
			}
			conn.commit();
			logger.info("->EntradaSalida after Commit");
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				conn.rollback();
			} catch (SQLException exR) {
				logger.error("RollBack failed:", ex);
			}
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if (ps != null) {
				try {
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

	public int update(EntradaSalida x, ArrayList<? extends EntradaSalidaDetalle> pvdList, Usuario u) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement psESE = null;
		PreparedStatement psESD = null;

		int r = -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA SET TIPO_MOV=?,SUCURSAL_ID=?,ESTADO_ID=?,CLIENTE_ID=?,FORMA_DE_PAGO_ID=?,METODO_DE_PAGO_ID=?,FACTOR_IVA=?,COMENTARIOS=?,CFD_ID=?,NUMERO_TICKET=?,CAJA=?,IMPORTE_RECIBIDO=?,APROBACION_VISA_MASTERCARD=?,PORCENTAJE_DESCUENTO_CALCULADO=?,PORCENTAJE_DESCUENTO_EXTRA=?,CONDICIONES_DE_PAGO=?,NUM_DE_CUENTA=?,AUTORIZA_DESCUENTO=? "
					+ " WHERE ID=?");
			
			int ci = 1;
			ps.setObject(ci++, x.getTipoMov());
			ps.setObject(ci++, x.getSucursalId());
			ps.setObject(ci++, x.getEstadoId());
			//ps.setObject(ci++, x.getFechaCreo());
			//ps.setObject(ci++, x.getUsuarioEmailCreo());
			ps.setObject(ci++, x.getClienteId());
			ps.setObject(ci++, x.getFormaDePagoId());
			ps.setObject(ci++, x.getMetodoDePagoId());
			ps.setObject(ci++, x.getFactorIva());
			ps.setObject(ci++, x.getComentarios());
			ps.setObject(ci++, x.getCfdId());
			ps.setObject(ci++, x.getNumeroTicket());
			ps.setObject(ci++, x.getCaja());
			ps.setObject(ci++, x.getImporteRecibido());
			ps.setObject(ci++, x.getAprobacionVisaMastercard());
			ps.setObject(ci++, x.getPorcentajeDescuentoCalculado());
			ps.setObject(ci++, x.getPorcentajeDescuentoExtra());
			ps.setObject(ci++, x.getCondicionesDePago());
			ps.setObject(ci++, x.getNumDeCuenta());
			ps.setObject(ci++, x.getAutorizaDescuento());
			
			ps.setObject(ci++, x.getId());
			
			r = ps.executeUpdate();						

			int rESD = conn.createStatement().executeUpdate("DELETE FROM ENTRADA_SALIDA_DETALLE WHERE ENTRADA_SALIDA_ID=" + x.getId());
			logger.info("=>DELETE FROM ENTRADA_SALIDA_DETALLE WHERE ENTRADA_SALIDA_ID=" + x.getId()+"; affected="+rESD);
			
			psESD = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_DETALLE(ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA) "
					+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			rESD = 0;
			for (EntradaSalidaDetalle pvd : pvdList) {
				int ciESD = 1;

				psESD.clearParameters();
				psESD.clearWarnings();

				psESD.setInt(ciESD++, x.getId());
				if (pvd.getProductoCodigoBarras() == null) {
					psESD.setObject(ciESD++, null);
				} else {
					psESD.setString(ciESD++, pvd.getProductoCodigoBarras());
				}
				psESD.setInt(ciESD++, pvd.getAlmacenId());
				psESD.setInt(ciESD++, pvd.getCantidad());
				psESD.setDouble(ciESD++, pvd.getPrecioVenta());

				rESD += psESD.executeUpdate();
				logger.info("\t=>INSERT INTO ENTRADA_SALIDA_DETALLE .... "+pvd.getCantidad()+" X "+pvd.getProductoCodigoBarras()+" @ "+pvd.getAlmacenId());
			}

			psESE = conn.prepareStatement("UPDATE ENTRADA_SALIDA_ESTADO SET FECHA=?,USUARIO_EMAIL=?,COMENTARIOS=? WHERE ENTRADA_SALIDA_ID=? AND ESTADO_ID=?");
			int ciESE = 1;

			psESE.setTimestamp(ciESE++, now);
			psESE.setString(ciESE++, u.getEmail());
			psESE.setString(ciESE++, "--EDITADO--");

			psESE.setInt(ciESE++, x.getId());
			psESE.setInt(ciESE++, x.getEstadoId());

			psESE.executeUpdate();

			conn.commit();
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				conn.rollback();
			} catch (SQLException exR) {
				logger.error("RollBack failed:", ex);
			}
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if (ps != null) {
				try {
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

	public int verificar(EntradaSalida x, Usuario u) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement psESE = null;
		PreparedStatement psESD = null;

		int r = -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();
			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA SET ESTADO_ID=? WHERE ID=?");
			Timestamp now = new Timestamp(System.currentTimeMillis());

			int ci = 1;
			ps.setInt(ci++, Constants.ESTADO_VERIFICADO);
			ps.setInt(ci++, x.getId());

			r = ps.executeUpdate();
			psESE = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS) "
					+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			int ciESE = 1;

			int rESE = -1;

			psESE.setInt(ciESE++, x.getId());
			psESE.setInt(ciESE++, Constants.ESTADO_VERIFICADO);
			psESE.setTimestamp(ciESE++, now);
			psESE.setString(ciESE++, u.getEmail());
			psESE.setString(ciESE++, "--NORMAL--");

			rESE += psESE.executeUpdate();

			conn.commit();
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				conn.rollback();
			} catch (SQLException exR) {
				logger.error("RollBack failed:", ex);
			}
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if (ps != null) {
				try {
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

	public int surtir(EntradaSalida x, ArrayList<? extends EntradaSalidaDetalle> pvdList, Usuario u) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement psESE = null;
		PreparedStatement psESD = null;
		PreparedStatement psMHP = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();

			psESD = conn.prepareStatement("UPDATE ALMACEN_PRODUCTO SET CANTIDAD = CANTIDAD + ? "
					+ " WHERE PRODUCTO_CODIGO_BARRAS=? AND ALMACEN_ID=?");

			psMHP = conn.prepareStatement("INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO(ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS,ENTRADA_SALIDA_ID) "
					+ " VALUES(?,?,?,?,?,?,?,?,?)");

			Timestamp now = new Timestamp(System.currentTimeMillis());
			int cant=0;
			for (EntradaSalidaDetalle pvd : pvdList) {
				psESD.clearParameters();
				cant=0;
				if(x.getTipoMov() >= Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA && x.getTipoMov() < Constants.TIPO_MOV_OTRO){
					cant = -1 * pvd.getCantidad();
					psESD.setInt(1, cant);
				} else if(x.getTipoMov() >= Constants.TIPO_MOV_ENTRADA_ALMACEN_COMPRA &&  x.getTipoMov() < Constants.TIPO_MOV_SALIDA_ALMACEN_VENTA){				
					cant = pvd.getCantidad();
					psESD.setInt(1, cant);
				}
				logger.info("->tipomov="+x.getTipoMov()+", cant="+cant);
				psESD.setString(2, pvd.getProductoCodigoBarras());
				psESD.setInt(3, pvd.getAlmacenId());

				psESD.executeUpdate();

				int ci = 1;
				psMHP.clearParameters();

				psMHP.setInt(ci++, pvd.getAlmacenId());
				psMHP.setTimestamp(ci++, now);
				psMHP.setInt(ci++, x.getTipoMov());
				psMHP.setInt(ci++, pvd.getCantidad());
				psMHP.setObject(ci++, null);
				psMHP.setObject(ci++, null);
				psMHP.setString(ci++, u.getEmail());
				psMHP.setString(ci++, pvd.getProductoCodigoBarras());
				psMHP.setInt   (ci++, x.getId());

				r = psMHP.executeUpdate();

			}
			psESD.close();
			psMHP.close();

			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA SET ESTADO_ID=? WHERE ID=?");

			int ci = 1;
			ps.setInt(ci++, Constants.ESTADO_SURTIDO);
			ps.setInt(ci++, x.getId());

			r = ps.executeUpdate();
			psESE = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS) "
					+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			int ciESE = 1;

			int rESE = -1;
			psESE.clearParameters();
			ciESE = 1;

			psESE.setInt(ciESE++, x.getId());
			psESE.setInt(ciESE++, Constants.ESTADO_SURTIDO);
			psESE.setTimestamp(ciESE++, now);
			psESE.setString(ciESE++, u.getEmail());
			psESE.setString(ciESE++, "--NORMAL--");

			rESE += psESE.executeUpdate();

			conn.commit();

		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				conn.rollback();
			} catch (SQLException exR) {
				logger.error("RollBack failed:", ex);
			}
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if (ps != null) {
				try {
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

	public void invocarInicioWSCFDI(EntradaSalidaQuickView pedidoVenta,ArrayList<EntradaSalidaDetalleQuickView> pvdList,Cliente c, Usuario u,Sucursal s) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement psCFD = null;
		PreparedStatement psESE = null;
		PreparedStatement psESD = null;
		
		ResultSet rsCFD = null;
		Cfd cfd = null;
		Connection conn = null;
		String mensajeRefacturado="";
		Timestamp now = new Timestamp(System.currentTimeMillis());
		try {
			conn = getConnectionCommiteable();
			
			if(pedidoVenta.getCfdId() != null){
				mensajeRefacturado="REFACTRUADO, POR ULTIMO ERROR";
				psCFD = conn.prepareStatement("SELECT ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO FROM CFD "+
						"WHERE ID=?"
				);
				psCFD.setInt(1, pedidoVenta.getCfdId());

				rsCFD = psCFD.executeQuery();
				if(rsCFD.next()) {
					cfd = new Cfd();
					cfd.setId((Integer)rsCFD.getObject("ID"));
					cfd.setUltimaActualizacion((Timestamp)rsCFD.getObject("ULTIMA_ACTUALIZACION"));
					Blob bc=rsCFD.getBlob("CONTENIDO_ORIGINAL_XML");
					if(bc!=null)cfd.setContenidoOriginalXml(bc.getBytes(0, (int) bc.length()));
					cfd.setCallingErrorResult((String)rsCFD.getObject("CALLING_ERROR_RESULT"));
					cfd.setNumCfd((String)rsCFD.getObject("NUM_CFD"));
					cfd.setTipo((String)rsCFD.getObject("TIPO"));
				} 
				psCFD.close();
				rsCFD.close();
			} else {
				mensajeRefacturado="FACTURADO 1RA VEZ";
				cfd = new Cfd();
				cfd.setUltimaActualizacion(now);
			}
			
			DigifactClient.invokeWSFactura(cfd,pedidoVenta, pvdList, c, s.getSerieSicofi(), s.getUsuarioSicofi(), s.getPasswordSicofi());
			
			logger.info("===================AFTER DigifactClient.invokeWSFactura================");			
			try {
				logger.info("-->> cfd ="+BeanUtils.describe(cfd));
			} catch (Exception ex) {
				logger.error ("Describe fails:", ex);
			}
			logger.info("===================>> INSERTING OR UPDATING ================");
			if(cfd.getId() == null){
				psCFD = conn.prepareStatement("INSERT INTO CFD(ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO) "+
						" VALUES(?,?,?,?,?)"
						,Statement.RETURN_GENERATED_KEYS);			
				int ci=1;				
				psCFD.setObject(ci++,now);
				if(cfd.getContenidoOriginalXml()!=null)psCFD.setObject(ci++,new ByteArrayInputStream(cfd.getContenidoOriginalXml())); else psCFD.setNull(ci++,java.sql.Types.BLOB);
				psCFD.setObject(ci++,cfd.getCallingErrorResult());
				psCFD.setObject(ci++,cfd.getNumCfd());
				psCFD.setObject(ci++,cfd.getTipo());

				psCFD.executeUpdate();
				logger.info("inserted");
				ResultSet rsk = psCFD.getGeneratedKeys();
				if(rsk != null){
					while(rsk.next()){
						cfd.setId(((Long)rsk.getObject(1)).intValue());
						pedidoVenta.setCfdId(cfd.getId());
						logger.info("inserted, CFD pedidoVenta id="+cfd.getId());
					}
				}		
			} else {
				psCFD = conn.prepareStatement("UPDATE CFD SET ULTIMA_ACTUALIZACION=?,CONTENIDO_ORIGINAL_XML=?,CALLING_ERROR_RESULT=?,NUM_CFD=?,TIPO=? "+
					" WHERE ID=?");
			
				int ci=1;
				psCFD.setObject(ci++,cfd.getId());
				psCFD.setObject(ci++,now);
				if(cfd.getContenidoOriginalXml()!=null)psCFD.setObject(ci++,new ByteArrayInputStream(cfd.getContenidoOriginalXml())); else psCFD.setNull(ci++,java.sql.Types.BLOB);
				psCFD.setObject(ci++,cfd.getCallingErrorResult());
				psCFD.setObject(ci++,cfd.getNumCfd());
				psCFD.setObject(ci++,cfd.getTipo());
				psCFD.setObject(ci++,cfd.getId());

				psCFD.executeUpdate();						
				logger.info("updated");
			}
			psCFD.close();
			logger.info("psCDF closed");
			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA SET ESTADO_ID=?,CFD_ID=? WHERE ID=?");

			int ci = 1;
			ps.setInt(ci++, Constants.ESTADO_FACTURADO);
			ps.setInt(ci++, cfd.getId());
			ps.setInt(ci++, pedidoVenta.getId());
			ps.executeUpdate();			
			ps.close();
			logger.info("psCDF closed");
			
			psESE = conn.prepareStatement("SELECT ID,ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS FROM ENTRADA_SALIDA_ESTADO "					
					+ "WHERE ENTRADA_SALIDA_ID=? AND ESTADO_ID=?");
			psESE.setInt(1, pedidoVenta.getId());
			psESE.setInt(2, Constants.ESTADO_FACTURADO);
			
			ResultSet rsESE = psESE.executeQuery();
			EntradaSalidaEstado eseX = null;
			if(rsESE.next()) {
				eseX = new EntradaSalidaEstado();
				eseX.setId((Integer)rsESE.getObject("ID"));
				eseX.setEntradaSalidaId((Integer)rsESE.getObject("ENTRADA_SALIDA_ID"));
				eseX.setEstadoId((Integer)rsESE.getObject("ESTADO_ID"));
				eseX.setFecha((Timestamp)rsESE.getObject("FECHA"));
				eseX.setUsuarioEmail((String)rsESE.getObject("USUARIO_EMAIL"));
				eseX.setComentarios((String)rsESE.getObject("COMENTARIOS"));
			} else{
				eseX = new EntradaSalidaEstado();
				eseX.setId(null);
				eseX.setEntradaSalidaId(pedidoVenta.getId());
				eseX.setEstadoId(Constants.ESTADO_FACTURADO);
				eseX.setFecha(now);
				eseX.setUsuarioEmail(u.getEmail());
				eseX.setComentarios(mensajeRefacturado);
			}
			rsESE.close();
			psESE.close();			
			logger.info("psESE closed");
			if(eseX.getId() == null){
				psESE = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS) "
						+ " VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

				int ciESE = 1;

				psESE.setInt(ciESE++, pedidoVenta.getId());
				psESE.setInt(ciESE++, eseX.getEstadoId());
				psESE.setTimestamp(ciESE++, eseX.getFecha());
				psESE.setString(ciESE++, eseX.getUsuarioEmail());
				psESE.setString(ciESE++, eseX.getComentarios());

				psESE.executeUpdate();
				logger.info("insert psESE executed");
			} else {
				psESE = conn.prepareStatement("UPDATE ENTRADA_SALIDA_ESTADO SET FECHA=?,USUARIO_EMAIL=?,COMENTARIOS=? WHERE ENTRADA_SALIDA_ID=? AND ESTADO_ID=?");
				int ciESE = 1;

				psESE.setTimestamp(ciESE++, eseX.getFecha());
				psESE.setString(ciESE++, eseX.getUsuarioEmail());
				psESE.setString(ciESE++, eseX.getComentarios());

				psESE.setInt(ciESE++, pedidoVenta.getId());
				psESE.setInt(ciESE++, eseX.getEstadoId());

				psESE.executeUpdate();
				logger.info("update psESE executed");
			}
			psESE.close();
			logger.info("update psESE closed");
			
			
			conn.commit();
			logger.info("============== COMMIT =================");
		} catch (SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				conn.rollback();
				logger.info("============== ROLLBACK =================");
			} catch (SQLException exR) {
				logger.error("RollBack failed:", ex);
			}
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			logger.info("============== FIN METODO DAO =================");
		}
	}


    public int delete(EntradaSalida x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM ENTRADA_SALIDA WHERE ID=?");
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
