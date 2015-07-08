/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja;

import com.pmarlen.backend.model.quickviews.SyncDTOPackage;
import com.pmarlen.caja.control.ApplicationLogic;
import com.pmarlen.caja.control.DialogLoginControl;
import com.pmarlen.caja.control.FirstRunParamsConfigDialogControl;
import com.pmarlen.caja.control.FramePrincipalControl;
import com.pmarlen.caja.control.UpadateApplicationJFrameControl;
import com.pmarlen.caja.dao.MemoryDAO;
import com.pmarlen.caja.view.DialogLogin;
import com.pmarlen.caja.view.UpadateApplicationJFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author alfredo
 */
public class Main {

	private static boolean singleInstanceRunning = false;
	private static Logger logger = Logger.getLogger(Main.class.getName());
	//public static final String INTELBTH = "intelbth";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		FramePrincipalControl framePrincipalControl = null;
		DialogLoginControl dialogLoginControl = null;

		isSingleInstanceRunning();

		logger.info("==========================================================>>>");

		MemoryDAO.loadProperties();

		logger.info("<<<==========================================================");

		try {
			logger.info("L&Fs:" + Arrays.asList(UIManager.getInstalledLookAndFeels()));
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);

		} catch (Exception e) {
			logger.log(Level.WARNING, "setLookAndFeel:", e);
		}

		if (!MemoryDAO.isExsistFile()) {
			logger.info("==========================================================>>> 1 Time");
			FirstRunParamsConfigDialogControl.getInstance().estadoInicial();

			while (FirstRunParamsConfigDialogControl.isConfiguring()) {
				try {
					logger.info("==>> configuring");
					Thread.sleep(500);
				} catch (InterruptedException ie) {
				}
			}
			logger.info("==>> is configuring:" + FirstRunParamsConfigDialogControl.isConfiguring());
			if (!FirstRunParamsConfigDialogControl.getParamatersConfigured()) {
				System.exit(2);
			}
		}

		if (ApplicationLogic.getInstance().needsUpdateApplciation()) {
			int respuesta = JOptionPane.showConfirmDialog(null, 					
					"HAY UNA NUEVA VERIÓN PARA INSTALAR,\n ¿ DESEA ACTUALIZAR DE LA VERSIÓN ACTUAL "+
					ApplicationLogic.getInstance().getVersion()+" A "+ApplicationLogic.getInstance().getVersionRead()+
					"\n EN ESTE MOMENTO ?",
					"ACTUALIZACION DISPONIBLE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (respuesta == JOptionPane.YES_OPTION) {
				UpadateApplicationJFrame uaf = new UpadateApplicationJFrame();
				UpadateApplicationJFrameControl uafc = new UpadateApplicationJFrameControl(uaf);
				uafc.estadoInicial();
				
				while(uafc.isActualizando()){
					try {
						logger.info("==>> updating");
						Thread.sleep(500);
					} catch (InterruptedException ie) {
					}
				}
			}
		}
		
		logger.info("======================= INICIANDO =======================");

		MemoryDAO.startPaqueteSyncService();

		try {
			framePrincipalControl = FramePrincipalControl.getInstance();

			DialogLogin dialogLogin = DialogLogin.getInstance(framePrincipalControl.getFramePrincipal());
			dialogLoginControl = DialogLoginControl.getInstance(dialogLogin);
			framePrincipalControl.setFontBigest();
			framePrincipalControl.estadoInicial();

			logger.info("-------->> Frame Principal, esperando antes de login");
			dialogLoginControl.setFontBigest();
			dialogLoginControl.getDialogLogin().pack();
			dialogLoginControl.getDialogLogin().setLocationRelativeTo(null);
			dialogLoginControl.estadoInicial();

			if (!dialogLoginControl.isLoggedIn()) {
				throw new IllegalAccessException("NO SE ACCESO ");
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
		new Thread() {
			@Override
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(5690);
					System.out.println("OK, is single instance !");
					singleInstanceRunning = true;
					Socket s = serverSocket.accept();
				} catch (IOException ioe) {
					ioe.printStackTrace(System.err);
					JOptionPane.showMessageDialog(null, "¡LA APLICACIÓN YA SE HA INICIADO!, \nCIERRE PRIMERA ESTA OTRA.", "PMCaja >> Express", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.start();

		int timeOut = 0;
		try {
			while (!singleInstanceRunning) {
				Thread.sleep(1000L);
				logger.info("-->> close ?");
				timeOut++;
				if (timeOut >= 10) {
					break;
				}
			}
		} catch (InterruptedException ie) {

		} finally {
			if (!singleInstanceRunning) {
				logger.info("-->> close !!");
				System.exit(1);
			}
		}
	}
}
