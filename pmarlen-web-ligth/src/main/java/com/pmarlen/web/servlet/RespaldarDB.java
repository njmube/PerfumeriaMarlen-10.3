/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.servlet;

import com.pmarlen.model.Constants;
import com.pmarlen.web.security.managedbean.SystemInfoMB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alfredo
 */
public class RespaldarDB extends HttpServlet {

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
		ServletOutputStream os = response.getOutputStream();
		InputStream is=null;
		File        f=null;
		try {
			f = runCommand(os);
			response.setContentType("application/x-gzip");
			response.setContentLength((int)f.length());
			is = new FileInputStream(f);
			byte[] buffer = new byte[1024*256];
			int r;
			while((r = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, r);
				os.flush();
			}
			os.close();
			is.close();
		} catch(Exception e){
			e.printStackTrace(System.err);
		}
	}
	
	private File runCommand(OutputStream os) throws Exception{
		Runtime r = Runtime.getRuntime();
		File is=null;
		Process p = null;
		PrintStream ps = new PrintStream(os);
		String user     = "root"; //SystemInfoMB.getUser();
		String password = "root"; //SystemInfoMB.getPassword();
		String db       = SystemInfoMB.getDB();
		System.err.println("->RespaldarDB servlet: user="+user+", password="+password+", db="+db);
		
		//p = r.exec("ls -l /usr/local/pmarlen_mysqldump/");
		p = r.exec("/usr/local/pmarlen_mysqldump/backupPM103.sh "+user+" "+password+" "+db);
		int exitValue = p.waitFor();
		if(exitValue == 0) {
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			String fileName=null;
			while ((line = b.readLine()) != null){
				fileName = line;
			}
			b.close();
			
			is=new File(fileName);			
			
			return is;
		} else {
			throw new Exception("Error");
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
