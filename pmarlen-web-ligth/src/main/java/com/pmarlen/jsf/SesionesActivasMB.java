package com.pmarlen.jsf;

import com.pmarlen.backend.dao.ClienteDAO;
import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.dao.FormaDePagoDAO;
import com.pmarlen.backend.dao.MetodoDePagoDAO;
import com.pmarlen.backend.dao.ProductoDAO;
import com.pmarlen.backend.model.Cliente;
import com.pmarlen.backend.model.EntradaSalida;
import com.pmarlen.backend.model.EntradaSalidaDetalle;
import com.pmarlen.backend.model.FormaDePago;
import com.pmarlen.backend.model.MetodoDePago;
import com.pmarlen.backend.model.Producto;
import com.pmarlen.backend.model.quickviews.EntradaSalidaDetalleQuickView;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.model.Constants;
import com.pmarlen.web.servlet.ContextAndSessionListener;
import com.pmarlen.web.servlet.SessionInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.ReorderEvent;

@ManagedBean(name="sesionesActivasMB")
@SessionScoped
public class SesionesActivasMB {
	private transient static Logger logger = Logger.getLogger("sesionesActivasMB");
	private int noPedidoBuscar;
	
	@PostConstruct
	public void init(){
		logger.info("->init:");	
	}
	
	public List<SessionInfo> getSesionesActivas(){
		List<SessionInfo>  siList = new ArrayList<SessionInfo> ();
	
		Enumeration<SessionInfo> elements = ContextAndSessionListener.sessionInfoHT.elements();
		while(elements.hasMoreElements()){
			SessionInfo si=elements.nextElement();
			siList.add(si);
		}
		
		return siList;
	}

	public List<String> getSesionesCajaActivas(){
		List<String>  sesionesCajaActivas = new ArrayList<String> ();
	
		Enumeration<String> elements = ContextAndSessionListener.cajaSessionInfoHT.elements();
		while(elements.hasMoreElements()){			
			sesionesCajaActivas.add(elements.nextElement());
		}
		
		return sesionesCajaActivas;
	}

}
