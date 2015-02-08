/**
 * Archivo: ImportSepomex.java
 * 
 * Fecha de Creaci&oacute;n: 8/06/2011
 *
 * 2H Software - Bursatec 2011
 */
package com.pmarlen.dev.task;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Alfredo Estrada Gonz&aacute;lez.
 * @version 1.0
 *
 */

class LugarGeografico {
    Integer id;
    String nombre;
    
    @Override
    public boolean equals(Object o) {
        if( o == null) {
            return false;
        }
        LugarGeografico lg = (LugarGeografico)o;
        return this.id.equals(lg.id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{ id="+id+" , nommbre="+nombre+"}";
    }
    
}

class EntidadFederativa extends LugarGeografico{
    public String toSQLString() {
        return "INSERT INTO ENTIDAD_FEDERATIVA(ID,NOMBRE) VALUES(" + id+" , '"+nombre+"');";        
    }    
}

class MunicipioODelegacion extends LugarGeografico{
    EntidadFederativa entidadFederativa;
    public String toSQLString() {
        return "INSERT INTO MUNICIPIO_O_DELEGACION(ID,ENTIDAD_FEDERATIVA_ID,NOMBRE) VALUES(" + id+","+ entidadFederativa.id+",'"+nombre+"');";        
    }
}

class Poblacion  extends LugarGeografico{    
    String cp;
    String tipoAsentamiento;
    String oficina;
    String asentaCPCons;
    String zona;
    
    MunicipioODelegacion municipioODelegacion;
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{ id="+id+" , nommbre="+nombre+", cp="+cp+", tipoAsentamiento="+tipoAsentamiento+", oficina="+oficina+", asentaCPCons="+asentaCPCons+", zona="+zona+"}";
    }
    
    public String toSQLString() {
        return "INSERT INTO POBLACION(ID,MUNICIPIO_O_DELEGACION_ID,CODIGO_POSTAL,NOMBRE) VALUES(" + id+","+ municipioODelegacion.id+","+cp+",'"+nombre+"');";        
    }
}



class MyHandler extends DefaultHandler{
    String currentElement = null;
    List<String> posibleEmeentsList = Arrays.asList(new String[]{"d_codigo","d_asenta","d_tipo_asenta","D_mnpio","d_estado","d_CP","c_estado","c_oficina","c_CP","c_tipo_asenta","c_mnpio","id_asenta_cpcons","d_zona"});
    Hashtable<Integer,EntidadFederativa> entidadFederativaHS;
    Hashtable<String,MunicipioODelegacion> municipioODelegacionHS;
    Hashtable<String,Poblacion> poblacionHS;
    Hashtable<String,String> tipoAsentamientoHS;
    
    EntidadFederativa entidadFederativaActual;
    MunicipioODelegacion municipioODelegacionActual;
    Poblacion poblacionActual;
    DecimalFormat df = new DecimalFormat("00000");
    
    int depth = 1;
    int numXMLElementTable;
    int numPoblaciones;
    int totalDePoblaciones;
    
    public MyHandler(int tp){
        this.totalDePoblaciones = tp;
        
        entidadFederativaHS = new Hashtable<Integer,EntidadFederativa>();
        municipioODelegacionHS = new Hashtable<String,MunicipioODelegacion> ();
        poblacionHS = new Hashtable<String,Poblacion>();
        tipoAsentamientoHS = new Hashtable<String,String> ();
    
    }
    
    @Override
    public void startDocument() throws SAXException {
//        for(int j=0;j<depth;j++){
//            System.err.print("\t");
//        }
//        System.err.println("->start Document");
        numXMLElementTable = 0;
        numPoblaciones = 0;
    }

    @Override
    public void startElement(String string, String string1, String string2, Attributes atrbts) throws SAXException {
        depth++;
        
        if(string2.equals("table")){
            entidadFederativaActual =  new EntidadFederativa();
            municipioODelegacionActual = new MunicipioODelegacion();
            poblacionActual = new Poblacion();
        }
        
        currentElement = string2;
        
//        for(int j=0;j<depth;j++){
//            System.err.print("\t");
//        }
//        System.err.println("->startElement: "+ string2 );
//        for (int ai = 0; ai < atrbts.getLength(); ai++) {                        
//            for(int j=0;j<depth;j++){
//                System.err.print("\t");
//            }
//            System.err.println("\tAttribute:-> " + string2 + "." + atrbts.getQName(ai) + " = '" + atrbts.getValue(ai) + "';");            
//        }
//        System.err.println();
    }
    
    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        if(currentElement!=null && posibleEmeentsList.contains(currentElement)){
            for(int j=0;j<depth;j++){
                System.err.print("\t");
            }
            String valueOfChars = new String(chars, i, i1);
//            System.err.print("\tChars:->");
//            System.err.print(valueOfChars);
//            System.err.println("<-");
            try {
                if(currentElement.equals("d_codigo")){
                    poblacionActual.id = new Integer(df.parse(valueOfChars).intValue());
                } else if(currentElement.equals("d_asenta")){
                    poblacionActual.nombre = valueOfChars;
                } else if(currentElement.equals("d_tipo_asenta")){
                    poblacionActual.tipoAsentamiento = valueOfChars;
                } else if(currentElement.equals("c_oficina")){
                    poblacionActual.oficina = valueOfChars;
                } else if(currentElement.equals("id_asenta_cpcons")){
                    poblacionActual.asentaCPCons= valueOfChars;
                } else if(currentElement.equals("d_zona")){
                    poblacionActual.zona = valueOfChars;
                } else if(currentElement.equals("d_CP")){
                    poblacionActual.cp = valueOfChars;
                } else if(currentElement.equals("c_mnpio")){
                    municipioODelegacionActual.id = new Integer(df.parse(valueOfChars).intValue());
                } else if(currentElement.equals("D_mnpio")){
                    municipioODelegacionActual.nombre = valueOfChars;                    
                } else if(currentElement.equals("d_estado")){
                    entidadFederativaActual.nombre = valueOfChars;
                } else if(currentElement.equals("c_estado")){
                    entidadFederativaActual.id = new Integer(df.parse(valueOfChars).intValue());
                } 

            } catch(ParseException ps){
            
            }
            
        }
    }

    @Override
    public void endElement(String string, String string1, String string2) throws SAXException {
        depth--;
        currentElement = null;
        if(string2.equals("table")){
            numXMLElementTable++;
            Poblacion poblacionBuscada = poblacionHS.get(poblacionActual.id+"|"+poblacionActual.tipoAsentamiento+"|"+poblacionActual.nombre);            
            if( poblacionActual.cp == null) {
                System.err.println("======>CP es NULL !! poblacionActual :"+poblacionActual);            
            }            
            if(     poblacionBuscada != null  && 
                    poblacionBuscada.cp.equals(poblacionActual.cp) 
                ){
                //System.err.println("======>ya esta \n\tpoblacionActual :"+poblacionActual+" != \n\tpoblacionBuscada:"+poblacionBuscada);                                                       
            } else {
                numPoblaciones++;
                poblacionActual.municipioODelegacion = municipioODelegacionActual;                
                
                tipoAsentamientoHS.put(poblacionActual.tipoAsentamiento, poblacionActual.tipoAsentamiento);
                
                poblacionHS.put(poblacionActual.id+"|"+poblacionActual.tipoAsentamiento+"|"+poblacionActual.nombre,
                                poblacionActual);
            }
            
            MunicipioODelegacion municipioODelegacionBuscado = municipioODelegacionHS.get(entidadFederativaActual.id+"|"+municipioODelegacionActual.id);
            if(municipioODelegacionBuscado != null ){
            
            } else {
                municipioODelegacionActual.entidadFederativa = entidadFederativaActual;
                municipioODelegacionHS.put(entidadFederativaActual.id+"|"+municipioODelegacionActual.id,municipioODelegacionActual);
            }
            
            entidadFederativaHS.put(entidadFederativaActual.id,entidadFederativaActual);
            
            System.err.print("\r->\t"+((100 * numXMLElementTable / totalDePoblaciones))+"%");
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        System.err.println();
        System.err.println("->end Document: numXMLElementTable="+numXMLElementTable+", numPoblaciones="+numPoblaciones);
    }    
};

/**
 * 
 * @author Alfredo Estrada Gonz&aacute;lez.
 * @version 1.0
 *
 */
public class ImportSepomex {

    public static void main(String[] args) {
        MyHandler handler = new MyHandler(Integer.parseInt(args[1]));
        parseXmlFile(args[0], handler, false);
        System.err.println();
        System.err.println("=> EntidadFederativa: "+handler.entidadFederativaHS.size()+" elements.");
        System.err.println("=> Municipio o Delegacion: "+handler.municipioODelegacionHS.size() + " elements.// 2447 = 2456 ?");
        
//        Enumeration<MunicipioODelegacion> me = handler.municipioODelegacionHS.elements();
//        while(me.hasMoreElements()) {
//            System.err.println("\t=> Municipio o Delegacion: "+me.nextElement());
//        }
        
        System.err.println("=> Poblacion: "+handler.poblacionHS.size() +" elements.");
        
//        Collection<String> asentamientosColection = handler.tipoAsentamientoHS.values();
//        for(String as: asentamientosColection) {
//            System.err.println("=> Tipo asentamientos: "+as);
//        }
//        
        Collection<Poblacion> poblaciones = handler.poblacionHS.values();
        int nuevoId=1;
        for(Poblacion pob: poblaciones){
            pob.id = nuevoId++;
        }
        
    }

    public static void parseXmlFile(String filename, DefaultHandler handler, boolean validating) {
        try {
            // Create a builder factory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(validating);

            // Create the builder and parse the file
            factory.newSAXParser().parse(new File(filename), handler);
        } catch (SAXException e) {
            // A parsing error occurred; the xml input is not valid
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        }
    }
}

