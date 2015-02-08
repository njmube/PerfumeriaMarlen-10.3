/**
 * ClienteDAO
 *
 * Created 2015/02/07 21:02
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

import java.util.logging.Logger;
import java.util.logging.Level;

import com.pmarlen.backend.model.*;
import com.tracktopell.jdbc.DataSourceFacade;

/**
 * Class for ClienteDAO of Table CLIENTE.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/02/07 21:02
 */

public class ClienteDAO {

	private final static Logger logger = Logger.getLogger(ClienteDAO.class.getName());

	/**
	*	Datasource for table CLIENTE simple CRUD operations.
	*   x common paramenter.
	*/

	private static ClienteDAO instance;

	private ClienteDAO(){	
		logger.fine("created ClienteDAO.");
	}

	public static ClienteDAO getInstance() {
		if(instance == null){
			instance = new ClienteDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public Cliente findBy(Cliente x) throws DAOException, EntityNotFoundException{
		Cliente r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CONTACTO,TELEFONOS,EMAIL,OBSERVACIONES,DIRECCION FROM CLIENTE "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new Cliente();
				r.setId((Integer)rs.getObject("ID"));
				r.setRfc((String)rs.getObject("RFC"));
				r.setRazonSocial((String)rs.getObject("RAZON_SOCIAL"));
				r.setNombreEstablecimiento((String)rs.getObject("NOMBRE_ESTABLECIMIENTO"));
				r.setContacto((String)rs.getObject("CONTACTO"));
				r.setTelefonos((String)rs.getObject("TELEFONOS"));
				r.setEmail((String)rs.getObject("EMAIL"));
				r.setObservaciones((String)rs.getObject("OBSERVACIONES"));
				r.setDireccion((String)rs.getObject("DIRECCION"));
			} else {
				throw new EntityNotFoundException("CLIENTE NOT FOUND FOR ID="+x.getId());
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	}

    public ArrayList<Cliente> findAll() throws DAOException {
		ArrayList<Cliente> r = new ArrayList<Cliente>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CONTACTO,TELEFONOS,EMAIL,OBSERVACIONES,DIRECCION FROM CLIENTE");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Cliente x = new Cliente();
				x.setId((Integer)rs.getObject("ID"));
				x.setRfc((String)rs.getObject("RFC"));
				x.setRazonSocial((String)rs.getObject("RAZON_SOCIAL"));
				x.setNombreEstablecimiento((String)rs.getObject("NOMBRE_ESTABLECIMIENTO"));
				x.setContacto((String)rs.getObject("CONTACTO"));
				x.setTelefonos((String)rs.getObject("TELEFONOS"));
				x.setEmail((String)rs.getObject("EMAIL"));
				x.setObservaciones((String)rs.getObject("OBSERVACIONES"));
				x.setDireccion((String)rs.getObject("DIRECCION"));
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;		
	};
    
    public int insert(Cliente x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO CLIENTE(RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CONTACTO,TELEFONOS,EMAIL,OBSERVACIONES,DIRECCION) "+
					" VALUES(?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getRfc());
			ps.setObject(ci++,x.getRazonSocial());
			ps.setObject(ci++,x.getNombreEstablecimiento());
			ps.setObject(ci++,x.getContacto());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getObservaciones());
			ps.setObject(ci++,x.getDireccion());

			r = ps.executeUpdate();					
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){
				while(rsk.next()){
					x.setId((Integer)rsk.getObject(1));
				}
			}
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{				
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

	public int update(Cliente x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CLIENTE SET RFC=?,RAZON_SOCIAL=?,NOMBRE_ESTABLECIMIENTO=?,CONTACTO=?,TELEFONOS=?,EMAIL=?,OBSERVACIONES=?,DIRECCION=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getRfc());
			ps.setObject(ci++,x.getRazonSocial());
			ps.setObject(ci++,x.getNombreEstablecimiento());
			ps.setObject(ci++,x.getContacto());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getObservaciones());
			ps.setObject(ci++,x.getDireccion());
			ps.setObject(ci++,x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

    public int delete(Cliente x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM CLIENTE WHERE ID=?");
			ps.setObject(1, x.getId());
			
			r = ps.executeUpdate();						
		}catch(SQLException ex) {
			logger.log(Level.SEVERE, "SQLException:", ex);
			throw new DAOException("InUpdate:" + ex.getMessage());
		} finally {
			if(ps != null) {
				try{
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.log(Level.SEVERE, "clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return r;
	}

}
