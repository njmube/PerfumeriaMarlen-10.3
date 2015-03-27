/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alfredo
 */
public class ImageServlet extends HttpServlet {
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger logger=Logger.getLogger(ImageServlet.class.getName());
		String fn = request.getRequestURI();
		logger.info("->request URI="+fn);
		fn = fn.substring(fn.lastIndexOf("/"));
		OutputStream os = null;
		InputStream is = null;
		File f =  new File("/usr/local/pmarlen_multimedio/"+fn);
		logger.info("->try to read:="+f);
		if(f.exists() && f.canRead()){
			if(fn.equalsIgnoreCase("jpeg")||fn.equalsIgnoreCase("jpg")){
				response.setContentType("image/jpeg");
			}
			response.setContentLength((int)f.length());
			is = new FileInputStream(f);
			os = response.getOutputStream();
			byte buffer[]=new byte[1024*8];
			int r=-1;
			while((r=is.read(buffer, 0, buffer.length)) != -1){
				os.write(buffer, 0, r);
				os.flush();
			}
			is.close();
			os.close();			
		} else {
			logger.log(Level.WARNING, "read image: not found");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
