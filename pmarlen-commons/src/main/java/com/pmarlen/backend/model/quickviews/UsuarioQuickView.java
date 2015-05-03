/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.UsuarioPerfil;
import com.pmarlen.model.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class UsuarioQuickView extends Usuario{
	private List<PerfiQuickView> perfiles;
	private String passwordConfirm;
	private boolean editPassword;

	private boolean inRoleRoot;
	private boolean inRoleAdmin;
	private boolean inRoleFinances;
	private boolean inRolePMarlenUser;
	private boolean inRoleSales;
	private boolean inRoleStock;
	
	public UsuarioQuickView() {
		perfiles = new ArrayList<PerfiQuickView>();
		passwordConfirm = null;
	}

	public void setHabilitado(boolean habilitadoValue) {
		setAbilitado(habilitadoValue?1:0);
	}

	public boolean isHabilitado() {
		return getAbilitado()==1;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setEditPassword(boolean editPassword) {
		this.editPassword = editPassword;
	}

	public boolean isEditPassword() {
		return editPassword;
	}
	
	/**
	 * @return the inRoleRoot
	 */
	public boolean isInRoleRoot() {
		return inRoleRoot;
	}

	/**
	 * @param inRoleRoot the inRoleRoot to set
	 */
	public void setInRoleRoot(boolean inRoleRoot) {
		this.inRoleRoot = inRoleRoot;
	}

	/**
	 * @return the inRoleAdmin
	 */
	public boolean isInRoleAdmin() {
		return inRoleAdmin;
	}

	/**
	 * @param inRoleAdmin the inRoleAdmin to set
	 */
	public void setInRoleAdmin(boolean inRoleAdmin) {
		this.inRoleAdmin = inRoleAdmin;
	}

	/**
	 * @return the inRoleFinances
	 */
	public boolean isInRoleFinances() {
		return inRoleFinances;
	}

	/**
	 * @param inRoleFinances the inRoleFinances to set
	 */
	public void setInRoleFinances(boolean inRoleFinances) {
		this.inRoleFinances = inRoleFinances;
	}

	/**
	 * @return the inRolePMarlenUser
	 */
	public boolean isInRolePMarlenUser() {
		return inRolePMarlenUser;
	}

	/**
	 * @param inRolePMarlenUser the inRolePMarlenUser to set
	 */
	public void setInRolePMarlenUser(boolean inRolePMarlenUser) {
		this.inRolePMarlenUser = inRolePMarlenUser;
	}

	/**
	 * @return the inRoleSales
	 */
	public boolean isInRoleSales() {
		return inRoleSales;
	}

	/**
	 * @param inRoleSales the inRoleSales to set
	 */
	public void setInRoleSales(boolean inRoleSales) {
		this.inRoleSales = inRoleSales;
	}

	/**
	 * @return the inRoleStock
	 */
	public boolean isInRoleStock() {
		return inRoleStock;
	}

	/**
	 * @param inRoleStock the inRoleStock to set
	 */
	public void setInRoleStock(boolean inRoleStock) {
		this.inRoleStock = inRoleStock;
	}
	
	public boolean getPlaysAsAdmin(){
		return inRoleRoot || inRoleAdmin && isHabilitado();
	}	
	public boolean getPlaysAsFinances(){
		return inRoleRoot || inRoleFinances && isHabilitado();
	}
	public boolean getPlaysAsPMarlenUser(){
		return inRoleRoot || inRolePMarlenUser && isHabilitado();
	}
	public boolean getPlaysAsSales(){
		return inRoleRoot || inRoleSales && isHabilitado();
	}
	public boolean getPlaysAsStock(){
		return inRoleRoot || inRoleStock && isHabilitado();
	}
	public boolean getPlaysAsRoot(){
		return inRoleRoot;
	}
	
	public void addPerfil(String perfil) {
		if(perfil.equalsIgnoreCase(Constants.PERFIL_ROOT)){
			inRoleRoot = true;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_ADMIN)){
			inRoleAdmin = true;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_FINANCES)){
			inRoleFinances = true;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_PMARLENUSER)){
			inRolePMarlenUser = true;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_SALES)){
			inRoleSales = true;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_STOCK)){
			inRoleStock = true;
		}
	}

	public void removePerfil(String perfil) {
		if(perfil.equalsIgnoreCase(Constants.PERFIL_ROOT)){
			inRoleRoot = false;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_ADMIN)){
			inRoleAdmin = false;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_FINANCES)){
			inRoleFinances = false;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_PMARLENUSER)){
			inRolePMarlenUser = false;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_SALES)){
			inRoleSales = false;
		} else if(perfil.equalsIgnoreCase(Constants.PERFIL_STOCK)){
			inRoleStock = false;
		}
	}
	
	public List<String> getRoleList(){
		List<String> rolesList=new ArrayList<String>();
		
		if(inRoleRoot){
			rolesList.add(Constants.PERFIL_ROOT);
		} 
		if(inRoleAdmin){
			rolesList.add(Constants.PERFIL_ADMIN);
		} 
		if(inRoleFinances){
			rolesList.add(Constants.PERFIL_FINANCES);			
		} 
		if(inRolePMarlenUser){
			rolesList.add(Constants.PERFIL_PMARLENUSER);
		} 
		if(inRoleSales){
			rolesList.add(Constants.PERFIL_SALES);
		} 
		if(inRoleStock){
			rolesList.add(Constants.PERFIL_STOCK);
		}
		
		return rolesList;
	}
}
