package com.pmarlen.businesslogic.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 *
 * @author alfred
 */
@WebFault(name="AuthenticationException")
@XmlAccessorType( XmlAccessType.FIELD )
public class AuthenticationException extends Exception {

    /**
     * Creates a new instance of <code>AuthenticationException</code> without detail message.
     */
    public AuthenticationException() {
    }

    /**
     * Constructs an instance of <code>AuthenticationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AuthenticationException(String msg) {
        super(msg);
    }
}
