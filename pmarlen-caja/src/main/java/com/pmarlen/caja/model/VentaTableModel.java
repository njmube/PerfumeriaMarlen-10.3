/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.model;

import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author alfredo
 */
public class VentaTableModel implements TableModel{
	
	private static String[] columnNames = new String [] {
			"No", "Fecha", "Importe"
	};
	private static Class[] columnClasses = new Class [] {
			Integer.class,Date.class,BigDecimal.class
	};
	
	private List<EntradaSalidaQuickView> ventaList;
	private Hashtable<Integer,Double> ventaImporteList;
	
	private List<TableModelListener> tableModelListenerList;
	
	public VentaTableModel(){
		this.ventaList = new ArrayList<EntradaSalidaQuickView>();
		tableModelListenerList = new ArrayList<TableModelListener> ();
	}
	
	public VentaTableModel(List<EntradaSalidaQuickView> ventaList,Hashtable<Integer,Double> ventaImporteList){
		this.tableModelListenerList = new ArrayList<TableModelListener> ();
		this.ventaList			= ventaList;
		this.ventaImporteList	= ventaImporteList;
	}

	@Override
	public int getRowCount() {
		return ventaList.size();
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
		final EntradaSalida dvti = ventaList.get(rowIndex);
		if(columnIndex == 0)
			return dvti.getId();
		else if(columnIndex == 1)
			return dvti.getFechaCreo();
		else if(columnIndex == 2)
			//return new Double(-1.0);
			return new BigDecimal(ventaImporteList.get(dvti.getId()));
		else
			throw new IndexOutOfBoundsException("for column:"+columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		/*
		final Venta dvti = detalleVentaTableItemList.get(rowIndex);
		
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
	public List<EntradaSalidaQuickView> getVentaList() {
		return ventaList;
	}

	private void callListeners() {
		for(TableModelListener tml: tableModelListenerList){
			tml.tableChanged(new TableModelEvent(this));
		}
	}
	
}
