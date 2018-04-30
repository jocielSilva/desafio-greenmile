package com.greenmile.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.greenmile.comum.util.DataUtil;
import com.greenmile.model.Projeto;
import com.greenmile.model.Usuario;
import com.greenmile.repository.ProjetoRepository;
import com.greenmile.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootApplicationTests {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
    @Test
    public void testUsuarioRepository(){
    	Usuario testeUsuario = new Usuario();
    	testeUsuario.setName("Maria Oliveira");
    	testeUsuario.setEmail("maria@gmail.com");
    	testeUsuario.setSkill("Java");
    	testeUsuario.setTeam("Java");
        Usuario usuarioSave = usuarioRepository.save(testeUsuario);

        final List<Usuario> todos = usuarioRepository.findAll();
        Assert.assertEquals(usuarioRepository.count(), todos.size());

        final Usuario maria = usuarioRepository.findOne(usuarioSave.getId());
        Assert.assertEquals("Maria Oliveira", maria.getName());
    }
    
    @Test
    public void testProjetoRepository(){
    	Projeto testeProjeto = new Projeto();
    	testeProjeto.setName("Desafio GreenMile");
    	testeProjeto.setProjectManager("Jean");
    	testeProjeto.setProjectManagerEmail("jean@greenmile.com");
    	testeProjeto.setProjectManagerSkill("PMP");
    	testeProjeto.setStart(DataUtil.getSQLDate(new Date()));
    	testeProjeto.setEnd(DataUtil.getSQLDate(new Date()));
    	
    	Projeto projetoSave = projetoRepository.save(testeProjeto);
    	
        final List<Projeto> todos = projetoRepository.findAll();
        Assert.assertEquals(projetoRepository.count(), todos.size());

        final Projeto desafio = projetoRepository.findOne(projetoSave.getId());
        Assert.assertEquals("Desafio GreenMile", desafio.getName());
    }
    
    @Test
    public void testAssociacaoUsuarioProjeto() {
    	Projeto testeProjeto = new Projeto();
    	testeProjeto.setName("Desafio GreenMile");
    	testeProjeto.setProjectManager("Jean");
    	testeProjeto.setProjectManagerEmail("jean@greenmile.com");
    	testeProjeto.setProjectManagerSkill("PMP");
    	testeProjeto.setStart(DataUtil.getSQLDate(new Date()));
    	testeProjeto.setEnd(DataUtil.getSQLDate(new Date()));
		
		Usuario testeUsuario = new Usuario();
    	testeUsuario.setName("Maria Oliveira");
    	testeUsuario.setEmail("maria@gmail.com");
    	testeUsuario.setSkill("Java");
    	testeUsuario.setTeam("Java");
    	Usuario usuarioSave = usuarioRepository.save(testeUsuario);
    	
    	Projeto projetoSave = projetoRepository.save(testeProjeto);
    	projetoSave.setUsuarios(new ArrayList<Usuario>());
    	projetoSave.getUsuarios().add(usuarioSave);
    	projetoRepository.save(projetoSave);
		
		final Projeto desafio = projetoRepository.findOne(projetoSave.getId());
		Assert.assertEquals(projetoSave.getUsuarios().size(), desafio.getUsuarios().size());
    }
}