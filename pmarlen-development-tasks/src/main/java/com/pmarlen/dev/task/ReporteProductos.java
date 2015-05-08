/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.dev.task;

import com.pmarlen.businesslogic.reports.GeneradorImpresionPedidoVenta;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author alfredo
 */
public class ReporteProductos {
	private static Connection newDBConnection = null;
	private static boolean debug = false;	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	private static DecimalFormat df = new DecimalFormat("$ ###,###,##0.00");
	
	public static void main(String[] args) {
		  
		String url = "jdbc:mysql://localhost/PMDB103_DEVE?characterEncoding=UTF-8";//;args[0];
		String usr = "PMDB103_DEVE_USR";//args[1];
		String pwd = "PMDB103_DEVE_PWD";//args[2];
		
		connectToNewDB(url, usr, pwd);
		
		String query = "SELECT P.CODIGO_BARRAS, P.NOMBRE, P.PRESENTACION, P.CONTENIDO, P.UNIDAD_MEDIDA , AP.PRECIO"
				+ " FROM PRODUCTO P , ALMACEN_PRODUCTO AP"
				+ " WHERE P.CODIGO_BARRAS=AP.PRODUCTO_CODIGO_BARRAS AND AP.ALMACEN_ID=1 "
				+ " ORDER BY P.NOMBRE, P.PRESENTACION";
		List<Object[]> productos = null;
		try{
			productos=executeQuery(newDBConnection, query);
		}catch(SQLException se){
			se.printStackTrace(System.err);
			System.exit(1);
		}
		
		Collection<Map<String,?>> col = new ArrayList<Map<String,?>>();
		Random r=new Random();
		for(Object[] arr: productos){
			String cb=arr[0].toString();
			String cb_ean13=null;
			File imgFile=new File("/Users/alfredo/pmarlen_imgs/transformed/MED_PRODUCTO_MULTIMEDIO_"+cb+"_1.jpg");
			
			if(r.nextInt(100)%5==0 && imgFile.exists()){
				
				Map<String,Object> vals = new HashMap<String,Object> ();
				
				vals.put("codigo_barras"  ,arr[0].toString());
				
				vals.put("producto_desc",arr[1]);
				vals.put("presentacion",arr[2]);
				vals.put("contenido",arr[3]+" "+arr[4]);
				vals.put("precio",df.format(arr[5]));
				if(cb.length()==13){
					cb_ean13=cb.substring(0, 12);
					vals.put("codigo_barras_ean13",cb_ean13);					
				}
				
				vals.put("img",imgFile.getAbsolutePath());					
				
				System.out.println("SELECTED: ->"+arr[0]+", "+arr[1]+", "+arr[2]+", "+arr[3]+", "+arr[4]+", ean13->"+cb_ean13+"<-");

				col.add(vals);
			}
		}
		System.out.println("JUST SELECTED:"+col.size());
		
		JasperReport jasperReport = null;
		
		Map parameters = new HashMap();
		JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);

		InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream("/reports/productos_catalogo.jrxml");
		try{
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			System.out.println("..compiled");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			System.out.println("..filled");
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream("./reportes_productos_"+sdf.format(new Date())+".pdf")));

			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setMetadataAuthor("PerfumeriaMarlen_SAA_10.3");						
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();			
			System.out.println("ok, exported.");
		}catch(Exception ex){
			ex.printStackTrace(System.err);
			System.exit(2);
		}
		
	}

	private static void connectToNewDB(String url, String usr,String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}

		try {
			newDBConnection = DriverManager.getConnection(url, usr, pwd);
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
	
}
