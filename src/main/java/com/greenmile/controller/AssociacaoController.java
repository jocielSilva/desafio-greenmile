package com.greenmile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenmile.model.Projeto;
import com.greenmile.model.Usuario;
import com.greenmile.repository.ProjetoRepository;
import com.greenmile.repository.UsuarioRepository;

/**
 * @author Juciel Almeida
 * 
 * @class AssociacaoController
 * 
 *        Classe responsável pelo controle da tela de Associação de Usuários a Projetos.
 * 
 */
@Controller
@RequestMapping("/associacaoUsuarioProjeto")
public class AssociacaoController {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Projeto projetoSelecionado;
	
	private Usuario usuarioSelecionado;
	
	/**
	 * Instanciar os objetos quando necessário
	 * 
	 * @param modelView
	 * @return {@link ModelAndView}
	 */
	@GetMapping
	public ModelAndView iniciar(String modelView) {
		if(modelView == null || modelView.equals("")) {
			modelView = "associacaoUsuarioProjeto";
		}
		ModelAndView modelAndView = new ModelAndView(modelView);
		
		modelAndView.addObject("projetoSelecionado", getProjetoSelecionado());
		
		modelAndView.addObject("usuarioSelecionado", getUsuarioSelecionado());
		
		List<Projeto> lista = projetoRepository.findAll();
		modelAndView.addObject("projetos", lista);
		
		if(getProjetoSelecionado() != null && getProjetoSelecionado().getId() != null) {
			List<Usuario> listaUsuario = usuarioRepository.findAll();
			modelAndView.addObject("usuarios", listaUsuario);
		}
		
		return modelAndView;
	}
	
	/**
	 * Selecionar um projeto na lista
	 * 
	 * @param id
	 * @return {@link ModelAndView}
	 */
	@GetMapping("/selecionarProjeto/{projeto}")
    public ModelAndView selecionarProjeto(@PathVariable("projeto") Long id) {
		projetoSelecionado = projetoRepository.findOne(id);
		setProjetoSelecionado(projetoSelecionado);
		return iniciar("associacaoUsuarioProjeto");
	}
	
	/**
	 * 
	 *	Salvar ou atualizar o usuario no projeto selecionado
	 * 
	 * @param usuario
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return  {@link ModelAndView}
	 */
	@PostMapping("/salvarAssociacao")
    public ModelAndView salvarAssociacao(@ModelAttribute 
			@Valid Usuario usuario, 
			final BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes){
		
		Usuario usuarioEncontrado = usuarioRepository.findOne(usuario.getId());
		usuarioEncontrado.setFuncao(usuario.getFuncao());
		Integer posicao = recuperarPosicao(usuarioEncontrado.getId());
		if(posicao == null) {
			projetoSelecionado.getUsuarios().add(usuarioEncontrado);
		}else{
			projetoSelecionado.getUsuarios().set(posicao, usuarioEncontrado);
		}
		
		projetoRepository.save(projetoSelecionado);
		redirectAttributes.addFlashAttribute("msg_resultado", "Registro salvo com sucesso!");
		setProjetoSelecionado(projetoSelecionado);
		return iniciar("redirect:/associacaoUsuarioProjeto");
	}

	/**
	 * Recupera a posição se o usuario exitir na lista de usuarios do projeto selecionado
	 * @param id
	 * @return
	 */
	private Integer recuperarPosicao(Long id) {
		Integer posicao = 0;
		Boolean isExiste = Boolean.FALSE;
		for (Usuario usuario : projetoSelecionado.getUsuarios()) {
			if(usuario.getId().equals(id)) {
				isExiste = Boolean.TRUE;
				break;
			}
			posicao++;
		}
		
		return isExiste ? posicao : null;
	}

	public Projeto getProjetoSelecionado() {
		if (projetoSelecionado == null) {
			projetoSelecionado = new Projeto();
		}
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public Usuario getUsuarioSelecionado() {
		usuarioSelecionado = new Usuario();
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	
	
	
}
