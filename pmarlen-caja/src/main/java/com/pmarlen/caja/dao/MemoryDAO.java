/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.dao;

import com.google.gson.Gson;
import com.pmarlen.backend.model.quickviews.SyncDTOPackage;
import com.pmarlen.caja.control.FramePrincipalControl;
import com.pmarlen.rest.dto.P;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.log4j.Logger;
/**
 *
 * @author alfredo
 */
public class MemoryDAO {
	private static Properties properties = new Properties();
	
	static {
		//properties.put("host","pmarlencloudsrv1.dyndns.org");
		//properties.put("host","192.168.1.70");
		properties.put("sucursal","0");
		properties.put("caja","1");
		properties.put("host","localhost");
		properties.put("port","8070");
		properties.put("context","/pmarlen-web-ligth");
		properties.put("dropboxdir",System.getProperty("user.home")+"/DropBox/");
	}
	
	private static SyncDTOPackage paqueteSinc;
	private static Logger logger = Logger.getLogger(MemoryDAO.class.getName());
	private static String fileName = "./fileModel.zip";
	//static String hostDownload = "localhost:8070"; //"pmarlencloudsrv2.dyndns.org:8070";
	private static String urlDownload = null;//"http://"+hostDownload+"/pmarlen-web-ligth/sync/data?sucursalId=1&format=zip";
	
	private static HashMap<String,P> productosParaBuscar;
	private static String propertiesFileNAme="./system.properties";
	private static boolean exsistFile = false;	
	
	public static void loadProperties() {
		
		File fileProperties = new File(propertiesFileNAme);
		
		if(fileProperties.exists() && fileProperties.canRead()){
			try {
				logger.info("->Properties local File found, reading File Properties:"+fileProperties+"");
				properties.load(new FileInputStream(fileProperties));
				logger.info("->ok, Properties read:"+properties);
				exsistFile = true;
			}catch(IOException ioe){				
				logger.error( "Can`t read File for properties", ioe);
			}
		}
	}

	public static void persistPRoperties() {
		try {
			logger.info("->Properties Doesn't exsist, wrinting File Properties.");
			properties.store(new FileOutputStream(propertiesFileNAme), "Persisted");
			logger.info("->ok, writing.");
		}catch(IOException ioe){
			logger.error( "Can`t create File for properties", ioe);
		}
	}

	public static boolean isExsistFile() {
		return exsistFile;
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static String getServerContext(){
		StringBuilder sbURL = new StringBuilder("http://");
		sbURL.append(properties.get("host")).
				append(":").
				append(properties.get("port")).
				append(properties.get("context"));
		return sbURL.toString();
	}
	
	public static void setPaqueteSinc(SyncDTOPackage paqueteSinc) {
		MemoryDAO.paqueteSinc = paqueteSinc;
	}

	public static SyncDTOPackage getPaqueteSinc() {
		if(paqueteSinc ==null ){
			try {
				if(!exsistPackage()){
					download();
				}
				readLocally();
			}catch(IOException ioe){
				logger.error("downoload",ioe);
			}
		}
		return paqueteSinc;
	}
	private static boolean runnigPool = true;
	
	public static void getPaqueteSyncPoll(){
		logger.info("----------------->>BEFORE while ");
		while(runnigPool){
			try {
				logger.info("\t----------------->> while running, download");
				download();
				logger.info("\t----------------->> OK, downloded, read locally");
				readLocally();
				FramePrincipalControl.getInstance().getFramePrincipal().setConectado();
				//FramePrincipalControl.getInstance().getFramePrincipal().repaint();
			}catch(MalformedURLException e){
				logger.error("URL: downoload",e);
				FramePrincipalControl.getInstance().getFramePrincipal().setDesconectado();
				break;
			}catch(Exception e){
				logger.error("X: downoload",e);
				FramePrincipalControl.getInstance().getFramePrincipal().setDesconectado();
			}
			
			try {
				logger.info("\t----------------->> while running, sleep,.....");
				Thread.sleep(60000L);
			}catch(Exception e){
				logger.error("downoload",e);
			}
		}
	}
	
	public static void startPaqueteSyncService(){
		logger.info("----------------->> startPaqueteSyncService");
		new Thread(){
			@Override
			public void run() {
				getPaqueteSyncPoll();
			}
		}.start();
	}

	private static boolean exsistPackage() {
		File modelFile = null;
		modelFile = new File(fileName);
		if (modelFile.canRead() && modelFile.isFile() && modelFile.length() > 1024) {
			return true;
		}
		return false;
	}

	private static void download() throws IOException{
		URL url = null;

		StringBuilder sbURL = new StringBuilder("http://");
		sbURL.append(properties.get("host")).
				append(":").
				append(properties.get("port")).
				append(properties.get("context")).
				append("/sync/data?format=zip&sucursalId=").
				append(properties.get("sucursal")).
				append("&caja=").
				append(properties.get("caja"));
		url = new URL(sbURL.toString());
		logger.info(">> Downloading from:"+url);
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
				logger.info(">> productosParaBuscar, ok ready !");
			}
			
		} catch (Exception ex) {
			logger.error(null, ex);
		}

	}
	
	public static String getProperty(String p){
		return properties.getProperty(p);
	}

	public static P fastSearchProducto(String codigoBuscar) {
		return productosParaBuscar.get(codigoBuscar);
	}

}
