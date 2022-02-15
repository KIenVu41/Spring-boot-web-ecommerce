package com.dev.dao;

import java.util.List;

import com.dev.entity.BillProduct;

public interface BillProductDAO {
	void add(BillProduct billProduct);

	void update(BillProduct billProduct);

	void delete(BillProduct billProduct);

	BillProduct findById(int id);

	List<BillProduct> pagination(int id,int start, int length);
	
	public int count();
}
