package com.dev.dao;

import java.util.List;

import com.dev.entity.Coupon;

public interface CouponDAO {
	public List<Coupon> search(String code, int start, int length);
	
	public List<Coupon> listAll();
	
	public void add(Coupon coupon);
	
	public void update(Coupon coupon);
	
	public void updateBill(Coupon coupon);
	
	public void delete(Coupon coupon);
	
	public int count();
	
	public Coupon findById(int id);
	
	public Coupon findByCode(String code);
}
