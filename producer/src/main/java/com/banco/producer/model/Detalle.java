package com.banco.producer.model;

public class Detalle {
	
	private String nombreBeneficiario;
	private String tipoTransferencia;
	private String descripcion;
	private Referencias referencias; 
	
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
    public void setNombreBeneficiario(String nombreBeneficiatio) {
    	this.nombreBeneficiario = nombreBeneficiario;
    }
	public String getTipoTransferencia() {
		return tipoTransferencia; 
	}
	
	public void setTipoTranferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}
	public String getDescripcion() {
		return descripcion; 
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Referencias getReferencias() {
		return referencias;
	}
	public void setReferancias(Referencias referencias) {
		this.referencias = referencias; 
	}

}
