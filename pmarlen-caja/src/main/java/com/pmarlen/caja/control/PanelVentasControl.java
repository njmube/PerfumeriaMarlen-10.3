/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.backend.dao.PedidoVentaDAO;
import com.pmarlen.backend.dao.PedidoVentaDetalleDAO;
import com.pmarlen.backend.model.FechaCellRender;
import com.pmarlen.backend.model.ImporteCellRender;
import com.pmarlen.backend.model.PedidoVenta;
import com.pmarlen.backend.model.PedidoVentaDetalle;
import com.pmarlen.caja.model.VentaTableModel;
import com.pmarlen.caja.view.PanelVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author alfredo
 */
public class PanelVentasControl implements ActionListener,TableModelListener,MouseListener{

	private PanelVentas panelVentas;
	private PedidoVentaDAO pedidoVentaDAO;
	private PedidoVentaDetalleDAO pedidoVentaDetelleDAO;	
	private DecimalFormat df;
	private VentaTableModel ventasTM;
	private ImporteCellRender importeCellRender;
	private FechaCellRender fechaCellRender;
	public PanelVentasControl(PanelVentas panelVentas) {
		this.panelVentas = panelVentas;
		ventasTM = (VentaTableModel)this.panelVentas.getVentasJTable().getModel();
		ventasTM.addTableModelListener( this);
		panelVentas.getVentasJTable().addMouseListener(this);
		panelVentas.getVentasJTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.err.println("->>table columns="+panelVentas.getVentasJTable().getColumnCount());
		
		importeCellRender = new ImporteCellRender();
		fechaCellRender = new FechaCellRender();
		
		importeCellRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pedidoVentaDAO = PedidoVentaDAO.getInstance();
		pedidoVentaDetelleDAO = PedidoVentaDetalleDAO.getInstance();
	}
	
	public void refrescar() {
		
		List<PedidoVenta> ventaList  = (List<PedidoVenta>)pedidoVentaDAO.findAll();
		Hashtable<Integer,Double> ventaImporteList = new Hashtable<Integer,Double>();
		for(PedidoVenta v: ventaList){
			double t=0.0;
			List<PedidoVentaDetalle> detalleVenta = null;//pedidoVentaDetelleDAO.findBy(v);
			for(PedidoVentaDetalle dv: detalleVenta){
				t += dv.getPrecioVenta() * dv.getCantidad();
			}
			
			ventaImporteList.put(v.getId(), t);
		}
		ventasTM = new VentaTableModel(ventaList, ventaImporteList);
		panelVentas.getVentasJTable().setModel(ventasTM);
		final int tw = panelVentas.getVentasJTable().getWidth();
		int[] cws = new int[]{10,60,30};
		int cw;
		System.err.println("->panelVentas.getVentasJTable().getSize()="+tw);
		int i=0;
		for(int cwi : cws) {
			cw = (tw * cwi)/100;
			System.err.println("->\tpanelVentas.getVentasJTable().column["+i+"] PreferredWidth:"+cw);
		
			panelVentas.getVentasJTable().getColumnModel().getColumn(i++).setPreferredWidth(cw);
		}
		
		panelVentas.getVentasJTable().getColumnModel().getColumn(1).setCellRenderer(fechaCellRender);		
		panelVentas.getVentasJTable().getColumnModel().getColumn(2).setCellRenderer(importeCellRender);		
		panelVentas.getVentasJTable().updateUI();
	}
	
	public void estadoInicial(){
		refrescar();
		panelVentas.getVentasJTable().updateUI();		
		panelVentas.getVentasJTable().getSelectionModel().clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		panelVentas.getVentasJTable().updateUI();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}	
}
