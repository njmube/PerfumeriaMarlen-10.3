/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.io.File;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author alfredo
 */
public class DOMParser {

    public static void main(String[] args) {
        try {
            
            if( args.length != 3) {
                System.err.println("usage: fileToParse fileOutput sqlOutput={true|false}");
                System.exit(1);
            }
            
            String fileToParse = args[0];            
            String fileOutput = args[1];
            
            File file = new File(fileToParse);
            boolean sqlOutput = args[2].equals("sqlOutput=true");
            
            PrintStream ps = null;
            if(fileOutput.equals("-stdout")) {
                ps = System.out;
            }else {
                ps = new PrintStream(fileOutput);            
            }
            


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("table");

            String[] elementsToScan = new String[]{"d_asenta", "d_tipo_asenta", "D_mnpio", "d_estado", "d_codigo"};
            int nodeElementId;

            if (sqlOutput) {
                ps.println("LOCK TABLES `POBLACION` WRITE;");
                ps.println("/*!40000 ALTER TABLE `POBLACION` DISABLE KEYS */;");
                ps.println("");
                ps.print("INSERT INTO POBLACION (ID, NOMBRE  ,TIPO_ASENTAMIENTO, MUNICIPIO_O_DELEGACION, ENTIDAD_FEDERATIVA, CODIGO_POSTAL) VALUES ");
            }


            for (nodeElementId = 1; nodeElementId < nodeLst.getLength(); nodeElementId++) {

                Node fstNode = nodeLst.item(nodeElementId);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    int numProperties = 0;
                    Element fstElmnt = (Element) fstNode;

                    if (sqlOutput) {
                        if (nodeElementId > 1) {
                            ps.print(", ");
                        }
                        ps.print("(");
                    }

                    for (String elemenTag : elementsToScan) {
                        NodeList lstNmElmntLst = fstElmnt.getElementsByTagName(elemenTag);
                        Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
                        NodeList lstNm = lstNmElmnt.getChildNodes();

                        if (sqlOutput) {
                            if (numProperties > 0) {
                                ps.print(",'");
                            } else {
                                ps.print(nodeElementId + ",'");
                            }

                            Node itemNode_0 = (Node) lstNm.item(0);
                            if (itemNode_0 != null) {
                                String contentToPrint = itemNode_0.getNodeValue();
                                if(contentToPrint.contains("\"")){
                                    contentToPrint = contentToPrint.replace("\"", "\\\"");
                                }
                                else if(contentToPrint.contains("'")){
                                    contentToPrint = contentToPrint.replace("'", "\\'");
                                }
                                
                                ps.print(contentToPrint);
                                if (numProperties > 0) {
                                    ps.print("'");
                                }
                            } else {
                                //ps.println("<NULL>");
                            }
                        } else {
                            if (numProperties > 0) {
                                ps.print("|");
                            } else {
                                ps.print(nodeElementId + "|");
                            }

                            Node itemNode_0 = (Node) lstNm.item(0);
                            if (itemNode_0 != null) {
                                String contentToPrint = itemNode_0.getNodeValue();
                                if(contentToPrint.contains("\"")){
                                    contentToPrint = contentToPrint.replace("\"", "'");
                                }
                                
                                ps.print(contentToPrint);
                            } else {
                                //ps.println("<NULL>");
                            }
                        }
                        numProperties++;
                    }
                }
                if (sqlOutput) {
                    ps.print(")");
                } else {
                    ps.println();
                }
            }
            if (sqlOutput) {
                ps.println(";");
                ps.println("/*!40000 ALTER TABLE `POBLACION` ENABLE KEYS */;");
                ps.println("UNLOCK TABLES;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
