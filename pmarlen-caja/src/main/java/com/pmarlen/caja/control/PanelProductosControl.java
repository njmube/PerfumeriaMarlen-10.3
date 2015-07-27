/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.caja.model.CantidadCellRender;
import com.pmarlen.caja.model.DetalleProductoTableModel;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.ProductoQuickView;
import com.pmarlen.caja.model.ImporteCellRender;
import com.pmarlen.caja.view.PanelProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author alfredo
 */
public class PanelProductosControl implements ActionListener,TableModelListener,MouseListener{

	private PanelProductos panelProductos;
	private List<Producto> detalleProductoList;
	private ProductoDAO productoDAO;
	private DecimalFormat df;
	
	public PanelProductosControl(PanelProductos panelProductos) {
		this.panelProductos = panelProductos;
		DetalleProductoTableModel x = (DetalleProductoTableModel)this.panelProductos.getDetalleProductoJTable().getModel();
		x.addTableModelListener( this);
		panelProductos.getDetalleProductoJTable().addMouseListener(this);
		panelProductos.getDetalleProductoJTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//System.err.println("->>table columns="+panelProductos.getDetalleProductoJTable().getColumnCount());
		
		final ImporteCellRender importeCellRender = new ImporteCellRender();
		final CantidadCellRender cantidadCellRender = new CantidadCellRender();
		
		importeCellRender.setHorizontalAlignment(SwingConstants.RIGHT);
		cantidadCellRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panelProductos.getDetalleProductoJTable().getColumnModel().getColumn(4).setCellRenderer(importeCellRender);
		panelProductos.getDetalleProductoJTable().getColumnModel().getColumn(5).setCellRenderer(importeCellRender);
		panelProductos.getDetalleProductoJTable().getColumnModel().getColumn(6).setCellRenderer(cantidadCellRender);
		panelProductos.getDetalleProductoJTable().getColumnModel().getColumn(7).setCellRenderer(cantidadCellRender);
			
		panelProductos.getDetalleProductoJTable().addComponentListener(new JTableColumnAutoResizeHelper(
				new int[]{12,38,14,14,8,8,8,8}));
		
		detalleProductoList = (x).getDetalleProductoList();
		
		this.panelProductos.getNuevo().addActionListener(this);
		
		this.panelProductos.getEditar().addActionListener(this);
		
		this.panelProductos.getEliminar().addActionListener(this);

		this.panelProductos.getCodigoBuscar().addActionListener(this);

		df = new DecimalFormat("$ ###,###,###,##0.00");
	}
	
	public void estadoInicial(){
		if(productoDAO == null) {
			productoDAO = ProductoDAO.getInstance();
		} else if(detalleProductoList.size()>0){
			detalleProductoList.clear();
		}
		
		List<ProductoQuickView> productosList = null;
		try {
			productosList = productoDAO.findAll();
		}catch(DAOException e){
			
		}
		System.err.println("==>>estadoInicial:productosList = "+productosList.size());
		detalleProductoList.addAll(productosList);		
		panelProductos.getDetalleProductoJTable().updateUI();		
		panelProductos.getDetalleProductoJTable().getSelectionModel().clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == panelProductos.getNuevo()){
			nuevo_actionPerformed();
		} else if(e.getSource() == panelProductos.getEditar()){
			editar_actionPerformed();
		}
		else if(e.getSource() == this.panelProductos.getCodigoBuscar()){
			codigoBuscar_actionPerformed();
		}
		
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		panelProductos.getDetalleProductoJTable().updateUI();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==panelProductos.getDetalleProductoJTable()){
			if(e.getClickCount()==2 ){
				editar_actionPerformed();
			}
		}
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

	void ventaeliminarProdMenu() {
		int selectedRow = panelProductos.getDetalleProductoJTable().getSelectedRow();
		if(selectedRow>=0){
			detalleProductoList.remove(selectedRow);
			panelProductos.getDetalleProductoJTable().updateUI();
			
			panelProductos.getDetalleProductoJTable().getSelectionModel().clearSelection();
		}
	}

	private void nuevo_actionPerformed() {		
	}
	
	private void nuevo_actionPerformed_precargado() {
		
	}

	private void editar_actionPerformed() {
		int selectedRow = panelProductos.getDetalleProductoJTable().getSelectedRow();
		if(selectedRow>=0){
				
		}
	}
	
	private void codigoBuscar_actionPerformed() {
	}
	
}
