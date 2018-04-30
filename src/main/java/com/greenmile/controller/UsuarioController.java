package com.greenmile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenmile.model.Usuario;
import com.greenmile.repository.UsuarioRepository;

/**
 * @author Juciel Almeida
 * 
 * @class UsuarioController
 * 
 *        Classe responsável pelo controle da tela de Usuarios.
 * 
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ModelAndView listar(Model model) {
		model.addAttribute("usuario", new Usuario());
		
		List<Usuario> lista = usuarioRepository.findAll();
		
		ModelAndView modelAndView = new ModelAndView("usuarios");		
		modelAndView.addObject("usuarios", lista);
		
		return modelAndView;
	}
	
	/***
	 * SALVA UM NOVO USUÁRIO NO SISTEMA
	 * @param usuario
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/salvarUsuario")
	public ModelAndView salvarUsuario(@ModelAttribute 
								@Valid Usuario usuario, 
								final BindingResult result,
								Model model,
								RedirectAttributes redirectAttributes){
 
		/*SALVANDO UM NOVO REGISTRO*/
     	usuarioRepository.save(usuario);
 
		ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
 
		/*PASSANDO O ATRIBUTO PARA O ModelAndView QUE VAI REALIZAR 
		 * O REDIRECIONAMENTO COM A MENSAGEM DE SUCESSO*/
		redirectAttributes.addFlashAttribute("msg_sucesso", "Registro salvo com sucesso!");
 
		/*REDIRECIONANDO PARA UM NOVO CADASTRO*/
		return modelAndView;
	}
	
}
