package com.dev.controller.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.BillProductDTO;
import com.dev.model.ProductDTO;
import com.dev.service.ProductService;

@Controller
public class ClientController {
	@Autowired
	ProductService productService;
	
	@GetMapping(value = "/home")
	public String findProducts(HttpServletRequest request, HttpSession session) {
		List<ProductDTO> newArrivals = productService.search("", 0, 7, 0);
		List<ProductDTO> featured= productService.search("", 8, 11, 0);
	
		request.setAttribute("newArrivals", newArrivals);
		request.setAttribute("featured", featured);
		
		return "client/index";
	}
	
	@GetMapping(value = "/product/download")
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
	
	@GetMapping(value = "/order-confirmation")
	public String success() {
		return "client/thankyou";
	}
}
