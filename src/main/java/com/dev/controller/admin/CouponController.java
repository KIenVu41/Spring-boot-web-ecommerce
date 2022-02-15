package com.dev.controller.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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

import com.dev.model.CouponDTO;
import com.dev.service.CouponService;


@Controller
@RequestMapping("/admin")
public class CouponController {
	@Autowired
	private CouponService couponService;
	
	@GetMapping(value = "/coupon/search")
	public String search(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {	
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		
		int count = couponService.count();
		int pageSize = 3;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<CouponDTO> coupons = keyword.equals("")? couponService.search(keyword, offset, pageSize):couponService.search(keyword, 0, pageSize);
		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		request.setAttribute("endPage", endPage);	
		request.setAttribute("coupons", coupons);	
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "admin/coupon/couponList";
	}
	
	@GetMapping(value = "/coupon/add")
	public String add(Model model) {
		model.addAttribute("coupon", new CouponDTO());
		return "admin/coupon/add";
	}
	
	@PostMapping(value = "/coupon/add")
	public String add(HttpServletRequest req, @ModelAttribute(name="coupon") CouponDTO coupon) {	
		String sdate = req.getParameter("expired");
		System.out.println(sdate);
		try {
			Date date =new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			coupon.setExpiredDate(date);
			couponService.add(coupon);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return "redirect:/admin/coupon/search";
	}
	
	@GetMapping(value = "/coupon/delete")
	public String delete(@RequestParam(name = "id") int id) {
		couponService.delete(id);
		
		return "redirect:/admin/coupon/search";
	}
	
	@GetMapping(value="/coupon/export")
	public void export(HttpServletResponse response) throws IOException {
	 response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=coupons_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<CouponDTO> listCoupons = couponService.listAll();
         
        CouponExcelExporter excelExporter = new CouponExcelExporter(listCoupons);
         
        excelExporter.export(response);    
	}
}
