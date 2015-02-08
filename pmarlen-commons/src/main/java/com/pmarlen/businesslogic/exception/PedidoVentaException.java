package com.pmarlen.businesslogic.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.WebFault;

/**
 *
 * @author alfred
 */
@WebFault(name="PedidoVentaException")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoVentaException extends Exception {
    public transient static final int ERROR_REGISTRAR_CLIENTE     = 1;
    public transient static final int ERROR_REGISTRAR_EDO_PEDIDO  = 2;
    public transient static final int ERROR_REGISTRAR_DET_PEDIDO  = 4;
    public transient static final int ERROR_PERSISTENCIA          = 8;
    
    //private int errorType;
    
    /**
     * Creates a new instance of <code>AuthenticationException</code> without detail message.
     */
    public PedidoVentaException() {
    }

    /**
     * Constructs an instance of <code>AuthenticationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PedidoVentaException(int errorType,String msg) {
        super(msg+",errorType="+errorType);        
    }
}
