package com.pmarlen.rest.client;


import com.google.gson.*;
import com.pmarlen.backend.model.quickviews.SyncDTOPackage;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;

public class SyncDTOPackageClient {
	
	private static String urlService = "http://localhost:8070/pmarlen-rest-services/rest/syncservice/syncdtopackage/1";
	
	private static String uriService = "/pmarlen-rest-services/rest/syncservice/syncdtopackage/1";

	private static SyncDTOPackage getAllOfSucursal(String hostPort){
		SyncDTOPackage paqueteSinc=null;
		try {
			long t0=System.currentTimeMillis();
			Client client = Client.create();
			Gson gson=new Gson();
			System.out.println("...creating WebResource");
			WebResource webResource = client.resource(hostPort+uriService);
			long t1=System.currentTimeMillis();
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			System.out.println("...get response");
			long t2=System.currentTimeMillis();
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed HTTP error code :"
						+ response.getStatus());
			}
			System.out.println("OK, not error, trying get JSON response");

			String output = response.getEntity(String.class);
			long t3=System.currentTimeMillis();
			System.out.println("Output from Server:output.length="+output.length());
			
			paqueteSinc = gson.fromJson(output, SyncDTOPackage.class);
			long t4=System.currentTimeMillis();
			
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
		SyncDTOPackage paqueteSinc = SyncDTOPackageClient.getAllOfSucursal(args[0]);
		System.out.println("-->> paqueteSinc{"+paqueteSinc+"}");
	}

}
