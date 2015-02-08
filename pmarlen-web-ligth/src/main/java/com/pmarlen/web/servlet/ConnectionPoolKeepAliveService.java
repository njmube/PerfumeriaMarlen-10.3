/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.servlet;

import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Usuario;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfredo
 */
public class ConnectionPoolKeepAliveService extends Thread implements Runnable{
	private static boolean running=false;
	private static Logger logger = Logger.getLogger(ConnectionPoolKeepAliveService.class.getName());

	static ConnectionPoolKeepAliveService instance;
	
	private ConnectionPoolKeepAliveService() {
		logger.info("-> init.");
	}

	public static ConnectionPoolKeepAliveService getInstance() {
		if(instance == null){
			instance = new ConnectionPoolKeepAliveService();
		}
		return instance;
	}
	
	public void stopRunning(){
		logger.info("-> stopRunning:");
		running = false;
		this.interrupt();
	}
	
	@Override
	public void run() {
		running = true;
		logger.info("-> run: running?"+running);
		
		while(running){			
			try {
				ArrayList<Usuario> usuariosList = UsuarioDAO.getInstance().findAll();
				logger.info("\t-> while ("+running+"): getting usuariosList:"+(usuariosList!=null?usuariosList.size()+" elements.":null));				
				Thread.sleep(60000);
			} catch(Exception e){
				logger.log(Level.SEVERE, "while running:", e);
				break;
			}
		}
		logger.info("->end run.");
	}
	
}
