package com.greenmile.batch.to;

import java.io.Serializable;

/**
 * @author Juciel Almeida
 * 
 * @class ProjetoTO
 * 
 *        Classe TO responsável pelas informações do CVS de projeto.
 * 
 */
public class ProjetoTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String start;

	private String end;

	private String projectManager;

	private String projectManagerEmail;

	private String projectManagerSkill;
	
	private String employeeName;
	
	private String employeeEmail;
	
	private String employeeTeam;
	
	private String employeeSkill;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getEmployeeTeam() {
		return employeeTeam;
	}

	public void setEmployeeTeam(String employeeTeam) {
		this.employeeTeam = employeeTeam;
	}

	public String getEmployeeSkill() {
		return employeeSkill;
	}

	public void setEmployeeSkill(String employeeSkill) {
		this.employeeSkill = employeeSkill;
	}
	
}