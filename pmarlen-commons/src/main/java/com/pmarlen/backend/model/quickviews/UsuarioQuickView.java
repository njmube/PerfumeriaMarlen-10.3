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
	
	public void addPerfil(String perfil){
		perfiles.add(new PerfiQuickView(perfil));
	}

	public List<PerfiQuickView> getPerfiles() {
		return perfiles;
	}
	private List<UsuarioQuickView> listForRenderRoles;
	
	public List<UsuarioQuickView> getListForRenderRoles(){
		if(listForRenderRoles ==null){
			listForRenderRoles = new  ArrayList<UsuarioQuickView>();
			listForRenderRoles.add(this);
		}
		return listForRenderRoles;
	}
	
	public boolean isInRoleAdmin(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_ADMIN)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRoleAdmin(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_ADMIN)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_ADMIN));
		}
	}
	
	public boolean isInRoleRoot(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_ROOT)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRoleRoot(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_ROOT)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_ROOT));
		}
	}

	public boolean isInRoleFinances(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_FINANCES)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRoleFinances(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_FINANCES)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_FINANCES));
		}
	}

	public boolean isInRolePMarlenUser(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_PMARLENUSER)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRolePMarlenUser(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_PMARLENUSER)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_PMARLENUSER));
		}
	}

	public boolean isInRoleSales(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_SALES)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRoleSales(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_SALES)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_SALES));
		}
	}
	
	public boolean isInRoleStock(){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_STOCK)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRoleStock(){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(Constants.PERFIL_STOCK)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(Constants.PERFIL_STOCK));
		}
	}
	//--------------------------------------------------------------------------
	public boolean isInRole(String role){
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(role)) {
				return true;
			}
		}
		return false;
	}
	
	public void setInRole(String role){
		boolean exist=false;
		for(PerfiQuickView pqv: perfiles){
			if(pqv.getPerfil().equalsIgnoreCase(role)) {
				exist=true;
				break;
			}
		}
		if(!exist){
			perfiles.add(new PerfiQuickView(role));
		}
	}
	
}
