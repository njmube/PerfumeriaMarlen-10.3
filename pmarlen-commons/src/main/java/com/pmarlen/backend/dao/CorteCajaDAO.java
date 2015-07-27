/**
 * CorteCajaDAO
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
 * Class for CorteCajaDAO of Table CORTE_CAJA.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class CorteCajaDAO {

	private final static Logger logger = Logger.getLogger(CorteCajaDAO.class.getName());

	/**
	*	Datasource for table CORTE_CAJA simple CRUD operations.
	*   x common paramenter.
	*/

	private static CorteCajaDAO instance;

	private CorteCajaDAO(){	
		logger.debug("created CorteCajaDAO.");
	}

	public static CorteCajaDAO getInstance() {
		if(instance == null){
			instance = new CorteCajaDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public CorteCaja findBy(CorteCaja x) throws DAOException, EntityNotFoundException{
		CorteCaja r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,FECHA,SUCURSAL_ID,CAJA,USUARIO_EMAIL,TOTAL,COMENTARIOS FROM CORTE_CAJA "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new CorteCaja();
				r.setId((Integer)rs.getObject("ID"));
				r.setFecha((Timestamp)rs.getObject("FECHA"));
				r.setSucursalId((Integer)rs.getObject("SUCURSAL_ID"));
				r.setCaja((Integer)rs.getObject("CAJA"));
				r.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				r.setTotal((Double)rs.getObject("TOTAL"));
				r.setComentarios((String)rs.getObject("COMENTARIOS"));
			} else {
				throw new EntityNotFoundException("CORTE_CAJA NOT FOUND FOR ID="+x.getId());
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

    public ArrayList<CorteCaja> findAll() throws DAOException {
		ArrayList<CorteCaja> r = new ArrayList<CorteCaja>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,FECHA,SUCURSAL_ID,CAJA,USUARIO_EMAIL,TOTAL,COMENTARIOS FROM CORTE_CAJA");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CorteCaja x = new CorteCaja();
				x.setId((Integer)rs.getObject("ID"));
				x.setFecha((Timestamp)rs.getObject("FECHA"));
				x.setSucursalId((Integer)rs.getObject("SUCURSAL_ID"));
				x.setCaja((Integer)rs.getObject("CAJA"));
				x.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
				x.setTotal((Double)rs.getObject("TOTAL"));
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
    
    public int insert(CorteCaja x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO CORTE_CAJA(FECHA,SUCURSAL_ID,CAJA,USUARIO_EMAIL,TOTAL,COMENTARIOS) "+
					" VALUES(?,?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getSucursalId());
			ps.setObject(ci++,x.getCaja());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getTotal());
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

	public int update(CorteCaja x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CORTE_CAJA SET FECHA=?,SUCURSAL_ID=?,CAJA=?,USUARIO_EMAIL=?,TOTAL=?,COMENTARIOS=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getSucursalId());
			ps.setObject(ci++,x.getCaja());
			ps.setObject(ci++,x.getUsuarioEmail());
			ps.setObject(ci++,x.getTotal());
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

    public int delete(CorteCaja x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM CORTE_CAJA WHERE ID=?");
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
