/**
 * EntradaSalidaEstadoDAO
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
 * Class for EntradaSalidaEstadoDAO of Table ENTRADA_SALIDA_ESTADO.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalidaEstadoDAO {

	private final static Logger logger = Logger.getLogger(EntradaSalidaEstadoDAO.class.getName());

	/**
	*	Datasource for table ENTRADA_SALIDA_ESTADO simple CRUD operations.
	*   x common paramenter.
	*/

	private static EntradaSalidaEstadoDAO instance;

	private EntradaSalidaEstadoDAO(){	
		logger.debug("created EntradaSalidaEstadoDAO.");
	}

	public static EntradaSalidaEstadoDAO getInstance() {
		if(instance == null){
			instance = new EntradaSalidaEstadoDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public EntradaSalidaEstado findBy(EntradaSalidaEstado x) throws DAOException, EntityNotFoundException{
		EntradaSalidaEstado eseX = null;
		PreparedStatement ps = null;
		ResultSet rsESE = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS FROM ENTRADA_SALIDA_ESTADO "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rsESE = ps.executeQuery();
			if(rsESE.next()) {
				eseX = new EntradaSalidaEstado();
				eseX.setId((Integer)rsESE.getObject("ID"));
				eseX.setEntradaSalidaId((Integer)rsESE.getObject("ENTRADA_SALIDA_ID"));
				eseX.setEstadoId((Integer)rsESE.getObject("ESTADO_ID"));
				eseX.setFecha((Timestamp)rsESE.getObject("FECHA"));
				eseX.setUsuarioEmail((String)rsESE.getObject("USUARIO_EMAIL"));
				eseX.setComentarios((String)rsESE.getObject("COMENTARIOS"));
			} else {
				throw new EntityNotFoundException("ENTRADA_SALIDA_ESTADO NOT FOUND FOR ID="+x.getId());
			}
		}catch(SQLException ex) {
			logger.error("SQLException:", ex);
			throw new DAOException("InQuery:" + ex.getMessage());
		} finally {
			if(rsESE != null) {
				try{
					rsESE.close();
					ps.close();
					conn.close();
				}catch(SQLException ex) {
					logger.error("clossing, SQLException:" + ex.getMessage());
					throw new DAOException("Closing:"+ex.getMessage());
				}
			}
		}
		return eseX;		
	}

    public ArrayList<EntradaSalidaEstado> findAll() throws DAOException {
		ArrayList<EntradaSalidaEstado> r = new ArrayList<EntradaSalidaEstado>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS FROM ENTRADA_SALIDA_ESTADO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				EntradaSalidaEstado x = new EntradaSalidaEstado();
				x.setId((Integer)rs.getObject("ID"));
				x.setEntradaSalidaId((Integer)rs.getObject("ENTRADA_SALIDA_ID"));
				x.setEstadoId((Integer)rs.getObject("ESTADO_ID"));
				x.setFecha((Timestamp)rs.getObject("FECHA"));
				x.setUsuarioEmail((String)rs.getObject("USUARIO_EMAIL"));
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
    
    public int insert(EntradaSalidaEstado x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL,COMENTARIOS) "+
					" VALUES(?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getEntradaSalidaId());
			ps.setObject(ci++,x.getEstadoId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getUsuarioEmail());
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

	public int update(EntradaSalidaEstado x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE ENTRADA_SALIDA_ESTADO SET ENTRADA_SALIDA_ID=?,ESTADO_ID=?,FECHA=?,USUARIO_EMAIL=?,COMENTARIOS=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getEntradaSalidaId());
			ps.setObject(ci++,x.getEstadoId());
			ps.setObject(ci++,x.getFecha());
			ps.setObject(ci++,x.getUsuarioEmail());
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

    public int delete(EntradaSalidaEstado x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM ENTRADA_SALIDA_ESTADO WHERE ID=?");
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
