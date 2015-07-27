/**
 * SucursalDAO
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
 * Class for SucursalDAO of Table SUCURSAL.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class SucursalDAO {

	private final static Logger logger = Logger.getLogger(SucursalDAO.class.getName());

	/**
	*	Datasource for table SUCURSAL simple CRUD operations.
	*   x common paramenter.
	*/

	private static SucursalDAO instance;

	private SucursalDAO(){	
		logger.debug("created SucursalDAO.");
	}

	public static SucursalDAO getInstance() {
		if(instance == null){
			instance = new SucursalDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public Sucursal findBy(Sucursal x) throws DAOException, EntityNotFoundException{
		Sucursal r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ID_PADRE,NOMBRE,DIRECCION,TELEFONOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI,COMENTARIOS,DESCUENTO_MAYOREO_HABILITADO FROM SUCURSAL "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new Sucursal();
				r.setId((Integer)rs.getObject("ID"));
				r.setIdPadre((Integer)rs.getObject("ID_PADRE"));
				r.setNombre((String)rs.getObject("NOMBRE"));
				r.setDireccion((String)rs.getObject("DIRECCION"));
				r.setTelefonos((String)rs.getObject("TELEFONOS"));
				r.setUsuarioSicofi((String)rs.getObject("USUARIO_SICOFI"));
				r.setPasswordSicofi((String)rs.getObject("PASSWORD_SICOFI"));
				r.setSerieSicofi((String)rs.getObject("SERIE_SICOFI"));
				r.setComentarios((String)rs.getObject("COMENTARIOS"));
				r.setDescuentoMayoreoHabilitado((Integer)rs.getObject("DESCUENTO_MAYOREO_HABILITADO"));
			} else {
				throw new EntityNotFoundException("SUCURSAL NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<Sucursal> findAll() throws DAOException {
		ArrayList<Sucursal> r = new ArrayList<Sucursal>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ID_PADRE,NOMBRE,DIRECCION,TELEFONOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI,COMENTARIOS,DESCUENTO_MAYOREO_HABILITADO FROM SUCURSAL");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Sucursal x = new Sucursal();
				x.setId((Integer)rs.getObject("ID"));
				x.setIdPadre((Integer)rs.getObject("ID_PADRE"));
				x.setNombre((String)rs.getObject("NOMBRE"));
				x.setDireccion((String)rs.getObject("DIRECCION"));
				x.setTelefonos((String)rs.getObject("TELEFONOS"));
				x.setUsuarioSicofi((String)rs.getObject("USUARIO_SICOFI"));
				x.setPasswordSicofi((String)rs.getObject("PASSWORD_SICOFI"));
				x.setSerieSicofi((String)rs.getObject("SERIE_SICOFI"));
				x.setComentarios((String)rs.getObject("COMENTARIOS"));
				x.setDescuentoMayoreoHabilitado((Integer)rs.getObject("DESCUENTO_MAYOREO_HABILITADO"));
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
    
    public int insert(Sucursal x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO SUCURSAL(ID_PADRE,NOMBRE,DIRECCION,TELEFONOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI,COMENTARIOS,DESCUENTO_MAYOREO_HABILITADO) "+
					" VALUES(?,?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getIdPadre());
			ps.setObject(ci++,x.getNombre());
			ps.setObject(ci++,x.getDireccion());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getUsuarioSicofi());
			ps.setObject(ci++,x.getPasswordSicofi());
			ps.setObject(ci++,x.getSerieSicofi());
			ps.setObject(ci++,x.getComentarios());
			ps.setObject(ci++,x.getDescuentoMayoreoHabilitado());

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

	public int update(Sucursal x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE SUCURSAL SET ID_PADRE=?,NOMBRE=?,DIRECCION=?,TELEFONOS=?,USUARIO_SICOFI=?,PASSWORD_SICOFI=?,SERIE_SICOFI=?,COMENTARIOS=?,DESCUENTO_MAYOREO_HABILITADO=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getIdPadre());
			ps.setObject(ci++,x.getNombre());
			ps.setObject(ci++,x.getDireccion());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getUsuarioSicofi());
			ps.setObject(ci++,x.getPasswordSicofi());
			ps.setObject(ci++,x.getSerieSicofi());
			ps.setObject(ci++,x.getComentarios());
			ps.setObject(ci++,x.getDescuentoMayoreoHabilitado());
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

    public int delete(Sucursal x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM SUCURSAL WHERE ID=?");
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
