/**
 * CfdDAO
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
 * Class for CfdDAO of Table CFD.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class CfdDAO {

	private final static Logger logger = Logger.getLogger(CfdDAO.class.getName());

	/**
	*	Datasource for table CFD simple CRUD operations.
	*   x common paramenter.
	*/

	private static CfdDAO instance;

	private CfdDAO(){	
		logger.debug("created CfdDAO.");
	}

	public static CfdDAO getInstance() {
		if(instance == null){
			instance = new CfdDAO();
		}
		return instance;
	}

	private Connection getConnection(){
		return DataSourceFacade.getStrategy().getConnection();
	}

	private Connection getConnectionCommiteable(){
		return DataSourceFacade.getStrategy().getConnectionCommiteable();
	}

    public Cfd findBy(Cfd x) throws DAOException, EntityNotFoundException{
		Cfd r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO FROM CFD "+
					"WHERE ID=?"
			);
			ps.setInt(1, x.getId());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new Cfd();
				r.setId((Integer)rs.getObject("ID"));
				r.setUltimaActualizacion((Timestamp)rs.getObject("ULTIMA_ACTUALIZACION"));
				Blob bc=rs.getBlob("CONTENIDO_ORIGINAL_XML");
				if(bc!=null)r.setContenidoOriginalXml(bc.getBytes(1, (int) bc.length()));
				r.setCallingErrorResult((String)rs.getObject("CALLING_ERROR_RESULT"));
				r.setNumCfd((String)rs.getObject("NUM_CFD"));
				r.setTipo((String)rs.getObject("TIPO"));
			} else {
				throw new EntityNotFoundException("CFD NOT FOUND FOR ID="+x.getId());
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
	
	public Cfd findByNumCFDI(String numCFDI) throws DAOException, EntityNotFoundException{
		Cfd r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO FROM CFD "+
					"WHERE NUM_CFD=?"
			);
			ps.setString(1, numCFDI);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = new Cfd();
				r.setId((Integer)rs.getObject("ID"));
				r.setUltimaActualizacion((Timestamp)rs.getObject("ULTIMA_ACTUALIZACION"));
				Blob bc=rs.getBlob("CONTENIDO_ORIGINAL_XML");
				if(bc!=null)r.setContenidoOriginalXml(bc.getBytes(1, (int) bc.length()));
				r.setCallingErrorResult((String)rs.getObject("CALLING_ERROR_RESULT"));
				r.setNumCfd((String)rs.getObject("NUM_CFD"));
				r.setTipo((String)rs.getObject("TIPO"));
			} else {
				throw new EntityNotFoundException("CFD NOT FOUND FOR NUM_CFD="+numCFDI);
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


    public ArrayList<Cfd> findAll() throws DAOException {
		ArrayList<Cfd> r = new ArrayList<Cfd>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO FROM CFD");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Cfd x = new Cfd();
				x.setId((Integer)rs.getObject("ID"));
				x.setUltimaActualizacion((Timestamp)rs.getObject("ULTIMA_ACTUALIZACION"));
				Blob bc=rs.getBlob("CONTENIDO_ORIGINAL_XML");
				if(bc!=null)x.setContenidoOriginalXml(bc.getBytes(1, (int) bc.length()));
				x.setCallingErrorResult((String)rs.getObject("CALLING_ERROR_RESULT"));
				x.setNumCfd((String)rs.getObject("NUM_CFD"));
				x.setTipo((String)rs.getObject("TIPO"));
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
    
    public int insert(Cfd x) throws DAOException {
		PreparedStatement ps = null;
		int r = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO CFD(ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO) "+
					" VALUES(?,?,?,?,?)"
					,Statement.RETURN_GENERATED_KEYS);			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getUltimaActualizacion());
			if(x.getContenidoOriginalXml()!=null)ps.setObject(ci++,new ByteArrayInputStream(x.getContenidoOriginalXml())); else ps.setNull(ci++,java.sql.Types.BLOB);
			ps.setObject(ci++,x.getCallingErrorResult());
			ps.setObject(ci++,x.getNumCfd());
			ps.setObject(ci++,x.getTipo());

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

	public int update(Cfd x) throws DAOException {		
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CFD SET ULTIMA_ACTUALIZACION=?,CONTENIDO_ORIGINAL_XML=?,CALLING_ERROR_RESULT=?,NUM_CFD=?,TIPO=? "+
					" WHERE ID=?");
			
			int ci=1;
			ps.setObject(ci++,x.getId());
			ps.setObject(ci++,x.getUltimaActualizacion());
			if(x.getContenidoOriginalXml()!=null)ps.setObject(ci++,new ByteArrayInputStream(x.getContenidoOriginalXml())); else ps.setNull(ci++,java.sql.Types.BLOB);
			ps.setObject(ci++,x.getCallingErrorResult());
			ps.setObject(ci++,x.getNumCfd());
			ps.setObject(ci++,x.getTipo());
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

    public int delete(Cfd x)throws DAOException {
		PreparedStatement ps = null;
		int r= -1;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM CFD WHERE ID=?");
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
