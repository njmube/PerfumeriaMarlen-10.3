/**
 * EventoSincronizacionDAO
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
 * Class for EventoSincronizacionDAO of Table EVENTO_SINCRONIZACION.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EventoSincronizacionDAO {

	private final static Logger logger = Logger.getLogger(EventoSincronizacionDAO.class.getName());

	/**
	*	Datasource for table EVENTO_SINCRONIZACION simple CRUD operations.
	*   x common paramenter.
	*/

	private static EventoSincronizacionDAO instance;

	private EventoSincronizacionDAO(){	
		logger.debug("created EventoSincronizacionDAO.");
	}

	public static EventoSincronizacionDAO getInstance() {
		if(instance == null){
			instance = new EventoSincronizacionDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public EventoSincronizacion findBy(EventoSincronizacion x) throws DAOException, EntityNotFoundException{
		EventoSincronizacion r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,FECHA_EVENTO,TIPO_EVENTO,AFECTACION_GLOBAL,SUCURSAL_ID_AFECTADA,TABLA,CAMPOS_LLAVE,VALORES_LLAVE FROM EVENTO_SINCRONIZACION "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new EventoSincronizacion();
				r.setId((Integer)rs.getObject("ID"));
				r.setFechaEvento((Timestamp)rs.getObject("FECHA_EVENTO"));
				r.setTipoEvento((Integer)rs.getObject("TIPO_EVENTO"));
				r.setAfectacionGlobal((Integer)rs.getObject("AFECTACION_GLOBAL"));
				r.setSucursalIdAfectada((Integer)rs.getObject("SUCURSAL_ID_AFECTADA"));
				r.setTabla((String)rs.getObject("TABLA"));
				r.setCamposLlave((String)rs.getObject("CAMPOS_LLAVE"));
				r.setValoresLlave((String)rs.getObject("VALORES_LLAVE"));
			} else {
				throw new EntityNotFoundException("EVENTO_SINCRONIZACION NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<EventoSincronizacion> findAll() throws DAOException {
		ArrayList<EventoSincronizacion> r = new ArrayList<EventoSincronizacion>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,FECHA_EVENTO,TIPO_EVENTO,AFECTACION_GLOBAL,SUCURSAL_ID_AFECTADA,TABLA,CAMPOS_LLAVE,VALORES_LLAVE FROM EVENTO_SINCRONIZACION");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				EventoSincronizacion x = new EventoSincronizacion();
				x.setId((Integer)rs.getObject("ID"));
				x.setFechaEvento((Timestamp)rs.getObject("FECHA_EVENTO"));
				x.setTipoEvento((Integer)rs.getObject("TIPO_EVENTO"));
				x.setAfectacionGlobal((Integer)rs.getObject("AFECTACION_GLOBAL"));
				x.setSucursalIdAfectada((Integer)rs.getObject("SUCURSAL_ID_AFECTADA"));
				x.setTabla((String)rs.getObject("TABLA"));
				x.setCamposLlave((String)rs.getObject("CAMPOS_LLAVE"));
				x.setValoresLlave((String)rs.getObject("VALORES_LLAVE"));
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
    
    public int insert(EventoSincronizacion x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO EVENTO_SINCRONIZACION(FECHA_EVENTO,TIPO_EVENTO,AFECTACION_GLOBAL,SUCURSAL_ID_AFECTADA,TABLA,CAMPOS_LLAVE,VALORES_LLAVE) "+
					" VALUES(?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getFechaEvento());
			ps.setObject(ci++,x.getTipoEvento());
			ps.setObject(ci++,x.getAfectacionGlobal());
			ps.setObject(ci++,x.getSucursalIdAfectada());
			ps.setObject(ci++,x.getTabla());
			ps.setObject(ci++,x.getCamposLlave());
			ps.setObject(ci++,x.getValoresLlave());

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

	public int update(EventoSincronizacion x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE EVENTO_SINCRONIZACION SET FECHA_EVENTO=?,TIPO_EVENTO=?,AFECTACION_GLOBAL=?,SUCURSAL_ID_AFECTADA=?,TABLA=?,CAMPOS_LLAVE=?,VALORES_LLAVE=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getFechaEvento());
			ps.setObject(ci++,x.getTipoEvento());
			ps.setObject(ci++,x.getAfectacionGlobal());
			ps.setObject(ci++,x.getSucursalIdAfectada());
			ps.setObject(ci++,x.getTabla());
			ps.setObject(ci++,x.getCamposLlave());
			ps.setObject(ci++,x.getValoresLlave());
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

    public int delete(EventoSincronizacion x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM EVENTO_SINCRONIZACION WHERE ID=?");
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
