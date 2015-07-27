/**
 * MovimientoOperativoAlmacenDAO
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
 * Class for MovimientoOperativoAlmacenDAO of Table MOVIMIENTO_OPERATIVO_ALMACEN.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MovimientoOperativoAlmacenDAO {

	private final static Logger logger = Logger.getLogger(MovimientoOperativoAlmacenDAO.class.getName());

	/**
	*	Datasource for table MOVIMIENTO_OPERATIVO_ALMACEN simple CRUD operations.
	*   x common paramenter.
	*/

	private static MovimientoOperativoAlmacenDAO instance;

	private MovimientoOperativoAlmacenDAO(){	
		logger.debug("created MovimientoOperativoAlmacenDAO.");
	}

	public static MovimientoOperativoAlmacenDAO getInstance() {
		if(instance == null){
			instance = new MovimientoOperativoAlmacenDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public MovimientoOperativoAlmacen findBy(MovimientoOperativoAlmacen x) throws DAOException, EntityNotFoundException{
		MovimientoOperativoAlmacen r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MOTIVO,USUARIO_GENERO,FECHA_INICIO,ALMACEN_ORIGEN,ALMACEN_DESTINO,TIPO_MOVIMIENTO,USUARIO_CONFIRMO,FECHA_CONFIRMACION,COMENTARIOS FROM MOVIMIENTO_OPERATIVO_ALMACEN "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new MovimientoOperativoAlmacen();
				r.setId((Integer)rs.getObject("ID"));
				r.setMotivo((String)rs.getObject("MOTIVO"));
				r.setUsuarioGenero((String)rs.getObject("USUARIO_GENERO"));
				r.setFechaInicio((Timestamp)rs.getObject("FECHA_INICIO"));
				r.setAlmacenOrigen((Integer)rs.getObject("ALMACEN_ORIGEN"));
				r.setAlmacenDestino((Integer)rs.getObject("ALMACEN_DESTINO"));
				r.setTipoMovimiento((Integer)rs.getObject("TIPO_MOVIMIENTO"));
				r.setUsuarioConfirmo((String)rs.getObject("USUARIO_CONFIRMO"));
				r.setFechaConfirmacion((Timestamp)rs.getObject("FECHA_CONFIRMACION"));
				r.setComentarios((String)rs.getObject("COMENTARIOS"));
			} else {
				throw new EntityNotFoundException("MOVIMIENTO_OPERATIVO_ALMACEN NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<MovimientoOperativoAlmacen> findAll() throws DAOException {
		ArrayList<MovimientoOperativoAlmacen> r = new ArrayList<MovimientoOperativoAlmacen>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MOTIVO,USUARIO_GENERO,FECHA_INICIO,ALMACEN_ORIGEN,ALMACEN_DESTINO,TIPO_MOVIMIENTO,USUARIO_CONFIRMO,FECHA_CONFIRMACION,COMENTARIOS FROM MOVIMIENTO_OPERATIVO_ALMACEN");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				MovimientoOperativoAlmacen x = new MovimientoOperativoAlmacen();
				x.setId((Integer)rs.getObject("ID"));
				x.setMotivo((String)rs.getObject("MOTIVO"));
				x.setUsuarioGenero((String)rs.getObject("USUARIO_GENERO"));
				x.setFechaInicio((Timestamp)rs.getObject("FECHA_INICIO"));
				x.setAlmacenOrigen((Integer)rs.getObject("ALMACEN_ORIGEN"));
				x.setAlmacenDestino((Integer)rs.getObject("ALMACEN_DESTINO"));
				x.setTipoMovimiento((Integer)rs.getObject("TIPO_MOVIMIENTO"));
				x.setUsuarioConfirmo((String)rs.getObject("USUARIO_CONFIRMO"));
				x.setFechaConfirmacion((Timestamp)rs.getObject("FECHA_CONFIRMACION"));
				x.setComentarios((String)rs.getObject("COMENTARIOS"));
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
    
    public int insert(MovimientoOperativoAlmacen x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO MOVIMIENTO_OPERATIVO_ALMACEN(MOTIVO,USUARIO_GENERO,FECHA_INICIO,ALMACEN_ORIGEN,ALMACEN_DESTINO,TIPO_MOVIMIENTO,USUARIO_CONFIRMO,FECHA_CONFIRMACION,COMENTARIOS) "+
					" VALUES(?,?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMotivo());
			ps.setObject(ci++,x.getUsuarioGenero());
			ps.setObject(ci++,x.getFechaInicio());
			ps.setObject(ci++,x.getAlmacenOrigen());
			ps.setObject(ci++,x.getAlmacenDestino());
			ps.setObject(ci++,x.getTipoMovimiento());
			ps.setObject(ci++,x.getUsuarioConfirmo());
			ps.setObject(ci++,x.getFechaConfirmacion());
			ps.setObject(ci++,x.getComentarios());

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

	public int update(MovimientoOperativoAlmacen x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE MOVIMIENTO_OPERATIVO_ALMACEN SET MOTIVO=?,USUARIO_GENERO=?,FECHA_INICIO=?,ALMACEN_ORIGEN=?,ALMACEN_DESTINO=?,TIPO_MOVIMIENTO=?,USUARIO_CONFIRMO=?,FECHA_CONFIRMACION=?,COMENTARIOS=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMotivo());
			ps.setObject(ci++,x.getUsuarioGenero());
			ps.setObject(ci++,x.getFechaInicio());
			ps.setObject(ci++,x.getAlmacenOrigen());
			ps.setObject(ci++,x.getAlmacenDestino());
			ps.setObject(ci++,x.getTipoMovimiento());
			ps.setObject(ci++,x.getUsuarioConfirmo());
			ps.setObject(ci++,x.getFechaConfirmacion());
			ps.setObject(ci++,x.getComentarios());
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

    public int delete(MovimientoOperativoAlmacen x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM MOVIMIENTO_OPERATIVO_ALMACEN WHERE ID=?");
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
