package com.pmarlen.rest.client;


import com.google.gson.*;
import com.pmarlen.wscommons.services.dto.PaqueteDeSincronizacionSucursalHija;
import com.pmarlen.wscommons.services.dto.Producto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;

public class SincronizacionSucursalServiceClient {
	
	private static String urlService = "http://localhost:8070/pmarlen-rest-services/rs/sicnonizacionSucursal/getAllOfSucursal";

	private static PaqueteDeSincronizacionSucursalHija getAllOfSucursal(){
		PaqueteDeSincronizacionSucursalHija paqueteSinc=null;
		try {
			long t0=System.currentTimeMillis();
			Client client = Client.create();
			Gson gson=new Gson();

			WebResource webResource = client.resource(urlService);
			long t1=System.currentTimeMillis();
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			long t2=System.currentTimeMillis();
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed HTTP error code :"
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);
			long t3=System.currentTimeMillis();
			//System.out.println("Output from Server .... \n");
			//System.out.println(output);
			
			paqueteSinc = gson.fromJson(output, PaqueteDeSincronizacionSucursalHija.class);
			long t4=System.currentTimeMillis();
			System.out.println("paqueteSinc="+paqueteSinc.toString());
			long t5=System.currentTimeMillis();
			System.out.println("  T = "+(t5-t0));
			System.out.println("+T1 = "+(t1-t0));
			System.out.println("+T2 = "+(t2-t1));
			System.out.println("+T3 = "+(t3-t2));
			System.out.println("+T4 = "+(t4-t3));
			System.out.println("+T5 = "+(t5-t4));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			return paqueteSinc;
		}
	}
	
	public static void main(String[] args) {
		PaqueteDeSincronizacionSucursalHija paqueteSinc = SincronizacionSucursalServiceClient.getAllOfSucursal();
	}

}
