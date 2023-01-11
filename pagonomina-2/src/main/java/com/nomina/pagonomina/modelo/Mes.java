package com.nomina.pagonomina.modelo;

public class Mes {
	private int numero;
	private String nombre;
	private int cantidadDias;
	private int[] diasNoTrabajo;
	private Mes proximoMes;
	private Mes anteriorMes;

	public Mes(int numero, String nombre, int cantidadDias, Mes anteriorMes, int ... diasNoTrabajo) {
		// TODO Auto-generated constructor stub
		this.nombre = nombre;
		this.cantidadDias = cantidadDias;
		this.diasNoTrabajo = diasNoTrabajo;
		this.anteriorMes = anteriorMes;
		this.numero = numero;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}

	public int[] getDiasNoTrabajo() {
		return diasNoTrabajo;
	}

	public void setDiasNoTrabajo(int[] diasNoTrabajo) {
		this.diasNoTrabajo = diasNoTrabajo;
	}

	public Mes getProximoMes() {
		return proximoMes;
	}

	public void setProximoMes(Mes proximoMes) {
		this.proximoMes = proximoMes;
	}

	public Mes getAnteriorMes() {
		return anteriorMes;
	}

	public void setAnteriorMes(Mes anteriorMes) {
		this.anteriorMes = anteriorMes;
	}

	
}
