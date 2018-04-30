package com.greenmile.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.supercsv.exception.SuperCsvReflectionException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.greenmile.batch.to.ProjetoTO;
import com.greenmile.comum.util.DataUtil;
import com.greenmile.model.Projeto;
import com.greenmile.model.Usuario;
import com.greenmile.repository.ProjetoRepository;

/**
 * @author Juciel Almeida
 * 
 * @class ProjetoController
 * 
 *        Classe responsável pelo controle da tela de Projetos.
 * 
 */
@Controller
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	private ProjetoRepository projetoRepository;

	private Projeto projeto;
	private Projeto projetoSelecionado;

	/**
	 * 
	 * Popular as listas e instância os objetos
	 * @param model
	 * @return {@link ModelAndView}
	 */
	@GetMapping
	public ModelAndView iniciar(Model model) {
		model.addAttribute("projeto", getProjeto());
		List<Projeto> lista = projetoRepository.findAll();

		ModelAndView modelAndView = new ModelAndView("projetos");
		modelAndView.addObject("projetoSelecionado", getProjetoSelecionado());
		modelAndView.addObject("projetos", lista);

		return modelAndView;
	}

	/**
	 * Salvar o projeto
	 * @param projeto
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute @Valid Projeto projeto, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Projeto projetoExiste = projetoRepository.consultarExistente(projeto.getName());
		ModelAndView modelAndView = new ModelAndView("redirect:/projetos");
		if (projetoExiste != null) {
			String mensagemErro = "Esse projeto já existe, gerenciado por ".concat(projetoExiste.getProjectManager());
			redirectAttributes.addFlashAttribute("msg_erro", mensagemErro);
			setProjeto(projeto);
		} else {
			projetoRepository.save(projeto);
			redirectAttributes.addFlashAttribute("msg_sucesso", "Registro Salvo com sucesso!");
		}
		return modelAndView;
	}

	
	/**
	 * Selecionar um projeto 
	 * @param id
	 * @return
	 */
	@GetMapping("/selecionarProjeto/{projeto}")
	public ModelAndView selecionarProjeto(@PathVariable("projeto") Long id) {
		setProjetoSelecionado(projetoRepository.findOne(id));
		ModelAndView modelAndView = new ModelAndView("redirect:/projetos");
		return modelAndView;
	}

	/**
	 * Arquivar o projeto selecionado
	 * @param response
	 * @param id
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "/downloadCSV/{projeto}")
	public void downloadCSV(HttpServletResponse response, @PathVariable("projeto") Long id,
			RedirectAttributes redirectAttributes) {
		try {
			setProjetoSelecionado(projetoRepository.findOne(id));
			String csvFileName = "gm-challenge.csv";
			response.setContentType("text/csv");

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
			response.setHeader(headerKey, headerValue);

			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String[] writeHeader = { "# ProjectName", "PlannedStart", "PlannedEnd", "PM", "PMEmail", "PMSkills",
					"EmployeeName", "EmployeeEmail", "EmployeeTeam", "EmployeeSkills" };
			csvWriter.writeHeader(writeHeader);

			String[] nameMapping = { "name", "start", "end", "projectManager", "projectManagerEmail",
					"projectManagerSkill", "employeeName", "employeeEmail", "employeeTeam", "employeeSkill" };
			ProjetoTO projetoTO = new ProjetoTO();
			projetoTO.setName(getProjetoSelecionado().getName());
			projetoTO.setStart(DataUtil.getDataFormatada(getProjetoSelecionado().getStart()));
			projetoTO.setEnd(DataUtil.getDataFormatada(getProjetoSelecionado().getEnd()));
			projetoTO.setProjectManager(getProjetoSelecionado().getProjectManager());
			projetoTO.setProjectManagerEmail(getProjetoSelecionado().getProjectManagerEmail());
			projetoTO.setProjectManagerSkill(getProjetoSelecionado().getProjectManagerSkill());

			List<ProjetoTO> lista = new ArrayList<>();
			lista.add(projetoTO);
			for (ProjetoTO itemProjeto : lista) {
				if (getProjetoSelecionado().getUsuarios() != null) {
					for (Usuario itemUsuario : getProjetoSelecionado().getUsuarios()) {
						itemProjeto.setEmployeeName(itemUsuario.getName());
						itemProjeto.setEmployeeEmail(itemUsuario.getEmail());
						itemProjeto.setEmployeeTeam(itemUsuario.getTeam());
						itemProjeto.setEmployeeSkill(itemUsuario.getSkill());
						csvWriter.write(itemProjeto, nameMapping);
					}
				} else {
					csvWriter.write(itemProjeto, nameMapping);
				}
			}

			csvWriter.close();
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("msg_erro", "Erro ao Arquivar Projetos");
		} catch (SuperCsvReflectionException e) {
			redirectAttributes.addFlashAttribute("msg_erro", "Erro ao Arquivar Projetos");
		}
	}

	/**
	 * Arquivar todos os projetos
	 * @param response
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String csvFileName = "gm-challenge.csv";
			response.setContentType("text/csv");

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
			response.setHeader(headerKey, headerValue);

			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String[] writeHeader = { "# ProjectName", "PlannedStart", "PlannedEnd", "PM", "PMEmail", "PMSkills",
					"EmployeeName", "EmployeeEmail", "EmployeeTeam", "EmployeeSkills" };
			csvWriter.writeHeader(writeHeader);

			String[] nameMapping = { "name", "start", "end", "projectManager", "projectManagerEmail",
					"projectManagerSkill", "employeeName", "employeeEmail", "employeeTeam", "employeeSkill" };

			for (Projeto itemProjeto : projetoRepository.findAll()) {
				ProjetoTO projetoTO = new ProjetoTO();
				projetoTO.setName(itemProjeto.getName());
				projetoTO.setStart(DataUtil.getDataFormatada(itemProjeto.getStart()));
				projetoTO.setEnd(DataUtil.getDataFormatada(itemProjeto.getEnd()));
				projetoTO.setProjectManager(itemProjeto.getProjectManager());
				projetoTO.setProjectManagerEmail(itemProjeto.getProjectManagerEmail());
				projetoTO.setProjectManagerSkill(itemProjeto.getProjectManagerSkill());

				if (itemProjeto.getUsuarios() != null) {
					for (Usuario itemUsuario : itemProjeto.getUsuarios()) {
						projetoTO.setEmployeeName(itemUsuario.getName());
						projetoTO.setEmployeeEmail(itemUsuario.getEmail());
						projetoTO.setEmployeeTeam(itemUsuario.getTeam());
						projetoTO.setEmployeeSkill(itemUsuario.getSkill());
						csvWriter.write(projetoTO, nameMapping);
					}
				} else {
					csvWriter.write(projetoTO, nameMapping);
				}
			}

			csvWriter.close();
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("msg_erro", "Erro ao Arquivar Projetos");
		} catch (SuperCsvReflectionException e) {
			redirectAttributes.addFlashAttribute("msg_erro", "Erro ao Arquivar Projetos");
		}
	}

	public Projeto getProjeto() {
		if (projeto == null) {
			projeto = new Projeto();
		}
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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

}