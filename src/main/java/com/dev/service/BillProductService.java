package com.dev.service;

import java.util.List;

import com.dev.model.BillProductDTO;


public interface BillProductService {
	void add(BillProductDTO billProductDTO);

	void update(BillProductDTO billProductDTO);

	void delete(int id);

	List<BillProductDTO> pagination(int id,int start, int length);
	
	public int count();
}
