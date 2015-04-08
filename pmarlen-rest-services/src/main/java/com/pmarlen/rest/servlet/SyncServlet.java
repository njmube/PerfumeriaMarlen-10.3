/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.rest.servlet;

import com.pmarlen.backend.dao.AlmacenProductoDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.model.quickviews.InventarioSucursalQuickView;
import com.pmarlen.rest.dto.P;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author alfredo
 */
public class SyncServlet extends HttpServlet {

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
		
		response.setContentType("application/zip");		
		response.addHeader("Content-Disposition", "attachment; filename=data.zip");

		OutputStream os=response.getOutputStream();
        List<P> l = null;
		String sucursalId=request.getParameter("sucursalId");
		try {
			l = new ArrayList<P>();
			int sucId=new Integer(sucursalId);
			ArrayList<InventarioSucursalQuickView> p = AlmacenProductoDAO.getInstance().findAllBySucursal(sucId);
			for(InventarioSucursalQuickView is: p){
				l.add(is.getFaccadeForREST());
			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(l);
			
			ZipOutputStream zos = new ZipOutputStream(os);

			zos.putNextEntry(new ZipEntry("data.json"));
			zos.write(jsonString.getBytes());
			
			zos.closeEntry();
			zos.finish();

		} catch (Exception ex) {
			
		} finally {
			os.close();
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
