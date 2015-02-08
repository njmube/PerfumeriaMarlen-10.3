/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class ParseSepomexSubString {

	public static void main(String[] args) {
		String readFromFile = args[0];
		String writeToFile	= args[1];
		
		File file = new File(readFromFile);
		InputStream is = null;
		PrintStream ps = null;
		
		int fl = (int) file.length();
		String str = null;
		
		final String startTag = "<table xmlns=\"NewDataSet\">";
		final String endTag = "</table>";

		try {
			is = new FileInputStream(file);
			ps = new PrintStream(new FileOutputStream(writeToFile));
			
			byte[] content = new byte[fl];
			is.read(content, 0, fl);
			str = new String(content);

			scanMassive(str, startTag, endTag, ps);

		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
		}
	}

	private static void scanMassive(String str, String startTag, String endTag, PrintStream ps) {
		int start = 0;
		int end = -1;
		int lineNumber = 1;

		ps.println("LOCK TABLES `POBLACION` WRITE;");
		ps.println("/*!40000 ALTER TABLE `POBLACION` DISABLE KEYS */;");
		ps.println("");
		ps.println("INSERT INTO POBLACION (ID, NOMBRE  ,TIPO_ASENTAMIENTO, MUNICIPIO_O_DELEGACION, ENTIDAD_FEDERATIVA, CODIGO_POSTAL) VALUES ");
		
		for (start = str.indexOf(startTag, start); start >= 0; start = str.indexOf(startTag, end), lineNumber++) {
			end = str.indexOf(endTag, start);
			
			String line = str.substring(start + startTag.length(), end);

			String scanList_d_asenta = scanFirst(line, "<d_asenta>", "</d_asenta>");
			String scanList_d_tipo_asenta = scanFirst(line, "<d_tipo_asenta>", "</d_tipo_asenta>");
			String scanList_D_mnpio = scanFirst(line, "<D_mnpio>", "</D_mnpio>");
			String scanList_d_estado = scanFirst(line, "<d_estado>", "</d_estado>");
			String scanList_d_CP = scanFirst(line, "<d_codigo>", "</d_codigo>");//scanFirst(line, "<d_CP>", "</d_CP>");

			if(scanList_d_estado.contains(" DE ZARAGOZA")){
				scanList_d_estado = scanList_d_estado.replace(" DE ZARAGOZA","");
			} else if(scanList_d_estado.contains(" DE OCAMPO")){
				scanList_d_estado = scanList_d_estado.replace(" DE OCAMPO","");
			} else if(scanList_d_estado.contains(" DE IGNACIO DE LA LLAVE")){
				scanList_d_estado = scanList_d_estado.replace(" DE IGNACIO DE LA LLAVE","");
			}
			
			if(lineNumber > 1){
				ps.println(", ");
			}
			ps.print(
					"("+
					lineNumber + ","
					+ "'" + scanList_d_asenta + "', "
					+ "'" + scanList_d_tipo_asenta + "', "
					+ "'" + scanList_D_mnpio + "', "
					+ "'" + scanList_d_estado + "',"
					+ "'" + scanList_d_CP + "')");
		}
		ps.println(";");
		ps.println("/*!40000 ALTER TABLE `POBLACION` ENABLE KEYS */;");
		ps.println("UNLOCK TABLES;");

	}

	private static List<String> scan(String str, String startTag, String endTag) {
		List<String> strScan = new ArrayList<String>();
		int start = 0;
		int end = -1;
		int counter = 0;
		for (start = str.indexOf(startTag, start); start >= 0; start = str.indexOf(startTag, end)) {
			end = str.indexOf(endTag, start);
			String found = str.substring(start + startTag.length(), end);

			if (found.contains("&quot;")) {
				found = found.replace("&quot;", "\\\"");
			}
			if (found.contains("\"")) {
				found = found.replace("\"", "\\\"");
			}
			if (found.contains("'")) {
				found = found.replace("'", "\\'");
			}

			found = found.toUpperCase();

			strScan.add(found);
		}
		return strScan;
	}

	private static String scanFirst(String str, String startTag, String endTag) {
		String strScan = null;
		int start = 0;
		int end = -1;
		for (start = str.indexOf(startTag, start); start >= 0; start = str.indexOf(startTag, end)) {
			end = str.indexOf(endTag, start);
			String found = str.substring(start + startTag.length(), end);

			if (found.contains("&quot;")) {
				found = found.replace("&quot;", "\\\"");
			}
			if (found.contains("\"")) {
				found = found.replace("\"", "\\\"");
			}
			if (found.contains("'")) {
				found = found.replace("'", "\\'");
			}

			strScan = found.toUpperCase();

			break;
		}
		return strScan;
	}
}
