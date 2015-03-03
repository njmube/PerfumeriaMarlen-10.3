package com.pmarlen.jsf;

import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.model.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="productosFunMB")
@SessionScoped
public class ProductosFunMB {
	int tipoAlmacen;
	private static List<SelectItem> tipoAlmacenList;
	private transient static Logger logger = Logger.getLogger(ProductosFunMB.class.getName());
	private ArrayList<Producto> todosLosProductos = null;
	@PostConstruct
    public void init() {
		System.out.println("->ProductosFunMB: init.");
		tipoAlmacen = 1;
		try {
			todosLosProductos = ProductoDAO.getInstance().findAll();		
		}catch(DAOException de){
			todosLosProductos = new ArrayList<Producto>();
			logger.severe(de.getMessage());
		}
    }
	
	public int getTipoAlmacen() {
		return tipoAlmacen;
	}

	
	public void setTipoAlmacen(int tipoAlmacen) {
		logger.info("->setTipoAlmacen:tipoAlmacen="+tipoAlmacen);
		this.tipoAlmacen = tipoAlmacen;
	}
	
	public void onTipoAlmacenChange() {
		logger.info("->onTipoAlmacenChange:tipoAlmacen="+tipoAlmacen);	
		
	}

	public List<SelectItem> getTipoAlmacenList() {
		if (tipoAlmacenList == null) {

			tipoAlmacenList = new ArrayList<SelectItem>();
			Iterator<Integer> almacentesIt = Constants.tipoAlmacen.keySet().iterator();
			while(almacentesIt.hasNext()){
				Integer almacenId = almacentesIt.next();
				tipoAlmacenList.add(new SelectItem(almacenId, Constants.tipoAlmacen.get(almacenId)));
			}
		}
		return tipoAlmacenList;
	}
	
	List<String> lineas;
	List<String> industrias;
	List<String> marcas;

	String industria;
	String linea;
	String marca;
	
	int modo; // 1: INDUSTRIA-MARCA , 2: LINEA-MARCA
	
	public List<String> getLineas() {
		if(lineas == null){
			TreeSet<String> lineasSet= new TreeSet<String>();
			lineas =  new ArrayList<String>();
			for(Producto p: todosLosProductos){
				lineasSet.add(p.getLinea());
			}
			for(String ls:lineasSet){
				lineas.add(ls);
			}
		}
		return lineas;
	}
	
	public List<String> getIndustrias() {
		if(industrias == null){
			TreeSet<String> industriasSet= new TreeSet<String>();
			industrias =  new ArrayList<String>();
			for(Producto p: todosLosProductos){
				industriasSet.add(p.getIndustria());
			}
			for(String is:industriasSet){
				industrias.add(is);
			}
		}
		return industrias;
	}
	
	public List<String> getMarcas() {
		if(marcas == null){
			TreeSet<String> marcasSet= new TreeSet<String>();
			marcas =  new ArrayList<String>();
			for(Producto p: todosLosProductos){
				marcasSet.add(p.getMarca());
			}
			for(String ms:marcasSet){
				marcas.add(ms);
			}
		}
		return marcas;
	}

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}
	
	
	
}
