/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.caja.view.UpadateApplicationJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author alfredo
 */
public class UpadateApplicationJFrameControl implements UpdateApplicationListener, ActionListener{
	private static boolean actualizando;
	private UpadateApplicationJFrame ua;	

	public UpadateApplicationJFrameControl(UpadateApplicationJFrame ua) {
		this.ua = ua;
		this.ua.getProgressUpdate().setValue(0);
		this.ua.getCancelar().addActionListener(this);
	}
	
	public void estadoInicial(){
		actualizando=true;
		if(! ApplicationLogic.getInstance().canDownlaodUpdateApplication()){
			JOptionPane.showMessageDialog(null, "NO SE PUEDE DESCARGAR EL ARCHIVO, PARECE ESTAR CORRUPTO", "ACTUALIZAR", JOptionPane.ERROR_MESSAGE);
			actualizando=false;
			return;
		}
		ua.setVisible(true);
		ApplicationLogic.getInstance().updateApplication(this);
	}
	
	public void updateProgress(int percentAdvance){
		ua.getProgressUpdate().setValue(percentAdvance);
		if(percentAdvance > 99){
			this.ua.setVisible(false);
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == ua.getCancelar()){
			cancelar_actionPerformed();
		}
	}

	private void cancelar_actionPerformed() {
		ApplicationLogic.getInstance().cacellUpdateApplication();
		//this.ua.getCancelar().setEnabled(false);		
	}	


	public static boolean isActualizando() {
		return actualizando;
	}

	@Override
	public void finisUpdate() {
		JOptionPane.showMessageDialog(null, "SE DESCARGO E EINSTALO CORRECTAMENTE,\n REINICIE PARA APLICAR CAMBIOS", "ACTUALIZAR", JOptionPane.INFORMATION_MESSAGE);
		actualizando =  false;
		System.exit(3);
	}

	@Override
	public void cancelUpdate() {
		JOptionPane.showMessageDialog(null, "SE CANCELO LA DESCARGA", "ACTUALIZAR", JOptionPane.ERROR_MESSAGE);
		actualizando =  false;
	}

	@Override
	public void errorUpdate(String error) {
		JOptionPane.showMessageDialog(null, "ERROR AN DESCARGA:"+error, "ACTUALIZAR", JOptionPane.ERROR_MESSAGE);
		actualizando = false;
	}
}
