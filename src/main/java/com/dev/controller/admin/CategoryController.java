package com.dev.controller.admin;

import java.io.IOException;
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

import com.dev.model.CategoryDTO;
import com.dev.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/category/search")
	public String search(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {	
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int count = categoryService.count();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<CategoryDTO> categories = keyword.equals("")? categoryService.search(keyword, offset, pageSize):categoryService.search(keyword, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("categories", categories);	
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/category/categoryList";
	}
	
	@GetMapping(value = "/category/add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDTO());
		return "admin/category/add";
	}
	
	@PostMapping(value = "/category/add")
	public String add(HttpServletRequest req, @ModelAttribute(name="category") CategoryDTO category) {		
		categoryService.add(category);
		
		return "redirect:/admin/category/search";
	}
	
	@GetMapping(value = "/category/edit")
	public String edit(@RequestParam(name = "id") int id, Model model) {
		model.addAttribute("category", categoryService.getCategoryById(id));
		
		return "admin/category/edit";
	}
	
	@PostMapping(value = "/category/edit")
	public String edit(@ModelAttribute(name="category") CategoryDTO category) {
		categoryService.update(category);
		
		return "redirect:/admin/category/search";
	}
	
	@GetMapping(value = "/category/delete")
	public String delete(@RequestParam(name = "id") int id) {
		categoryService.delete(id);
		
		return "redirect:/admin/category/search";
	}
	
	@GetMapping(value="/category/export")
	public void export(HttpServletResponse response) throws IOException {
	 response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=categories_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<CategoryDTO> listCategories = categoryService.listAll();
         
        CategoryExcelExporter excelExporter = new CategoryExcelExporter(listCategories);
         
        excelExporter.export(response);    
	}
}
