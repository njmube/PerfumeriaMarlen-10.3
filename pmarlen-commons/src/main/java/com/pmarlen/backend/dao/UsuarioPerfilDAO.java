/**
 * UsuarioPerfilDAO
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
 * Class for UsuarioPerfilDAO of Table USUARIO_PERFIL.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class UsuarioPerfilDAO {

	private final static Logger logger = Logger.getLogger(UsuarioPerfilDAO.class.getName());

	/**
	*	Datasource for table USUARIO_PERFIL simple CRUD operations.
	*   x common paramenter.
	*/

	private static UsuarioPerfilDAO instance;

	private UsuarioPerfilDAO(){	
		logger.debug("created UsuarioPerfilDAO.");
	}

	public static UsuarioPerfilDAO getInstance() {
		if(instance == null){
			instance = new UsuarioPerfilDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public UsuarioPerfil findBy(UsuarioPerfil x) throws DAOException, EntityNotFoundException{
		UsuarioPerfil r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT EMAIL,PERFIL FROM USUARIO_PERFIL "+
					"WHERE PERFIL=?"
			);
			ps.setString(1, x.getPerfil());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new UsuarioPerfil();
				r.setEmail((String)rs.getObject("EMAIL"));
				r.setPerfil((String)rs.getObject("PERFIL"));
			} else {
				throw new EntityNotFoundException("USUARIO_PERFIL NOT FOUND FOR PERFIL="+x.getPerfil());
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

	public ArrayList<UsuarioPerfil> findBy(Usuario u) throws DAOException {
		ArrayList<UsuarioPerfil> r = new ArrayList<UsuarioPerfil>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT EMAIL,PERFIL FROM USUARIO_PERFIL "
					+ "WHERE EMAIL=?"
			);
			ps.setString(1, u.getEmail());

			rs = ps.executeQuery();
			while (rs.next()) {
				UsuarioPerfil x = new UsuarioPerfil();
				x.setEmail(rs.getString("EMAIL"));
				x.setPerfil(rs.getString("PERFIL"));
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

    public ArrayList<UsuarioPerfil> findAll() throws DAOException {
		ArrayList<UsuarioPerfil> r = new ArrayList<UsuarioPerfil>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT EMAIL,PERFIL FROM USUARIO_PERFIL");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				UsuarioPerfil x = new UsuarioPerfil();
				x.setEmail((String)rs.getObject("EMAIL"));
				x.setPerfil((String)rs.getObject("PERFIL"));
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
    
    public int insert(UsuarioPerfil x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO USUARIO_PERFIL(EMAIL,PERFIL) "+
					" VALUES(?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getPerfil());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setPerfil((String)rsk.getObject(1));
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

	public int update(UsuarioPerfil x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE USUARIO_PERFIL SET  "+
					" WHERE PERFIL=?");
			
			int ci=1;
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getPerfil());
			ps.setObject(ci++,x.getPerfil());
			
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

    public int delete(UsuarioPerfil x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM USUARIO_PERFIL WHERE PERFIL=?");
			ps.setObject(1, x.getPerfil());
			
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
