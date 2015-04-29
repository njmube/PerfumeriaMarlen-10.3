/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.caja.view.DialogLogin;
import com.pmarlen.rest.dto.U;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class DialogLoginControl implements ActionListener , FocusListener{
	DialogLogin dialogLogin;
	private boolean leggedIn;
	final int MAX_INTENTOS = 3;
	int intentos;
	U logged;

	private static DialogLoginControl instance;

	public static DialogLoginControl getInstance(DialogLogin dialogLogin) {
		if(instance == null){
			instance = new DialogLoginControl(dialogLogin);
		}
		return instance;
	}
	
	
	
	private DialogLoginControl(DialogLogin dialogLogin) {
		this.dialogLogin = dialogLogin;
		this.dialogLogin.getEmail()   .addFocusListener(this);
		this.dialogLogin.getEmail()   .addActionListener(this);
		this.dialogLogin.getAceptar() .addActionListener(this);
		this.dialogLogin.getPassword().addActionListener(this);
	}
	
	public void estadoInicial(){
		this.dialogLogin.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialogLogin.getAceptar()) {
			aceptar_ActionPerformed();
		} else if (e.getSource() == dialogLogin.getEmail()) {
			aceptar_ActionPerformed();
		} else if (e.getSource() == dialogLogin.getPassword()) {
			aceptar_ActionPerformed();
		}		
	}
	
	private void aceptar_ActionPerformed(){
		if(!validate()){
			
			JOptionPane.showMessageDialog(dialogLogin, "Complete los campos por favor", "Entrar", JOptionPane.WARNING_MESSAGE);
			dialogLogin.getEmail().requestFocus();
			
			return;
		}
		
		if(!autheticate()){
			dialogLogin.getPassword().setText("");
			JOptionPane.showMessageDialog(dialogLogin, "Correo/Contraseña incorrecta", "Entrar", JOptionPane.ERROR_MESSAGE);
			dialogLogin.getPassword().requestFocus();
			intentos++;
			if(intentos>=3){
				leggedIn = false;
				dialogLogin.dispose();
			}
		} else {
			leggedIn = true;
			
			JOptionPane.showMessageDialog(dialogLogin, "¡ Bienvenido al Sistema "+logged.getNc()+" !", "Entrar", JOptionPane.INFORMATION_MESSAGE);									
			
			dialogLogin.dispose();
		}
	}
	
	private boolean validate(){
		
		String passwordValue = new String(dialogLogin.getPassword().getPassword());
		if(passwordValue.trim().length()==0 || dialogLogin.getEmail().getText().trim().length()==0){
			return false;
		}
		return 	true;	
		
	}
	private boolean autheticate(){
		
		String passwordValue = new String(dialogLogin.getPassword().getPassword());
		if(passwordValue.trim().length()<1){
			return false;
		}
		logged =ApplicationLogic.getInstance().checkForUser(dialogLogin.getEmail().getText(),passwordValue);
		return 	logged != null;	
		
	}

	public boolean isLoggedIn() {
		return logged != null;
	}

	@Override
	public void focusGained(FocusEvent fe) {
		
	}

	@Override
	public void focusLost(FocusEvent fe) {
		if (fe.getSource() == dialogLogin.getEmail()) {
			email_FocusLost();
		}
	}
	
	private static final String DOMAIN_NAME="@perfumeriamarlen.com.mx";
	
	private void email_FocusLost(){
		String emailValue = dialogLogin.getEmail().getText().trim();
		if(emailValue.trim().length()>2 && !emailValue.contains(DOMAIN_NAME)){
			dialogLogin.getEmail().setText(emailValue+DOMAIN_NAME);
		}
	}
}
