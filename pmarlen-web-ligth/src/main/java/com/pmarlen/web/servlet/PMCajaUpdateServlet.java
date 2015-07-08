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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alfredo
 */
public class PMCajaUpdateServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	private static final String URI_VERSION   = "/pmcajaupdate/version.properties";
	private static final String URI_FILE      = "/pmcajaupdate/update.zip";
	private static final String URI_INSTALLER = "/pmcajaupdate/pmarlen-caja-installer.jar";
		
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String pmcajadist = getInitParameter("pmcajadist");
		String uri=request.getRequestURI();
		OutputStream os = null;
		InputStream is = null;
		try {			
			System.out.println("->PMCajaUpdateServlet: request.getRequestURI():"+request.getRequestURI());
			File fileForDownload=null;
			if(uri.endsWith(URI_VERSION)  || uri.endsWith(URI_FILE)||uri.endsWith(URI_INSTALLER)){
				if(uri.endsWith(URI_VERSION) ){
					fileForDownload =  new File(pmcajadist+"/classes/version.properties");
					response.setContentType("text/plain");
				} else if(uri.endsWith(URI_FILE)){
					fileForDownload =  new File(pmcajadist+"/update.zip");
					response.setContentType("application/zip");
				} else if(uri.endsWith(URI_INSTALLER)){
					fileForDownload =  new File(pmcajadist+"/pmarlen-caja-installer.jar");
					response.setContentType("application/zip");
				}
				
				if(!fileForDownload.exists() || !fileForDownload.canRead()) {
					File symLinkForDist =  new File(pmcajadist);
					if(!symLinkForDist.exists()){
						throw new IllegalStateException("NO SE HA CREADO LA SYM LINK:"+pmcajadist+" EN ESTE SERVIDOR QUE APUNTE A EL DIR DE */pmarlen-caja/target");
					}
					if(!fileForDownload.exists() || !fileForDownload.canRead()) {
						throw new IllegalStateException("NO SE PUEDE LEER ARCHIVO PARA:"+uri+", TAL VEZ FALTE EJECUTAR: mvn clean install -Pinstaller.");
					}
					throw new IllegalStateException("NO SE PUEDE LEER ARCHIVO PARA:"+uri);
				}
				
				System.out.println("OK, found, wrinting: "+fileForDownload.length()+" bytes.");
				response.setContentLength((int)fileForDownload.length());
				
				os = response.getOutputStream();
				is = new FileInputStream(fileForDownload);
				
				byte buffer[] = new byte[1024*16];
				int r;
				
				while((r=is.read(buffer, 0, buffer.length))!= -1){
					os.write(buffer, 0, r);
					os.flush();
				}
				buffer=null;
				
				is.close();				
			} else {
				throw new IllegalArgumentException("URI, ERRONEA:"+uri);
			}
		} catch(IllegalStateException ise){
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			ise.printStackTrace(System.err);
		} catch(IllegalArgumentException iae){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			iae.printStackTrace(System.err);
		} catch(Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace(System.err);			
		} finally {
			if(is != null){
				is.close();
			}
			if(os != null){
				os.close();
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
