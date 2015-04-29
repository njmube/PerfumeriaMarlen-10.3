/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja;

import com.pmarlen.backend.model.quickviews.SyncDTOPackage;
import com.pmarlen.caja.control.DialogLoginControl;
import com.pmarlen.caja.control.FramePrincipalControl;
import com.pmarlen.caja.dao.MemoryDAO;
import com.pmarlen.caja.view.DialogLogin;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author alfredo
 */
public class Main {

	private static Logger logger = Logger.getLogger(Main.class.getName());
	public static final String INTELBTH = "intelbth";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		FramePrincipalControl framePrincipalControl = null;
		DialogLoginControl dialogLoginControl = null;
		//debugClassLoader();
		//isSingleInstanceRunning();
		//CajaDataSourceFacade.registerStrategy();
		/*
		
		 if( ApplicationLogic.getInstance().needsUpdateApplciation()) {
		 int respuesta = JOptionPane.showConfirmDialog(null, "Hay una nueva version disponibla, ¿Desea que se descargue y acualizar esta?", 
		 "Actualización disponible", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
		 if(respuesta == JOptionPane.YES_OPTION){
		 UpadateApplicationJFrame uaf = new UpadateApplicationJFrame();
		 UpadateApplicationJFrameControl uafc = new UpadateApplicationJFrameControl(uaf);
		 uafc.estadoInicial();
		 }
		 }
		 */
		logger.info("==========================================================>>>");
		SyncDTOPackage paqueteSinc = MemoryDAO.getPaqueteSinc();
		logger.info("<<<==========================================================");

		try{
			logger.info("L&Fs:"+Arrays.asList(UIManager.getInstalledLookAndFeels()));
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
					 
		}catch(Exception e){
			logger.log(Level.WARNING, "setLookAndFeel:", e);
		}
		
		try {
			
			framePrincipalControl = FramePrincipalControl.getInstance();

			DialogLogin dialogLogin = DialogLogin.getInstance(framePrincipalControl.getFramePrincipal());
			dialogLoginControl = DialogLoginControl.getInstance(dialogLogin);

			framePrincipalControl.estadoInicial();

			logger.info("-------->> antes de login");

			dialogLoginControl.estadoInicial();

			if (!dialogLoginControl.isLoggedIn()) {
				throw new IllegalAccessException("Contraseña incorrecta");
			} else {
				logger.info("->OK logedin, GO !");
				framePrincipalControl.enableAndDisableAdminControls();
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

	}

	private static void isSingleInstanceRunning() {
		// Re implement
	}
	/*
	 private static void debugClassLoader() {
	 String os_arch = System.getProperty("os.arch");
	 System.out.println("OS_ARCH:->"+os_arch);
	 System.setProperty("os.arch", "x86");
	 os_arch = System.getProperty("os.arch");
	 System.out.println("\tOS_ARCH:->>>>"+os_arch);
		
	 ClassLoader cl = ClassLoader.getSystemClassLoader();
 
	 URL[] urls = ((URLClassLoader)cl).getURLs();
 
	 for(URL url: urls){
	 String cpFile = url.getFile();
	 System.out.println("CLASSPATH:->"+cpFile);
	 if(cpFile.toLowerCase().contains(".jar")){
	 try {
	 JarFile jarCPFile = new JarFile(new File(cpFile));
	 Enumeration<JarEntry> jarEntries = jarCPFile.entries();
	 while(jarEntries.hasMoreElements()){
	 JarEntry nextJarEntry = jarEntries.nextElement();
	 if(nextJarEntry.getName().contains(INTELBTH)){
	 System.out.println("\t=> IN JAR:"+nextJarEntry);
	 }
	 }
	 } catch (IOException ex) {
	 ex.printStackTrace(System.err);
	 }
	 }
	 }
	 Map<String, String> env = System.getenv();
	 for (String envName : env.keySet()) {
	 System.out.format("SYSTEM_ENV:-> %s=%s%n",
	 envName,
	 env.get(envName));
	 }		

	 Properties sysProp = System.getProperties();		
	 Set sysPropertiesSet = sysProp.keySet();
		
	 for(Object k: sysPropertiesSet){
	 String var = sysProp.getProperty(k.toString());						
	 System.out.print("SYSTEM_PROP:->"+k+"="+var);						
	 if(k.toString().toLowerCase().contains("java.library.path")){
	 System.out.println("");
	 String[] varDisr = var.split(";");
	 for(String dir: varDisr){
	 System.out.println("\t=>DIR:"+dir);
	 File fileDir = new File(dir);
	 File[] filesInDir = fileDir.listFiles();
	 if(filesInDir != null){
	 for(File fin: filesInDir){
	 if(fin.getName().contains(INTELBTH)){
	 System.out.println("\t\t (*) FILE :"+fin.getAbsolutePath());
	 } else {
	 //System.out.println("\t\t     FILE :"+fin.getAbsolutePath());
	 }
							
	 }
	 }
	 }
	 } else {
	 System.out.println("");				
	 }
	 }
	 System.out.println("=========================================================");
	 }
	 */
}
