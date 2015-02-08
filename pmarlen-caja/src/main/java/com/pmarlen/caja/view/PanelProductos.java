/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.view;

import com.pmarlen.backend.model.DetalleProductoTableModel;
import com.pmarlen.caja.model.PedidoVentaDetalleTableModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author alfredo
 */
public class PanelProductos extends javax.swing.JPanel {

	/**
	 * Creates new form PanelVenta
	 */
	public PanelProductos() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        codigoBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        detalleProductoJTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        agregarAVenta = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        nuevo = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("BUSCAR POR ");
        jPanel2.add(jLabel1);

        jLabel2.setIcon(getBarCodeImageIcon());
        jPanel2.add(jLabel2);

        codigoBuscar.setColumns(10);
        jPanel2.add(codigoBuscar);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        detalleProductoJTable.setModel(new DetalleProductoTableModel());
        jScrollPane1.setViewportView(detalleProductoJTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        agregarAVenta.setText("Agregar +1 a Venta Actual");
        jPanel1.add(agregarAVenta);

        jPanel3.add(jPanel1);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        nuevo.setText("Nuevo");
        jPanel4.add(nuevo);

        editar.setText("Editar");
        jPanel4.add(editar);

        eliminar.setText("Eliminar");
        jPanel4.add(eliminar);

        jPanel3.add(jPanel4);

        add(jPanel3, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarAVenta;
    private javax.swing.JTextField codigoBuscar;
    private javax.swing.JTable detalleProductoJTable;
    private javax.swing.JButton editar;
    private javax.swing.JButton eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nuevo;
    // End of variables declaration//GEN-END:variables

	
	/**
	 * @return the terminar
	 */
	public javax.swing.JButton getTerminar() {
		return nuevo;
	}	

	/**
	 * @return the detalleProductoJTable
	 */
	public javax.swing.JTable getDetalleProductoJTable() {
		return detalleProductoJTable;
	}

	/**
	 * @return the editar
	 */
	public javax.swing.JButton getEditar() {
		return editar;
	}

	/**
	 * @return the eliminar
	 */
	public javax.swing.JButton getEliminar() {
		return eliminar;
	}

	/**
	 * @return the guardar
	 */
	public javax.swing.JButton getNuevo() {
		return nuevo;
	}

	/**
	 * @return the codigoBuscar
	 */
	public javax.swing.JTextField getCodigoBuscar() {
		return codigoBuscar;
	}

	private javax.swing.ImageIcon getBarCodeImageIcon(){
		javax.swing.ImageIcon ii = null;
		try{
			ii = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/barcode_icon_52x24.png")));
		} catch(Exception ioe){
		}
		return ii;
	}

	public JButton getAgregarAVenta() {
		return agregarAVenta;
	}

}
