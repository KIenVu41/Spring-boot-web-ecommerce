package com.dev.controller.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.BillProductDTO;
import com.dev.model.CommentDTO;
import com.dev.model.ProductDTO;
import com.dev.model.ReviewDTO;
import com.dev.model.UserDTO;
import com.dev.service.CommentService;
import com.dev.service.ProductService;
import com.dev.service.ReviewService;
import com.dev.service.UserService;

@Controller
public class ClientProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ClientAuthController auth;
	
	@GetMapping(value = "/product/search")
	public String searchProducts(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "mode", required = false) Integer mode) {
		
		String name = auth.getPrincipal();
		HttpSession session = request.getSession();
		if(name != null) {
			session.setAttribute("name", name);
		}
		
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		mode = mode == null ? 1 : mode;
		
		int count = keyword.equals("") ? productService.countAll(): productService.countByName(keyword);
	
		int pageSize = 8;
		int endPage = 0;
		int offset = page * pageSize - pageSize;
		
		List<ProductDTO> productList = productService.search(keyword, offset, pageSize, mode);

		endPage = count / pageSize;
		
		if(count % pageSize != 0) {
			endPage += 1;
		}
		
		Object obj = session.getAttribute("cart");
		int cartItems = 0;
		if(obj != null) {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			 for (Map.Entry<Integer,BillProductDTO> entry : cart.entrySet()) {
				 	cartItems += entry.getValue().getQuantity();
		     }
		}
		
		session.setAttribute("cartItems", cartItems);	
		request.setAttribute("endPage", endPage);
		request.setAttribute("products", productList);
		request.setAttribute("current", page);
		request.setAttribute("keyword", keyword);
		
		return "client/product";
	}
	
	@GetMapping(value = "/product/detail")
	public String productDetail(HttpServletRequest request,HttpSession session, Model model,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "loadP", required = false) Integer loadP) {
		ProductDTO productDTO = productService.getProductById(id);
		List<CommentDTO> comments = commentService.findByProductId(id);
		ReviewDTO review = new ReviewDTO();
		CommentDTO comment = new CommentDTO();
		review.setProduct(productDTO);
		comment.setProduct(productDTO);
		loadP = loadP == null ? 1: loadP;
		List<ProductDTO> relatedProduct = productService.findByCategory(productDTO.getCategory().getName(), 0, loadP * 4);

		model.addAttribute("review", review);
		model.addAttribute("comment", comment);
		request.setAttribute("product", productDTO);
		request.setAttribute("comments", comments);
		request.setAttribute("id", id);
		request.setAttribute("loadP", loadP);
		request.setAttribute("relatedProduct", relatedProduct);
		return "client/productDetail";
	}
	
	@PostMapping(value = "/product/rate")
	public String rating(HttpServletRequest request, @ModelAttribute(name="review") ReviewDTO review) {
		String username = getPrincipal();
		int id = review.getProduct().getId();
		if(username.equals("anonymousUser")) {
			return "redirect:/product/detail?id=" + id + "&e1=Can dang nhap truoc khi rating";
		}
		UserDTO userDTO = userService.getUserByUsername(username);
		review.setUser(userDTO);
		reviewService.add(review);
		
		return "redirect:/product/detail?id=" + id;
	}
	
	@PostMapping(value = "/product/comment")
	public String comment(HttpServletRequest request, @ModelAttribute(name="comment") CommentDTO comment) {
		String username = getPrincipal();
		int id = comment.getProduct().getId();
		if(username.equals("anonymousUser")) {
			return "redirect:/product/detail?id=" + id + "&e2=Can dang nhap truoc khi binh luan";
		}
		UserDTO userDTO = userService.getUserByUsername(username);
		comment.setUser(userDTO);
		commentService.add(comment);
		
		return "redirect:/product/detail?id=" + id;
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
	
}
