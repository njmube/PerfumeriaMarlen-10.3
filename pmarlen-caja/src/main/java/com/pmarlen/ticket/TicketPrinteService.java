/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.ticket;

import com.pmarlen.caja.control.ApplicationLogic;
import com.pmarlen.backend.model.PedidoVenta;
import com.pmarlen.backend.model.PedidoVentaDetalle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author alfredo
 */
public interface TicketPrinteService {
    Object generateTicket(PedidoVenta pv,ArrayList<PedidoVentaDetalle> pvdList,HashMap<String,String> extraInformation) throws IOException ;
    void sendToPrinter(Object objectToPrint) throws IOException ;
    void testDefaultPrinter() throws IOException;
	void setApplicationLogic(ApplicationLogic al);
}
