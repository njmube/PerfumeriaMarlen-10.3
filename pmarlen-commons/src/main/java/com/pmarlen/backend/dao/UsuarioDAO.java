/**
 * UsuarioDAO
 *
 * Created 2015/03/15 12:43
 *
 * @author tracktopell :: DAO Builder
 * http://www.tracktopell.com.mx
 */

package com.pmarlen.backend.dao;

import com.pmarlen.backend.model.*;
import com.pmarlen.backend.model.quickviews.UsuarioQuickView;
import com.pmarlen.rest.dto.U;
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
import java.util.List;
import org.apache.log4j.Logger;
/**
 * Class for UsuarioDAO of Table USUARIO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class UsuarioDAO {

	private final static Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

	/**
	*	Datasource for table USUARIO simple CRUD operations.
	*   x common paramenter.
	*/

	private static UsuarioDAO instance;

	private UsuarioDAO(){	
		logger.debug("created UsuarioDAO.");
	}

	public static UsuarioDAO getInstance() {
		if(instance == null){
			instance = new UsuarioDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public UsuarioQuickView findBy(String e) throws DAOException, EntityNotFoundException{
		UsuarioQuickView x = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT   U.EMAIL,U.ABILITADO,U.NOMBRE_COMPLETO,U.PASSWORD,UP.PERFIL\n" +
					"FROM     USUARIO_PERFIL UP,USUARIO U\n" +
					"WHERE    U.EMAIL = UP.EMAIL\n" +
					"     AND U.EMAIL = ?\n" +		
					"ORDER BY U.NOMBRE_COMPLETO");
			ps.setString(1,e);
			rs = ps.executeQuery();
			
			String email = null;
			String perfil=null;
			String nombreCompleto=null;
			String password=null;
			Integer abilitado = null;
			logger.info("============================================>");
			while(rs.next()) {
				email			= (String)rs.getObject("EMAIL");
				perfil			= (String)rs.getObject("PERFIL");
				nombreCompleto	= (String)rs.getObject("NOMBRE_COMPLETO");
				password		= (String)rs.getObject("PASSWORD");
				abilitado		= (Integer)rs.getObject("ABILITADO");
				logger.info("->"+email+","+perfil+","+nombreCompleto+","+password+","+abilitado);
				if(x == null){
					// NUEVO
					x = new UsuarioQuickView();					
				} else if(x.getEmail().equalsIgnoreCase(email)){
					// EL MISMO EMAIL
				} else {
					// CAMBIO EMAIL					
					x = new UsuarioQuickView();			
				}
				
				x.setEmail(email);
				x.setAbilitado(abilitado);
				x.setNombreCompleto(nombreCompleto);
				x.setPassword(password);
				
				x.addPerfil(perfil);
			}
			logger.info("<============================================");
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
		return x;
	}

    public ArrayList<UsuarioQuickView> findAll() throws DAOException {
		ArrayList<UsuarioQuickView> r = new ArrayList<UsuarioQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT   U.EMAIL,U.ABILITADO,U.NOMBRE_COMPLETO,U.PASSWORD,UP.PERFIL\n" +
					"FROM     USUARIO_PERFIL UP,USUARIO U\n" +
					"WHERE    U.EMAIL = UP.EMAIL\n" +
					"ORDER BY U.NOMBRE_COMPLETO");
			
			rs = ps.executeQuery();
			UsuarioQuickView x = null;
			String email = null;
			String perfil=null;
			String nombreCompleto=null;
			String password=null;
			Integer abilitado = null;
			logger.info("============================================>");
			while(rs.next()) {
				email			= (String)rs.getObject("EMAIL");
				perfil			= (String)rs.getObject("PERFIL");
				nombreCompleto	= (String)rs.getObject("NOMBRE_COMPLETO");
				password		= (String)rs.getObject("PASSWORD");
				abilitado		= (Integer)rs.getObject("ABILITADO");
				logger.info("->"+email+","+perfil+","+nombreCompleto+","+password+","+abilitado);
				if(x == null){
					// NUEVO
					x = new UsuarioQuickView();					
				} else if(x.getEmail().equalsIgnoreCase(email)){
					// EL MISMO EMAIL
				} else {
					// CAMBIO EMAIL
					r.add(x);
					x = new UsuarioQuickView();					
				}
				
				x.setEmail(email);
				x.setAbilitado(abilitado);
				x.setNombreCompleto(nombreCompleto);
				x.setPassword(password);
				
				x.addPerfil(perfil);
			}
			if(x != null){
				r.add(x);
			}
			logger.info("<============================================");
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

    public ArrayList<U> findAllForRest() throws DAOException {
		ArrayList<U> r = new ArrayList<U>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT   U.EMAIL,U.ABILITADO,U.NOMBRE_COMPLETO,U.PASSWORD,UP.PERFIL\n" +
					"FROM     USUARIO_PERFIL UP,USUARIO U\n" +
					"WHERE    U.EMAIL = UP.EMAIL\n" +
					"ORDER BY U.NOMBRE_COMPLETO");
			
			rs = ps.executeQuery();
			U x = null;
			String email = null;
			String perfil=null;
			String nombreCompleto=null;
			String password=null;
			Integer abilitado = null;
			while(rs.next()) {
				email			= (String)rs.getObject("EMAIL");
				perfil			= (String)rs.getObject("PERFIL");
				nombreCompleto	= (String)rs.getObject("NOMBRE_COMPLETO");
				password		= (String)rs.getObject("PASSWORD");
				abilitado		= (Integer)rs.getObject("ABILITADO");
				//logger.info("->"+email+","+perfil+","+nombreCompleto+","+password+","+abilitado);
				if(x == null){
					// NUEVO
					x = new U();					
				} else if(x.getE().equalsIgnoreCase(email)){
					// EL MISMO EMAIL
				} else {
					// CAMBIO EMAIL
					r.add(x);
					x = new U();					
				}
				
				x.setE(email);
				x.setA(abilitado);
				x.setNc(nombreCompleto);
				x.setP(password);
				
				x.addRole(perfil);
			}
			if(x != null){
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
	
    public ArrayList<Usuario> findAllSimple() throws DAOException {
		ArrayList<Usuario> r = new ArrayList<Usuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT   U.EMAIL,U.ABILITADO,U.NOMBRE_COMPLETO,U.PASSWORD\n" +
					"FROM     USUARIO U\n" +
					"ORDER BY U.NOMBRE_COMPLETO");
			
			rs = ps.executeQuery();
			Usuario x = null;
			String email = null;
			String nombreCompleto=null;
			String password=null;
			Integer abilitado = null;

			while(rs.next()) {
				email			= (String)rs.getObject("EMAIL");
				abilitado		= (Integer)rs.getObject("ABILITADO");
				nombreCompleto	= (String)rs.getObject("NOMBRE_COMPLETO");				
				password		= (String)rs.getObject("PASSWORD");
				
				x = new Usuario();					
				
				x.setEmail(email);
				x.setAbilitado(abilitado);
				x.setNombreCompleto(nombreCompleto);
				x.setPassword(password);
				
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
    
    public int insert(UsuarioQuickView x) throws DAOException {
		PreparedStatement ps   = null;
		PreparedStatement psUP = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();
			ps = conn.prepareStatement("INSERT INTO USUARIO(EMAIL,ABILITADO,NOMBRE_COMPLETO,PASSWORD) "+
					" VALUES(?,?,?,?)");			
			psUP = conn.prepareStatement("INSERT INTO USUARIO_PERFIL(EMAIL,PERFIL) VALUES(?,?)");			
			
			r = ps.executeUpdate();					

			int ci=1;
			
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getAbilitado());
			ps.setObject(ci++,x.getNombreCompleto());
			ps.setObject(ci++,x.getPassword());

			r = ps.executeUpdate();
			
			List<String> roleList = x.getRoleList();
			for(String rol: roleList){
				psUP.clearParameters();
				psUP.setString(1, x.getEmail());
				psUP.setString(2, rol);
				psUP.executeUpdate();
			}
			psUP.close();
			conn.commit();
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				if(conn!=null){
					conn.rollback();
				}
			} catch(SQLException re){
				logger.error("SQLException:", re);
			}
			throw new DAOException("InInsert:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{				
					ps.close();
					psUP.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

	public int update(UsuarioQuickView x) throws DAOException {		
		PreparedStatement ps = null;
		PreparedStatement psUP = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnectionCommiteable();
			ps = conn.prepareStatement("UPDATE USUARIO SET ABILITADO=?,NOMBRE_COMPLETO=?,PASSWORD=? "+
					" WHERE EMAIL=?");
			psUP = conn.prepareStatement("DELETE FROM USUARIO_PERFIL WHERE EMAIL=?");
			
			logger.info("->x.email="+x.getEmail());
			int ci=1;
			ps.setObject(ci++,x.getAbilitado());
			ps.setObject(ci++,x.getNombreCompleto());
			ps.setObject(ci++,x.getPassword());
			ps.setObject(ci++,x.getEmail());
			
			r = ps.executeUpdate();
			
			psUP.setString(1, x.getEmail());
			
			psUP.executeUpdate();
			psUP.close();
			
			psUP = conn.prepareStatement("INSERT INTO USUARIO_PERFIL(EMAIL,PERFIL) VALUES(?,?)");
			List<String> roleList = x.getRoleList();
			for(String rol: roleList){
				psUP.clearParameters();
				psUP.setString(1, x.getEmail());
				psUP.setString(2, rol);
				psUP.executeUpdate();
			}
			psUP.close();
			
			conn.commit();
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			try {
				if(conn!=null){
					conn.rollback();
				}
			} catch(SQLException re){
				logger.error("SQLException:", re);
			}
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

    public int delete(Usuario x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM USUARIO WHERE EMAIL=?");
			ps.setObject(1, x.getEmail());
			
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
