package com.dev.controller.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dev.model.UserDTO;
import com.dev.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user/search")
	public String searchUser(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int pageSize = 3;
		int endPage = 0;
		
		int offset = page * pageSize - pageSize;
		List<UserDTO> userList = keyword.equals("")? userService.search(keyword, offset, pageSize):userService.search(keyword, 0, pageSize);

		int count = userService.count();
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
			
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		request.setAttribute("endPage", endPage);
		request.setAttribute("users", userList);

		return "admin/user/userList";
	}
	
	@GetMapping("/user/delete")
	public String delete(@RequestParam("id") int id) {
		userService.delete(id);
		
		return "redirect:/admin/user/search";
	}
	
	@GetMapping("/user/edit")
	public String edit(@RequestParam("id") int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		
		return "/admin/user/edit";
	}
	
	@PostMapping("/user/edit")
	public String edit(@ModelAttribute("user") UserDTO userDTO) {
		MultipartFile file = userDTO.getImage();
		try {
			File newFile = new File("D:\\code\\eclipse\\SpringMVC\\web\\src\\main\\resources\\static\\img\\" + file.getOriginalFilename());
			FileOutputStream fos;
			fos = new FileOutputStream(newFile);
			fos.write(file.getBytes());
			
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		userDTO.setImageURL(file.getOriginalFilename());
		userService.update(userDTO);
		return "redirect:/admin/user/search";
	}
	
	@GetMapping(value = "/user/add")
	public String add(Model model) {
		model.addAttribute("user", new UserDTO());
		
		return "admin/user/add";
	}
	
	@PostMapping(value = "/user/add")
	public String add(HttpServletRequest req, @ModelAttribute(name="user") UserDTO user) {		
		MultipartFile file = user.getImage();
		try {
			File newFile = new File("D:\\code\\eclipse\\SpringMVC\\web\\src\\main\\resources\\static\\img\\" + file.getOriginalFilename());
			FileOutputStream fos;
			fos = new FileOutputStream(newFile);
			fos.write(file.getBytes());
			
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		user.setImageURL(file.getOriginalFilename());
		userService.add(user);
		
		return "redirect:/admin/user/search";
	}
	
	@GetMapping(value = "/user/download")
	public void download(HttpServletRequest request,HttpServletResponse response, @RequestParam(name = "url") String url) {
		String dataDirectory = "D:\\code\\eclipse\\SpringMVC\\web\\src\\main\\resources\\static\\img\\";
		Path file = Paths.get(dataDirectory, url);
		if(Files.exists(file)) {
			response.setContentType("image/jpg");
			response.addHeader("Content-Disposition", "attachment; filename =anh.jpg");
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@GetMapping(value = "/change-status")
	public String changeStatus(@RequestParam("id") int id) {
		UserDTO userDTO = userService.getUserById(id);
		boolean statusRev = !userDTO.getEnabled();
		
		userService.changeEnable(id, statusRev);
		
		return "redirect:/admin/user/search";
	}
	
	@GetMapping(value="/user/export")
		public void export(HttpServletResponse response) throws IOException {
		 response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	        List<UserDTO> listUsers = userService.listAll();
	         
	        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
	         
	        excelExporter.export(response);    
		}
	
}
