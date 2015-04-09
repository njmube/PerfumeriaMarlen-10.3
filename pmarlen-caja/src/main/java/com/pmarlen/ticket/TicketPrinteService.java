/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.ticket;

import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.caja.control.ApplicationLogic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author alfredo
 */
public interface TicketPrinteService {
    Object generateTicket(EntradaSalida pv,ArrayList<EntradaSalidaDetalle> pvdList,HashMap<String,String> extraInformation) throws IOException ;
    void sendToPrinter(Object objectToPrint) throws IOException ;
    void testDefaultPrinter() throws IOException;
	void setApplicationLogic(ApplicationLogic al);
}
