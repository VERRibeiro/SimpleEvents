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
import br.edu.ifpb.pweb2.SimpleEvents.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UsuarioController {

	@Autowired
	private UsuarioLogado usuario;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public ModelAndView perfil(Model model) {
		if(usuario.getId() == 0) {			
			return new ModelAndView("redirect:loginForm");
		}
		Usuario u = usuarioRepo.getOne(usuario.getId());		
		model.addAttribute("u", u);		
		return new ModelAndView("usuario/perfil");
	}

	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public ModelAndView loginForm() {		
		if(usuario.getId() != 0)
			return new ModelAndView("redirect:perfil" + usuario.getId());
		return new ModelAndView("usuario/login");
	}
	@RequestMapping(value = "/cancelarInscricao", method = RequestMethod.GET)
	public ModelAndView cancelarInscricao() {		
		Usuario u = usuarioRepo.getOne(usuario.getId());
		u.setVaga(null);
		usuarioRepo.save(u);
		return new ModelAndView("usuario/perfil/" + u.getId());
	}		
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession session, @Valid Usuario user, BindingResult br) {
		if (br.hasErrors() || !usuarioRepo.findByEmail(user.getEmail()).getSenha().equals(user.getSenha()))
			return new ModelAndView("redirect:loginForm");
		else {
			Usuario u = usuarioRepo.findByEmail(user.getEmail());
			this.usuario.setId(u.getId());
			this.usuario.setNome(u.getNome());
			this.usuario.setEmail(u.getEmail());
			this.usuario.setAdmin(u.isAdmin());
			this.usuario.setCandidato(u.isCandidato());
			session.setAttribute("usuario", this.usuario);
			return new ModelAndView("redirect:perfil");
		}		
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("redirect:loginForm");
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String registerPage() {
		return "usuario/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, @Valid Usuario usuario, BindingResult br) {
		if (request.getParameter("isAdmin") != null)
			usuario.setAdmin(true);
		if (request.getParameter("isCandidato") != null)
			usuario.setCandidato(true);
		if (br.hasErrors())
			return new ModelAndView("redirect:formRegister");
		usuario.setSenha(request.getParameter("senha"));
		usuarioRepo.save(usuario);

		return new ModelAndView("redirect:loginForm");
	}

}
