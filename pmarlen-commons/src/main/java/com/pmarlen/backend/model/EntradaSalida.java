
package com.pmarlen.backend.model;

import java.io.Serializable;
import java.util.Set;
import java.util.MissingFormatArgumentException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Class for mapping DTO Entity of Table Entrada_Salida.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @date 2015/03/15 12:43
 */

public class EntradaSalida implements java.io.Serializable {
    private static final long serialVersionUID = 1021019426;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * tipo mov
    */
    private int tipoMov;
    
    /**
    * sucursal id
    */
    private int sucursalId;
    
    /**
    * estado id
    */
    private int estadoId;
    
    /**
    * fecha creo
    */
    private java.sql.Timestamp fechaCreo;
    
    /**
    * usuario email creo
    */
    private String usuarioEmailCreo;
    
    /**
    * cliente id
    */
    private Integer clienteId;
    
    /**
    * forma de pago id
    */
    private Integer formaDePagoId;
    
    /**
    * metodo de pago id
    */
    private Integer metodoDePagoId;
    
    /**
    * factor iva
    */
    private double factorIva;
    
    /**
    * comentarios
    */
    private String comentarios;
    
    /**
    * cfd id
    */
    private Integer cfdId;
    
    /**
    * numero ticket
    */
    private String numeroTicket;
    
    /**
    * caja
    */
    private Integer caja;
    
    /**
    * importe recibido
    */
    private Double importeRecibido;
    
    /**
    * aprobacion visa mastercard
    */
    private String aprobacionVisaMastercard;
    
    /**
    * porcentaje descuento calculado
    */
    private Integer porcentajeDescuentoCalculado;
    
    /**
    * porcentaje descuento extra
    */
    private Integer porcentajeDescuentoExtra;
    
    /**
    * condiciones de pago
    */
    private String condicionesDePago;
    
    /**
    * num de cuenta
    */
    private String numDeCuenta;
    
    /**
    * autoriza descuento
    */
    private Integer autorizaDescuento;

    /** 
     * Default Constructor
     */
    public EntradaSalida() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EntradaSalida( Integer id ) {
        this.id 	= 	id;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    public int getTipoMov() {
        return this.tipoMov;
    }

    public void setTipoMov(int v) {
        this.tipoMov = v;
    }

    public int getSucursalId() {
        return this.sucursalId;
    }

    public void setSucursalId(int v) {
        this.sucursalId = v;
    }

    public int getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(int v) {
        this.estadoId = v;
    }

    public java.sql.Timestamp getFechaCreo() {
        return this.fechaCreo;
    }

    public void setFechaCreo(java.sql.Timestamp v) {
        this.fechaCreo = v;
    }

    public String getUsuarioEmailCreo() {
        return this.usuarioEmailCreo;
    }

    public void setUsuarioEmailCreo(String v) {
        this.usuarioEmailCreo = v;
    }

    public Integer getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(Integer v) {
        this.clienteId = v;
    }

    public Integer getFormaDePagoId() {
        return this.formaDePagoId;
    }

    public void setFormaDePagoId(Integer v) {
        this.formaDePagoId = v;
    }

    public Integer getMetodoDePagoId() {
        return this.metodoDePagoId;
    }

    public void setMetodoDePagoId(Integer v) {
        this.metodoDePagoId = v;
    }

    public double getFactorIva() {
        return this.factorIva;
    }

    public void setFactorIva(double v) {
        this.factorIva = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public Integer getCfdId() {
        return this.cfdId;
    }

    public void setCfdId(Integer v) {
        this.cfdId = v;
    }

    public String getNumeroTicket() {
        return this.numeroTicket;
    }

    public void setNumeroTicket(String v) {
        this.numeroTicket = v;
    }

    public Integer getCaja() {
        return this.caja;
    }

    public void setCaja(Integer v) {
        this.caja = v;
    }

    public Double getImporteRecibido() {
        return this.importeRecibido;
    }

    public void setImporteRecibido(Double v) {
        this.importeRecibido = v;
    }

    public String getAprobacionVisaMastercard() {
        return this.aprobacionVisaMastercard;
    }

    public void setAprobacionVisaMastercard(String v) {
        this.aprobacionVisaMastercard = v;
    }

    public Integer getPorcentajeDescuentoCalculado() {
        return this.porcentajeDescuentoCalculado;
    }

    public void setPorcentajeDescuentoCalculado(Integer v) {
        this.porcentajeDescuentoCalculado = v;
    }

    public Integer getPorcentajeDescuentoExtra() {
        return this.porcentajeDescuentoExtra;
    }

    public void setPorcentajeDescuentoExtra(Integer v) {
        this.porcentajeDescuentoExtra = v;
    }

    public String getCondicionesDePago() {
        return this.condicionesDePago;
    }

    public void setCondicionesDePago(String v) {
        this.condicionesDePago = v;
    }

    public String getNumDeCuenta() {
        return this.numDeCuenta;
    }

    public void setNumDeCuenta(String v) {
        this.numDeCuenta = v;
    }

    public Integer getAutorizaDescuento() {
        return this.autorizaDescuento;
    }

    public void setAutorizaDescuento(Integer v) {
        this.autorizaDescuento = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
		// bug ?
        hash = ( (id != null ? id.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EntradaSalida)) {
            return false;
        }

    	EntradaSalida other = (EntradaSalida ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.backend.model.EntradaSalida[id = "+id+ "]";
    }

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdddHHmmss");
	private static final DecimalFormat    df  = new DecimalFormat("0.000000");
	private static final DecimalFormat    cur = new DecimalFormat("0.00");

	public String printPlainSeparated(String s){
		String ser=null;
		StringBuffer sb= new StringBuffer();

		
		// Integer
		sb.append(this.id);
		sb.append(s);
		// int
		sb.append(this.tipoMov);
		sb.append(s);
		// int
		sb.append(this.sucursalId);
		sb.append(s);
		// int
		sb.append(this.estadoId);
		sb.append(s);
		// java.sql.Timestamp
		sb.append(this.fechaCreo==null?"null":sdf.format(this.fechaCreo));
		sb.append(s);
		// String
		sb.append(this.usuarioEmailCreo);
		sb.append(s);
		// Integer
		sb.append(this.clienteId);
		sb.append(s);
		// Integer
		sb.append(this.formaDePagoId);
		sb.append(s);
		// Integer
		sb.append(this.metodoDePagoId);
		sb.append(s);
		// double
		sb.append( df.format(this.factorIva));
		sb.append(s);
		// String
		sb.append(this.comentarios);
		sb.append(s);
		// Integer
		sb.append(this.cfdId);
		sb.append(s);
		// String
		sb.append(this.numeroTicket);
		sb.append(s);
		// Integer
		sb.append(this.caja);
		sb.append(s);
		// Double
		sb.append( df.format(this.importeRecibido));
		sb.append(s);
		// String
		sb.append(this.aprobacionVisaMastercard);
		sb.append(s);
		// Integer
		sb.append(this.porcentajeDescuentoCalculado);
		sb.append(s);
		// Integer
		sb.append(this.porcentajeDescuentoExtra);
		sb.append(s);
		// String
		sb.append(this.condicionesDePago);
		sb.append(s);
		// String
		sb.append(this.numDeCuenta);
		sb.append(s);
		// Integer
		sb.append(this.autorizaDescuento);

		return ser;
	}

	public void scanFrom(String src, String s) throws MissingFormatArgumentException{
		String srcSpplited[] = src.split(s);
		int nf=0;
		try {			
			
			// Integer
			this.id =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.tipoMov =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.sucursalId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// int
			this.estadoId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// java.sql.Timestamp
			this.fechaCreo =  srcSpplited[nf].equals("null")?null:new java.sql.Timestamp(sdf.parse(srcSpplited[nf]).getTime());
			nf++;
			// String
			this.usuarioEmailCreo = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.clienteId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Integer
			this.formaDePagoId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Integer
			this.metodoDePagoId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// double
			this.factorIva =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.comentarios = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.cfdId =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.numeroTicket = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.caja =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Double
			this.importeRecibido =  df.parse(srcSpplited[nf]).doubleValue();
			nf++;
			// String
			this.aprobacionVisaMastercard = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.porcentajeDescuentoCalculado =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// Integer
			this.porcentajeDescuentoExtra =  Integer.parseInt(srcSpplited[nf]);
			nf++;
			// String
			this.condicionesDePago = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// String
			this.numDeCuenta = srcSpplited[nf].equals("null")?null:srcSpplited[nf];
			nf++;
			// Integer
			this.autorizaDescuento =  Integer.parseInt(srcSpplited[nf]);
			nf++;

		}catch(Exception e){
			throw new MissingFormatArgumentException("Exception scanning for["+nf+"] from string ->"+srcSpplited[nf]+"<-");
		}
	}

}
