package com.pmarlen.rest.client;


import com.google.gson.*;
import com.pmarlen.wscommons.services.dto.Producto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;

public class ProductoServiceClient {
	
	private static String urlService = "http://localhost:8070/pmarlen-rest-services/rs/producto/getAll";

	private static List<com.pmarlen.model.beans.Producto> getProductoList(){
		List<com.pmarlen.model.beans.Producto> productoList=new ArrayList<com.pmarlen.model.beans.Producto>();
		try {

			Client client = Client.create();
			Gson gson=new Gson();

			WebResource webResource = client.resource(urlService);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			//System.out.println("Output from Server .... \n");
			//System.out.println(output);

			Producto[] productoRead = new Producto[1];
			productoRead = gson.fromJson(output, productoRead.getClass());

			// System.out.println("productoRead:");
			for(Producto pX: productoRead){
		
				productoList.add(pX.buildJpaEntity());
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			return productoList;
		}
	}
	
	public static void main(String[] args) {
		List<com.pmarlen.model.beans.Producto> productoList = getProductoList();
		for(com.pmarlen.model.beans.Producto pX: productoList){
			System.out.println("\t->JPA Producto:"+pX.getId()+", ["+pX.getCodigoBarras()+"]"+pX.getNombre()+", "+pX.getPresentacion());
		}
	}

}
