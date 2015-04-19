/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools,Templates
 * and open the template in the editor.
 */
package com.pmarlen.migration;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class ImportData {

	private static Connection oldDBConnection = null;

	private static Connection newDBConnection = null;

	private static boolean debug = false;

	private static String usrOldDB = null;
	private static String pwdOldDB = null;
	private static String urlOldDB = null;

	private static String usrNewDB = null;
	private static String pwdNewDB = null;
	private static String urlNewDB = null;

	public static void main(String[] args) {
		System.out.println("---------------------- ImportData -----------------------");

		usrOldDB = args[0];
		pwdOldDB = args[1];
		urlOldDB = args[2];

		usrNewDB = args[3];
		pwdNewDB = args[4];
		urlNewDB = args[5];

		connectToOldDB();

		connectToNewDB();
		
		ArrayList<String> tables=new ArrayList<String>();
		
		tables.add("CONFIGURACION_SISTEMA");
		tables.add("MOVIMIENTO_HISTORICO_PRODUCTO");
		tables.add("MOVIMIENTO_OPERATIVO_ALMACEN");
		tables.add("MOVIMIENTO_OPERATIVO_ALMACEN_DETALLE");
		tables.add("ALMACEN_PRODUCTO");
		tables.add("EVENTO_SINCRONIZACION");
		tables.add("MULTIMEDIO");		
		tables.add("ENTRADA_SALIDA_ESTADO");		
		tables.add("ENTRADA_SALIDA_DETALLE");
		tables.add("ENTRADA_SALIDA");
		tables.add("ESTADO");
		tables.add("CFD");
		tables.add("FORMA_DE_PAGO");
		tables.add("METODO_DE_PAGO");
		tables.add("PRODUCTO_MULTIMEDIO");
		tables.add("PRODUCTO");
		tables.add("ALMACEN");
		tables.add("SUCURSAL WHERE ID_PADRE <> 0");
		tables.add("SUCURSAL");
		tables.add("USUARIO_PERFIL");
		tables.add("CLIENTE");
		tables.add("USUARIO");
		
		try {
			System.out.println("---------------------- DELETE ALL -----------------------");
			for(String t: tables){
				String dt="DELETE FROM "+t;
				ArrayList<Object[]> p = new ArrayList<Object[]>();
				executeDirectUpdate(newDBConnection,dt);
			}
			
			List<Object[]> resultProducto = executeQuery(oldDBConnection, 
					"SELECT P.CODIGO_BARRAS,I.NOMBRE,L.NOMBRE,M.NOMBRE,P.NOMBRE,P.PRESENTACION,P.ABREBIATURA,P.UNIDADES_POR_CAJA,P.CONTENIDO,P.UNIDAD_MEDIDA,P.COSTO,P.COSTO_VENTA \n" +
					"FROM   PRODUCTO P,LINEA L,INDUSTRIA I,MARCA M\n" +
					"WHERE  1=1\n" +
					"AND    P.MARCA_ID     = M.ID \n" +
					"AND    M.INDUSTRIA_ID = I.ID\n" +
					"AND    M.LINEA_ID     = L.ID\n" 
					);
			for(Object[] arrXM: resultProducto){
				arrXM[0 ]=arrXM[0]!=null?((String)arrXM[0]).toUpperCase():null;
				arrXM[1 ]=arrXM[1]!=null?((String)arrXM[1]).toUpperCase():null;
				arrXM[2 ]=arrXM[2]!=null?((String)arrXM[2]).toUpperCase():null;
				arrXM[3 ]=arrXM[3]!=null?((String)arrXM[3]).toUpperCase():null;
				arrXM[4 ]=arrXM[4]!=null?((String)arrXM[4]).toUpperCase():null;
				arrXM[5 ]=arrXM[5]!=null?((String)arrXM[5]).toUpperCase():null;
				arrXM[6 ]=arrXM[6]!=null?((String)arrXM[6]).toUpperCase():null;
				arrXM[9 ]=arrXM[9]!=null?((String)arrXM[9]).toUpperCase():null;
			}
			executeMultipleUpdates("INSERT INTO PRODUCTO(CODIGO_BARRAS,INDUSTRIA,LINEA,MARCA,NOMBRE,PRESENTACION,ABREBIATURA,UNIDADES_X_CAJA,CONTENIDO,UNIDAD_MEDIDA,COSTO,COSTO_VENTA,UNIDAD_EMPAQUE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,'PZ')", resultProducto);
			resultProducto = null;

			List<Object[]> resultFDP = executeQuery(oldDBConnection, "SELECT ID,DESCRIPCION FROM FORMA_DE_PAGO");
			for(Object[] arrXM: resultFDP){
				arrXM[1]=((String)arrXM[1]).toUpperCase();
			}
			executeMultipleUpdates("INSERT INTO FORMA_DE_PAGO(ID,DESCRIPCION) VALUES(?,?)", resultFDP);
			resultFDP = null;

			List<Object[]> resultMDP = executeQuery(oldDBConnection, "SELECT ID,DESCRIPCION FROM METODO_DE_PAGO");
			for(Object[] arrXM: resultMDP){
				arrXM[1]=((String)arrXM[1]).toUpperCase();
			}
			executeMultipleUpdates("INSERT INTO METODO_DE_PAGO(ID,DESCRIPCION) VALUES(?,?)", resultMDP);
			resultMDP = null;

			List<Object[]> resultSucursal = executeQuery(oldDBConnection, "SELECT S.ID,S.ID_PADRE,S.NOMBRE,CONCAT(S.CALLE,', ',S.NUM_INTERIOR,', ',S.NUM_EXTERIOR,', ',P.NOMBRE,', ',P.MUNICIPIO_O_DELEGACION,', ',P.ENTIDAD_FEDERATIVA,', C.P. ',P.CODIGO_POSTAL) AS DIRECCION,S.TELEFONOS,S.USUARIO_SICOFI,S.PASSWORD_SICOFI,S.SERIE_SICOFI,S.COMENTARIOS,S.DESCUENTO_MAYOREO_HABILITADO FROM SUCURSAL S,POBLACION P WHERE S.POBLACION_ID=P.ID");
			for(Object[] arrXM: resultSucursal){				
				arrXM[2]=((String)arrXM[2]).toUpperCase();
				arrXM[3]=((String)arrXM[2]).toUpperCase();
				arrXM[4]=((String)arrXM[4]).toUpperCase();
			}
			executeMultipleUpdates("INSERT INTO SUCURSAL(ID,ID_PADRE,NOMBRE,DIRECCION,TELEFONOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI,COMENTARIOS,DESCUENTO_MAYOREO_HABILITADO) VALUES(?,?,?,?,?,?,?,?,?,?)", resultSucursal);
			resultSucursal = null;

			List<Object[]> resultCliente = executeQuery(oldDBConnection, "SELECT C.ID,C.RFC,C.RAZON_SOCIAL,C.NOMBRE_ESTABLECIMIENTO,C.CONTACTO,C.TELEFONOS,C.EMAIL,C.OBSERVACIONES,C.CALLE,"
					+ "C.NUM_INTERIOR,C.NUM_EXTERIOR,P.NOMBRE,P.MUNICIPIO_O_DELEGACION,P.MUNICIPIO_O_DELEGACION,P.ENTIDAD_FEDERATIVA,P.CODIGO_POSTAL,C.DIRECCION_FACTURACION FROM CLIENTE C,POBLACION P WHERE C.POBLACION_ID=P.ID");
			for(Object[] arrXC: resultCliente){
				arrXC[2]=((String)arrXC[2]).toUpperCase();
				arrXC[3]=arrXC[3]!=null?((String)arrXC[3]).toUpperCase():null;
				arrXC[4]=arrXC[4]!=null?((String)arrXC[4]).toUpperCase():null;
				arrXC[5]=arrXC[5]!=null?((String)arrXC[5]).toUpperCase():null;				
				arrXC[6]=arrXC[6]!=null?((String)arrXC[6]).toLowerCase():null;
				arrXC[7]=arrXC[7]!=null?((String)arrXC[7]).toLowerCase():null;
				arrXC[8]=arrXC[8]!=null?((String)arrXC[8]).toUpperCase():null;				
				
				arrXC[9] =arrXC[9] !=null?((String)arrXC[9]).toUpperCase():"S/N";								
				arrXC[10]=arrXC[10]!=null?((String)arrXC[10]).toUpperCase():"S/N";
				
				arrXC[12]=arrXC[12]!=null?((String)arrXC[12]).toUpperCase():null;
				arrXC[13]=arrXC[13]!=null?((String)arrXC[13]).toUpperCase():null;
				arrXC[14]=arrXC[14]!=null?((String)arrXC[14]).toUpperCase():null;
				arrXC[15]=arrXC[15]!=null?((String)arrXC[15]).toUpperCase():null;
				String direccionFacturacion=(String)arrXC[16];
				if(direccionFacturacion == null){
					direccionFacturacion =	arrXC[8] + 
											(arrXC[10]!=null?(",NUM EXT."+arrXC[10]):(","))+
											(arrXC[ 9]!=null?(",NUM INT."+arrXC[9]):(","))+
											", "+arrXC[11]+
											", "+arrXC[12]+
											((! arrXC[12].equals(arrXC[13]))?(", CIUDAD "+arrXC[13]):(""))+
											", "+arrXC[14]+
											", C.P. "+arrXC[15];
					direccionFacturacion = direccionFacturacion.toUpperCase();
				}				
				arrXC[16]=direccionFacturacion;
			}
			//											0	1	2			3						4		5			6	7				8	9				10			11		12		13		14	  15	16
			executeMultipleUpdates("INSERT INTO CLIENTE(ID,RFC,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CONTACTO,TELEFONOS,EMAIL,OBSERVACIONES,CALLE,NUM_INTERIOR,NUM_EXTERIOR,COLONIA,MUNICIPIO,CIUDAD,ESTADO,CP,DIRECCION_FACTURACION) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", resultCliente);
			resultCliente = null;

			List<Object[]> resultUsuario = executeQuery(oldDBConnection, "SELECT USUARIO_ID,HABILITADO,NOMBRE_COMPLETO,PASSWORD FROM USUARIO");
			for(Object[] arrXC: resultUsuario){
				arrXC[0]=arrXC[0]+"@perfumeriamarlen.com.mx";
			}
			executeMultipleUpdates("INSERT INTO USUARIO(EMAIL,ABILITADO,NOMBRE_COMPLETO,PASSWORD) VALUES(?,?,?,?)", resultUsuario);
			resultUsuario = null;

			List<Object[]> resultUsuarioPerfil = executeQuery(oldDBConnection, "SELECT USUARIO_ID,PERFIL_ID FROM USUARIO_PERFIL");
			for(Object[] arrXC: resultUsuarioPerfil){
				arrXC[0]=arrXC[0]+"@perfumeriamarlen.com.mx";
			}
			executeMultipleUpdates("INSERT INTO USUARIO_PERFIL(EMAIL,PERFIL) VALUES(?,?)", resultUsuarioPerfil);
			resultUsuarioPerfil = null;

			List<Object[]> resultAlmacen = executeQuery(oldDBConnection, "SELECT ID,SUCURSAL_ID,TIPO_ALMACEN FROM ALMACEN");
			executeMultipleUpdates("INSERT INTO ALMACEN(ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(?,?,?)", resultAlmacen);
			resultAlmacen = null;

			List<Object[]> resultAlmacenProducto = executeQuery(oldDBConnection, "SELECT AP.ALMACEN_ID,P.CODIGO_BARRAS,AP.CANTIDAD_ACTUAL,AP.PRECIO_VENTA FROM ALMACEN_PRODUCTO AP,PRODUCTO P WHERE AP.PRODUCTO_ID=P.ID");
			executeMultipleUpdates("INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,CANTIDAD,PRECIO) VALUES(?,?,?,?)", resultAlmacenProducto);
			resultAlmacenProducto = null;

			List<Object[]> resultCFD = executeQuery(oldDBConnection, "SELECT ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,'NUM_CFD' AS NUM_CFD,'TIPO' AS TIPO FROM CFD_VENTA");
			
			for(Object[] arrCDF:resultCFD){
				
				if(arrCDF[2] != null){
					String stringXML=arrCDF[2].toString();
					
					String folio=extractXMLAtribute("folio", stringXML);
					String serie=extractXMLAtribute("serie", stringXML);
					String tipoDeComprobante=extractXMLAtribute("tipoDeComprobante", stringXML);
					
					if (debug) {
						System.out.println("==>>EXTRACTED: folio="+folio+", serie="+serie+",tipoDeComprobante="+tipoDeComprobante);
					}
					if(folio!=null && serie !=null && tipoDeComprobante!=null){
						arrCDF[4] = serie+folio;
						arrCDF[5] = tipoDeComprobante;
					}
				} else {
					arrCDF[4] = null;
					arrCDF[5] = null;
				}				
			}
			
			executeMultipleUpdates("INSERT INTO CFD (ID,ULTIMA_ACTUALIZACION,CONTENIDO_ORIGINAL_XML,CALLING_ERROR_RESULT,NUM_CFD,TIPO) VALUES(?,?,?,?,?,?)", resultCFD);
			resultCFD = null;
			
			List<Object[]> resultEstado = executeQuery(oldDBConnection, "SELECT ID,DESCRIPCION FROM ESTADO");
			executeMultipleUpdates("INSERT INTO ESTADO(ID,DESCRIPCION) VALUES(?,?)", resultEstado);
			resultUsuarioPerfil = null;

			List<Object[]> resultPedidoVenta = executeQuery(oldDBConnection, "SELECT PV.ID,30 AS TM,S.ID,MAX(PVE.ESTADO_ID) AS ESTADO,PVE.FECHA,PV.USUARIO_ID,PV.CLIENTE_ID,PV.FORMA_DE_PAGO_ID,PV.CFD_VENTA_ID,PV.METODO_DE_PAGO_ID,PV.FACTORIVA,PV.COMENTARIOS,PV.NUMERO_TICKET,PV.CAJA,PV.IMPORTE_RECIBIDO,PV.APROBACION_BANCARIA_TC,PV.PORCENTAJE_DESCUENTO_CALCULADO,PV.PORCENTAJE_DESCUENTO_EXTRA,PV.CONDICIONES_DE_PAGO,PV.NUM_DE_CUENTA,1 AS AUTORIZA_DESCUENTO "
					+ "FROM PEDIDO_VENTA_ESTADO PVE,PEDIDO_VENTA PV,ALMACEN A,SUCURSAL S "
					+ "WHERE PVE.PEDIDO_VENTA_ID=PV.ID AND PV.ALMACEN_ID=A.ID AND A.SUCURSAL_ID=S.ID GROUP BY PVE.PEDIDO_VENTA_ID");
			for(Object[] arrXC: resultPedidoVenta){
				arrXC[5]=arrXC[5]+"@perfumeriamarlen.com.mx";
			}
			executeMultipleUpdates("INSERT INTO ENTRADA_SALIDA(ID,TIPO_MOV,SUCURSAL_ID,ESTADO_ID,FECHA_CREO,USUARIO_EMAIL_CREO,CLIENTE_ID,FORMA_DE_PAGO_ID,CFD_ID,METODO_DE_PAGO_ID,FACTOR_IVA,COMENTARIOS,NUMERO_TICKET,CAJA,IMPORTE_RECIBIDO,APROBACION_VISA_MASTERCARD,PORCENTAJE_DESCUENTO_CALCULADO,PORCENTAJE_DESCUENTO_EXTRA,CONDICIONES_DE_PAGO,NUM_DE_CUENTA,AUTORIZA_DESCUENTO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", resultPedidoVenta);
			resultPedidoVenta = null;
			
			List<Object[]> resultPedidoVentaDetalle = executeQuery(oldDBConnection, "SELECT PVD.PEDIDO_VENTA_ID,P.CODIGO_BARRAS,A.ID,PVD.CANTIDAD,PVD.PRECIO_VENTA "
					+ "FROM PEDIDO_VENTA_DETALLE PVD,PEDIDO_VENTA PV,ALMACEN A,PRODUCTO P "
					+ "WHERE PVD.PEDIDO_VENTA_ID=PV.ID AND PV.ALMACEN_ID=A.ID AND PVD.PRODUCTO_ID=P.ID");
			executeMultipleUpdates("INSERT INTO ENTRADA_SALIDA_DETALLE(ENTRADA_SALIDA_ID,PRODUCTO_CODIGO_BARRAS,ALMACEN_ID,CANTIDAD,PRECIO_VENTA) VALUES(?,?,?,?,?)", resultPedidoVentaDetalle);
			resultPedidoVentaDetalle = null;
			
/*
SELECT PVE.PEDIDO_VENTA_ID,E.ID,E.DESCRIPCION,PVE.FECHA,PVE.USUARIO_MODIFICO 
FROM   PEDIDO_VENTA_ESTADO PVE,ESTADO E WHERE PVE.ESTADO_ID=E.ID AND PEDIDO_VENTA_ID IN(335,228,1808,1806) ORDER BY PVE.PEDIDO_VENTA_ID,PVE.FECHA
			

SELECT PVE.PEDIDO_VENTA_ID,COUNT(1) AS FREC_ESTADOS,PVE.ESTADO_ID,E.DESCRIPCION,PVE.FECHA,PVE.USUARIO_MODIFICO 
FROM   PEDIDO_VENTA_ESTADO PVE,ESTADO E 
WHERE  1=1 
AND    PVE.ESTADO_ID=E.ID 
AND PEDIDO_VENTA_ID IN(335,228,1808,1806) 
GROUP BY PVE.PEDIDO_VENTA_ID,PVE.ESTADO_ID 
ORDER BY PVE.PEDIDO_VENTA_ID,PVE.FECHA ASC;
			

			
			
SELECT R1.PEDIDO_VENTA_ID,COUNT(1) AS FREC_ESTADOS,R1.ID,R1.DESCRIPCION,R1.FECHA,R1.USUARIO_MODIFICO
FROM (
SELECT PVE.PEDIDO_VENTA_ID,E.ID,E.DESCRIPCION,PVE.FECHA,PVE.USUARIO_MODIFICO
FROM PEDIDO_VENTA_ESTADO PVE,ESTADO E WHERE PVE.ESTADO_ID=E.ID AND PEDIDO_VENTA_ID IN(335,228,1808,1806) ORDER BY PVE.PEDIDO_VENTA_ID,PVE.FECHA ASC) AS R1
GROUP BY R1.PEDIDO_VENTA_ID,R1.ID;
			

SELECT R1.PEDIDO_VENTA_ID,R1.ID,R1.FECHA,R1.USUARIO_MODIFICO,COUNT(1) AS FREC_ESTADOS
FROM (
SELECT PVE.PEDIDO_VENTA_ID,E.ID,E.DESCRIPCION,PVE.FECHA,PVE.USUARIO_MODIFICO
FROM PEDIDO_VENTA_ESTADO PVE,ESTADO E WHERE PVE.ESTADO_ID=E.ID ORDER BY PVE.PEDIDO_VENTA_ID,PVE.FECHA ASC) AS R1
GROUP BY R1.PEDIDO_VENTA_ID,R1.ID

*/
			List<Object[]> resultPedidoVentaEstado = executeQuery(oldDBConnection, 
					"SELECT R2.PEDIDO_VENTA_ID,R2.ID,R2.FECHA,R2.USUARIO_MODIFICO\n" +
					"FROM (\n" +
					"SELECT R1.PEDIDO_VENTA_ID,R1.ID,R1.FECHA,R1.USUARIO_MODIFICO,COUNT(1) AS FREC_ESTADOS\n" +
					"FROM (\n" +
					"SELECT PVE.PEDIDO_VENTA_ID,E.ID,E.DESCRIPCION,PVE.FECHA,PVE.USUARIO_MODIFICO\n" +
					"FROM PEDIDO_VENTA_ESTADO PVE,ESTADO E WHERE PVE.ESTADO_ID=E.ID ORDER BY PVE.PEDIDO_VENTA_ID,PVE.FECHA ASC) AS R1\n" +
					"GROUP BY R1.PEDIDO_VENTA_ID,R1.ID ) AS R2");
			for(Object[] arrXC: resultPedidoVentaEstado){
				arrXC[3]=arrXC[3]+"@perfumeriamarlen.com.mx";
			}
			executeMultipleUpdates("INSERT INTO ENTRADA_SALIDA_ESTADO(ENTRADA_SALIDA_ID,ESTADO_ID,FECHA,USUARIO_EMAIL) VALUES(?,?,?,?)", resultPedidoVentaEstado);
			resultPedidoVentaEstado = null;

			List<Object[]> resultMHP = executeQuery(oldDBConnection, 
					"SELECT A.ID,MHP.FECHA,MHP.TIPO_MOVIMIENTO_ID,MHP.CANTIDAD,MHP.COSTO,MHP.PRECIO,MHP.USUARIO_ID,P.CODIGO_BARRAS " +
					"FROM MOVIMIENTO_HISTORICO_PRODUCTO MHP, PRODUCTO P,ALMACEN A "+
					"WHERE 1=1 " +
					"AND   MHP.ALMACEN_ID=A.ID " +
					"AND   MHP.PRODUCTO_ID=P.ID");
			for(Object[] arrXC: resultMHP){
				arrXC[6]=arrXC[6]+"@perfumeriamarlen.com.mx";
			}
			executeMultipleUpdates("INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO(ALMACEN_ID,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL,PRODUCTO_CODIGO_BARRAS) VALUES(?,?,?,?,?,?,?,?)", resultMHP);
			resultMHP = null;			
			
			
			String queryAjustes = 
			"SELECT   A.ID,P.CODIGO_BARRAS,NOW(),0,(AP.CANTIDAD_ACTUAL-R1.SUM_CANT_AFECTADA) AS CANT_AJUSTE,AP.COSTO,AP.PRECIO_VENTA,'root@perfumeriamarlen.com.mx'\n" +
			"FROM     ALMACEN_PRODUCTO AP, ALMACEN A,PRODUCTO P,(\n" +
			"    SELECT   MHP.ALMACEN_ID ,P.CODIGO_BARRAS,SUM(MHP.CANTIDAD*TM.FACTOR_AFECTA_CANTIDAD_INV) AS SUM_CANT_AFECTADA\n" +
			"    FROM     MOVIMIENTO_HISTORICO_PRODUCTO MHP, PRODUCTO P , TIPO_MOVIMIENTO TM\n" +
			"    WHERE    1=1\n" +
			"    AND      MHP.PRODUCTO_ID = P.ID\n" +
			"    AND      MHP.TIPO_MOVIMIENTO_ID = TM.ID\n" +
			"    AND      MHP.TIPO_MOVIMIENTO_ID BETWEEN 20 AND 40\n" +
			"    GROUP BY MHP.ALMACEN_ID ,MHP.PRODUCTO_ID\n" +
			") R1\n" +
			"WHERE    1=1\n" +
			"AND      AP.PRODUCTO_ID = P.ID\n" +
			"AND      AP.ALMACEN_ID = R1.ALMACEN_ID\n" +
			"AND      AP.ALMACEN_ID = A.ID\n" +		
			"AND      P.CODIGO_BARRAS = R1.CODIGO_BARRAS\n" +
			"AND      (AP.CANTIDAD_ACTUAL-R1.SUM_CANT_AFECTADA) <> 0\n" +
			"ORDER BY AP.ALMACEN_ID,CANT_AJUSTE,AP.PRODUCTO_ID\n" +
			"";
			
			List<Object[]> resultMHPAj = executeQuery(oldDBConnection, queryAjustes);
			int cantAfect = 0;
			for(Object[] arrX: resultMHPAj){
				cantAfect=((BigDecimal)arrX[4]).intValue();
				if(cantAfect>0){
					arrX[3]=21;
				} else {
					arrX[3]=31;
					arrX[4]=new BigDecimal(cantAfect*-1);
				}				
			}
			executeMultipleUpdates("INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO(ALMACEN_ID,PRODUCTO_CODIGO_BARRAS,FECHA,TIPO_MOVIMIENTO,CANTIDAD,COSTO,PRECIO,USUARIO_EMAIL) VALUES(?,?,?,?,?,?,?,?)", resultMHPAj);
			
			System.out.println("=================================== END IMPORT OK =================================");	
			System.exit(0);	
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(3);
		}
	}

	private static void executeMultipleUpdates(String sql, List<Object[]> resultLineas) throws SQLException {
		executeMultipleUpdates(newDBConnection, sql, resultLineas);
	}
	
	private static void executeMultipleUpdates(Connection conn,String sql, List<Object[]> resultLineas) throws SQLException {
		System.out.println("-> executeMultipleUpdates: SQL:"+sql);
		final PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		int cm = 0;
        int tm = resultLineas.size();
		for (Object[] row : resultLineas) {
			if (debug) {
				System.out.println("SQL:" + sql + " <= " + Arrays.asList(row));
			}
			executeUpdate(ps, row);
			cm++;
			System.out.print("\rAv:\t"+((cm*100)/tm)+"%");
		}
		System.out.println("\t Total Affected: " + cm);
		
		ps.close();
	}

	private static void connectToOldDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}

		try {
			oldDBConnection = DriverManager.getConnection(urlOldDB, usrOldDB, pwdOldDB);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(2);
		}

	}

	private static void connectToNewDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}

		try {
			newDBConnection = DriverManager.getConnection(urlNewDB, usrNewDB, pwdNewDB);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(2);
		}
	}

	private static List<Object[]> executeQuery(Connection conn, String sql) throws SQLException {		
		System.out.println("-> executeQuery: SQL:"+sql);
		List<Object[]> result = new ArrayList<Object[]>();
		final Statement st = conn.createStatement();

		final ResultSet rs = st.executeQuery(sql);
		final ResultSetMetaData rsMD = rs.getMetaData();
		if (debug) {
			for (int i = 1; i <= rsMD.getColumnCount(); i++) {
				if (i > 1) {
					System.out.print(",");
				}
				System.out.print(rsMD.getColumnName(i));
			}
			System.out.println("");
		}
		int c = 0;
		for (c = 0; rs.next(); c++) {
			Object[] row = new Object[rsMD.getColumnCount()];
			for (int i = 1; i <= rsMD.getColumnCount(); i++) {
				//row[i - 1] = rs.getObject(rsMD.getColumnName(i));
				row[i - 1] = rs.getObject(i);

				if (debug) {
					if (i > 1) {
						System.out.print(",");
					}
					System.out.print(row[i - 1]);
				}

			}
			result.add(row);
			if (debug) {
				System.out.println("");
			}
		}
		System.out.println("Count: " + c);
		
		rs.close();
		return result;
	}
	/*
	private static void executeUpdate(Connection conn, PreparedStatement st, Object[] row) throws SQLException {
		st.clearParameters();
		st.clearWarnings();

		for (int j = 1; j <= row.length; j++) {
			st.setObject(j, row[j - 1]);
		}

		int r = st.executeUpdate();
		if (debug) {
			System.out.println("Affected: " + r);
		}
	}
	*/
	
	private static Object executeUpdate(PreparedStatement st, Object[] row) throws SQLException {
		Object generatedKey=null;
		
		if(row != null) {
			st.clearParameters();
			st.clearWarnings();	
			for (int j = 1; j <= row.length; j++) {
				st.setObject(j, row[j - 1]);
			}
		}

		int r = st.executeUpdate();
		if (debug) {
			System.out.print("Affected: " + r);
		}
		
		ResultSet gksRS = st.getGeneratedKeys();
		if(gksRS != null) {
			if(gksRS.next()){
				generatedKey = gksRS.getObject(1);
			}
			if (debug) {
				System.out.print(", KEY=" + generatedKey);
			}
		}
		if (debug) {
			System.out.println(".");
		}
		
		return generatedKey;
	}
	
	private static void executeDirectUpdate(Connection conn, String q) throws SQLException {		
		System.out.println("-> executeDirectUpdate: SQL:"+q);
		int r = conn.createStatement().executeUpdate(q);
		if (debug) {
			System.out.println("Affected: " + r);
		}
	}

	private static String extractXMLValue(String label, String xml) {
		String bl = "<"+label+">";
		String el = "</"+label+">";
		int    bli = xml.indexOf(bl);
		int    bti = bli+bl.length();
		int    eli = xml.indexOf(el);
		
		if(bli >=  0 && eli > bti) {
			return xml.substring(bti, eli);
		} else {
			return null;
		}		
	}
	
	private static String extractXMLAtribute(String atribute, String xml) {
		
		int    bli = xml.indexOf(atribute);
		int    bti = xml.indexOf("\"",bli);
		int    eti = xml.indexOf("\"",bti+1);
		
		if(bli >=  0 && eti > bti) {
			return xml.substring(bti+1, eti);
		} else {
			return null;
		}
	}
}
