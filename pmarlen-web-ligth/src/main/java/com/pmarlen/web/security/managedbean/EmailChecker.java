/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.security.managedbean;

import java.util.Properties;

import org.apache.log4j.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 *
 * @author alfredo
 */
public class EmailChecker {
	private static String host   = "mail.perfumeriamarlen.com.mx";
	private static String port_1 = "25";
	private static String port_2 = "26";
	private static String port  = null;
	private static Logger logger = Logger.getLogger(EmailChecker.class.getSimpleName());
	
	public static boolean isSameEmailPassword(String username, String password){
		
		logger.info("----->>username="+username+", password="+password+", port:"+port);
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		//properties.put		  ("mail.smtp.socketFactory.port", port);
		
		Session session = null;
		
		try {
			if(port == null){
				properties.setProperty("mail.smtp.port", port_1);
				session = Session.getDefaultInstance(properties);
				logger.info("Session created, trying:"+port_1);
				Transport tr = session.getTransport("smtp");
				tr.connect(host, username, password);	
				logger.info("OK, Connected....");
				port = port_1;
				return true;
			} else {
				properties.setProperty("mail.smtp.port", port);
				session = Session.getDefaultInstance(properties);
				logger.info("Session created, trying the definnitive port:"+port);
				Transport tr = session.getTransport("smtp");
				tr.connect(host, username, password);			
				logger.info("OK, Connected....");
				return true;
			}			
		} catch (MessagingException me1) {			
			if(me1 instanceof javax.mail.MessagingException && me1.getCause()!=null && me1.getCause() instanceof java.net.ConnectException){
				try {
					properties.setProperty("mail.smtp.port", port_2);
					session = Session.getDefaultInstance(properties);
					logger.info("Session can't create, trying with :"+port_2);
					Transport tr = session.getTransport("smtp");
					tr.connect(host, username, password);	
					logger.info("OK, Connected....");
					port = port_2;
					return true;
				} catch (MessagingException me2) {
					logger.error( "fail:", me2);
					if(me2 instanceof javax.mail.AuthenticationFailedException && port ==null) {
						port = port_2;
					}
					return false;
				}
			} else if(me1 instanceof javax.mail.AuthenticationFailedException && port ==null){
				port = port_1;				
			}
			return false;
						
		}	
	}
	
	public static void main(String[] args) {
		if(args.length==2){
			System.out.println(isSameEmailPassword(args[0],args[1]));
		} else {
			System.out.println(isSameEmailPassword("aestrada@perfumeriamarlen.com.mx","dh3rku61c"));
		}
	}

	public static String getPort() {
		return port;
	}
	
}
