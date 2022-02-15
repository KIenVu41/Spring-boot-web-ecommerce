package com.dev.controller.admin;

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
@RequestMapping(value = "/admin")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/dashboard")
	public String init(Model model) {
		String username = getPrincipal();
		model.addAttribute("username", username);
		return "admin/dashboard";
	}
	
	@GetMapping(value = "/login")
	public String login(HttpServletRequest req, @RequestParam(name = "e", required = false) String error,@RequestParam(name = "logout", required = false) String logout) {
		if (error != null) {
			req.setAttribute("e", error);
		}
		if (logout != null) {
			req.setAttribute("logout", logout);
		}
		return "admin/auth/login";
	}

	@GetMapping(value = "/register")
	public String showRegistrationForm(Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "admin/auth/register";
	}

	@PostMapping("/register")
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest req) {

		try {
			userService.registerNewUserAccount(userDTO);
		} catch (Exception e) {
			req.setAttribute("message", "An account for that username/email already exists");
		}

		return "redirect:/admin/login";
	}
	
	private String getPrincipal(){
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
