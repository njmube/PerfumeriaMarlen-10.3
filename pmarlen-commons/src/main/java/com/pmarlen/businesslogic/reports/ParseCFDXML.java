/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic.reports;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author alfredo
 */
public class ParseCFDXML {

	public static HashMap parseCFDXML(InputStream isXML) {
		
		HashMap<String,String> result = null;

		String folio        = "";
		String emisorRFC    = "";
		String receptorRFC  = "";
		String total		="";
		String serie        = "";
		String sello        = "";
		String fecha        = "";
		
		String version="";
		String UUID="";
		String FechaTimbrado="";
		String selloCFD="";
		String selloSAT="";
		String noCertificadoSAT="";
		
		result = new HashMap<String,String>();
			
		
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(isXML);
			
			Element rootElement = doc.getDocumentElement();

			rootElement.normalize();

			total=rootElement.getAttribute("total");
			folio = rootElement.getAttribute("folio");
			fecha = rootElement.getAttribute("fecha");
			
			System.out.println("folio        : " + folio);
			System.out.println("sello        : " + rootElement.getAttribute("sello"));
			System.out.println("subTotal     : " + rootElement.getAttribute("subTotal"));
			System.out.println("total        : " + total);
			System.out.println("serie        : " + rootElement.getAttribute("serie"));
			System.out.println("fecha        : " + fecha);

			result.put("folio",folio);
			result.put("sello",rootElement.getAttribute("sello"));
			result.put("subTotal",rootElement.getAttribute("subTotal"));
			result.put("total",total);
			result.put("serie",rootElement.getAttribute("serie"));
			result.put("fecha",fecha);
			
			NodeList nListe = doc.getElementsByTagName("cfdi:Emisor");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nListe.getLength(); temp++) {

				Node nNode = nListe.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElemente = (Element) nNode;
					emisorRFC   = eElemente.getAttribute("rfc");
					System.out.println("emisor rfc    : " + emisorRFC);
							
				}
			}
			

			NodeList nListr = doc.getElementsByTagName("cfdi:Receptor");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nListr.getLength(); temp++) {

				Node nNode = nListr.item(temp);

				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElementr = (Element) nNode;
					receptorRFC = eElementr.getAttribute("rfc");
					System.out.println("receptor rfc    : " + receptorRFC);
							
				}
			}

			
			NodeList nList = doc.getElementsByTagName("cfdi:Complemento");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NodeList elementsByTagName = eElement.getElementsByTagName("tfd:TimbreFiscalDigital");
					for (int tempx = 0; tempx < elementsByTagName.getLength(); tempx++) {

						Node nNodex = elementsByTagName.item(tempx);

						//System.out.println("\nCurrent Element :" + nNodex.getNodeName());

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElementx = (Element) nNodex;
														
							version			 = eElementx.getAttribute("version");
							UUID			 = eElementx.getAttribute("UUID");
							FechaTimbrado	 = eElementx.getAttribute("FechaTimbrado");
							selloCFD		 = eElementx.getAttribute("selloCFD");
							selloSAT		 = eElementx.getAttribute("selloSAT");
							noCertificadoSAT = eElementx.getAttribute("noCertificadoSAT");

							System.out.println("version          : " + version);
							System.out.println("UUID             : " + UUID);							
							System.out.println("FechaTimbrado    : " + FechaTimbrado);
							System.out.println("selloCFD         : " + selloCFD);
							System.out.println("selloSAT         : " + selloSAT);
							System.out.println("noCertificadoSAT : " + noCertificadoSAT);
							
						}
					}				
				}
			}
			
			String strCadenaOriginal = "|"+version+"|"+UUID+"|"+FechaTimbrado+"|"+selloCFD+"|"+noCertificadoSAT+"|";
			
			String QR = "?re=+"+emisorRFC+"+&rr=+"+receptorRFC+"+&tt=+"+total+"+&id=+"+UUID;
			
			System.out.println("----------------------------");
			System.out.println(" strCadenaOriginal = "+strCadenaOriginal);
			System.out.println(" QR                = "+QR);
			
			result.put("emisorRFC",emisorRFC);
			result.put("receptorRFC",receptorRFC);
			result.put("version", version);
			result.put("UUID", UUID);
			result.put("FechaTimbrado", FechaTimbrado);
			result.put("selloCFD", selloCFD);
			result.put("selloSAT", selloSAT);
			result.put("noCertificadoSAT", noCertificadoSAT);

			result.put("cadenaOriginal",strCadenaOriginal);
			result.put("QR",QR);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
