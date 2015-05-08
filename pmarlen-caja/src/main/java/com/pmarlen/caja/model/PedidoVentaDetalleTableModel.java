/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author alfredo
 */
public class PedidoVentaDetalleTableModel implements TableModel{
	
	private static String[] columnNames = new String [] {
		"#", "Producto", "Precio", "Importe"
	};
	private static Class[] columnClasses = new Class [] {
			Integer.class,String.class,String.class,String.class,String.class,Double.class,Double.class
	};
	
	private ArrayList<PedidoVentaDetalleTableItem> detalleVentaTableItemList;
	private ArrayList<TableModelListener> tableModelListenerList;
	
	public PedidoVentaDetalleTableModel(){
		tableModelListenerList = new ArrayList<TableModelListener> ();
		detalleVentaTableItemList = new ArrayList<PedidoVentaDetalleTableItem> (); 
	}

	@Override
	public int getRowCount() {
		return detalleVentaTableItemList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		//return columnIndex==0;
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		final PedidoVentaDetalleTableItem dvti = detalleVentaTableItemList.get(rowIndex);
		if(columnIndex == 0)
			return dvti.getCantidad();
		else if(columnIndex == 1)
			return dvti.getProducto();
		else if(columnIndex == 2)
			return dvti.getPrecioVenta();
		else if(columnIndex == 3)
			return dvti.getImporete();
		else 
			throw new IndexOutOfBoundsException("for column:"+columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		/*
		final PedidoVentaDetalleTableItem dvti = detalleVentaTableItemList.get(rowIndex);
		
		if(columnIndex == 0) {
			dvti.setCantidad((Integer)aValue);
			callListeners();
		} else {
			throw new IndexOutOfBoundsException("for column:"+columnIndex);
		}
		*/
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		tableModelListenerList.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		tableModelListenerList.remove(l);
	}

	/**
	 * @return the detalleVentaTableItemList
	 */
	public ArrayList<PedidoVentaDetalleTableItem> getDetalleVentaTableItemList() {
		return detalleVentaTableItemList;
	}

	private void callListeners() {
		for(TableModelListener tml: tableModelListenerList){
			tml.tableChanged(new TableModelEvent(this));
		}
	}
}
