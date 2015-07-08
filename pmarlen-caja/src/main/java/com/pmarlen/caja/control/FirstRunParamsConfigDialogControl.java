/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.caja.control;

import com.pmarlen.caja.dao.MemoryDAO;
import com.pmarlen.caja.view.FirstRunParamsConfigDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author alfredo
 */
public class FirstRunParamsConfigDialogControl implements ActionListener{
	private static boolean configuring=false;
	private static boolean paramatersConfigured=false;
	
	public static boolean isConfiguring() {
		return configuring;
	}

	public static boolean getParamatersConfigured() {
		return paramatersConfigured;
	}
	FirstRunParamsConfigDialog dlg;
	
	private static FirstRunParamsConfigDialogControl instance;

	private FirstRunParamsConfigDialogControl() {
		this.dlg = new FirstRunParamsConfigDialog();
		dlg.getGuardar().addActionListener(this);
		dlg.getCancelar().addActionListener(this);		
	}
	
	public void estadoInicial(){
		configuring = true;
		this.dlg.getServer().setText(MemoryDAO.getProperties().getProperty("host").trim());
		this.dlg.getPort().setText(MemoryDAO.getProperties().getProperty("port").trim());
		this.dlg.getDropBoxDir().setText(MemoryDAO.getProperties().getProperty("dropboxdir"));
		this.dlg.getSucursal().setText(MemoryDAO.getProperties().getProperty("sucursal"));
		this.dlg.getCaja().setText(MemoryDAO.getProperties().getProperty("caja"));
		this.dlg.setVisible(true);
	}

	public static FirstRunParamsConfigDialogControl getInstance() {
		if(instance == null){
			instance = new FirstRunParamsConfigDialogControl();
		}
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dlg.getGuardar()) {
			guardar_ActionPerformed();
		} else if (e.getSource() == dlg.getCancelar()) {
			cancelar_ActionPerformed();
		} else {
		
		}
	}
	
	private void guardar_ActionPerformed(){
		MemoryDAO.getProperties().setProperty("host",this.dlg.getServer().getText().trim());
		MemoryDAO.getProperties().setProperty("port",this.dlg.getPort().getText().trim());
		MemoryDAO.getProperties().setProperty("dropboxdir",this.dlg.getDropBoxDir().getText().trim());
		MemoryDAO.getProperties().setProperty("sucursal",this.dlg.getSucursal().getText().trim());
		MemoryDAO.getProperties().setProperty("caja",this.dlg.getCaja().getText().trim());
		
		MemoryDAO.persistPRoperties();
		
		this.dlg.dispose();
		configuring = false;
		paramatersConfigured = true;
	}
	
	private void cancelar_ActionPerformed(){
		this.dlg.dispose();
		configuring = false;
		//System.exit(2);
	}
	
}
