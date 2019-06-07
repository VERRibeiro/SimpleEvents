package br.edu.ifpb.pweb2.SimpleEvents.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.SimpleEvents.model.Evento;
import br.edu.ifpb.pweb2.SimpleEvents.model.Vaga;
import br.edu.ifpb.pweb2.SimpleEvents.model.VagaEvento;
import br.edu.ifpb.pweb2.SimpleEvents.repository.EventoRepository;
import br.edu.ifpb.pweb2.SimpleEvents.repository.VagaEventoRepository;
import br.edu.ifpb.pweb2.SimpleEvents.repository.VagaRepository;

@Controller
@RequestMapping("/evento")
public class EventoController {

	@Autowired
	private EventoRepository eventoRepo;
	
	@Autowired
	private VagaEventoRepository vagaEventoRepo;
	
	@Autowired
	private VagaRepository vagaRepo;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView listar() {
		return new ModelAndView("evento/listar");
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView cadastro(Model model) {		
		model.addAttribute("vagas", vagaRepo.findAll());
		return new ModelAndView("evento/cadastrar");
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(HttpServletRequest request, @Valid Evento evento, BindingResult br) {		
		System.out.println(evento);
		System.out.println(request.getParameter("vagas"));
		List<Vaga> vagas = vagaRepo.findAll();		
		int tam = request.getParameterValues("vagas").length;
		eventoRepo.save(evento);
		
		for(int i = 0; i < tam; i++) {
			VagaEvento vg = new VagaEvento(vagas.get(i), evento,Integer.parseInt(request.getParameterValues("vagas")[i]));
			vagaEventoRepo.save(vg);					
		}					
		eventoRepo.flush();
		return new ModelAndView("redirect:cadastro");
	}
}
