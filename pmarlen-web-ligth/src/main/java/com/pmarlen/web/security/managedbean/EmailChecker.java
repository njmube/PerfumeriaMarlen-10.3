/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.security.managedbean;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 *
 * @author alfredo
 */
public class EmailChecker {
	private static String host = "mail.perfumeriamarlen.com.mx";
	private static String port = "25";
	private static Logger logger = Logger.getLogger(EmailChecker.class.getSimpleName());
	
	public static boolean isSameEmailPassword(String username, String password){
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.put		  ("mail.smtp.socketFactory.port", port);
		properties.setProperty("mail.smtp.port", port);
		
		Session session = null;
		
		try {			
			session = Session.getDefaultInstance(properties);
			logger.info("Session created....");
			Transport tr = session.getTransport("smtp");
			tr.connect(host, username, password);			
			logger.info("OK, Connected....");
			return true;
		} catch (MessagingException mex) {
			logger.log(Level.WARNING, "fail:", mex);
			return false;
		}	
	}
	
}
