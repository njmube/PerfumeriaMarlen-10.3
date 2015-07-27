/**
 * ClienteDAO
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
import com.pmarlen.backend.model.quickviews.ClienteQuickView;
import com.tracktopell.jdbc.DataSourceFacade;

/**
 * Class for ClienteDAO of Table CLIENTE.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class ClienteDAO {

	private final static Logger logger = Logger.getLogger(ClienteDAO.class.getName());

	/**
	*	Datasource for table CLIENTE simple CRUD operations.
	*   x common paramenter.
	*/

	private static ClienteDAO instance;

	private ClienteDAO(){	
		logger.debug("created ClienteDAO.");
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
			ps = conn.prepareStatement("SELECT ID,RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_EXTERIOR,NUM_INTERIOR,COLONIA,MUNICIPIO,REFERENCIA,CIUDAD,CP,ESTADO,EMAIL,TELEFONOS,CONTACTO,OBSERVACIONES,UBICACION_LAT,UBICACION_LON,NUM_CUENTA,BANCO,DIRECCION_FACTURACION FROM CLIENTE "+
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
				r.setCalle((String)rs.getObject("CALLE"));
				r.setNumExterior((String)rs.getObject("NUM_EXTERIOR"));
				r.setNumInterior((String)rs.getObject("NUM_INTERIOR"));
				r.setColonia((String)rs.getObject("COLONIA"));
				r.setMunicipio((String)rs.getObject("MUNICIPIO"));
				r.setReferencia((String)rs.getObject("REFERENCIA"));
				r.setCiudad((String)rs.getObject("CIUDAD"));
				r.setCp((String)rs.getObject("CP"));
				r.setEstado((String)rs.getObject("ESTADO"));
				r.setEmail((String)rs.getObject("EMAIL"));
				r.setTelefonos((String)rs.getObject("TELEFONOS"));
				r.setContacto((String)rs.getObject("CONTACTO"));
				r.setObservaciones((String)rs.getObject("OBSERVACIONES"));
				r.setUbicacionLat((Double)rs.getObject("UBICACION_LAT"));
				r.setUbicacionLon((Double)rs.getObject("UBICACION_LON"));
				r.setNumCuenta((String)rs.getObject("NUM_CUENTA"));
				r.setBanco((String)rs.getObject("BANCO"));
				r.setDireccionFacturacion((String)rs.getObject("DIRECCION_FACTURACION"));
				r.setId((Integer)rs.getObject("ID"));
			} else {
				throw new EntityNotFoundException("CLIENTE NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<ClienteQuickView> findAll() throws DAOException {
		ArrayList<ClienteQuickView> r = new ArrayList<ClienteQuickView>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_EXTERIOR,NUM_INTERIOR,COLONIA,MUNICIPIO,REFERENCIA,CIUDAD,CP,ESTADO,EMAIL,TELEFONOS,CONTACTO,OBSERVACIONES,UBICACION_LAT,UBICACION_LON,NUM_CUENTA,BANCO,DIRECCION_FACTURACION FROM CLIENTE ORDER BY RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				ClienteQuickView x = new ClienteQuickView();
				x.setId((Integer)rs.getObject("ID"));
				x.setRfc((String)rs.getObject("RFC"));
				x.setRazonSocial((String)rs.getObject("RAZON_SOCIAL"));
				x.setNombreEstablecimiento((String)rs.getObject("NOMBRE_ESTABLECIMIENTO"));
				x.setCalle((String)rs.getObject("CALLE"));
				x.setNumExterior((String)rs.getObject("NUM_EXTERIOR"));
				x.setNumInterior((String)rs.getObject("NUM_INTERIOR"));
				x.setColonia((String)rs.getObject("COLONIA"));
				x.setMunicipio((String)rs.getObject("MUNICIPIO"));
				x.setReferencia((String)rs.getObject("REFERENCIA"));
				x.setCiudad((String)rs.getObject("CIUDAD"));
				x.setCp((String)rs.getObject("CP"));
				x.setEstado((String)rs.getObject("ESTADO"));
				x.setEmail((String)rs.getObject("EMAIL"));
				x.setTelefonos((String)rs.getObject("TELEFONOS"));
				x.setContacto((String)rs.getObject("CONTACTO"));
				x.setObservaciones((String)rs.getObject("OBSERVACIONES"));
				x.setUbicacionLat((Double)rs.getObject("UBICACION_LAT"));
				x.setUbicacionLon((Double)rs.getObject("UBICACION_LON"));
				x.setNumCuenta((String)rs.getObject("NUM_CUENTA"));
				x.setBanco((String)rs.getObject("BANCO"));
				x.setDireccionFacturacion((String)rs.getObject("DIRECCION_FACTURACION"));
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
    
    public int insert(Cliente x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO CLIENTE("
					+ "RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_EXTERIOR,NUM_INTERIOR,COLONIA,MUNICIPIO,REFERENCIA,CIUDAD,"
					+ "CP,ESTADO,EMAIL,TELEFONOS,CONTACTO,OBSERVACIONES,UBICACION_LAT,UBICACION_LON,NUM_CUENTA,BANCO,DIRECCION_FACTURACION) "+
					" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getRfc());
			ps.setObject(ci++,x.getRazonSocial());
			ps.setObject(ci++,x.getNombreEstablecimiento());
			ps.setObject(ci++,x.getCalle());
			ps.setObject(ci++,x.getNumExterior());
			ps.setObject(ci++,x.getNumInterior());
			ps.setObject(ci++,x.getColonia());
			ps.setObject(ci++,x.getMunicipio());
			ps.setObject(ci++,x.getReferencia());
			ps.setObject(ci++,x.getCiudad());
			ps.setObject(ci++,x.getCp());
			ps.setObject(ci++,x.getEstado());
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getContacto());
			ps.setObject(ci++,x.getObservaciones());
			ps.setObject(ci++,x.getUbicacionLat());
			ps.setObject(ci++,x.getUbicacionLon());
			ps.setObject(ci++,x.getNumCuenta());
			ps.setObject(ci++,x.getBanco());
			ps.setObject(ci++,x.getDireccionFacturacion());

			r = ps.executeUpdate();
			ResultSet rsk = ps.getGeneratedKeys();
			if(rsk != null){				
				while(rsk.next()){
					x.setId(rsk.getInt(1));
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

	public int update(Cliente x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CLIENTE SET RFC=?,RAZON_SOCIAL=?,NOMBRE_ESTABLECIMIENTO=?,CALLE=?,NUM_EXTERIOR=?,NUM_INTERIOR=?,COLONIA=?,MUNICIPIO=?,REFERENCIA=?,CIUDAD=?,CP=?,ESTADO=?,EMAIL=?,TELEFONOS=?,CONTACTO=?,OBSERVACIONES=?,UBICACION_LAT=?,UBICACION_LON=?,NUM_CUENTA=?,BANCO=?,DIRECCION_FACTURACION=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getRfc());
			ps.setObject(ci++,x.getRazonSocial());
			ps.setObject(ci++,x.getNombreEstablecimiento());
			ps.setObject(ci++,x.getCalle());
			ps.setObject(ci++,x.getNumExterior());
			ps.setObject(ci++,x.getNumInterior());
			ps.setObject(ci++,x.getColonia());
			ps.setObject(ci++,x.getMunicipio());
			ps.setObject(ci++,x.getReferencia());
			ps.setObject(ci++,x.getCiudad());
			ps.setObject(ci++,x.getCp());
			ps.setObject(ci++,x.getEstado());
			ps.setObject(ci++,x.getEmail());
			ps.setObject(ci++,x.getTelefonos());
			ps.setObject(ci++,x.getContacto());
			ps.setObject(ci++,x.getObservaciones());
			ps.setObject(ci++,x.getUbicacionLat());
			ps.setObject(ci++,x.getUbicacionLon());
			ps.setObject(ci++,x.getNumCuenta());
			ps.setObject(ci++,x.getBanco());
			ps.setObject(ci++,x.getDireccionFacturacion());
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
