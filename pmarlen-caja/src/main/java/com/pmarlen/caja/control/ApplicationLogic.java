/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.backend.model.ConfiguracionSistema;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class ApplicationLogic {
	private static final String ULR_VERSION_FILE = "http://dulcesaga.com.mx/xcd/version.properties";
	private static final String ULR_APP_PACKAGE  = "http://dulcesaga.com.mx/xcd/UPDATE_BUILD.zip";
	private static final String FILE_APP_PACKAGE = "./UPDATE_BUILD.zip";
	
	private static String _version = null;
	private static final boolean printingEnabled = false; 
		
	private static final String VERSION_PROPERTY = "xpresscashdrawer.version";
	private boolean adminLogedIn = false;
	
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
	
	public boolean needsUpdateApplciation(){
		boolean updateApp =  false;
		
		URL url=null;
		InputStream is = null;
		BufferedReader br = null;
		try{
			url = new URL(ULR_VERSION_FILE);
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
					String versionReadOfLine = propValue[1]; 
					
					System.err.println("->needsUpdateApplciation:lineRead="+lineRead+", versionReadOfLine="+versionReadOfLine);
					System.err.println("->needsUpdateApplciation:version ="+getVersion());
					System.err.println("->result ? ="+versionReadOfLine.compareTo(getVersion()));
					
					if(versionReadOfLine.compareTo(getVersion())>0){
						System.err.println("->needsUpdateApplciation: Ok, update!");
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
			url = new URL(ULR_APP_PACKAGE);
			conn = (HttpURLConnection)url.openConnection();
			int length = conn.getContentLength();
			is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(FILE_APP_PACKAGE);
			byte[] buffer = new byte[1024 * 16];
			int r = -1;
			int t= 0;
			keepDownlaod = true;
			while ((r = is.read(buffer, 0, buffer.length)) != -1) {
				if(!keepDownlaod){
					int resp = JOptionPane.showConfirmDialog(null, "¿Desea cancelar la descarga ?", "Cancelar", 
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(resp == JOptionPane.YES_OPTION){
						break;
					} else {
						keepDownlaod = true;
					}
				}
				t += r;
				fos.write(buffer, 0, r);
				fos.flush();
				int advance = (100 * t) / length;
				System.err.print("Downloaded:\t"+advance+" % \r");
				ual.updateProgress(advance);				
			}
			System.err.println("");
			System.err.println("finished");
			is.close();
			fos.close();
			if(!keepDownlaod){
				throw new IllegalStateException("Update Canceled");
			} else {
				extractFolder(FILE_APP_PACKAGE);
				JOptionPane.showMessageDialog(null, "Se ha actualizado la Aplicación, \nReinicie por favor.", 
						"Actualización", JOptionPane.INFORMATION_MESSAGE);
				//System.exit(2);			
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Can't download UPDATE data package:"+ex.getMessage());
		}
		/*
		try {
			extractFolder(FILE_APP_PACKAGE);
			JOptionPane.showMessageDialog(null, "Se ha actualizado la Aplicación, \nPor favor reinicie nuevamente", 
					"Actualización", JOptionPane.INFORMATION_MESSAGE);
			System.exit(2);
		} catch (IOException ex) {
			throw new IllegalStateException("Can't extract & deflate UPDATE data paclkage:"+ex.getMessage());
		}
		*/
	}

	private void extractFolder(String zipFile) throws ZipException, IOException {
		System.out.println(zipFile);
		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		String destPathToInflate = ".";

		Enumeration zipFileEntries = zip.entries();

		// Process each entry
		System.err.println("-> extracting :");
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(destPathToInflate, currentEntry);
			System.err.println("-> inflating :"+destFile.getPath());
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
		System.err.println("-> OK, finish extracting.");
	}

	boolean canDownlaodUpdateApplication() {
		try {
			URL  url = new URL(ULR_APP_PACKAGE);
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
	
	boolean checkForAdmin(String plainPassword) {
		return checkFor("ADMIN_PASSWD", plainPassword);
	}
	
	boolean checkForUser(String plainPassword) {
		return checkFor("USER_PASSWD", plainPassword);
	}
	
	void updateForAdmin(String plainPassword) {
		updateFor("ADMIN_PASSWD", plainPassword);
	}
	
	void updateForUser(String plainPassword) {
		updateFor("USER_PASSWD", plainPassword);
	}
	
	private boolean checkFor(String property,String plainPassword) {
		return false;
	}

	private void updateFor(String property,String plainPassword) {
		
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

	String getVersion() {
		if(_version == null){
			Properties porpVersion = new Properties();
			try {
				porpVersion.load(getClass().getResourceAsStream("/version.properties"));
				_version = porpVersion.getProperty("xpresscashdrawer.version");
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
