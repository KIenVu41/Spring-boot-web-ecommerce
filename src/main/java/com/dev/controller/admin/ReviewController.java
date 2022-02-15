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

import com.dev.model.ReviewDTO;
import com.dev.service.ReviewService;

@Controller
@RequestMapping("/admin")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value = "/review/search")
	public String search(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {	
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int count = reviewService.count();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<ReviewDTO> reviews = keyword.equals("")? reviewService.search(keyword, offset, pageSize): reviewService.search(keyword, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("reviews", reviews);	
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/review/reviewList";
	}
	
	@GetMapping(value = "/review/delete")
	public String delete(@RequestParam(name = "id") int id) {
		reviewService.delete(id);
		
		return "redirect:/admin/review/search";
	}
	
	@GetMapping(value="/review/export")
	public void export(HttpServletResponse response) throws IOException {
	 response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reviews_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<ReviewDTO> listReviews = reviewService.listAll();
         
        ReviewExcelExporter excelExporter = new ReviewExcelExporter(listReviews);
         
        excelExporter.export(response);    
	}
}
