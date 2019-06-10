package br.edu.ifpb.pweb2.SimpleEvents.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.SimpleEvents.model.Evento;
import br.edu.ifpb.pweb2.SimpleEvents.model.Usuario;
import br.edu.ifpb.pweb2.SimpleEvents.model.UsuarioLogado;
import br.edu.ifpb.pweb2.SimpleEvents.model.Vaga;
import br.edu.ifpb.pweb2.SimpleEvents.model.VagaEvento;
import br.edu.ifpb.pweb2.SimpleEvents.repository.EventoRepository;
import br.edu.ifpb.pweb2.SimpleEvents.repository.UsuarioRepository;
import br.edu.ifpb.pweb2.SimpleEvents.repository.VagaEventoRepository;
import br.edu.ifpb.pweb2.SimpleEvents.repository.VagaRepository;
import br.edu.ifpb.pweb2.SimpleEvents.util.Status;

@Controller
@RequestMapping("/evento")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class EventoController {
	@Autowired
	private UsuarioLogado usuario;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private EventoRepository eventoRepo;

	@Autowired
	private VagaEventoRepository vagaEventoRepo;

	@Autowired
	private VagaRepository vagaRepo;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView listar(HttpSession session, Model model) {
		if (usuario.getId() == 0)
			usuario = new UsuarioLogado();
		session.setAttribute("usuario", this.usuario);
		model.addAttribute("eventos", eventoRepo.findAll());
		return new ModelAndView("evento/listar");
	}

	@RequestMapping(value = "/inscreverUsuario/{id}", method = RequestMethod.GET)
	public ModelAndView cadastroPage(Model model, @PathVariable("id") Integer id) {
		Vaga vaga = new Vaga();
		List<Vaga> vagas = new ArrayList<Vaga>();
		for (VagaEvento ve : vagaEventoRepo.findByEventoId(id)) {
			vagas.add(new Vaga(ve.getVaga().getId(), ve.getVaga().getNome(), ve.getVaga().getDescricao(),
					ve.getQtdeEvento()));
		}		
		model.addAttribute("usuario", usuario);
		model.addAttribute("eventoId", id);
		model.addAttribute("vagas", vagas);
		return new ModelAndView("evento/inscrever");
	}

	@RequestMapping(value = "/inscreverUsuario/{usuarioId}/{eventoId}/{vagaId}", method = RequestMethod.GET)
	public ModelAndView cadastroVaga(Model model, @PathVariable("usuarioId") Integer usuarioId,
			@PathVariable("eventoId") Integer eventoId, @PathVariable("vagaId") Integer vagaId) {
		Optional<Usuario> u = usuarioRepo.findById(usuarioId);
		Optional<Evento> evento = eventoRepo.findById(eventoId);
		VagaEvento vaga = null;
		Integer evento_id = eventoId;

		for (VagaEvento vagaEvento : vagaEventoRepo.findByEventoId(evento_id)) {
			if (vagaEvento.getVaga().getId() == vagaId) {
				vaga = vagaEvento;
			}
		}
		u.get().setStatus(Status.NEUTRO);
		u.get().setVaga(vaga);
		vaga.addCandidato(u.get());
		System.out.println(u.get());		
		vagaEventoRepo.save(vaga);
		usuario.setVaga(vaga);
		model.addAttribute("eventos", eventoRepo.findAll());
		return new ModelAndView("evento/listar");
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
	public ModelAndView editarEvento(HttpServletRequest request, Model model, Evento evento,
			@PathVariable("id") Integer id) throws ParseException {
		Vaga vaga = new Vaga();
		List<Vaga> vagas = new ArrayList<Vaga>();
		Date data=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
		evento.setDataHora(data);
		int i = 0;
		for (VagaEvento ve : vagaEventoRepo.findByEventoId(id)) {
			vagas.add(new Vaga(ve.getVaga().getId(), ve.getVaga().getNome(), ve.getVaga().getDescricao(),
					Integer.parseInt(request.getParameterValues("vagas")[i])));
			ve.setQtdeEvento(Integer.parseInt(request.getParameterValues("vagas")[i]));
			vagaEventoRepo.save(ve);
			i++;
		}

		Evento e = eventoRepo.findById(id).get();

		e.setFinalizado(evento.isFinalizado());
		e.setTitulo(evento.getTitulo());
		e.setLocal(evento.getLocal());
		eventoRepo.save(e);
		model.addAttribute("evento", e);
		model.addAttribute("vagas", vagas);
		return new ModelAndView("evento/editar");
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public ModelAndView editar(Model model, @PathVariable("id") Integer id) {
		Vaga vaga = new Vaga();
		Evento e = eventoRepo.findById(id).get();
		List<Usuario> candidatos = new ArrayList<Usuario>();
		List<Vaga> vagas = new ArrayList<Vaga>();
		for (VagaEvento ve : vagaEventoRepo.findByEventoId(id)) {
			vagas.add(new Vaga(ve.getVaga().getId(), ve.getVaga().getNome(), ve.getVaga().getDescricao(),
					ve.getQtdeEvento()));
			for (Usuario c1 : ve.getCandidatos()) {
				if(c1.getStatus() == Status.APROVADO)
					candidatos.add(c1);
			}
		}

		model.addAttribute("evento", eventoRepo.findById(id).get());
		model.addAttribute("candidatos", candidatos);
		model.addAttribute("vagas", vagas);
		return new ModelAndView("evento/editar");
	}

	@RequestMapping(value = "/aprovar/{id}", method = RequestMethod.POST)
	public ModelAndView aprovar(HttpServletRequest request, Model model, @PathVariable("id") Integer id) {
		int tam = request.getParameterValues("candidatos").length;		
		for (int i = 0; i < tam; i++) {
			System.out.println(request.getParameterValues("candidatos")[i]);
		}
		model.addAttribute("vagas", vagaRepo.findAll());
		return new ModelAndView("evento/listar");
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView cadastro(Model model) {
		model.addAttribute("vagas", vagaRepo.findAll());
		return new ModelAndView("evento/cadastrar");
	}

	@RequestMapping(value = "/editar/avaliar/{eventoId}", method = RequestMethod.GET)
	public ModelAndView avaliarCandidato(HttpServletRequest request, Model model,
			@PathVariable("eventoId") Integer eventoId) {
		Vaga vaga = new Vaga();
		Evento e = eventoRepo.findById(eventoId).get();
		List<Usuario> candidatos = new ArrayList<Usuario>();
		List<Vaga> vagas = new ArrayList<Vaga>();
		for (VagaEvento ve : vagaEventoRepo.findByEventoId(eventoId)) {
			vagas.add(new Vaga(ve.getVaga().getId(), ve.getVaga().getNome(), ve.getVaga().getDescricao(),
					ve.getQtdeEvento()));
			for (Usuario c1 : ve.getCandidatos()) {
				if(c1.getStatus() != Status.APROVADO && c1.getStatus() != Status.REPROVADO )
					candidatos.add(c1);
			}
		}

		model.addAttribute("evento", eventoRepo.findById(eventoId).get());
		model.addAttribute("candidatos", candidatos);
		model.addAttribute("vagas", vagas);
		return new ModelAndView("evento/avaliarCandidato");
	}
	
	@RequestMapping(value="/aprovarCandidato/{id}", method = RequestMethod.GET)
	public ModelAndView aprovarCandidator(HttpServletRequest request, @PathVariable("id") Integer id) {
		Usuario u = usuarioRepo.getOne(id);		
		u.setStatus(Status.APROVADO);
		u.getVaga().setQtdeEvento(u.getVaga().getQtdeEvento() - 1);
		vagaEventoRepo.save(u.getVaga());
		usuarioRepo.save(u);
		return new ModelAndView("redirect:/evento/editar/avaliar/" + u.getVaga().getEvento().getId());
	}	
	@RequestMapping(value="/finalizar/{id}", method = RequestMethod.GET)
	public ModelAndView finalizar(Model model, @PathVariable("id") Integer id) {
		Evento e = eventoRepo.getOne(id);
		e.setFinalizado(true);
		for (VagaEvento vg : e.getVagas()) {
			for(Usuario cv : vg.getCandidatos()) {
				cv.setStatus(Status.NEUTRO);
				cv.setVaga(null);
				usuarioRepo.save(cv);
			}			
		}
		eventoRepo.save(e);
		model.addAttribute("eventos", eventoRepo.findAll());
		return new ModelAndView("evento/listar");
	}
	
	@RequestMapping(value="/reprovar/{id}", method = RequestMethod.GET)
	public ModelAndView reprovar(HttpServletRequest request, @PathVariable("id") Integer id) {
		Usuario u = usuarioRepo.getOne(id);		
		u.setStatus(Status.REPROVADO);
		vagaEventoRepo.save(u.getVaga());
		usuarioRepo.save(u);
		return new ModelAndView("redirect:/evento/editar/avaliar/" + u.getVaga().getEvento().getId());
	}	
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(HttpServletRequest request, @Valid Evento evento, BindingResult br) throws ParseException {
		System.out.println(evento);
		System.out.println(request.getParameter("vagas"));
		Usuario u = usuarioRepo.getOne(usuario.getId()); 
		List<Vaga> vagas = vagaRepo.findAll();
		int tam = request.getParameterValues("vagas").length;
		Date data=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
		evento.setDataHora(data);
		u.addEvento(evento);
		evento.setAdmin(usuarioRepo.getOne(usuario.getId()));		
			
		eventoRepo.save(evento);
		int qtde = 0;		
		for (int i = 0; i < tam; i++) {
			if(request.getParameterValues("vagas")[i]== "") 
				qtde = 0;
			else
				qtde = Integer.parseInt(request.getParameterValues("vagas")[i]);

			VagaEvento vg = new VagaEvento(vagas.get(i), evento,
					(qtde));
			
			vagaEventoRepo.save(vg);
		}
		eventoRepo.flush();
		return new ModelAndView("redirect:listar");
	}
}
