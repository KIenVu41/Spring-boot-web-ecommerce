package com.dev.service;

import java.util.List;

import com.dev.model.BillDTO;

public interface BillService {
	public List<BillDTO> search(String name, int start, int length);
	
	public List<BillDTO> listAll();
	
	public BillDTO findById(int id);
	
	public int add(BillDTO bill);
	
	public void update(BillDTO bill);
	
	public void delete(int id);
	
	public int count();
}
