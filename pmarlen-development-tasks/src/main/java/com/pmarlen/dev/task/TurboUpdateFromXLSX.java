/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TurboUpdateFromXLSX {

	private Hashtable<String, Integer> excelColumnNames;
	private LinkedHashMap<String, ProductoEXCELValue> productoHT;
	private DecimalFormat dfClave;
	private DecimalFormat dfImporte;
	private DecimalFormat dfCodigoBarras;

	public TurboUpdateFromXLSX() {
		excelColumnNames = new Hashtable<String, Integer>();
		excelColumnNames.put("A", 0);
		excelColumnNames.put("B", 1);
		excelColumnNames.put("C", 2);
		excelColumnNames.put("D", 3);
		excelColumnNames.put("E", 4);
		excelColumnNames.put("F", 5);
		excelColumnNames.put("G", 6);
		excelColumnNames.put("H", 7);
		excelColumnNames.put("I", 8);
		excelColumnNames.put("J", 9);
		excelColumnNames.put("K", 10);
		excelColumnNames.put("L", 11);
		excelColumnNames.put("M", 12);
		excelColumnNames.put("N", 13);
		excelColumnNames.put("O", 14);
		excelColumnNames.put("P", 15);
		excelColumnNames.put("Q", 16);
		excelColumnNames.put("R", 17);
		excelColumnNames.put("S", 18);
		excelColumnNames.put("T", 19);
		excelColumnNames.put("U", 20);
		excelColumnNames.put("V", 21);
		excelColumnNames.put("W", 22);
		excelColumnNames.put("X", 23);
		excelColumnNames.put("Y", 24);
		excelColumnNames.put("Z", 25);
		excelColumnNames.put("AA", 26);
		excelColumnNames.put("AB", 27);
		excelColumnNames.put("AC", 28);
		excelColumnNames.put("AD", 29);
		excelColumnNames.put("AE", 30);
		excelColumnNames.put("AF", 31);
		excelColumnNames.put("AG", 32);
		excelColumnNames.put("AH", 33);
		excelColumnNames.put("AI", 34);
		excelColumnNames.put("AJ", 35);
		excelColumnNames.put("AK", 36);

		productoHT = new LinkedHashMap<String, ProductoEXCELValue>();

		dfClave = new DecimalFormat("0000");
		dfCodigoBarras = new DecimalFormat("#########0000");
		dfImporte = new DecimalFormat("#########0.000000");

	}

	public static void main(String[] args) {

		if (args.length != 6) {
			System.err.println("Usage: file.xlsx classNameDriver JDBC_URL user password [-cantidad|-precio]");
			System.exit(1);
		}

		String fileName = args[0];
		String driver = args[1];
		String url = args[2];
		String usr = args[3];
		String pwd = args[4];
		String fld = args[5];
		
		if(!fld.equals("-cantidad")&&!fld.equals("-precio")){
			System.err.println("field selection to update must be [-cantidad|-precio]");
			System.exit(2);
		}

		TurboUpdateFromXLSX ipreX = new TurboUpdateFromXLSX();
		try {
			ipreX.importFromDataXLSX(fileName, driver, url, usr, pwd,fld);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace(System.err);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace(System.err);
		}

	}

	private void importFromDataXLSX(String fileName, String driver, String url, String user, String psswd,String fld)
			throws UnsupportedEncodingException,
			FileNotFoundException {

		//XSSFWorkbook embeddedWorkbook = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(fileName);
			Iterator<XSSFSheet> iteratorXSSFSheet = workbook.iterator();
			while (iteratorXSSFSheet.hasNext()) {
				XSSFSheet xssfSheet = iteratorXSSFSheet.next();
				System.err.println("==>>> SheetName: \\_" + xssfSheet.getSheetName() + "_/  rows: [" + xssfSheet.getFirstRowNum() + ", " + xssfSheet.getLastRowNum() + "]");

				Iterator<Row> rowIterator = xssfSheet.rowIterator();

				for (int rowNum = xssfSheet.getFirstRowNum() + 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					Row row = xssfSheet.getRow(rowNum);
					try {
						if (row == null) {
							continue;
						} else if (xssfSheet.getSheetName().startsWith("PRODUCTOS")) {
							processRowProductos(row,fld);
						}
					} catch (Exception ex) {
						System.err.println("-->> " + ex + ": rowNum=" + rowNum);
						ex.printStackTrace(System.err);
						
						break;
					}

				}

			}

			Connection connQuery = getConnection(driver, url, user, psswd);

			Object resultInsert = null;
			Object resultUpdate = null;

			int t = productoHT.size();
			int r = 0;
			int p = (r * 100) / t;
			System.out.println("========>> REVIEW");
			System.out.println();
			for (ProductoEXCELValue productoEV : productoHT.values()) {
				//System.out.println("\t "+productoEV.pev+"|"+productoEV.marca.nombre+"|"+productoEV.marca.linea.nombre+"|"+productoEV.marca.industria.nombre+"|"+productoEV.id);
			}
			//System.exit(0);
			System.out.println("=======>> DB");
			System.out.println();
			int tipoMov =	fld.equals("-cantidad") ? 20 :
							fld.equals("-precio")   ? 50 :  0;
			
			for (ProductoEXCELValue productoEV : productoHT.values()) {

				Object productoId = searchFor(connQuery, "SELECT ID FROM PRODUCTO WHERE CODIGO_BARRAS = ?", productoEV.codigoBarras.toUpperCase());

				if (productoId != null) {
					
					productoEV.id = ((Integer) productoId).intValue();
					
					resultUpdate = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(1,?,?,?,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(),tipoMov ,productoEV.cantidadAlmacen, productoEV.costo, productoEV.precioVenta});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error insert MOVIMIENTO_HISTORICO_PRODUCTO.");
					}
					if(tipoMov==20){
						resultUpdate = executeFor(connQuery,
								"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=CANTIDAD_ACTUAL+? WHERE PRODUCTO_ID=? AND ALMACEN_ID=1 ",
								new Object[]{productoEV.cantidadAlmacen, productoEV.id});
						if (resultUpdate == null) {
							throw new IllegalStateException("Error update +CANTIDAD_ACTUAL in ALMACEN_PRODUCTO.");
						}
					} else if(tipoMov==50){
						resultUpdate = executeFor(connQuery,
								"UPDATE ALMACEN_PRODUCTO SET PRECIO_VENTA=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=1 ",
								new Object[]{productoEV.precioVenta, productoEV.id});
						if (resultUpdate == null) {
							throw new IllegalStateException("Error update PRECIO_VENTA in ALMACEN_PRODUCTO.");
						}
					}
					
				} 
				r++;
				p = (r * 100) / t;
				System.out.print("OK, PROCESO, ==> % terminado:\t" + p + "\r");

			}

			System.out.println("\n\n==> OK.");

			closeConnection();

		} catch (Exception ex) {
			System.out.println("--------------------------------------->> ERROR:");

			ex.printStackTrace(System.err);
		}

	}

	private void processRowProductos(Row row,String fld) throws ParseException {
		int cells = row.getPhysicalNumberOfCells();
		//----------------------------------------------------------------------
		ProveedorEXCELValue pev = null;


		//----------------------------------------------------------------------

		ProductoEXCELValue productoEV = new ProductoEXCELValue();
		productoEV.id  = 0;
		productoEV.pev = pev;
		productoEV.codigoBarras = row.getCell(excelColumnNames.get("A")).toString().replace("#", ""); // A
		//======================================================================
		try {
			if(fld.equals("-cantidad")){
				productoEV.cantidadAlmacen = new Double(row.getCell(excelColumnNames.get("B")).toString()).intValue();
			} else if(fld.equals("-precio")){
				productoEV.precioVenta = Double.parseDouble(row.getCell(excelColumnNames.get("B")).toString());
			}
			
		} catch (Exception e) {
			System.err.println("X -->> " + e.getMessage() + " ROW:" + row.getRowNum());
		}
		
		productoHT.put(productoEV.codigoBarras, productoEV);
	}
	private static Connection conn;

	private static Connection getConnection(String driver, String url, String user, String psswd) {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(url, user, psswd);


			} catch (SQLException se) {
				//se.printStackTrace(System.err);
				throw new IllegalStateException("Cant open Connection:" + se);
			}

		}
		return conn;
	}

	private static void closeConnection() {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}

	private Object searchFor(Connection connQuery, String query) throws IllegalStateException {
		return searchFor(connQuery, query, null);
	}

	private Object searchFor(Connection connQuery, String query, Object param) throws IllegalStateException {
		Object result = null;
		try {
			PreparedStatement ps = connQuery.prepareStatement(query);

			if (param != null) {
				if (param.getClass().equals(Integer.class)) {
					ps.setInt(1, ((Integer) param).intValue());
				} else if (param.getClass().equals(String.class)) {
					ps.setString(1, param.toString());
				} else {
					ps.setObject(1, param);
				}

			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getObject(1);
			}
			rs.close();

			return result;
		} catch (SQLException se) {
			se.printStackTrace(System.err);
			throw new IllegalStateException("Can't execute query:");
		}
	}

	private Object executeFor(Connection connQuery, String query, Object[] params) throws IllegalStateException {
		Object result = null;
		try {
			List<Object> listParams = Arrays.asList(params);
			//System.err.println("\t\t-->>executeFor("+query+","+listParams +"):");
			PreparedStatement ps = connQuery.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 1;

			for (Object param : listParams) {
				if(param != null){
					if (param.getClass().equals(Integer.class)) {
						ps.setInt(i, ((Integer) param).intValue());
					} else if (param.getClass().equals(String.class)) {
						ps.setString(i, param.toString());
					} else {
						ps.setObject(i, param);
					}
				} else {
					
				}

				i++;
			}

			int ra = ps.executeUpdate();

			if (ra > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					result = rs.getObject(1);
				}
				rs.close();
				if (result == null) {
					result = ra;
				}
			}

			return result;
		} catch (SQLException se) {
			se.printStackTrace(System.err);
			throw new IllegalStateException("Can't execute update:" + se);
		}
	}
}
