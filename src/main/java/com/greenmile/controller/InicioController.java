package com.greenmile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @class InicioController
 * 
 *        Classe responsável pelo controle da tela de Inicio.
 * 
 */
@Controller
@RequestMapping("/inicio")
public class InicioController {
	
		
	@GetMapping
	public ModelAndView iniciar() {
		ModelAndView modelAndView = new ModelAndView("inicio");		
		return modelAndView;
	}
}