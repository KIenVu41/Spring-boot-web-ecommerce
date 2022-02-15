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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.BillDTO;
import com.dev.model.BillProductDTO;
import com.dev.service.BillProductService;
import com.dev.service.BillService;

@Controller
@RequestMapping("/admin")
public class BillController {
	@Autowired
	BillService billService;
	
	@Autowired
	BillProductService billProductService;
	
	@GetMapping(value="/bill/search")
	public String search(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
	
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int count = billService.count();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<BillDTO> bills = keyword.equals("")? billService.search(keyword, offset, pageSize):billService.search(keyword, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("bills", bills);	
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/bill/billList";
	}
	
	@GetMapping(value = "/bill/delete") 
	public String delete(@RequestParam("id") int id) {
		billService.delete(id);
		
		return "redirect:/admin/bill/search";
	}
	
	
	@GetMapping(value="/bill/export")
	public void export(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=bills_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<BillDTO> listBills = billService.listAll();
         
        BillExcelExporter excelExporter = new BillExcelExporter(listBills);
         
        excelExporter.export(response);    
	}
	
	@GetMapping(value = "/bill/detail")
	public String billProductsearch(HttpServletRequest request,
			@RequestParam("id") int id,
			@RequestParam(value = "page", required = false) Integer page) {
	
		page = page == null ? 1 : page;
	
		int count = billProductService.count();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<BillProductDTO> billProducts = page != 1 ? billProductService.pagination(id, offset, pageSize):billProductService.pagination(id, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("billProducts", billProducts);	
		request.setAttribute("current", page);
		request.setAttribute("id", id);
		
		return "admin/bill/detail";
	}
	
}
