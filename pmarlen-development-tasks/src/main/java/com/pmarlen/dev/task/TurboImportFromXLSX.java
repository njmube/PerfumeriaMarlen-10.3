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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TurboImportFromXLSX {

	private Hashtable<String, Integer> excelColumnNames;
	private LinkedHashMap<String, ProveedorEXCELValue> proveedorHT;
	private LinkedHashMap<String, ClienteEXCELValue> clienteHT;
	private LinkedHashMap<String, IndustriaEXCELValue> industriaHT;
	private LinkedHashMap<String, LineaEXCELValue> lineaHT;
	private LinkedHashMap<String, MarcaEXCELValue> marcaHT;
	private LinkedHashMap<Integer, ProductoEXCELValue> productoHT;
	private DecimalFormat dfClave;
	private DecimalFormat dfImporte;
	private DecimalFormat dfCodigoBarras;

	public TurboImportFromXLSX() {
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



		proveedorHT = new LinkedHashMap<String, ProveedorEXCELValue>();
		clienteHT = new LinkedHashMap<String, ClienteEXCELValue>();
		industriaHT = new LinkedHashMap<String, IndustriaEXCELValue>();
		lineaHT = new LinkedHashMap<String, LineaEXCELValue>();
		marcaHT = new LinkedHashMap<String, MarcaEXCELValue>();
		productoHT = new LinkedHashMap<Integer, ProductoEXCELValue>();

		dfClave = new DecimalFormat("0000");
		dfCodigoBarras = new DecimalFormat("#########0000");
		dfImporte = new DecimalFormat("#########0.000000");

	}

	public static void main(String[] args) {

		if (args.length != 5) {
			System.err.println("Usage: file.xlsx classNameDriver JDBC_URL user password");
			System.exit(1);
		}

		String fileName = args[0];
		String driver = args[1];
		String url = args[2];
		String usr = args[3];
		String pwd = args[4];


		TurboImportFromXLSX ipreX = new TurboImportFromXLSX();
		try {
			ipreX.importFromDataXLSX(fileName, driver, url, usr, pwd);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace(System.err);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace(System.err);
		}

	}

	private void importFromDataXLSX(String fileName, String driver, String url, String user, String psswd)
			throws UnsupportedEncodingException,
			FileNotFoundException {

		//XSSFWorkbook embeddedWorkbook = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(fileName);
			Iterator<XSSFSheet> iteratorXSSFSheet = workbook.iterator();
			while (iteratorXSSFSheet.hasNext()) {
				XSSFSheet xssfSheet = iteratorXSSFSheet.next();
				System.err.println("==>>> SheetName: \\_" + xssfSheet.getSheetName() + "_/  rows: [" + xssfSheet.getFirstRowNum() + ", " + xssfSheet.getLastRowNum() + "]");
				if (xssfSheet.getSheetName().equalsIgnoreCase("PRODUCTOS-INVENTARIOS")) {
					for (int rowNum = xssfSheet.getFirstRowNum() + 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
						Row row = xssfSheet.getRow(rowNum);
						//System.err.println("\t==>>> row:"+rowNum);
					
						try {
							if (row == null) {
								continue;
							} else if(row.getLastCellNum()==28 && row.getFirstCellNum() == 0){
								//System.err.println("\t==>>> process row:"+rowNum);
								processRowProductos(row);
							}
						} catch (Exception ex) {
							System.err.println("-->> " + ex + ": rowNum=" + rowNum);
							ex.printStackTrace(System.err);

							break;
						}
					}
					System.err.println("END process sheet.");
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
			for (ProductoEXCELValue productoEV : productoHT.values()) {

				Object provedorId = searchFor(connQuery, "SELECT ID FROM PROVEEDOR WHERE RAZON_SOCIAL LIKE ?", productoEV.pev.razonSocial.toUpperCase());
				if (provedorId != null) {
					productoEV.pev.id = new Integer(provedorId.toString());
				} else {
					Object proveedorNextId = searchFor(connQuery, "SELECT MAX(ID)+1 FROM PROVEEDOR");
					if (proveedorNextId == null) {
						proveedorNextId = 1;
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO PROVEEDOR"
							+ "(ID,RAZON_SOCIAL,RFC,TELEFONOS,"
							+ "CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,"
							+ "URL,FECHA_CREACION) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
							new Object[]{
						proveedorNextId,
						productoEV.pev.razonSocial.toUpperCase(), productoEV.pev.rfc, productoEV.pev.telefonos,
						productoEV.pev.calle.toUpperCase(), productoEV.pev.numExterior.toUpperCase(), productoEV.pev.numInterior.toUpperCase(), productoEV.pev.poblacionId, productoEV.pev.email.toLowerCase(),
						productoEV.pev.url, new Date()
					});
					productoEV.pev.id = new Integer(resultInsert.toString());
				}


				Object lineaId = searchFor(connQuery, "SELECT ID FROM LINEA WHERE NOMBRE LIKE ?", productoEV.marca.linea.nombre);
				if (lineaId != null) {
					productoEV.marca.linea.id = new Integer(lineaId.toString());
				} else {
					Object lineaNextId = searchFor(connQuery, "SELECT MAX(ID)+1 FROM LINEA");
					if (lineaNextId == null) {
						lineaNextId = 1;
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO LINEA(ID,NOMBRE) VALUES(?,?)",
							new Object[]{lineaNextId, productoEV.marca.linea.nombre.toUpperCase()});
					productoEV.marca.linea.id = new Integer(resultInsert.toString());
				}

				Object industriaId = searchFor(connQuery, "SELECT ID FROM INDUSTRIA WHERE NOMBRE LIKE ?", productoEV.marca.industria.nombre.toUpperCase());
				if (industriaId != null) {
					productoEV.marca.industria.id = new Integer(industriaId.toString());
				} else {
					Object industriaNextId = searchFor(connQuery, "SELECT MAX(ID)+1 FROM INDUSTRIA");
					if (industriaNextId == null) {
						industriaNextId = 1;
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES(?,?)",
							new Object[]{industriaNextId, productoEV.marca.industria.nombre.toUpperCase()});
					productoEV.marca.industria.id = new Integer(resultInsert.toString());
				}

				Object marcaId = searchFor(connQuery, "SELECT ID FROM MARCA WHERE NOMBRE LIKE ?", productoEV.marca.nombre.toUpperCase());
				if (marcaId != null) {
					productoEV.marca.id = new Integer(marcaId.toString());
				} else {
					Object marcaNextId = searchFor(connQuery, "SELECT MAX(ID)+1 FROM MARCA");
					if (marcaNextId == null) {
						marcaNextId = 1;
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES(?,?,?,?)",
							new Object[]{marcaNextId, productoEV.marca.linea.id, productoEV.marca.industria.id, productoEV.marca.nombre.toUpperCase()});
					productoEV.marca.id = new Integer(resultInsert.toString());
				}

				Object productoId = searchFor(connQuery, "SELECT ID FROM PRODUCTO WHERE CODIGO_BARRAS = ?", productoEV.codigoBarras.toUpperCase());

				if (productoId != null) {
					//System.out.println("=======>> UPDATE CODIGO_BARRAS=" + productoId + " ? >>");
					resultUpdate = executeFor(connQuery,
							"UPDATE PRODUCTO SET "
							+ "MARCA_ID=?,NOMBRE=?,PRESENTACION=?,CONTENIDO=?,UNIDADES_POR_CAJA=?,"
							+ "COSTO=?,COSTO_VENTA=?,UNIDAD_MEDIDA=? WHERE CODIGO_BARRAS=?",
							new Object[]{productoEV.marca.id, productoEV.descripcion.toUpperCase(), productoEV.presentacion.toUpperCase(), productoEV.cont,
						new BigDecimal(productoEV.unidadesXCaja).intValue(), productoEV.costo, productoEV.costoVenta,
						productoEV.unidadMedida, productoEV.codigoBarras});

					//System.out.println("\t=======>> PRODUCTO.ID=" + resultUpdate + " UPDATED, BUT OK?");

					productoEV.id = ((Integer) productoId).intValue();
					resultUpdate = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(1,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacen, productoEV.costo, productoEV.precioVenta});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(2,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacenOp, productoEV.costo, productoEV.precioVenta});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(3,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacenReg, productoEV.costo, productoEV.precioVenta});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}


					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=1 ",
							new Object[]{productoEV.cantidadAlmacen, productoEV.precioVenta, productoEV.precioVenta, productoEV.costoVenta, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=2 ",
							new Object[]{productoEV.cantidadAlmacenOp, productoEV.precioVentaOp, productoEV.precioVentaOp, productoEV.costoVentaOp, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=3 ",
							new Object[]{productoEV.cantidadAlmacenReg, productoEV.precioVentaReg, productoEV.precioVentaReg, 0.0, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}

					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=4 ",
							new Object[]{productoEV.sucCanAlmacen, productoEV.precioVentaSuc, productoEV.precioVentaSuc, productoEV.costoVentaSc, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=5 ",
							new Object[]{productoEV.sucCanAlmacenOp, productoEV.precioVentaSucOp, productoEV.precioVentaSucOp, productoEV.costoVentaSc, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
					resultUpdate = executeFor(connQuery,
							"UPDATE ALMACEN_PRODUCTO SET CANTIDAD_ACTUAL=?,PRECIO_VENTA=?,PRECIO_MAYOREO=?,COSTO=? WHERE PRODUCTO_ID=? AND ALMACEN_ID=6 ",
							new Object[]{productoEV.sucCanAlmacenReg, productoEV.precioVentaReg, productoEV.precioVentaReg, 0.0, productoEV.id});
					if (resultUpdate == null) {
						throw new IllegalStateException("Error update.");
					}
				} else {

					Object newProductoId = searchFor(connQuery, "SELECT MAX(ID)+1 AS NEXT_PRODUCTO_ID FROM PRODUCTO", null);

					productoId = newProductoId;

					//System.out.println("=======>> INSERT Producto.id=" + productoEV.id + ", productoId CB=" + productoId + " ? >>");
					resultInsert = executeFor(connQuery,
							"INSERT INTO PRODUCTO("
							+ "ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,"
							+ "COSTO,COSTO_VENTA,UNIDAD_MEDIDA,CODIGO_BARRAS) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?)",
							new Object[]{productoEV.id, productoEV.marca.id, productoEV.descripcion.toUpperCase(), productoEV.presentacion.toUpperCase(), productoEV.cont,
						new BigDecimal(productoEV.unidadesXCaja).intValue(), productoEV.costo, productoEV.costoVenta,
						productoEV.unidadMedida.toUpperCase(), productoEV.codigoBarras});

					//System.out.println("\t=======>> PRODUCTO.ID=" + resultInsert + " INSERTED, BUT OK?");


					productoEV.id = new Integer(resultInsert.toString());

					resultInsert = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(1,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacen, productoEV.costo, productoEV.precioVenta});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(2,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacenOp, productoEV.costo, productoEV.precioVentaOp});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) "
							+ "VALUES(3,?,?,20,?,?,?,'root')",
							new Object[]{productoEV.id, new Date(), productoEV.cantidadAlmacenReg, productoEV.costo, productoEV.precioVentaReg});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}


					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,1)",
							new Object[]{productoEV.cantidadAlmacen, productoEV.precioVenta, productoEV.precioVenta, productoEV.costoVenta, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,2)",
							new Object[]{productoEV.cantidadAlmacenOp, productoEV.precioVentaOp, productoEV.precioVentaOp, productoEV.costoVentaOp, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,3)",
							new Object[]{productoEV.cantidadAlmacenReg, productoEV.precioVentaReg, productoEV.precioVentaReg, 0.0, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}

					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,4)",
							new Object[]{productoEV.sucCanAlmacen, productoEV.precioVentaSuc, productoEV.precioVentaSuc, productoEV.costoVentaSc, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,5)",
							new Object[]{productoEV.sucCanAlmacenOp, productoEV.precioVentaSucOp, productoEV.precioVentaSucOp, productoEV.costoVentaOp, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
					}
					resultInsert = executeFor(connQuery,
							"INSERT INTO ALMACEN_PRODUCTO(CANTIDAD_ACTUAL, PRECIO_VENTA,PRECIO_MAYOREO,COSTO,PRODUCTO_ID,ALMACEN_ID)"
							+ "VALUES(?,?,?,?,?,6)",
							new Object[]{productoEV.sucCanAlmacenReg, productoEV.precioVentaReg, productoEV.precioVentaReg, 0.0, productoEV.id});
					if (resultInsert == null) {
						throw new IllegalStateException("Error insert.");
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

	private void processRowProductos(Row row) throws ParseException {
		int cells = row.getPhysicalNumberOfCells();
		//----------------------------------------------------------------------
		ProveedorEXCELValue pev = null;
		Cell cell = row.getCell(excelColumnNames.get("A"));
		if(cell == null){
			return;
		}
		pev = proveedorHT.get(cell.toString());
		if (pev == null) {
			//System.err.println("PROVEDOR ? "+row.getCell(0)+" ROW:"+row.getRowNum());
			pev = new ProveedorEXCELValue();

			pev.id = null;
			pev.razonSocial = row.getCell(excelColumnNames.get("A")).toString().trim().toUpperCase(); // A
			pev.cp = "55068";
			pev.poblacionId = 60816;
			pev.rfc = "KAJE010101XXX";
			pev.calle = "CALLE XX";
			pev.numInterior = "IN";
			pev.numExterior = "EX";
			pev.telefonos = "55-1234-5678,55-1234-5678";
			pev.telefonosMoviles = "55-1234-5678,55-1234-5678";
			pev.faxes = "55-1234-5678,55-1234-5678";
			pev.email = "nomail@mail.com";
			pev.plazoDePago = 30;
			pev.url = "http://pagina.com";

			proveedorHT.put(pev.razonSocial, pev);
		}
		//----------------------------------------------------------------------
		IndustriaEXCELValue eev = null;

		eev = industriaHT.get(row.getCell(excelColumnNames.get("B")).toString());
		if (eev == null) {
			eev = new IndustriaEXCELValue();

			eev.id = null;
			eev.nombre = row.getCell(excelColumnNames.get("B")).toString(); // B

			industriaHT.put(eev.nombre, eev);
		}
		//----------------------------------------------------------------------
		LineaEXCELValue lev = null;

		lev = lineaHT.get(row.getCell(excelColumnNames.get("C")).toString());

		if (lev == null) {
			lev = new LineaEXCELValue();

			lev.id = null;
			lev.nombre = row.getCell(excelColumnNames.get("C")).toString(); // C

			lineaHT.put(lev.nombre, lev);
		}
		//----------------------------------------------------------------------
		MarcaEXCELValue mev = null;

		mev = marcaHT.get(row.getCell(excelColumnNames.get("D")).toString());

		if (mev == null) {
			mev = new MarcaEXCELValue();
			mev.id = null;

			mev.nombre = row.getCell(excelColumnNames.get("D")).toString(); // D
			mev.linea = lineaHT.get(lev.nombre);
			mev.industria = industriaHT.get(eev.nombre);

			marcaHT.put(mev.nombre, mev);
		}

		//----------------------------------------------------------------------

		ProductoEXCELValue productoEV = new ProductoEXCELValue();

		//prodev.proveedorId = pev.id;
		productoEV.pev = pev;
		productoEV.id = new BigDecimal(row.getCell(excelColumnNames.get("E")).toString()).intValue(); // E

		productoEV.codigoBarras = row.getCell(excelColumnNames.get("F")).toString().replace("#", ""); // F
		productoEV.abrev = row.getCell(excelColumnNames.get("G")).toString().replace("'", "''");// G

		productoEV.descripcion = row.getCell(excelColumnNames.get("H")).toString().replace("'", "''");// H
		productoEV.presentacion = row.getCell(excelColumnNames.get("I")).toString(); // I
		try {
			productoEV.cont = new BigDecimal(row.getCell(excelColumnNames.get("J")).toString()).doubleValue();//J
			productoEV.unidadMedida = row.getCell(excelColumnNames.get("K")).toString();//K
		} catch (NumberFormatException nfe) {
			productoEV.cont = 1.0;
			productoEV.unidadMedida = row.getCell(excelColumnNames.get("K")).toString();//K
		}

		productoEV.unidadesXCaja = row.getCell(excelColumnNames.get("L")).toString();//L
		//======================================================================
		try {
			productoEV.costo = Double.parseDouble(row.getCell(excelColumnNames.get("M")).toString());
			productoEV.costoVenta = Double.parseDouble(row.getCell(excelColumnNames.get("N")).toString());
			productoEV.costoVentaSc = Double.parseDouble(row.getCell(excelColumnNames.get("O")).toString());
			productoEV.costoVentaOp = Double.parseDouble(row.getCell(excelColumnNames.get("P")).toString());

			productoEV.precioVenta = Double.parseDouble(row.getCell(excelColumnNames.get("Q")).toString());
			productoEV.precioVentaOp = Double.parseDouble(row.getCell(excelColumnNames.get("R")).toString());
			productoEV.precioVentaReg = Double.parseDouble(row.getCell(excelColumnNames.get("S")).toString());
			productoEV.cantidadAlmacen = new Double(row.getCell(excelColumnNames.get("T")).toString()).intValue();
			productoEV.cantidadAlmacenOp = new Double(row.getCell(excelColumnNames.get("U")).toString()).intValue();
			productoEV.cantidadAlmacenReg = new Double(row.getCell(excelColumnNames.get("V")).toString()).intValue();

			productoEV.precioVentaSuc = Double.parseDouble(row.getCell(excelColumnNames.get("W")).toString());
			productoEV.precioVentaSucOp = Double.parseDouble(row.getCell(excelColumnNames.get("X")).toString());
			productoEV.sucCanAlmacen = new Double((row.getCell(excelColumnNames.get("Z")).toString())).intValue();
			productoEV.sucCanAlmacenOp = new Double((row.getCell(excelColumnNames.get("AA")).toString())).intValue();
			productoEV.sucCanAlmacenReg = new Double((row.getCell(excelColumnNames.get("AB")).toString())).intValue();

		} catch (Exception e) {
			System.err.println("X -->> " + e.getMessage() + " ROW:" + row.getRowNum());
		}

		productoEV.marca = marcaHT.get(mev.nombre);

		productoHT.put(productoEV.id, productoEV);
	}
	private static Connection conn;

	private static Connection getConnection(String driver, String url, String user, String psswd) {
		if (conn == null) {
			/*
			 try {
			 Class.forName(driver);
			 } catch (ClassNotFoundException cnfe) {
			 cnfe.printStackTrace(System.err);
			 throw new IllegalStateException("Cant load driver class:" + driver);
			 }
			 */
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
				if (param.getClass().equals(Integer.class)) {
					ps.setInt(i, ((Integer) param).intValue());
				} else if (param.getClass().equals(String.class)) {
					ps.setString(i, param.toString());
				} else {
					ps.setObject(i, param);
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
