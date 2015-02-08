package com.pmarlen.dev.task;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alfredo estrada
 */
public class ParseSepomex {

	private static PrintStream ps;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		if (args.length != 2) {
			System.err.println("\tUsage: ParseSepomex xmlFileToParse  sqlFileOutput");
			System.exit(1);
		}
		String xmlFileToParse = args[0];
		String sqlFileOutput = args[1];
		try {
			ps = new PrintStream(sqlFileOutput);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean startNewDataSet = false;
				boolean startTable = false;
				boolean startdCodigo = false;
				boolean startDAsenta = false;
				boolean startDTipoAsenta = false;
				boolean startDMmnpio = false;
				boolean startDEstado = false;
				boolean startDCP = false;
				boolean startCEstado = false;
				String contentToPrint = null;
				String contntToPrint_dCodigo = null;
				String contntToPrint_DAsenta = null;
				String contntToPrint_DTipoAsenta = null;
				String contntToPrint_DMmnpio = null;
				String contntToPrint_DEstado = null;
				String contntToPrint_DCP = null;
				String contntToPrint_CEstado = null;
				DecimalFormat df = new DecimalFormat("000000");
				int intID = 0;
				int intCEstado = -1;
				int numRegs = 0;

				@Override
				public void startDocument() throws SAXException {
					ps.println("LOCK TABLES `POBLACION` WRITE;");
					ps.println("/*!40000 ALTER TABLE `POBLACION` DISABLE KEYS */;");
					ps.println("");
					ps.print("INSERT INTO POBLACION (ID, NOMBRE  ,TIPO_ASENTAMIENTO, MUNICIPIO_O_DELEGACION, ENTIDAD_FEDERATIVA, CODIGO_POSTAL) VALUES ");

				}

				@Override
				public void startElement(String uri, String localName, String qName,
						Attributes attributes) throws SAXException {

					//System.err.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("NewDataSet")) {
						startNewDataSet = true;
					}

					if (qName.equalsIgnoreCase("table")) {
						startTable = true;
						if (numRegs > 0) {
							ps.print(", ");
						}
						ps.print("(");
						numRegs++;
					}

					if (qName.equalsIgnoreCase("d_codigo")) {
						startdCodigo = true;
					}

					if (qName.equalsIgnoreCase("d_asenta")) {
						startDAsenta = true;
					}

					if (qName.equalsIgnoreCase("d_tipo_asenta")) {
						startDTipoAsenta = true;
					}

					if (qName.equalsIgnoreCase("D_mnpio")) {
						startDMmnpio = true;
					}

					if (qName.equalsIgnoreCase("D_mnpio")) {
						startDMmnpio = true;
					}

					if (qName.equalsIgnoreCase("d_estado")) {
						startDEstado = true;
					}

					if (qName.equalsIgnoreCase("d_CP")) {
						startDCP = true;
					}

					if (qName.equalsIgnoreCase("c_estado")) {
						startCEstado = true;
					}

				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					//System.err.println("End Element :" + qName);
					if (qName.equalsIgnoreCase("NewDataSet")) {
						startNewDataSet = false;
					}

					if (qName.equalsIgnoreCase("table")) {
						synchronized (this) {

							startTable = false;

							startdCodigo = false;
							startDAsenta = false;
							startDTipoAsenta = false;
							startDMmnpio = false;
							startDEstado = false;
							startDCP = false;
							startCEstado = false;

							try {
								intID++;

								intCEstado = df.parse(contntToPrint_CEstado).intValue();

							} catch (ParseException ex) {
								ex.printStackTrace(System.err);
								intID = -1;
							}
							ps.println(
									intID + ","
									+ "'" + contntToPrint_DAsenta + "', "
									+ "'" + contntToPrint_DTipoAsenta + "', "
									+ "'" + contntToPrint_DMmnpio + "', "
									+ "'" + contntToPrint_DEstado + "',"
									+ "'" + contntToPrint_dCodigo + "')");
						}

					}

				}

				@Override
				public void characters(char ch[], int start, int length) throws SAXException {
					synchronized (this) {
						if (startNewDataSet && startTable) {

							contentToPrint = new String(ch, start, length);

							if (contentToPrint.contains("&quot;")) {
								System.err.println("===>>>>>>> [" + intID + "]{" + start + "," + length + "," + startDAsenta + "}: COMILLA DOBLE ESCAPADA: " + contentToPrint);
								contentToPrint = new String(ch, start, length).replace("&quot;", "\\\"");
							}
							if (contentToPrint.contains("\"")) {
								System.err.println("===>>>>>>> [" + intID + "]{" + start + "," + length + "," + startDAsenta + "}: COMILLA DOBLE: " + contentToPrint);
								contentToPrint = new String(ch, start, length).replace("\"", "\\\"");
							}
							if (contentToPrint.contains("'")) {
								System.err.println("===>>>>>>> [" + intID + "]{" + start + "," + length + "," + startDAsenta + "}: COMILLA SIMPLE: " + contentToPrint);
								contentToPrint = new String(ch, start, length).replace("'", "\\'");
							}


							if (startdCodigo) {
								contntToPrint_dCodigo = contentToPrint;
								startdCodigo = false;
							}

							if (startDAsenta) {
								contntToPrint_DAsenta = contentToPrint.toUpperCase();
								startDAsenta = false;
							}

							if (startDTipoAsenta) {
								contntToPrint_DTipoAsenta = contentToPrint.toUpperCase();
								startDTipoAsenta = false;
							}
							if (startDMmnpio) {
								contntToPrint_DMmnpio = contentToPrint.toUpperCase();
								startDMmnpio = false;
							}
							if (startDEstado) {
								contntToPrint_DEstado = contentToPrint.toUpperCase();
								/*
								 if(contntToPrint_DEstado.contains(" de Ignacio de la Llave")){
								 contntToPrint_DEstado = contntToPrint_DEstado.replace(" de Ignacio de la Llave", "");
								 } else if(contntToPrint_DEstado.contains(" de Zaragoza")){
								 contntToPrint_DEstado = contntToPrint_DEstado.replace(" de Zaragoza", "");
								 } else if(contntToPrint_DEstado.contains(" de Ocampo")){
								 contntToPrint_DEstado = contntToPrint_DEstado.replace(" de Ocampo", "");
								 }
								 */
								System.out.println("->contntToPrint_DEstado[" + (numRegs) + "]=" + contntToPrint_DEstado);

								startDEstado = false;
							}
							if (startDCP) {
								contntToPrint_DCP = contentToPrint;
								startDCP = false;
							}
							if (startCEstado) {
								contntToPrint_CEstado = contentToPrint.toUpperCase();
								startCEstado = false;
							}
						}
					}
				}

				@Override
				public void endDocument() throws SAXException {
					ps.println(";");
					ps.println("/*!40000 ALTER TABLE `POBLACION` ENABLE KEYS */;");
					ps.println("UNLOCK TABLES;");

				}
			};

			saxParser.parse(xmlFileToParse, handler);

		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

	}
}
