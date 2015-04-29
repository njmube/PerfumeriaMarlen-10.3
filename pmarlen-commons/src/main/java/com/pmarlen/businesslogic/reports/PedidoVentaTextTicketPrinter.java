/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic.reports;

import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaFooter;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import java.util.ArrayList;

/**
 *
 * @author alfredo
 */
public class PedidoVentaTextTicketPrinter {

	public static void main(String[] args) {

		int pedidoVentaId = 0;

		for (String a : args) {
			//if(DEBUG) System.err.println("a->"+a+"<-");

			String aa[] = a.split("=");
			if (aa != null && aa.length > 1) {
				String an = aa[0];
				String av = aa[1];

				if (an.equals("pedidoVentaId")) {
					pedidoVentaId = Integer.parseInt(av);
				} else if (an.equals("--debug")) {
					TextReporter.DEBUG = av.equals("true");
				}
			}
		}

		EntradaSalidaQuickView pedidoVenta = null;
		ArrayList<EntradaSalidaDetalleQuickView> entityList = null;
		EntradaSalidaFooter entradaSalidaFooter= null;
		try{	
			pedidoVenta = EntradaSalidaDAO.getInstance().findBy(new EntradaSalida(pedidoVentaId));
			entityList = EntradaSalidaDAO.getInstance().findAllESDByEntradaSalida(pedidoVentaId);
			entradaSalidaFooter= new EntradaSalidaFooter();
			entradaSalidaFooter.calculaTotalesDesde(pedidoVenta, entityList);
		} catch(DAOException de){
			
		}
		
	}

}
