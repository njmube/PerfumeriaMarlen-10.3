package com.pmarlen.businesslogic.reports;

import com.pmarlen.backend.model.Cfd;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaFooter;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.model.Constants;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Hello world!
 *
 */

public class GeneradorImpresionPedidoVenta {
	private static Logger logger = Logger.getLogger("GeneradorImpresionPedidoVenta");
	private static String reportDesignDir="/reports/";
	
    public static byte[] generaPdfPedidoVenta(EntradaSalidaQuickView pedidoVenta,ArrayList<EntradaSalidaDetalleQuickView> esdListOriginal,Cliente clienteVenta,boolean fullPrint,boolean interna,String usuarioImr) {
		byte[] pdfBytes = null;
		ArrayList<EntradaSalidaDetalleQuickView> esdList=null;
		try {
			String reportFileName;
			String reportPath;
			String compiledReportPath = "";
			String compiledReportClassPath = "";
            
			logger.info("Default Locale:"+Locale.getDefault());
            
			if(interna){
				reportFileName = "pedidoVentaDesignInterno2";
				esdList = new ArrayList<EntradaSalidaDetalleQuickView>(esdListOriginal);
				Collections.sort(esdList);
			} else{
				esdList=esdListOriginal;
				reportFileName = "pedidoVentaDesignCliente";
				
			}
			reportPath = reportDesignDir + reportFileName + ".jrxml";
			compiledReportClassPath = reportDesignDir + reportFileName + ".jasper";
			compiledReportPath = "./"+ reportFileName + ".jasper";
			
            Collection<Map<String,?>> col = new ArrayList<Map<String,?>>();
            DecimalFormat    df    = new  DecimalFormat("$###,###,###,##0.00");
            DecimalFormat    dfEnt = new  DecimalFormat("###########0.00");
            logger.info("Ok, jrxml loaded");
			logger.info("Ok, jrxml loaded:"+pedidoVenta.getAutorizaDescuento()+"?,"+pedidoVenta.getPorcentajeDescuentoCalculado()+"%+"+pedidoVenta.getPorcentajeDescuentoExtra()+"%");
            int n;
			EntradaSalidaFooter esf=new EntradaSalidaFooter();
			esf.calculaTotalesDesde(pedidoVenta, esdList);
			for(EntradaSalidaDetalleQuickView pvd:esdList){
				Map<String,Object> vals = new HashMap<String,Object> ();
                
                n = pvd.getCantidad();
                
                vals.put("clave",pvd.getProductoCodigoBarras());
                vals.put("cantidad",n);
				vals.put("ta",Constants.getDescripcionTipoAlmacen(pvd.getApTipoAlmacen()).substring(0,3));
                vals.put("codigoBarras",pvd.getProductoCodigoBarras());                
                vals.put("descripcion",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion());
				vals.put("descripcionCont",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion()+" ("+pvd.getProductoContenido()+pvd.getProductoUnidadMedida()+")");
				vals.put("precio",df.format(pvd.getPrecioVenta()));
                vals.put("ppc"  ,pvd.getProductoUnidadesPorCaja());
				if(pvd.getApUbicacion() == null){
					vals.put("ubic"  ,"--N/D--");
				} else {
					vals.put("ubic"  ,pvd.getApUbicacion());
				}
				vals.put("ue"  ,pvd.getProductoUnidadEmpaque());
                vals.put("importe",df.format(n*pvd.getPrecioVenta()));
                vals.put("cont",pvd.getProductoContenido()+" "+pvd.getProductoUnidadMedida());
                
                col.add(vals);
			}
			
            JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);
            logger.info("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            SimpleDateFormat sdf_cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            SimpleDateFormat sdf_f1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf_h1 = new SimpleDateFormat("HH:mm");
            
            Date fechaReporte = new Date();
            
            parameters.put("printImages" ,fullPrint);
            String lugarExpedicion = "EDO. MÉX.";
			parameters.put("usuario",pedidoVenta.getUsuarioNombreCompleto());
			parameters.put("usuarioImpr",usuarioImr);
			parameters.put("pedidoVentaId",pedidoVenta.getId());
			parameters.put("pedidoVentaEstado",pedidoVenta.getEstadoDescripcion());
			
			//parameters.put("tipoAlmacen", "TA");
			
			parameters.put("fechaHoraImpr",sdf_cdf.format(new Date()));
						
            parameters.put("cliente",clienteVenta.getRazonSocial());
			parameters.put("nombreEstab",clienteVenta.getNombreEstablecimiento());
            parameters.put("rfc",clienteVenta.getRfc());
			String direccionValue = null;
			direccionValue = clienteVenta.getDireccionFacturacion();
			if(direccionValue == null){
				direccionValue = clienteVenta.getCalle()+
						", Num. Ext. "+(clienteVenta.getNumExterior()!=null&&clienteVenta.getNumExterior().length()>0?clienteVenta.getNumExterior():"S/N")+
						", Num. Int. "+(clienteVenta.getNumInterior()!=null&&clienteVenta.getNumInterior().length()>0?clienteVenta.getNumInterior():"S/N")+					
						", "+clienteVenta.getColonia()+
					", "+clienteVenta.getMunicipio()+
						", "+clienteVenta.getEstado()+
						", C.P. "+clienteVenta.getCp();
			}
            parameters.put("direccion" , direccionValue.toUpperCase());
			
            if(pedidoVenta.getComentarios()!=null && pedidoVenta.getComentarios().trim().length()>1){
				parameters.put("comentarios" ,pedidoVenta.getComentarios());
			} else{
				parameters.put("comentarios" ,null);
			}
			if(pedidoVenta.getCondicionesDePago()!=null && pedidoVenta.getCondicionesDePago().trim().length()>1){
				parameters.put("condiciones" ,pedidoVenta.getCondicionesDePago());
			} else{
				parameters.put("condiciones" ,"--NO IDENTIFICADO--");
			}
			if(pedidoVenta.getNumDeCuenta()!=null && pedidoVenta.getNumDeCuenta().trim().length()>1){
				parameters.put("noCuenta" ,pedidoVenta.getNumDeCuenta());
			} else{
				parameters.put("noCuenta" ,"--NO IDENTIFICADO--");
			}
			parameters.put("formaDePago" ,pedidoVenta.getFormaDePagoDescripcion().toUpperCase());
            parameters.put("metodoDePago",pedidoVenta.getMetodoDePagoDescripcion().toUpperCase());
            logger.info("->descuento:autorizadescuento?"+pedidoVenta.getAutorizaDescuento()+", "+pedidoVenta.getPorcentajeDescuentoCalculado()+"% + "+pedidoVenta.getPorcentajeDescuentoExtra());
            parameters.put("subtotal" , df.format(esf.getSubTotalBruto()));
            parameters.put("iva" ,df.format(esf.getImporteIVA()));
            parameters.put("descuento" ,df.format(esf.getImporteDescuentoAplicado()));
            
            parameters.put("total" ,df.format(esf.getTotal()));  
            JasperReport jasperReport = null;
            //String intDecParts[] = dfEnt.format(esf.getTotal()).split("\\.");
			File compiledReportPathFile=new File(compiledReportPath);
			/*
            if(! compiledReportPathFile.exists()){
				InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperCompileManager.compileReportToStream(jasperDesign,new FileOutputStream(compiledReportPathFile));
				logger.info("Ok, JasperReport compiled and saved 1st time, to:"+compiledReportPath);				
			}
			
			jasperReport = (JasperReport)JRLoader.loadObject(compiledReportPathFile);
			*/
			InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			logger.info("Ok, JasperReport loaded from:"+compiledReportPath);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            logger.info("Ok, JasperPrint created.");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());
    
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setMetadataAuthor("PerfumeriaMarlen_SAA_10.3");						
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();
			logger.info("Ok, JasperExportManager executed");
			pdfBytes = baos.toByteArray();            
            logger.info("Ok, finished");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            //System.exit(1);
        }
		return pdfBytes;
    }
	
	public static byte[] generaPdfTicketPedidoVenta(EntradaSalidaQuickView pedidoVenta,ArrayList<EntradaSalidaDetalleQuickView> esdList,Cliente clienteVenta,boolean fullPrint,boolean interna,String usuarioImr) {
		byte[] pdfBytes = null;
		try {
			String reportFileName;
			String reportPath;
			String compiledReportPath = "";
			String compiledReportClassPath = "";
            
			logger.info("Default Locale:"+Locale.getDefault());
            
			reportFileName = "pedidoVentaTicketDesign";
			
			reportPath = reportDesignDir + reportFileName + ".jrxml";
			compiledReportClassPath = reportDesignDir + reportFileName + ".jasper";
			compiledReportPath = "./"+ reportFileName + ".jasper";
			
            
            
            Collection<Map<String,?>> col = new ArrayList<Map<String,?>>();
            DecimalFormat    df    = new  DecimalFormat("$###,###,###,##0.00");
            DecimalFormat    dfEnt = new  DecimalFormat("###########0.00");
            logger.info("Ok, jrxml loaded");
			logger.info("Ok, jrxml loaded:"+pedidoVenta.getAutorizaDescuento()+"?,"+pedidoVenta.getPorcentajeDescuentoCalculado()+"%+"+pedidoVenta.getPorcentajeDescuentoExtra()+"%");
            int n;
			EntradaSalidaFooter esf=new EntradaSalidaFooter();
			esf.calculaTotalesDesde(pedidoVenta, esdList);
			for(EntradaSalidaDetalleQuickView pvd:esdList){
				Map<String,Object> vals = new HashMap<String,Object> ();
                
                n = pvd.getCantidad();
                
                vals.put("clave",pvd.getProductoCodigoBarras());
                vals.put("cantidad",n);
				vals.put("ta",Constants.getDescripcionTipoAlmacen(pvd.getApTipoAlmacen()).substring(0,3));
                vals.put("codigoBarras",pvd.getProductoCodigoBarras());                
                vals.put("descripcion",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion());
                vals.put("descripcionCont",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion()+"("+pvd.getProductoContenido()+pvd.getProductoUnidadMedida()+")");
				vals.put("precio",df.format(pvd.getPrecioVenta()));
                vals.put("ppc"  ,pvd.getProductoUnidadesPorCaja());
				if(pvd.getApUbicacion() == null){
					vals.put("ubic"  ,"--N/D--");
				} else {
					vals.put("ubic"  ,pvd.getApUbicacion());
				}
				vals.put("ue"  ,pvd.getProductoUnidadEmpaque());
                vals.put("importe",df.format(n*pvd.getPrecioVenta()));
                vals.put("cont",pvd.getProductoContenido()+" "+pvd.getProductoUnidadMedida());
                
                col.add(vals);
			}
			
            JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);
            logger.info("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            SimpleDateFormat sdf_cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            SimpleDateFormat sdf_f1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf_h1 = new SimpleDateFormat("HH:mm");
            
            Date fechaReporte = new Date();
            
            parameters.put("printImages" ,fullPrint);
            String lugarExpedicion = "EDO. MÉX.";
			parameters.put("usuario",pedidoVenta.getUsuarioNombreCompleto());
			parameters.put("ticket","00000000009");
			parameters.put("usuarioImpr",usuarioImr);
			parameters.put("pedidoVentaId",pedidoVenta.getId());
			parameters.put("pedidoVentaEstado",pedidoVenta.getEstadoDescripcion());
			parameters.put("caja","?1");
			parameters.put("usuario",pedidoVenta.getUsuarioNombreCompleto());
			
			parameters.put("pedidoVentaEstado",pedidoVenta.getEstadoDescripcion());
			
			//parameters.put("tipoAlmacen", "TA");
			
			parameters.put("fechaHoraImpr",sdf_cdf.format(new Date()));
						
            parameters.put("cliente",clienteVenta.getRazonSocial());
            parameters.put("rfc",clienteVenta.getRfc());
			String direccionValue = null;
			direccionValue = clienteVenta.getDireccionFacturacion();
			if(direccionValue == null){
				direccionValue = clienteVenta.getCalle()+
						", Num. Ext. "+(clienteVenta.getNumExterior()!=null&&clienteVenta.getNumExterior().length()>0?clienteVenta.getNumExterior():"S/N")+
						", Num. Int. "+(clienteVenta.getNumInterior()!=null&&clienteVenta.getNumInterior().length()>0?clienteVenta.getNumInterior():"S/N")+					
						", "+clienteVenta.getColonia()+
					", "+clienteVenta.getMunicipio()+
						", "+clienteVenta.getEstado()+
						", C.P. "+clienteVenta.getCp();
			}
            parameters.put("direccion" , direccionValue.toUpperCase());
			
            if(pedidoVenta.getComentarios()!=null && pedidoVenta.getComentarios().trim().length()>1){
				parameters.put("comentarios" ,pedidoVenta.getComentarios());
			} else{
				parameters.put("comentarios" ,null);
			}
			if(pedidoVenta.getCondicionesDePago()!=null && pedidoVenta.getCondicionesDePago().trim().length()>1){
				parameters.put("condiciones" ,pedidoVenta.getCondicionesDePago());
			} else{
				parameters.put("condiciones" ,"--NO IDENTIFICADO--");
			}
			if(pedidoVenta.getNumDeCuenta()!=null && pedidoVenta.getNumDeCuenta().trim().length()>1){
				parameters.put("noCuenta" ,pedidoVenta.getNumDeCuenta());
			} else{
				parameters.put("noCuenta" ,"--NO IDENTIFICADO--");
			}
			parameters.put("formaDePago" ,pedidoVenta.getFormaDePagoDescripcion().toUpperCase());
            parameters.put("metodoDePago",pedidoVenta.getMetodoDePagoDescripcion().toUpperCase());
            logger.info("->descuento:autorizadescuento?"+pedidoVenta.getAutorizaDescuento()+", "+pedidoVenta.getPorcentajeDescuentoCalculado()+"% + "+pedidoVenta.getPorcentajeDescuentoExtra());
            parameters.put("subtotal" , df.format(esf.getSubTotalBruto()));
            parameters.put("iva" ,df.format(esf.getImporteIVA()));
            parameters.put("descuento" ,df.format(esf.getImporteDescuentoAplicado()));
            parameters.put("recibimos" ,"?2");
			parameters.put("cambio" ,"?3");
			parameters.put("aprobacion" ,"?4");
			parameters.put("LeyendaFotter" ,"?5");
			
			
            parameters.put("total" ,df.format(esf.getTotal()));  
            JasperReport jasperReport = null;
            //String intDecParts[] = dfEnt.format(esf.getTotal()).split("\\.");
			File compiledReportPathFile=new File(compiledReportPath);
			/*
            if(! compiledReportPathFile.exists()){
				InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperCompileManager.compileReportToStream(jasperDesign,new FileOutputStream(compiledReportPathFile));
				logger.info("Ok, JasperReport compiled and saved 1st time, to:"+compiledReportPath);				
			}
			jasperReport = (JasperReport)JRLoader.loadObject(compiledReportPathFile);
			*/
			InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);			
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			logger.info("Ok, JasperReport loaded from:"+compiledReportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            logger.info("Ok, JasperPrint created.");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());
    
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setMetadataAuthor("PerfumeriaMarlen_SAA_10.3");						
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();
			logger.info("Ok, JasperExportManager executed");
			pdfBytes = baos.toByteArray();            
            logger.info("Ok, finished");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            //System.exit(1);
        }
		return pdfBytes;
    }


    public static byte[] generaPdfPfacturaPedidoVenta(EntradaSalidaQuickView pedidoVenta,Cfd cfdFactura,ArrayList<EntradaSalidaDetalleQuickView> esdList,Cliente clienteVenta,boolean fullPrint,String usuarioImr) {
		byte[] pdfBytes = null;
		try {
			String reportFileName;
			String reportPath;
			String compiledReportPath = "";
			String compiledReportClassPath = "";
            
			logger.info("Default Locale:"+Locale.getDefault());
            
			reportFileName = "facturaDesignNueva";
			
			reportPath = reportDesignDir + reportFileName + ".jrxml";
			compiledReportClassPath = reportDesignDir + reportFileName + ".jasper";
			compiledReportPath = "./"+ reportFileName + ".jasper";

            Collection<Map<String,?>> col = new ArrayList<Map<String,?>>();
            DecimalFormat    df    = new  DecimalFormat("$###,###,###,##0.00");
            DecimalFormat    dfEnt = new  DecimalFormat("###########0.00");
            DecimalFormat    dfBC  = new  DecimalFormat("0000000000000");
            logger.info("Ok, jrxml loaded:"+pedidoVenta.getAutorizaDescuento()+"?,"+pedidoVenta.getPorcentajeDescuentoCalculado()+"%+"+pedidoVenta.getPorcentajeDescuentoExtra()+"%");
            int n;
            EntradaSalidaFooter esf=new EntradaSalidaFooter();
			esf.calculaParaFacturaTotalesDesde(pedidoVenta, esdList);
			
            InputStream isXMLCFD = new ByteArrayInputStream(cfdFactura.getContenidoOriginalXml());
			HashMap cfdMap = ParseCFDXML.parseCFDXML(isXMLCFD);
			byte[] qrImage = QRImageGenerator.getQRImage(cfdMap.get("QR").toString());
			ByteArrayInputStream baosImageQR = new ByteArrayInputStream(qrImage);
			
			Image imageQR = ImageIO.read(baosImageQR);			
			double precioNoGrabado=0.0;
			for(EntradaSalidaDetalleQuickView pvd:esdList){
				Map<String,Object> vals = new HashMap<String,Object> ();
                
                n = pvd.getCantidad();
                
                vals.put("clave",pvd.getProductoCodigoBarras());
                vals.put("cantidad",n);
				vals.put("ue",pvd.getProductoUnidadEmpaque());
				vals.put("ta",Constants.getDescripcionTipoAlmacen(pvd.getApTipoAlmacen()).substring(0,3));
                vals.put("codigoBarras",pvd.getProductoCodigoBarras());                
                vals.put("descripcion",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion());
				vals.put("descripcionCont",pvd.getProductoNombre()+"/"+pvd.getProductoPresentacion()+" ("+pvd.getProductoContenido()+pvd.getProductoUnidadMedida()+")");
				precioNoGrabado=pvd.getPrecioVenta() / (1.0+LogicaFinaciera.getImpuestoIVA());
				vals.put("precio",df.format(pvd.getPrecioVenta() / (1.0+LogicaFinaciera.getImpuestoIVA())));
			    vals.put("precioNoGrabado",df.format(precioNoGrabado));
				
				vals.put("precioIVA",df.format(pvd.getPrecioVenta() * LogicaFinaciera.getImpuestoIVA()));
                vals.put("ppc"  ,pvd.getProductoUnidadesPorCaja());
				vals.put("ubic"  ,pvd.getApUbicacion());
				
                vals.put("importe",df.format(n*precioNoGrabado));
                vals.put("cont",pvd.getProductoContenido()+" "+pvd.getProductoUnidadMedida());
                vals.put("isEmptyRow",false);
                col.add(vals);
			}
						
            JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);
            logger.info("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            SimpleDateFormat sdf_cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            SimpleDateFormat sdf_f1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf_h1 = new SimpleDateFormat("HH:mm");
            
            Date fechaReporte = new Date();
            
            parameters.put("printImages" ,fullPrint);            
			parameters.put("usuario",pedidoVenta.getUsuarioNombreCompleto());
			parameters.put("usuarioImpr",usuarioImr);
			parameters.put("pedidoVentaId",pedidoVenta.getId());
			parameters.put("pedidoVentaEstado",pedidoVenta.getEstadoDescripcion());
			parameters.put("imageQR"    ,imageQR);			
			parameters.put("pedidoVentaId" ,String.valueOf(pedidoVenta.getId()));            
            parameters.put("noFactura" ,""+pedidoVenta.getCdfNumCFD());
            parameters.put("printImages" ,fullPrint);
            parameters.put("folioFiscal" ,cfdMap.get("UUID"));
            parameters.put("fechaYHoraCert" ,cfdMap.get("FechaTimbrado").toString().replace("T", " "));
			String lugarExpedicion = "TECAMAC, ESTADO DE MÉXICO";
			parameters.put("lugarExp" , lugarExpedicion);
            parameters.put("fechaYHoraExp" ,cfdMap.get("fecha").toString().replace("T", " "));
            parameters.put("noSerCertSAT" ,cfdMap.get("noCertificadoSAT"));
            
            parameters.put("cliente",clienteVenta.getRazonSocial());
            parameters.put("rfc",clienteVenta.getRfc());
			
			parameters.put("fechaHoraImpr",sdf_cdf.format(new Date()));
						
			String direccionValue = null;
			direccionValue = clienteVenta.getDireccionFacturacion();
			if(direccionValue == null){
				direccionValue = clienteVenta.getCalle()+
						", Num. Ext. "+(clienteVenta.getNumExterior()!=null&&clienteVenta.getNumExterior().length()>0?clienteVenta.getNumExterior():"S/N")+
						", Num. Int. "+(clienteVenta.getNumInterior()!=null&&clienteVenta.getNumInterior().length()>0?clienteVenta.getNumInterior():"S/N")+					
						", "+clienteVenta.getColonia()+
					", "+clienteVenta.getMunicipio()+
						", "+clienteVenta.getEstado()+
						", C.P. "+clienteVenta.getCp();
			}
            parameters.put("direccion" , direccionValue.toUpperCase());
			
            if(pedidoVenta.getComentarios()!=null && pedidoVenta.getComentarios().trim().length()>1){
				parameters.put("comentarios" ,pedidoVenta.getComentarios());
			} else{
				parameters.put("comentarios" ,null);
			}
			if(pedidoVenta.getCondicionesDePago()!=null && pedidoVenta.getCondicionesDePago().trim().length()>1){
				parameters.put("condiciones" ,pedidoVenta.getCondicionesDePago());
			} else{
				parameters.put("condiciones" ,"--NO IDENTIFICADO--");
			}
			if(pedidoVenta.getNumDeCuenta()!=null && pedidoVenta.getNumDeCuenta().trim().length()>1){
				parameters.put("noCuenta" ,pedidoVenta.getNumDeCuenta());
			} else{
				parameters.put("noCuenta" ,"--NO IDENTIFICADO--");
			}
			
			parameters.put("formaDePago" ,pedidoVenta.getFormaDePagoDescripcion().toUpperCase());
            parameters.put("metodoDePago",pedidoVenta.getMetodoDePagoDescripcion().toUpperCase());
            parameters.put("cadenaOriginalSAT"  ,cfdMap.get("cadenaOriginal"));            
            parameters.put("selloDigitalEmisor" ,cfdMap.get("sello"));
            parameters.put("selloDigitalSAT"    ,cfdMap.get("selloSAT"));			
            parameters.put("subtotal" , df.format(esf.getSubTotalNoGrabado()));
			if(esf.getDescuentoAplicado()>0){
				parameters.put("descuento" ,df.format(esf.getImporteDescuentoAplicado()));
			}else{
				parameters.put("descuento" ,null);
			}
			parameters.put("iva" ,df.format(esf.getImporteIVA()));
            
            parameters.put("total" ,df.format(esf.getTotal()));  
            
            String intDecParts[] = dfEnt.format(esf.getTotal()).split("\\.");
            
            String letrasParteEntera  = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[0])).trim();
            String letrasParteDecimal = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[1])).trim();
            parameters.put("importeLetra" ,"--("+(letrasParteEntera+" Pesos "+intDecParts[1]+"/100 M.N.").toUpperCase()+")--");
			
            JasperReport jasperReport = null;
            //String intDecParts[] = dfEnt.format(esf.getTotal()).split("\\.");
			File compiledReportPathFile=new File(compiledReportPath);
			/*
            if(! compiledReportPathFile.exists()){
				InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperCompileManager.compileReportToStream(jasperDesign,new FileOutputStream(compiledReportPathFile));
				logger.info("Ok, JasperReport compiled and saved 1st time, to:"+compiledReportPath);				
			}
			
			jasperReport = (JasperReport)JRLoader.loadObject(compiledReportPathFile);
			*/
			InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			logger.info("Ok, JasperReport loaded from:"+compiledReportPath);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            logger.info("Ok, JasperPrint created");
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "jasper_out_"+sdf.format(new Date())+".pdf");
            //logger.info("Ok, JasperExportManager executed");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			logger.info("Ok, JasperExportManager executed");
			pdfBytes = baos.toByteArray();
            //JasperPrintManager.printReport(jasperPrint, false);
            //logger.info("Ok, printed. executed");                        
            
            logger.info("Ok, finished");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            //System.exit(1);
        }
		return pdfBytes;
    }
}
