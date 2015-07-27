/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.model;

import com.pmarlen.backend.model.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author alfredo
 */
public class DetalleProductoTableModel implements TableModel{
	
	private static String[] columnNames = new String [] {
			"Codigo","Industria", "Linea", "Marca", "Nombre", "Presentacion", "Costo","Piezas X Caja", "Contenido","UnidiadMedida","Existencia","Precio Venta"
	};
	
	private static Class[] columnClasses = new Class [] {
			String.class,String.class,String.class,String.class,String.class,String.class,Double.class,Integer.class,Integer.class,String.class,Integer.class,Double.class
	};
	
	private List<Producto> detalleProductoList;
	private List<TableModelListener> tableModelListenerList;
	
	public DetalleProductoTableModel(){
		tableModelListenerList	= new ArrayList<TableModelListener> ();
		detalleProductoList		= new ArrayList<Producto> (); 
	}

	@Override
	public int getRowCount() {
		return detalleProductoList.size();
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
	//		"Codigo","Industria", "Linea", "Marca", "Nombre", "Presentacion", "Costo","Piezas X Caja", "Contenido","UnidiadMedida","Existencia","Precio Venta"
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		final Producto dvti = detalleProductoList.get(rowIndex);
		if(columnIndex == 0)
			return dvti.getCodigoBarras();
		else if(columnIndex == 1)
			return dvti.getIndustria();
		else if(columnIndex == 2)
			return dvti.getLinea();
		else if(columnIndex == 3)
			return dvti.getMarca();
		else if(columnIndex == 4)
			return dvti.getNombre();
		else if(columnIndex == 5)
			return dvti.getPresentacion();
		else if(columnIndex == 6)
			return dvti.getCosto();
		else if(columnIndex == 7)
			return dvti.getUnidadesXCaja();
		else if(columnIndex == 8)
			return dvti.getContenido();
		else if(columnIndex == 9)
			return dvti.getUnidadMedida();
		else if(columnIndex == 10)
			return 0;
		else if(columnIndex == 11)
			return 0.0;
		else 
			throw new IndexOutOfBoundsException("for column:"+columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		/*
		final  dvti = detalleVentaTableItemList.get(rowIndex);
		
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
	public List<Producto> getDetalleProductoList() {
		return detalleProductoList;
	}

	private void callListeners() {
		for(TableModelListener tml: tableModelListenerList){
			tml.tableChanged(new TableModelEvent(this));
		}
	}
}
