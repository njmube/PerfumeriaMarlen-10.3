package com.pmarlen.model;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 */
public class Constants {

	public static final String PERFIL_ROOT = "root";
	public static final String PERFIL_PMARLENUSER = "pmarlenuser";
	public static final String PERFIL_ADMIN = "admin";
	public static final String PERFIL_FINANCES = "finances";
	public static final String PERFIL_STOCK = "stock";
	public static final String PERFIL_SALES = "sales";

	public static final int CREATE_ACTION = 1;
	public static final int UPDATE_ACTION = 2;
	
	public static final int ESTADO_CAPTURADO = 1;
	public static final int ESTADO_SINCRONIZADO = 2;
	public static final int ESTADO_VERIFICADO = 4;
	public static final int ESTADO_SURTIDO = 8;
	public static final int ESTADO_FACTURADO = 16;
	public static final int ESTADO_REMISIONADO = 32;
	public static final int ESTADO_ENVIADO = 64;
	public static final int ESTADO_ENTREGADO = 128;
	public static final int ESTADO_DEVUELTO = 256;
	public static final int ESTADO_VENDIDO_SUCURSAL = 512;
	public static final int ESTADO_FACTURADO_SUCURSAL = 1024;
	public static final int ESTADO_DEVUELTO_SUCURSAL = 2048;
	public static final int ESTADO_PAGADO = 4096;
	public static final int ESTADO_CANCELADO = 65536;
	//--------------------------------------------------------------------------
	public static final int TIPO_MOV_CREACION = 10;
	public static final int TIPO_MOV_ENTRADA_ALMACEN = 20;
	public static final int TIPO_MOV_ENTRADA_ALMACEN_DEV = 21;
	public static final int TIPO_MOV_SALIDA_ALMACEN = 30;
	public static final int TIPO_MOV_SALIDA_DEV = 31;
	public static final int TIPO_MOV_MODIFICACION_COSTO_O_PRECIO = 50;
	public static final int TIPO_MOV_MODIFICACION_CODIGOBARRAS = 51;
	public static final int TIPO_MOV_MODIFICACION_NOMBRE_DESCRIPCION = 52;
	
//--------------------------------------------------------------------------
	public static final int ALMACEN_PRINCIPAL = 1;
	public static final int ALMACEN_OPORTUNIDAD = 2;
	public static final int ALMACEN_REGALIAS = 3;
	//--------------------------------------------------------------------------
	public static final int DESCUENTO_0 = 0;
	public static final int DESCUENTO_2 = 2;
	public static final int DESCUENTO_5 = 5;
	public static final int DESCUENTO_7 = 7;
	public static final int DESCUENTO_10=10;
	
	public static final double IVA     = 0.16;
	public static final double MAS_IVA = 1.0 + IVA;
	
	public static final DecimalFormat dfMoneda     = new DecimalFormat("$ ###,###,###,##0.00");
	public static final DecimalFormat dfMonedaLong = new DecimalFormat("$ ###,###,###,##0.000000");
	
	private static final String VERSION_FILE_RESOURCE = "/com/tracktopell/util/version/file/Version.properties";
	
	private static Logger logger = LoggerFactory.getLogger(Constants.class);

	private static String version   = null;
	private static String buildTime = null;
	private static String gitSHA1   = null;
	private static String mavenBuildTimeStamp = null;

	private static Properties pro =null;

	public static Properties getPro() {
		if(pro == null){
			try {
				pro = new Properties();
				InputStream resourceAsStream = Constants.class.getResourceAsStream(VERSION_FILE_RESOURCE);
				if (resourceAsStream == null) {
					throw new IOException("The resource:" + VERSION_FILE_RESOURCE + " doesn't exist!");
				}
				pro.load(resourceAsStream);
				logger.info("->"+VERSION_FILE_RESOURCE+"=" + pro);				
			} catch (IOException ex) {
				logger.error("Can't load Version properties:", ex);
				throw new IllegalStateException("Can't load Version properties:"+VERSION_FILE_RESOURCE);
			}
		}
		return pro;
	}
	
	
	public static String getServerVersion() {
		if (version == null) {
			version   = getPro().getProperty("version.parent");			
		}
		return version;
	}

	public static String getBuildTimestamp() {
		if (buildTime == null) {
			buildTime = getPro().getProperty("version.build.timestamp");
		}
		return buildTime;
	}

	public static String getGitSHA1() {
		if (gitSHA1 == null) {
			gitSHA1 = getPro().getProperty("git-sha-1");
		}
		return gitSHA1;
	}	
	
	public static String getMD5Encrypted(String e) {

		MessageDigest mdEnc = null; // Encryption algorithm
		try {
			mdEnc = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
		mdEnc.update(e.getBytes(), 0, e.length());
		return (new BigInteger(1, mdEnc.digest())).toString(16);
	}

	public static String getDescripcionTipoAlmacen(int tipoAlmacen) {
		if (tipoAlmacen == ALMACEN_PRINCIPAL) {
			return "PRINCIPAL";
		} else if (tipoAlmacen == ALMACEN_OPORTUNIDAD) {
			return "OPORTUNIDAD";
		} else if (tipoAlmacen == ALMACEN_REGALIAS) {
			return "REGALIAS";
		} else {
			return null;
		}
	}
	
	public static final LinkedHashMap<Integer,String> descuentos = new LinkedHashMap<Integer,String>();
	
	static {
		descuentos.put(DESCUENTO_0,  "SIN DESCUENTO");
		descuentos.put(DESCUENTO_2,  "2 %");
		descuentos.put(DESCUENTO_5,  "5 %");
		descuentos.put(DESCUENTO_7,  "7 %");
		descuentos.put(DESCUENTO_10, "10 %");		
	}
	
	public static final LinkedHashMap<Integer,String> tipoAlmacen = new LinkedHashMap<Integer,String>();
	static {
		tipoAlmacen.put(ALMACEN_PRINCIPAL  ,  "1RA LINEA");
		tipoAlmacen.put(ALMACEN_OPORTUNIDAD,  "OPORTUNIDAD");
		tipoAlmacen.put(ALMACEN_REGALIAS   ,  "REGALIAS");
	}
	
	public static final DecimalFormat dfCurrency     = new DecimalFormat("$ ###,###,###,##0.00");
	public static final DecimalFormat dfDecimal      = new DecimalFormat("###########0.00");
	public static final DecimalFormat dfLongDecimal  = new DecimalFormat("###########0.000000");
	public static final SimpleDateFormat sdfLogDate    = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final SimpleDateFormat sdfThinDate  = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getImporteDesglosado(double f){
		StringBuffer s = new StringBuffer();
				
		double importe = f / MAS_IVA;
		double iva     = f - importe;
		
		s.append(dfMoneda.format(importe)).append(" + ").append(dfMoneda.format(iva));
		
		return s.toString();
	
	}

	public static String getImporteMoneda(double f) {
		return dfMoneda.format(f);
	}
	
}
