
package com.pmarlen.backend.model.quickviews;

import com.pmarlen.backend.model.*;
import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Pedido_Venta_Estado.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2014/12/29 17:58
 */

public class EntradaSalidaEstadoQuickView extends EntradaSalidaEstado {

	private String usuarioNombreCompleto;
	
	private String estadoDescripcion;
	
    /** 
     * Default Constructor
     */
    public EntradaSalidaEstadoQuickView() {
    }

	/**
	 * @return the usuarioNombreCompleto
	 */
	public String getUsuarioNombreCompleto() {
		return usuarioNombreCompleto;
	}

	/**
	 * @param usuarioNombreCompleto the usuarioNombreCompleto to set
	 */
	public void setUsuarioNombreCompleto(String usuarioNombreCompleto) {
		this.usuarioNombreCompleto = usuarioNombreCompleto;
	}

	/**
	 * @return the estadoDescripcion
	 */
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}

	/**
	 * @param estadoDescripcion the estadoDescripcion to set
	 */
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	

}
