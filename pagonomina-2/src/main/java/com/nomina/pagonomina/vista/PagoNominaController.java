package com.nomina.pagonomina.vista;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.pagonomina.controlador.IFechaPagoController;
import com.nomina.pagonomina.modelo.FechaIngresoDTO;

import jakarta.validation.Valid;

@Controller
public class PagoNominaController {
	@Autowired
	IFechaPagoController fechaPagoController;
	
	@GetMapping({"/","/fechaingreso/formulario"})
	public String generarFormulario(Model model) {
		FechaIngresoDTO fechaIngresoDTO = new FechaIngresoDTO();
		model.addAttribute("fechaIngresoDTO", fechaIngresoDTO);
		return "index";
	}
	
	@PostMapping(value = "/fechaingreso/procesar")
	public String procesarFechaIngreso(@Valid @ModelAttribute("fechaIngresoDTO") FechaIngresoDTO fechaIngresoDTO, 
			Model model, BindingResult result) {
		if(fechaIngresoDTO.getFechaIngreso() == null) {
			result.rejectValue("fechaIngreso", "400", "La fecha de ingreso no puede ser null");
			return "index";
		}
		Date fechaPago = fechaPagoController.procesarFechaPago(fechaIngresoDTO);
		model.addAttribute("fechaIngresoDTO", fechaIngresoDTO);
		model.addAttribute("fechaPagoResultado", fechaPago);
		return "index";
	}
}
