package com.nomina.pagonomina.controlador;

import java.util.Date;

import com.nomina.pagonomina.modelo.FechaIngresoDTO;

public interface IFechaPagoController {

	Date procesarFechaPago(FechaIngresoDTO fechaPagoDTO);

}
