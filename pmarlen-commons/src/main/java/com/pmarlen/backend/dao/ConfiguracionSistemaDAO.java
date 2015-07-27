/**
 * ConfiguracionSistemaDAO
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
 * Class for ConfiguracionSistemaDAO of Table CONFIGURACION_SISTEMA.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class ConfiguracionSistemaDAO {

	private final static Logger logger = Logger.getLogger(ConfiguracionSistemaDAO.class.getName());

	/**
	*	Datasource for table CONFIGURACION_SISTEMA simple CRUD operations.
	*   x common paramenter.
	*/

	private static ConfiguracionSistemaDAO instance;

	private ConfiguracionSistemaDAO(){	
		logger.debug("created ConfiguracionSistemaDAO.");
	}

	public static ConfiguracionSistemaDAO getInstance() {
		if(instance == null){
			instance = new ConfiguracionSistemaDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public ConfiguracionSistema findBy(ConfiguracionSistema x) throws DAOException, EntityNotFoundException{
		ConfiguracionSistema r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT LLAVE,VALOR FROM CONFIGURACION_SISTEMA "+
					"WHERE LLAVE=?"
			);
			ps.setString(1, x.getLlave());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new ConfiguracionSistema();
				r.setLlave((String)rs.getObject("LLAVE"));
				r.setValor((String)rs.getObject("VALOR"));
			} else {
				throw new EntityNotFoundException("CONFIGURACION_SISTEMA NOT FOUND FOR LLAVE="+x.getLlave());
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

    public ArrayList<ConfiguracionSistema> findAll() throws DAOException {
		ArrayList<ConfiguracionSistema> r = new ArrayList<ConfiguracionSistema>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT LLAVE,VALOR FROM CONFIGURACION_SISTEMA");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				ConfiguracionSistema x = new ConfiguracionSistema();
				x.setLlave((String)rs.getObject("LLAVE"));
				x.setValor((String)rs.getObject("VALOR"));
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
    
    public int insert(ConfiguracionSistema x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO CONFIGURACION_SISTEMA(LLAVE,VALOR) "+
					" VALUES(?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getLlave());
			ps.setObject(ci++,x.getValor());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setLlave((String)rsk.getObject(1));
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

	public int update(ConfiguracionSistema x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CONFIGURACION_SISTEMA SET VALOR=? "+
					" WHERE LLAVE=?");
			
			int ci=1;
			ps.setObject(ci++,x.getLlave());
			ps.setObject(ci++,x.getValor());
			ps.setObject(ci++,x.getLlave());
			
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

    public int delete(ConfiguracionSistema x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM CONFIGURACION_SISTEMA WHERE LLAVE=?");
			ps.setObject(1, x.getLlave());
			
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
