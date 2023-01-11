package com.nomina.pagonomina.modelo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class FechaIngresoDTO {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngreso;

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
}
