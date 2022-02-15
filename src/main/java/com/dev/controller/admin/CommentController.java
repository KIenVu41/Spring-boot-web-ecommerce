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

import com.dev.model.CommentDTO;
import com.dev.service.CommentService;

@Controller
@RequestMapping("/admin")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value = "/comment/search")
	public String search(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {	
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int count = commentService.count();
	
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<CommentDTO> comments = keyword.equals("")?  commentService.search(keyword, offset, pageSize):commentService.search(keyword, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("comments", comments);	
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/comment/commentList";
	}
	
	@GetMapping(value = "/comment/delete")
	public String delete(@RequestParam(name = "id") int id) {
		commentService.delete(id);
		
		return "redirect:/admin/comment/search";
	}
	
	@GetMapping(value="/comment/export")
	public void export(HttpServletResponse response) throws IOException {
	 response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comments_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<CommentDTO> listComments = commentService.listAll();
         
        CommentExcelExporter excelExporter = new CommentExcelExporter(listComments);
         
        excelExporter.export(response);    
	}
}
