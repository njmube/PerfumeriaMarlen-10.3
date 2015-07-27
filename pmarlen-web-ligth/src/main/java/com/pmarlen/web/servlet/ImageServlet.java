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
import java.util.Arrays;

import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
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
		String uri = request.getRequestURI();
		//   http://host:port/pmarlen-web-ligth/multimedio/productos/med/8410190297579
		//   http://host:port/pmarlen-web-ligth/multimedio/productos/min/8410190297579
		//   http://host:port/pmarlen-web-ligth/multimedio/productos/def/8410190297579
		//   http://host:port/pmarlen-web-ligth/multimedio/productos/ico/8410190297579		
		
		//   /usr/local/pmarlen_multimedio/PM_MULTIMEDIO_MED_JPG/MED_PRODUCTO_MULTIMEDIO_8714789135243_1.png
		//	 /usr/local/pmarlen_multimedio/PM_MULTIMEDIO_MIN_JPG/MIN_PRODUCTO_MULTIMEDIO_8714789135243_1.png
		//	 /usr/local/pmarlen_multimedio/PM_MULTIMEDIO_DEF_JPG/DEF_PRODUCTO_MULTIMEDIO_8714789135243_1.png
		//	 /usr/local/pmarlen_multimedio/PM_MULTIMEDIO_ICO_JPG/ICO_PRODUCTO_MULTIMEDIO_8714789135243_1.png
		
		//logger.info("->request URI="+uri);
		String urix[]=uri.split("/");
		
		//logger.info("->urix="+Arrays.asList(urix));
		
		String size = uri.substring(uri.lastIndexOf("/")-3,uri.lastIndexOf("/")).toUpperCase();
		String cb   = uri.substring(uri.lastIndexOf("/")+1);
		//logger.info("->size="+size+",cb="+cb);
		
		OutputStream os = null;
		InputStream is = null;
		
		File f =  new File((new StringBuilder(
				"/usr/local/pmarlen_multimedio/imgs_productos/").append(
				size.toLowerCase()).append(
				"/png/").append(
				size.toUpperCase()).append(
				"-NWM_")).append(
				cb).append(
				"_01.png"
				).toString());
		
		//logger.info("->try to read file:="+f+", exsist?"+f.exists()+",read?"+f.canRead());
		if(f.exists() && f.canRead()){
			//logger.info("->Found, ok reading for dispatch");
			response.setContentType("image/png");			
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
			//logger.info("->Done dispatch for:"+uri);
		} else {			
			String defaultImageURI = (new StringBuilder(
				"/imgs/").append(
				size.toUpperCase()).append(
				".png")).toString();
			
			//logger.info("->File not found, for size:"+size.toUpperCase()+", read from classpath to:"+defaultImageURI);
			response.setContentType("image/png");			
			//response.setContentLength();
			is = getClass().getResourceAsStream(defaultImageURI);
			//logger.info("->is="+is);
			if(is != null){
				
				os = response.getOutputStream();
				byte buffer[]=new byte[1024*8];
				int r=-1;
				while((r=is.read(buffer, 0, buffer.length)) != -1){
					os.write(buffer, 0, r);
					os.flush();
				}
				is.close();
				os.close();			
				//logger.info("->Done dispatch for:"+uri);
			} else{
				//logger.info("->Can't dispatch default image, reading:"+defaultImageURI);
			}
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
