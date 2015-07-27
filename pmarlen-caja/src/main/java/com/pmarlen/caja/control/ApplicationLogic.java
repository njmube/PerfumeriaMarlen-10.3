/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.backend.model.ConfiguracionSistema;
import com.pmarlen.caja.dao.MemoryDAO;
import com.pmarlen.rest.dto.U;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class ApplicationLogic {
	private static Logger logger = Logger.getLogger(ApplicationLogic.class.getName());
	private static final String URI_VERSION_FILE = "/pmcajaupdate/version.properties";
	private static final String URI_APP_PACKAGE  = "/pmcajaupdate/update.zip";
	private static final String FILE_APP_PACKAGE = "./update.zip";
	
	private static String _version = null;
	private static final boolean printingEnabled = false; 
		
	private static final String VERSION_PROPERTY = "pmarlencaja.version";
	private boolean adminLogedIn = false;
	private String versionRead;
	private static ApplicationLogic instance;
	//private static PreferenciaDAO preferenciaDAO;
	private ApplicationLogic(){	
	}

	public boolean isAdminLogedIn() {
		return adminLogedIn;
	}

	public void setAdminLogedIn(boolean a) {
		adminLogedIn = a;
	}
	

	public String getNombreNegocio() {
		return null;
	}

	public void setNombreNegocio(String nombreNegocio) {
		
	}

	public String getDireccion() {
		return null;
	}
	
	public void setDireccion(String direccion) {
		
	}

	public String getTelefonos() {
		return null;
	}
	public void setTelefonos(String telefonos) {
		
	}

	public String getCliente() {
		return null;
	}
	public void setCliente(String cliente) {
		
	}

	public String getEmail() {
		return null;
	}
	public void setEmail(String email) {
		
	}

	public String getBTImpresora() {
		return null;
	}
	
	public void setBTImpresora(String btaImpresora) {
		
	}

	/**
	 * @return the instance
	 */
	public static ApplicationLogic getInstance() {
		if(instance == null){
			instance =  new ApplicationLogic();
		}
		return instance;
	}

	public String getVersionRead() {
		return versionRead;
	}
	
	public boolean needsUpdateApplciation(){
		boolean updateApp =  false;
		
		URL url=null;
		InputStream is = null;
		BufferedReader br = null;
		try{
			url = new URL(MemoryDAO.getServerContext()+URI_VERSION_FILE);
			logger.info("url="+url);
			is = url.openStream();
		}catch(IOException ioe){
			return false;
		}
		br = new BufferedReader(new InputStreamReader(is));
		String lineRead = null;
		try{
			while((lineRead = br.readLine()) != null) {
				if(lineRead.contains(VERSION_PROPERTY)){
					
					String[] propValue = lineRead.split("=");
					versionRead = propValue[1]; 
					
					logger.info("lineRead="+lineRead+", versionReadOfLine="+versionRead);
					logger.info("current version ="+getVersion());					
					
					String currentVersionParts[] = getVersion().split("\\.");
					String versionReadParts[]    = versionRead.split("\\.");
					
					logger.info("comparing: "+Arrays.asList(currentVersionParts)+" < "+Arrays.asList(versionReadParts));
					
					if(Integer.parseInt(currentVersionParts[3])<Integer.parseInt(versionReadParts[3])){
						logger.info("\t->needsUpdateApplciation: Ok, update!");
						return true;
					}
				}
			}			
		} catch(IOException ioe){
			return false;
		}
		
		return updateApp;
	}
	
	void updateApplication(final UpdateApplicationListener ual) {
		new Thread(){

			@Override
			public void run() {
				downloadApplication(ual);
			}
		}.start();
	}
	
	void cacellUpdateApplication() {
		keepDownlaod = false;
	}
	
	private boolean keepDownlaod;
	
	private void downloadApplication(final UpdateApplicationListener ual) {
		URL url=null;
		BufferedReader br = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		
		try{
			url = new URL(MemoryDAO.getServerContext()+URI_APP_PACKAGE);
			conn = (HttpURLConnection)url.openConnection();
			long length = conn.getContentLengthLong();
			is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(FILE_APP_PACKAGE);
			byte[] buffer = new byte[1024 * 16];
			long r = -1;
			long t= 0;
			keepDownlaod = true;
			while ((r = is.read(buffer, 0, buffer.length)) != -1) {
				if(!keepDownlaod){
					int resp = JOptionPane.showConfirmDialog(null, "¿ DESEA CANCELAR LA DESCARGA ?", "CANCELAR", 
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(resp == JOptionPane.YES_OPTION){
						break;
					} else {
						keepDownlaod = true;
					}
				}
				t += r;
				fos.write(buffer, 0, (int)r);
				fos.flush();
				long advance = (100L * t) / length;
				//logger.info("------->> Downloaded:\t [+ "+r+"]( "+t+"/"+length+") : "+advance+" %");
				ual.updateProgress((int)advance);
			}
			ual.updateProgress(100);
			logger.info("");
			logger.info("finished");
			is.close();
			fos.close();
			if(!keepDownlaod){
				throw new IllegalStateException("Update Canceled");
			} else {
				extractFolder(FILE_APP_PACKAGE);
				ual.finisUpdate();
			}
		} catch (IOException ex) {
			ual.errorUpdate("ERROR AL ACTUALIZAR:"+ex.getMessage());
		}		
	}

	private void extractFolder(String zipFile) throws ZipException, IOException {
		System.out.println(zipFile);
		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		String destPathToInflate = ".";

		Enumeration zipFileEntries = zip.entries();

		// Process each entry
		logger.info("-> extracting :");
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(destPathToInflate, currentEntry);
			logger.info("-> inflating :"+destFile.getPath());
			//destFile = new File(newPath, destFile.getName());
			File destinationParent = destFile.getParentFile();

			// create the parent directory structure if needed
			destinationParent.mkdirs();

			if (!entry.isDirectory()) {
				BufferedInputStream is = new BufferedInputStream(zip
						.getInputStream(entry));
				int currentByte;
				// establish buffer for writing file
				byte data[] = new byte[BUFFER];

				// write the current file to disk
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);

				// read and write until last byte is encountered
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}
		}
		logger.info("-> OK, finish extracting.");
	}

	boolean canDownlaodUpdateApplication() {
		try {
			URL  url = new URL(MemoryDAO.getServerContext()+URI_APP_PACKAGE);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			int length = conn.getContentLength();
			if(length > 1024*1024){
				return true;
			} else{
				return false;
			}
		} catch(Exception ex){
			ex.printStackTrace(System.err);
			return false;
		}
	}
	
	U checkForUser(String usuarioEmail,String plainPassword) {
		U logged=null;
		List<U> usuarioList = MemoryDAO.getPaqueteSinc().getUsuarioList();
		for(U u: usuarioList){
			if(u.getE().equals(usuarioEmail)){
				if(u.getP().equals(getMD5Encrypted(plainPassword))){
					logged=u;
					break;
				}
			}
		}
		return logged;
	}

	private String getMD5Encrypted(String e) {

        MessageDigest mdEnc = null; // Encryption algorithm
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        mdEnc.update(e.getBytes(), 0, e.length());
        return (new BigInteger(1, mdEnc.digest())).toString(16);
    }

	public String getVersion() {
		if(_version == null){
			Properties porpVersion = new Properties();
			try {
				porpVersion.load(getClass().getResourceAsStream("/version.properties"));
				_version = porpVersion.getProperty("pmarlencaja.version");
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return _version;
	}

	boolean isPrintingEnabled() {
		return printingEnabled;
	}
	
}
