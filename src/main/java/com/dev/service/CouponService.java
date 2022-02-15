package com.dev.service;

import java.util.List;

import com.dev.model.CouponDTO;

public interface CouponService {
	public List<CouponDTO> search(String code, int start, int length);
	
	public List<CouponDTO> listAll();
	
	public CouponDTO findById(int id);
	
	public CouponDTO findByCode(String code);
	
	public void add(CouponDTO coupon);
	
	public void update(CouponDTO coupon);
	
	public void delete(int id);
	
	public int count();
}
