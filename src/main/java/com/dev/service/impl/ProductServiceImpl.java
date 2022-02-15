package com.dev.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.ProductDAO;
import com.dev.entity.Category;
import com.dev.entity.Product;
import com.dev.model.CategoryDTO;
import com.dev.model.ProductDTO;
import com.dev.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO productDAO;
	
	@Override
	public List<ProductDTO> search(String name, int start, int length, int mode) {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.search(name, start, length, mode);
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		
		if(mode == 2) {
			 Collections.sort(products, new Comparator<Product>() {
		            @Override
		            public int compare(Product p1, Product p2) {
		                return (int) (p1.getPrice() - p2.getPrice());
		            }
		        });
		}else if(mode == 3) {
			Collections.sort(products, new Comparator<Product>() {
				  public int compare(Product p1, Product p2) {
				      if (p1.getCreated() == null || p2.getCreated() == null)
				        return 0;
				      return p1.getCreated().compareTo(p2.getCreated());
				  }
				});
		}
		
		for(Product p: products) {
			ProductDTO productDTO = new ProductDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			
			productDTO.setId(p.getId());
			productDTO.setName(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setImageURL(p.getImageURL());
			
			categoryDTO.setId(p.getCategory().getId());
			categoryDTO.setName(p.getCategory().getName());
			productDTO.setCategory(categoryDTO);
			
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}

	@Override
	public void add(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		Product product = new Product();
		Category category = new Category();
		
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
		product.setImageURL(productDTO.getImageURL());
		
		category.setId(productDTO.getCategory().getId());
		product.setCategory(category);
		
		productDAO.add(product);
	}

	@Override
	public void update(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		Product product = productDAO.getProductById(productDTO.getId());
		Category category = new Category();
		
		if(product != null) {
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			product.setQuantity(productDTO.getQuantity());
			product.setImageURL(productDTO.getImageURL());
			
			category.setId(productDTO.getCategory().getId());
			category.setName(product.getCategory().getName());
			product.setCategory(category);
			
			productDAO.update(product);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Product product = productDAO.getProductById(id);
		
		if(product != null) {
			productDAO.delete(product);
		}
	}

	@Override
	public ProductDTO getProductById(int id) {
		// TODO Auto-generated method stub
		Product p = productDAO.getProductById(id);
		ProductDTO productDTO = new ProductDTO();
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		productDTO.setId(p.getId());
		productDTO.setName(p.getName());
		productDTO.setPrice(p.getPrice());
		productDTO.setQuantity(p.getQuantity());
		productDTO.setImageURL(p.getImageURL());
		
		categoryDTO.setId(p.getCategory().getId());
		categoryDTO.setName(p.getCategory().getName());
		productDTO.setCategory(categoryDTO);
		
		return productDTO;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return productDAO.countAll();
	}

	@Override
	public List<ProductDTO> listAll() {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.listAll();
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		
		for(Product p: products) {
			ProductDTO productDTO = new ProductDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			
			productDTO.setId(p.getId());
			productDTO.setName(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setImageURL(p.getImageURL());
			
			categoryDTO.setId(p.getCategory().getId());
			categoryDTO.setName(p.getCategory().getName());
			productDTO.setCategory(categoryDTO);
			
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}


	@Override
	public int countByName(String name) {
		// TODO Auto-generated method stub
		return productDAO.countByName(name);
	}

	@Override
	public List<ProductDTO> findByCategory(String name, int start, int length) {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.findByCategory(name, start, length);
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		
		for(Product p: products) {
			ProductDTO productDTO = new ProductDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			
			productDTO.setId(p.getId());
			productDTO.setName(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setImageURL(p.getImageURL());
			
			categoryDTO.setId(p.getCategory().getId());
			categoryDTO.setName(p.getCategory().getName());
			productDTO.setCategory(categoryDTO);
			
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}


	@Override
	public List<ProductDTO> getLatest(int start, int length) {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.getLatest(start, length);
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		
		for(Product p: products) {
			ProductDTO productDTO = new ProductDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			
			productDTO.setId(p.getId());
			productDTO.setName(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setImageURL(p.getImageURL());
			
			categoryDTO.setId(p.getCategory().getId());
			categoryDTO.setName(p.getCategory().getName());
			productDTO.setCategory(categoryDTO);
			
			productsDTO.add(productDTO);
		}
		
		return productsDTO;
	}

}
