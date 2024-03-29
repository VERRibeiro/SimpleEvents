package br.edu.ifpb.pweb2.SimpleEvents.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.SimpleEvents.model.Usuario;
import br.edu.ifpb.pweb2.SimpleEvents.model.UsuarioLogado;
import br.edu.ifpb.pweb2.SimpleEvents.model.Vaga;
import br.edu.ifpb.pweb2.SimpleEvents.repository.VagaRepository;

@Controller
@RequestMapping("/vaga")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VagaController {
	
	@Autowired
	private UsuarioLogado usuario;
	
	@Autowired
	private VagaRepository vagaRepo;
	
	@RequestMapping(value="/cadastrar", method = RequestMethod.POST)
	private ModelAndView cadastrarVaga(HttpServletRequest request,@Valid Vaga vaga, BindingResult br) {		
		if(!br.hasErrors())
			vagaRepo.save(vaga);
		return new ModelAndView("redirect:listarVagas");
	}
	
	@RequestMapping(value="/listarVagas", method = RequestMethod.GET)
	private ModelAndView listarVagas(HttpSession sessao,HttpServletRequest request, Model model) {
		model.addAttribute("vagas", vagaRepo.findAll());
		return new ModelAndView("vaga/cadastrar");
	}
}
