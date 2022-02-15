package com.dev.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.model.BillProductDTO;
import com.dev.model.ProductDTO;
import com.dev.service.CategoryService;
import com.dev.service.ProductService;

@Controller
public class ClientCartController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping(value = "/cart")
	public String searchProducts(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "loadP", required = false) Integer loadP) {	
		loadP = loadP == null ? 1: loadP;
		Object obj = session.getAttribute("cart");
		Long total = Long.valueOf(0);
		List<ProductDTO> latestProduct = productService.getLatest(0, loadP * 4);
		int cartItems = 0;
		if(obj != null) {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			 for (Map.Entry<Integer,BillProductDTO> entry : cart.entrySet()) {
				 	total += entry.getValue().getPrice() * entry.getValue().getQuantity();
					cartItems += entry.getValue().getQuantity();
		     }
		}
		session.setAttribute("cartItems", cartItems);	
		request.setAttribute("total", total);
		request.setAttribute("loadP", loadP);
		request.setAttribute("latestProduct", latestProduct);
		return "client/cart";
	}
	
	@GetMapping(value = "/cart/add-to-cart")
	public String addToCart(HttpServletRequest request, HttpSession session ,
			@RequestParam(value = "id") Integer id, @RequestParam(value = "quantity", required = false) Integer quantity) {
		
		ProductDTO product = productService.getProductById(id);
		Object obj = session.getAttribute("cart");
		quantity = quantity == null ? 1 : quantity;
		if(obj == null) {
			BillProductDTO billProduct = new BillProductDTO();
			billProduct.setProduct(product);
			billProduct.setPrice(product.getPrice());
			billProduct.setQuantity(quantity);
			Map<Integer, BillProductDTO> cart = new HashMap<Integer, BillProductDTO>();
			session.setAttribute("cart", cart);
		}else {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			BillProductDTO billProduct = cart.get(id);
			if(billProduct == null) {
				billProduct = new BillProductDTO();
				billProduct.setProduct(product);
				billProduct.setPrice(product.getPrice());
				billProduct.setQuantity(quantity);
				cart.put(id, billProduct);
			}else {
				if(billProduct.getQuantity() < product.getQuantity()) {
					billProduct.setQuantity(billProduct.getQuantity() + 1);
				}else {
					billProduct.setQuantity(billProduct.getQuantity());
				}
			}
			session.setAttribute("cart", cart);
		}
		return "redirect:/cart";

	}
	
	@GetMapping(value = "/cart/update")
	public String update(HttpServletRequest req, @RequestParam(value = "action", required = false) String  action, @RequestParam(name = "id", required = true) Integer id) {
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("cart");
		if (obj != null) {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			BillProductDTO billProduct = cart.get(id);
			if(action.equals("decrease")) {
				billProduct.setQuantity(billProduct.getQuantity() - 1);		
				if(billProduct.getQuantity() < 1) {
					cart.remove(id);
				}
			}else if(action.equals("increase")) {
				billProduct.setQuantity(billProduct.getQuantity() + 1);
			}
			session.setAttribute("cart", cart);
		}
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/cart/delete")
	public String deleteCartElement(HttpServletRequest req, @RequestParam(name = "id", required = true) Integer id) {
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("cart");
		if (obj != null) {
			Map<Integer, BillProductDTO> cart = (Map<Integer, BillProductDTO>) obj;
			cart.remove(id);
			session.setAttribute("cart", cart);
		}
		return "redirect:/cart";
	}
}