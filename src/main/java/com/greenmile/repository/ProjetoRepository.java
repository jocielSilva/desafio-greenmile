package com.greenmile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greenmile.model.Projeto;

/**
 * @author Juciel Almeida
 * 
 * @class ProjetoRepository
 * 
 *        Interface responsável pelas consultas de projetos.
 * 
 */
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

	@Query("From Projeto p where p.name = ?1")
	Projeto consultarExistente(String name);

}
