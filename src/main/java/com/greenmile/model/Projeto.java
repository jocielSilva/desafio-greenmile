package com.greenmile.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 
 * @author Juciel Almeida
 *
 * @class Projeto
 * 
 *        Entidade de projetos
 * 
 */
@Entity
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "start")
	private Date start;

	@Column(name = "end")
	private Date end;

	@Column(name = "PROJECTMANAGER")
	private String projectManager;

	@Column(name = "PROJECTMANAGEREMAIL")
	private String projectManagerEmail;

	@Column(name = "PROJECTMANAGERSKILL")
	private String projectManagerSkill;
	
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="PROJETO_USUARIO",
	joinColumns={@JoinColumn(name="USUARIO_ID")},
	inverseJoinColumns={@JoinColumn(name="PROJETO_ID")})
	private List<Usuario> usuarios;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProjectManagerEmail() {
		return projectManagerEmail;
	}

	public void setProjectManagerEmail(String projectManagerEmail) {
		this.projectManagerEmail = projectManagerEmail;
	}

	public String getProjectManagerSkill() {
		return projectManagerSkill;
	}

	public void setProjectManagerSkill(String projectManagerSkill) {
		this.projectManagerSkill = projectManagerSkill;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}