/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.servlet;

import com.pmarlen.backend.dao.EstadoDAO;
import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Estado;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.quickviews.UsuarioQuickView;
import java.util.ArrayList;

import org.apache.log4j.Logger;

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
		logger.debug("-> run: running?"+running);
		
		while(running){			
			try {
				ArrayList<Estado> estados = EstadoDAO.getInstance().findAll();
				
				logger.debug("\t-> while ("+running+"): getting estados:"+(estados!=null?estados.size()+" elements.":null));				
				Thread.sleep(60000);
			} catch(Exception e){
				logger.error("while running:", e);
				break;
			}
		}
		logger.info("->end run.");
	}
	
}
