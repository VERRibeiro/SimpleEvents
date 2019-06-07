package br.edu.ifpb.pweb2.SimpleEvents.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.SimpleEvents.model.Usuario;
import br.edu.ifpb.pweb2.SimpleEvents.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepo;

	public UsuarioController() {

	}

	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "usuario/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession session, @Valid Usuario usuario, BindingResult br) {
		if (br.hasErrors() || !usuarioRepo.findByEmail(usuario.getEmail()).getSenha().equals(usuario.getSenha()))
			return new ModelAndView("redirect:loginForm");
		else {
			session.setAttribute("usuario", usuarioRepo.findByEmail(usuario.getEmail()));
			return new ModelAndView("home");
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/formRegister", method = RequestMethod.GET)
	public String registerPage() {
		return "usuario/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, @Valid Usuario usuario, BindingResult br) {
		if (request.getParameter("isAdmin").equals("on"))
			usuario.setAdmin(true);
		if (request.getParameter("isCandidato").equals("on"))
			usuario.setCandidato(true);
		if (br.hasErrors())
			return new ModelAndView("redirect:formRegister");
		usuarioRepo.save(usuario);

		return new ModelAndView("redirect:loginForm");
	}

}
