/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.dao;

import com.google.gson.Gson;
import com.pmarlen.backend.model.quickviews.SyncDTOPackage;
import com.pmarlen.rest.dto.P;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author alfredo
 */
public class MemoryDAO {
	static Properties properties = new Properties();
	
	static {
		properties.put("host","localhost");
		properties.put("port","8070");
		properties.put("context","/pmarlen-web-ligth/sync/data?sucursalId=1&format=zip");
	}
	
	static SyncDTOPackage paqueteSinc;
	static Logger logger = Logger.getLogger(MemoryDAO.class.getName());
	static String fileName = "./fileModel.zip";
	//static String hostDownload = "localhost:8070"; //"pmarlencloudsrv2.dyndns.org:8070";
	static String urlDownload = null;//"http://"+hostDownload+"/pmarlen-web-ligth/sync/data?sucursalId=1&format=zip";
	
	static HashMap<String,P> productosParaBuscar;
	static String propertiesFileNAme="./system.properties";
	
	public static void loadProperties() {
		
		File fileProperties = new File(propertiesFileNAme);
		boolean exsistFile = false;
		Properties propF1=null;
		if(fileProperties.canRead()){
			try {
				logger.info("->reading File Properties.");
				propF1.load(new FileInputStream(fileProperties));
				logger.info("->ok, writing.");
				exsistFile = true;
			}catch(IOException ioe){				
				logger.log(Level.WARNING, "Can`t read File for properties", ioe);
			}
		}
		if(!exsistFile) {
			try {
				logger.info("->wrinting File Properties.");
				properties.store(new FileOutputStream(propertiesFileNAme), fileName);
				logger.info("->ok, writing.");
			}catch(IOException ioe){
				logger.log(Level.WARNING, "Can`t create File for properties", ioe);
			}
		}	
	}	
	
	public static void setPaqueteSinc(SyncDTOPackage paqueteSinc) {
		MemoryDAO.paqueteSinc = paqueteSinc;
	}

	public static SyncDTOPackage getPaqueteSinc() {
		if(paqueteSinc ==null ){
			if(!exsistPackage()){
				download();
			}
			readLocally();
		}
		return paqueteSinc;
	}

	private static boolean exsistPackage() {
		File modelFile = null;
		modelFile = new File(fileName);
		if (modelFile.canRead() && modelFile.isFile() && modelFile.length() > 1024) {
			return true;
		}
		return false;
	}

	private static void download() {
		URL url = null;

		try {
			logger.info(">> Downloading");
			StringBuilder sbURL = new StringBuilder();
			sbURL.append("http://").
					append(properties.get("host")).
					append(":").
					append(properties.get("port")).
					append(properties.get("context"));
			url = new URL(sbURL.toString());
			InputStream is = url.openStream();
			FileOutputStream fos = new FileOutputStream(fileName);

			byte buffer[] = new byte[1024];
			int r;
			logger.info(">> reading");
			while ((r = is.read(buffer)) != -1) {
				fos.write(buffer, 0, r);
				fos.flush();
			}
			fos.close();
			is.close();
			logger.info(">> saving");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}

	}

	private static void readLocally() {
		ZipFile zf=null;
		Gson gson=new Gson();
		String jsonContent=null;

		try {
			logger.info(">> open ZIP");
			zf = new ZipFile(fileName);

			Enumeration<? extends ZipEntry> entries = zf.entries();

			while(entries.hasMoreElements()){
				ZipEntry ze = entries.nextElement();

				byte content[]=null;
				byte buffer[] =new byte[1024];
				ByteArrayOutputStream baos= new ByteArrayOutputStream();
				if(ze.getName().endsWith(".json")){
					logger.info(">> Reading from:"+ze.getName()+", "+ze.getSize()+" bytes");
					InputStream is = zf.getInputStream(ze);
					int r=0;
					while((r=is.read(buffer))!=-1){
						baos.write(buffer, 0, r);
						baos.flush();
					}
					baos.close();
					is.close();
					logger.info(">> OK read.");
					
					content = baos.toByteArray();
					logger.info(">> content.length="+content.length);
					
					jsonContent=new String(content);					
					
					logger.info(">> jsonContent.size="+jsonContent.length());
					//logger.info(">> jsonContent="+jsonContent);
				}
			}
			zf.close();
			logger.info(">> After read zip");
			if(jsonContent != null) {
				logger.info(">> parse:");
				paqueteSinc = gson.fromJson(jsonContent, SyncDTOPackage.class);			
				logger.info(">> paqueteSinc:"+paqueteSinc);
				List<P> lp=paqueteSinc.getInventarioSucursalQVList();
				productosParaBuscar = new HashMap<String,P>();
				for(P p: lp){
					productosParaBuscar.put(p.getCb(), p);
				}
			}
			
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}

	}

	public static P fastSearchProducto(String codigoBuscar) {
		return productosParaBuscar.get(codigoBuscar);
	}

}
