package com.dev.dao;

import java.util.List;

import com.dev.entity.Bill;

public interface BillDAO {
	public List<Bill> search(String name, int start, int length);
	
	public List<Bill> listAll();
	
	public int add(Bill bill);
	
	public void update(Bill bill);
	
	public void delete(Bill bill);
	
	public int count();
	
	public Bill findById(int id);
}
