/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.servlet;

import com.pmarlen.backend.dao.CfdDAO;
import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.UsuarioDAO;
import com.pmarlen.backend.model.Cfd;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.Usuario;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.businesslogic.reports.GeneradorImpresionPedidoVenta;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alfredo
 */
public class GetXMLCFDI extends HttpServlet {

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("-->>GetXMLCFDI servlet: RequestURI="+request.getRequestURI());
		
		ServletOutputStream outputStream = null;
		Integer pedidoVentaId = null;
		boolean fullPrint     = false;
		String pedidoId=null;
		try {
			//                /docs/cfdi/
			//                /docs/cfdi/CFDI_PerfumeriaMarlen_No_#####.xml
			// /pmarlen-webrf3/docs/cfdi/CFDI_PerfumeriaMarlen_No_316.xml
			// 0---------1---------2---------3---------4---------5-------
			String requestURI = request.getRequestURI();
			//String requestURIByPaths[] = requestURI.split("/");
			int pedidoIdIndex     = requestURI.lastIndexOf("No_")+3;			
			int xmlIndex          = requestURI.lastIndexOf(".xml");
			System.err.println("-->>GenerarPDFFactura servlet: pedidoIdIndex="+pedidoIdIndex+", xmlIndex="+xmlIndex);
			
			if(xmlIndex > pedidoIdIndex) {
				pedidoId = requestURI.substring(pedidoIdIndex,xmlIndex);
				pedidoVentaId = Integer.parseInt(pedidoId);
				fullPrint     = true;
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			int pedidoVentaID = Integer.parseInt(pedidoId);
			EntradaSalidaQuickView pv = EntradaSalidaDAO.getInstance().findBy(new EntradaSalida(pedidoVentaID));
			String cfdi= pv.getCdfNumCFD();
			
			Cfd cfd = CfdDAO.getInstance().findByNumCFDI(cfdi);
			
			byte[] bytesPdf = cfd.getContenidoOriginalXml();
			
			System.err.println("-->>OK writing PDF bytes");
			
			response.setContentType("application/xml");
			
			outputStream = response.getOutputStream();
			
			outputStream.write(bytesPdf);
			outputStream.flush();
			outputStream.close();
		} catch(Exception ex){
			ex.printStackTrace(System.err);			
		} finally {			
			
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
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
	 * Handles the HTTP
	 * <code>POST</code> method.
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

