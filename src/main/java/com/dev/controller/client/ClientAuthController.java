package com.dev.controller.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.UserDTO;
import com.dev.service.UserService;

@Controller
@RequestMapping(value = "/member")
public class ClientAuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/login")
	public String login(HttpServletRequest req, @RequestParam(name = "e", required = false) String error,@RequestParam(name = "logout", required = false) String logout) {
		if (error != null) {
			req.setAttribute("e", error);
		}
		if (logout != null) {
			req.setAttribute("logout", logout);
		}
		return "client/login";
	}
	
	
	@GetMapping(value = "/signup")
	public String signup(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "client/signup";
	}
	
	@PostMapping("/signup")
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest req) {

		try {
			userService.signup(userDTO);
		} catch (Exception e) {
			req.setAttribute("message", "An account for that username/email already exists");
		}

		return "redirect:/login";
	}
	
	public String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
