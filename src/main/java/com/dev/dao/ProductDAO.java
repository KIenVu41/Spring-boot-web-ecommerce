package com.dev.dao;

import java.util.List;

import com.dev.entity.Product;

public interface ProductDAO {
	public List<Product> search(String name, int start, int length, int mode);
	
	public List<Product> listAll();
	
	public List<Product> findByCategory(String name, int start, int length);
	
	public List<Product> getLatest(int start, int length);
	
	public void add(Product product);
	
	public void update(Product product);
	
	public void delete(Product product);
	
	public Product getProductById(int id);
	
	public int countAll();
	
	public int countByName(String name);
}
