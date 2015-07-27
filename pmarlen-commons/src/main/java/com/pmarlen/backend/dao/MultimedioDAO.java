/**
 * MultimedioDAO
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
 * Class for MultimedioDAO of Table MULTIMEDIO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class MultimedioDAO {

	private final static Logger logger = Logger.getLogger(MultimedioDAO.class.getName());

	/**
	*	Datasource for table MULTIMEDIO simple CRUD operations.
	*   x common paramenter.
	*/

	private static MultimedioDAO instance;

	private MultimedioDAO(){	
		logger.debug("created MultimedioDAO.");
	}

	public static MultimedioDAO getInstance() {
		if(instance == null){
			instance = new MultimedioDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public Multimedio findBy(Multimedio x) throws DAOException, EntityNotFoundException{
		Multimedio r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MIME_TYPE,RUTA_CONTENIDO,SIZE_BYTES,NOMBRE_ARCHIVO FROM MULTIMEDIO "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new Multimedio();
				r.setId((Integer)rs.getObject("ID"));
				r.setMimeType((String)rs.getObject("MIME_TYPE"));
				r.setRutaContenido((String)rs.getObject("RUTA_CONTENIDO"));
				r.setSizeBytes((Integer)rs.getObject("SIZE_BYTES"));
				r.setNombreArchivo((String)rs.getObject("NOMBRE_ARCHIVO"));
			} else {
				throw new EntityNotFoundException("MULTIMEDIO NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<Multimedio> findAll() throws DAOException {
		ArrayList<Multimedio> r = new ArrayList<Multimedio>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,MIME_TYPE,RUTA_CONTENIDO,SIZE_BYTES,NOMBRE_ARCHIVO FROM MULTIMEDIO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Multimedio x = new Multimedio();
				x.setId((Integer)rs.getObject("ID"));
				x.setMimeType((String)rs.getObject("MIME_TYPE"));
				x.setRutaContenido((String)rs.getObject("RUTA_CONTENIDO"));
				x.setSizeBytes((Integer)rs.getObject("SIZE_BYTES"));
				x.setNombreArchivo((String)rs.getObject("NOMBRE_ARCHIVO"));
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
    
    public int insert(Multimedio x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO MULTIMEDIO(MIME_TYPE,RUTA_CONTENIDO,SIZE_BYTES,NOMBRE_ARCHIVO) "+
					" VALUES(?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMimeType());
			ps.setObject(ci++,x.getRutaContenido());
			ps.setObject(ci++,x.getSizeBytes());
			ps.setObject(ci++,x.getNombreArchivo());

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

	public int update(Multimedio x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE MULTIMEDIO SET MIME_TYPE=?,RUTA_CONTENIDO=?,SIZE_BYTES=?,NOMBRE_ARCHIVO=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getMimeType());
			ps.setObject(ci++,x.getRutaContenido());
			ps.setObject(ci++,x.getSizeBytes());
			ps.setObject(ci++,x.getNombreArchivo());
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

    public int delete(Multimedio x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM MULTIMEDIO WHERE ID=?");
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
