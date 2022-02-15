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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dev.model.CategoryDTO;
import com.dev.model.ProductDTO;
import com.dev.service.CategoryService;
import com.dev.service.ProductService;

@Controller
@RequestMapping(value="/admin")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/product/search")
	public String searchProduct(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "mode", required = false) Integer mode) {
		
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		mode = mode == null ? 1 : mode;
		int count = productService.countAll();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<ProductDTO> productList = productService.search(keyword, offset, pageSize, mode);
		List<CategoryDTO> categoryList = categoryService.search("", 0, 100);

		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
			
		request.setAttribute("endPage", endPage);
		request.setAttribute("products", productList);
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/product/productList";
	}
	
	@GetMapping("/product/delete")
	public String delete(@RequestParam("id") int id) {
		productService.delete(id);
		
		return "redirect:/admin/product/search";
	}
	
	@GetMapping("/product/edit")
	public String edit(@RequestParam("id") int id, Model model, HttpServletRequest req) {
		model.addAttribute("product", productService.getProductById(id));
		req.setAttribute("categories", categoryService.search("", 0, 100));
		
		return "admin/product/edit";
	}
	
	@PostMapping("/product/edit")
	public String edit(@ModelAttribute(name="product") @Validated ProductDTO productDTO ) {
		MultipartFile file = productDTO.getImage();
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
		
		productDTO.setImageURL(file.getOriginalFilename());
		productService.update(productDTO);
		
		return "redirect:/admin/product/search";
	}
	
	@GetMapping(value = "/product/add")
	public String add(Model model) {
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("categories", categoryService.search("", 0, 100));
		
		return "admin/product/add";
	}
	
	@PostMapping(value = "/product/add")
	public String add(HttpServletRequest req, @ModelAttribute(name="product") ProductDTO productDTO) {		
		MultipartFile file = productDTO.getImage();
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

		productDTO.setImageURL(file.getOriginalFilename());
		productService.add(productDTO);
		
		return "redirect:/admin/product/search";
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
	
	@GetMapping(value="/product/export")
	public void export(HttpServletResponse response) throws IOException {
	 response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<ProductDTO> listProducts = productService.listAll();
         
        ProductExcelExporter excelExporter = new ProductExcelExporter(listProducts);
         
        excelExporter.export(response);    
	}
}
