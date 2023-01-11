package com.nomina.pagonomina.controlador;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.nomina.pagonomina.modelo.FechaIngresoDTO;
import com.nomina.pagonomina.modelo.Mes;
import com.nomina.pagonomina.repository.IMesRepository;

@Controller
public class FechaPagoController implements IFechaPagoController {
	@Autowired
	IMesRepository mesRepository;
	@Override
	public Date procesarFechaPago(FechaIngresoDTO fechaIngresoDTO) {
		int numeroMes = new Calendar.Builder().setInstant(fechaIngresoDTO.getFechaIngreso()).build().get(Calendar.MONTH);
		// TODO Auto-generated method stub
		Mes mes = mesRepository.encontrarTodos().stream().filter(m -> {
			return m.getNumero() == numeroMes;
		}).findFirst().orElseThrow();
		
		Calendar fechaPago = encontrarFechaPago(mes, fechaIngresoDTO);
		return fechaPago.getTime();
	}

	private Calendar encontrarFechaPago(Mes mes, FechaIngresoDTO fechaIngresoDTO) {
		Calendar fechaIngreso = new Calendar.Builder().setInstant(fechaIngresoDTO.getFechaIngreso()).build();
		if(!Arrays.stream(mes.getDiasNoTrabajo()).filter(d -> d == fechaIngreso.get(Calendar.DAY_OF_MONTH)).findFirst().isPresent())
			return fechaIngreso;
		Calendar fPHaciaAdelante = busquedaHaciaAdelante(mes, fechaIngreso);
		Calendar fPHaciaAtras = busquedaHaciaAtras(mes, fechaIngreso);
		Calendar fechaMasCercana = calcularFechaMasCercana(fechaIngreso, fPHaciaAtras, fPHaciaAdelante);
		return fechaMasCercana;
	}
	
	private Calendar calcularFechaMasCercana(Calendar fechaIngreso, Calendar fPHaciaAtras, Calendar fPHaciaAdelante) {
		int diasDifHaciaAdelante = busquedaHaciaAdelante(fechaIngreso, fPHaciaAdelante);
		int diasDifHaciaAtras = busquedaHaciaAtras(fechaIngreso, fPHaciaAtras);
		return diasDifHaciaAdelante > diasDifHaciaAtras ? fPHaciaAtras : fPHaciaAdelante;
	}

	private int busquedaHaciaAtras(Calendar fechaIngreso, Calendar fPHaciaAtras) {
		int cantidadDias = 0;
		if(fechaIngreso.get(Calendar.MONTH) == fPHaciaAtras.get(Calendar.MONTH)) {
			int diaInicio = fechaIngreso.get(Calendar.DAY_OF_MONTH);
			while(diaInicio >= fPHaciaAtras.get(Calendar.DAY_OF_MONTH)) {
				diaInicio -= 1;
				cantidadDias +=1; 
			}
		} else {
			int diaInicio = fechaIngreso.get(Calendar.DAY_OF_MONTH);
			while(diaInicio >= 1) {
				diaInicio -= 1;
				cantidadDias +=1;
			}
			Calendar fInicial = new Calendar.Builder().set(Calendar.DAY_OF_MONTH, 31).set(Calendar.MONTH, fechaIngreso.get(Calendar.MONTH) - 1).build();
			if(fechaIngreso.get(Calendar.MONTH) == 0) {
				fInicial.set(Calendar.YEAR, fechaIngreso.get(Calendar.YEAR) - 1);
			}
			cantidadDias += busquedaHaciaAdelante(fInicial, fPHaciaAtras);
		}
		return cantidadDias;
	}

	private int busquedaHaciaAdelante(Calendar fechaIngreso, Calendar fPHaciaAdelante) {
		// TODO Auto-generated method stub
		int cantidadDias = 0;
		if(fechaIngreso.get(Calendar.MONTH) == fPHaciaAdelante.get(Calendar.MONTH)) {
			int diaInicio = fechaIngreso.get(Calendar.DAY_OF_MONTH);
			while(diaInicio <= fPHaciaAdelante.get(Calendar.DAY_OF_MONTH)) {
				diaInicio += 1;
				cantidadDias +=1; 
			}
		} else {
			int diaInicio = fechaIngreso.get(Calendar.DAY_OF_MONTH);
			Mes mes = mesRepository.encontrarTodos().stream().filter(m -> { return m.getNumero() == fechaIngreso.get(Calendar.DAY_OF_MONTH); })
				.findFirst().get();
			while(diaInicio <= mes.getCantidadDias()) {
				diaInicio += 1;
				cantidadDias +=1;
			}
			Calendar fInicial = new Calendar.Builder().set(Calendar.DAY_OF_MONTH, 1).set(Calendar.MONTH, fechaIngreso.get(Calendar.MONTH) + 1).build();
			if(fechaIngreso.get(Calendar.MONTH) == 11) {
				fInicial.set(Calendar.YEAR, fechaIngreso.get(Calendar.YEAR) + 1);
			}
			cantidadDias += busquedaHaciaAdelante(fInicial, fPHaciaAdelante);
		}
		return cantidadDias;
	}

	private Calendar busquedaHaciaAdelante(Mes mes, Calendar fechaIngreso) {
		Calendar fechaIngresoEncontrada = null;
		for(int i = fechaIngreso.get(Calendar.DAY_OF_MONTH); i <= mes.getCantidadDias(); i++) {
			final int iFinal = i;
			if(Arrays.stream(mes.getDiasNoTrabajo()).filter(d -> d == iFinal).findFirst().isPresent())
				continue;
			else {
				fechaIngresoEncontrada = (Calendar) fechaIngreso.clone();
				fechaIngresoEncontrada.set(Calendar.DAY_OF_MONTH, i);
				break;
			}		
		}
		if(fechaIngresoEncontrada == null) {
			Calendar fechaIngresoProxima = (Calendar) fechaIngreso.clone();
			fechaIngresoProxima.set(Calendar.DAY_OF_MONTH, 1);
			fechaIngresoProxima.set(Calendar.MONTH, fechaIngreso.get(Calendar.MONTH) + 1);
			if(fechaIngreso.get(Calendar.MONTH) == 11) {
				fechaIngresoProxima.set(Calendar.YEAR, fechaIngreso.get(Calendar.YEAR) + 1);
			}
			fechaIngresoEncontrada = busquedaHaciaAdelante(mes.getProximoMes(), fechaIngresoProxima);
		}
		return fechaIngresoEncontrada;
	}
	
	private Calendar busquedaHaciaAtras(Mes mes, Calendar fechaIngreso) {
		Calendar fechaIngresoEncontrada = null;
		for(int i = fechaIngreso.get(Calendar.DAY_OF_MONTH); i >= 1; i--) {
			final int iFinal = i;
			if(Arrays.stream(mes.getDiasNoTrabajo()).filter(d -> d == iFinal).findFirst().isPresent())
				continue;
			else {
				fechaIngresoEncontrada = (Calendar) fechaIngreso.clone();
				fechaIngresoEncontrada.set(Calendar.DAY_OF_MONTH, i);
				break;
			}		
		}
		if(fechaIngresoEncontrada == null) {
			Calendar fechaIngresoAnterior = (Calendar) fechaIngreso.clone();
			fechaIngresoAnterior.set(Calendar.DAY_OF_MONTH, 31);
			fechaIngresoAnterior.set(Calendar.MONTH, fechaIngreso.get(Calendar.MONTH) - 1);
			if(fechaIngreso.get(Calendar.MONTH) == 1) {
				fechaIngresoAnterior.set(Calendar.YEAR, fechaIngreso.get(Calendar.YEAR) - 1);
			}
			fechaIngresoEncontrada = busquedaHaciaAtras(mes.getAnteriorMes(), fechaIngresoAnterior);
		}
		return fechaIngresoEncontrada;
	}
}
