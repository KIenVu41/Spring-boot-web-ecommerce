package com.dev.controller.client;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.BillDTO;
import com.dev.model.BillProductDTO;
import com.dev.model.CouponDTO;
import com.dev.model.UserDTO;
import com.dev.service.BillProductService;
import com.dev.service.BillService;
import com.dev.service.CouponService;
import com.dev.service.UserService;

@Controller
@RequestMapping(value = "/member")
public class ClientCheckoutController {
	@Autowired
	CouponService couponService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BillService billService;
	
	@Autowired
	BillProductService billProductService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private int time = 0;
	@GetMapping(value = "/checkout")
	public String checkout(HttpServletRequest req,  HttpSession session) {
		String username = getPrincipal();
		if(username.equals("anonymousUser")) {
			return "redirect:/cart?e=Can dang nhap truoc khi thanh toan";
		}
		Object obj = session.getAttribute("cart");
		if(obj == null) {
			return "redirect:/cart?e=Can mua do truoc khi thanh toan";
		}
		Long subtotal = Long.valueOf(0);
		if(obj != null) {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			 for (Map.Entry<Integer,BillProductDTO> entry : cart.entrySet()) {
				 	subtotal += entry.getValue().getPrice() * entry.getValue().getQuantity();
		     }
		}
		long vat = (long) (subtotal * 0.2);
		long total = subtotal - vat;
		CouponDTO coupon = (CouponDTO) session.getAttribute("discount");
		if(coupon != null) {
			long discount = (long) (coupon.getDiscountPercentage() * subtotal) / 100;
			total = subtotal - vat - discount;
			req.setAttribute("discount", discount);
		}
		session=req.getSession();  
		session.setAttribute("total", total);  
		req.setAttribute("subtotal", subtotal);
		req.setAttribute("total", total);
		req.setAttribute("vat", vat);
		return "client/checkout";
	}
	
	@GetMapping(value = "/coupon")
	public String checkout(HttpServletRequest req, @RequestParam(value = "code", required=false) String code, HttpSession session) {
		if(time < 1) {
			try {
				CouponDTO couponDTO = couponService.findByCode(code);
				Date today = new Date();
				if(today.after(couponDTO.getExpiredDate())) {
					return "redirect:/member/checkout?e=Ma giam gia het han";
				}	
				session=req.getSession();  
				session.setAttribute("discount", couponDTO);  
				time++;		
				}catch(Exception e) {
					e.printStackTrace();
					return "redirect:/member/checkout?e=Ma giam gia khong dung";
				}
		}else {
			return "redirect:/member/checkout?e=Chi nhap ma 1 lan";
		}
		return "redirect:/member/checkout";
	}
	
	@GetMapping(value = "/add-order")
	public String addOrder(HttpServletRequest req, HttpSession session) {
		String username = getPrincipal();
		UserDTO userDTO = userService.getUserByUsername(username);
		BillDTO billDTO = new BillDTO();
		Date today = new Date();
		Object obj = session.getAttribute("cart");
		Object obj2 = session.getAttribute("total");
		CouponDTO coupon = (CouponDTO) session.getAttribute("discount");
		long total = (long) obj2;
		billDTO.setBuyDate(today);
		billDTO.setTotal(total);
		billDTO.setUserDTO(userDTO);
		try {
			int billId = billService.add(billDTO);
			billDTO.setId(billId);
			if(coupon != null) {
				coupon.setBill(billDTO);
				couponService.update(coupon);
			}
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			 for (Map.Entry<Integer,BillProductDTO> entry : cart.entrySet()) {
				  BillProductDTO billProduct = entry.getValue();
				  billProduct.setBill(billDTO);
				  billProductService.add(billProduct);
		     }
			// gui mail
			sendEmail(userDTO.getEmail());
			session.removeAttribute("cart");
			session.removeAttribute("total");
			session.removeAttribute("discount");
			time = 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/order-confirmation";
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
	
	void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Dans shop");
        msg.setText("Thank you for your order!");

        javaMailSender.send(msg);

    }
}
