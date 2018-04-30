package com.greenmile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenmile.model.Usuario;


/**
 * @author Juciel Almeida
 * 
 * @class UsuarioRepository
 * 
 *        Interface responsável pelas consultas de usuarios.
 * 
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
