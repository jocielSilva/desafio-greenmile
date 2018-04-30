package com.greenmile.batch.processor;

import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.greenmile.batch.to.ProjetoTO;
import com.greenmile.comum.util.DataUtil;
import com.greenmile.model.Projeto;
import com.greenmile.model.Usuario;
import com.greenmile.repository.ProjetoRepository;
import com.greenmile.repository.UsuarioRepository;

/**
 * @author Juciel Almeida
 * 
 * @class ProjetoItemProcessor
 * 
 *        Classe responsável por processar as informações do Batch.
 * 
 */
public class ProjetoItemProcessor implements ItemProcessor<ProjetoTO, ProjetoTO>{

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Recebe um TO com as informações do cvs e inseri nas tabelas
	 */
	@Override
	public ProjetoTO process(final ProjetoTO item) throws Exception {
		Usuario usuario = inserirUsuario(item);
		Projeto projeto = projetoRepository.consultarExistente(item.getName());
		
		/** Se projeto já existe somente adiciona o usuário ao projeto*/
		if (projeto != null) {
			if (projeto.getUsuarios() == null) {
				projeto.setUsuarios(new ArrayList<Usuario>());
			}
			projeto.getUsuarios().add(usuario);
			projetoRepository.save(projeto);
			return null;
		} else {
			inserirProjeto(item, usuario);
			return item;
		}
	}


	/**
	 * Inserir usuario 
	 * @param item
	 * @return
	 */
	private Usuario inserirUsuario(final ProjetoTO item) {
		Usuario usuario = new Usuario();
		usuario.setEmail(item.getEmployeeEmail());
		usuario.setName(item.getEmployeeName());
		usuario.setSkill(item.getEmployeeSkill());
		usuario.setTeam(item.getEmployeeTeam());
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	/**
	 * Inserir projeto
	 * @param item
	 * @param usuario
	 * @throws ParseException
	 */
	private void inserirProjeto(final ProjetoTO item, Usuario usuario) throws ParseException {
		Projeto projeto;
		projeto = new Projeto();
		projeto.setName(item.getName());
		projeto.setStart(DataUtil.getSQLDate(DataUtil.converterStringData(item.getStart())));
		projeto.setEnd(DataUtil.getSQLDate(DataUtil.converterStringData(item.getEnd())));
		projeto.setProjectManager(item.getProjectManager());
		projeto.setProjectManagerEmail(item.getProjectManagerEmail());
		projeto.setProjectManagerSkill(item.getEmployeeSkill());
		projeto.setUsuarios(new ArrayList<Usuario>());
		projeto.getUsuarios().add(usuario);
		projetoRepository.save(projeto);
	}
}
