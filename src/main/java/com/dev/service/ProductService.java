package com.dev.service;

import java.util.List;

import com.dev.model.ProductDTO;

public interface ProductService {
	public List<ProductDTO> search(String name, int start, int length, int mode);
	
	public List<ProductDTO> listAll();
	
	public List<ProductDTO> findByCategory(String name, int start, int length);
	
	public List<ProductDTO> getLatest(int start, int length);
	
	public void add(ProductDTO productDTO);
	
	public void update(ProductDTO productDTO);
	
	public void delete(int id);
	
	public ProductDTO getProductById(int id);
	
	public int countAll();
	
	public int countByName(String name);
}
