package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CouponDAO;
import com.dev.entity.Bill;
import com.dev.entity.Coupon;
import com.dev.entity.User;
import com.dev.model.BillDTO;
import com.dev.model.CouponDTO;
import com.dev.service.CouponService;

@Service
@Transactional
public class CouponServiceImpl implements CouponService{
	
	@Autowired
	CouponDAO couponDAO;
	
	@Override
	public List<CouponDTO> search(String code, int start, int length) {
		// TODO Auto-generated method stub
		List<Coupon> coupons = couponDAO.search(code, start, length);
		List<CouponDTO> couponDTOs = new ArrayList<CouponDTO>();
		
		for(Coupon c : coupons) {		
			CouponDTO couponDTO = new CouponDTO();
			couponDTO.setId(c.getId());
			couponDTO.setCode(c.getCode());
			couponDTO.setDiscountPercentage(c.getDiscountPercentage());
			couponDTO.setExpiredDate(c.getExpiredDate());
			
			BillDTO billDTO = new BillDTO();		
			billDTO.setId(c.getBill().getId());	
			couponDTO.setBill(billDTO);
			
			couponDTOs.add(couponDTO);
		}
		
		return couponDTOs;
	}

	@Override
	public List<CouponDTO> listAll() {
		// TODO Auto-generated method stub
		List<Coupon> coupons = couponDAO.listAll();
		List<CouponDTO> couponDTOs = new ArrayList<CouponDTO>();
		
		for(Coupon c : coupons) {
			System.out.println(c);
			CouponDTO couponDTO = new CouponDTO();
			couponDTO.setId(c.getId());
			couponDTO.setCode(c.getCode());
			couponDTO.setDiscountPercentage(c.getDiscountPercentage());
			couponDTO.setExpiredDate(c.getExpiredDate());
			
			BillDTO billDTO = new BillDTO();
			billDTO.setId(c.getBill().getId());
			couponDTO.setBill(billDTO);
			
			couponDTOs.add(couponDTO);
		}
		
		return couponDTOs;
	}

	@Override
	public CouponDTO findById(int id) {
		// TODO Auto-generated method stub
		Coupon c = couponDAO.findById(id);
		CouponDTO couponDTO = new CouponDTO();
		
		couponDTO.setId(c.getId());
		couponDTO.setCode(c.getCode());
		couponDTO.setDiscountPercentage(c.getDiscountPercentage());
		couponDTO.setExpiredDate(c.getExpiredDate());
		
		BillDTO billDTO = new BillDTO();
		billDTO.setId(c.getBill().getId());
		couponDTO.setBill(billDTO);
		return couponDTO;
	}

	@Override
	public void add(CouponDTO couponDTO) {
		// TODO Auto-generated method stub
		Coupon coupon = new Coupon();
		
		coupon.setId(couponDTO.getId());
		coupon.setCode(couponDTO.getCode());
		coupon.setDiscountPercentage(couponDTO.getDiscountPercentage());
		coupon.setExpiredDate(couponDTO.getExpiredDate());
		
		couponDAO.add(coupon);
	}

	@Override
	public void update(CouponDTO couponDTO) {
		// TODO Auto-generated method stub
		Coupon coupon = couponDAO.findById(couponDTO.getId());
		Bill bill = new Bill();
		User user = new User();
		if(coupon != null) {
			coupon.setId(couponDTO.getId());
			coupon.setCode(couponDTO.getCode());
			coupon.setDiscountPercentage(couponDTO.getDiscountPercentage());
			coupon.setExpiredDate(couponDTO.getExpiredDate());
			bill.setCoupon(coupon);
			bill.setId(couponDTO.getBill().getId());
			bill.setBuyDate(couponDTO.getBill().getBuyDate());
			bill.setTotalPrice(couponDTO.getBill().getTotal());
			user.setId(couponDTO.getBill().getUserDTO().getId());
			user.setAddress(couponDTO.getBill().getUserDTO().getAddress());
			user.setEmail(couponDTO.getBill().getUserDTO().getEmail());
			user.setEnabled(couponDTO.getBill().getUserDTO().getEnabled());
			user.setGender(couponDTO.getBill().getUserDTO().getGender());
			user.setRole(couponDTO.getBill().getUserDTO().getRole());
			user.setName(couponDTO.getBill().getUserDTO().getName());
			user.setUsername(couponDTO.getBill().getUserDTO().getUsername());
			user.setPassword(couponDTO.getBill().getUserDTO().getPassword());
			bill.setUser(user);
			coupon.setBill(bill);
			couponDAO.update(coupon);
		}	
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Coupon coupon = couponDAO.findById(id);
		if(coupon != null) {
			couponDAO.delete(coupon);
		}	
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return couponDAO.count();
	}

	@Override
	public CouponDTO findByCode(String code) {
		// TODO Auto-generated method stub
		Coupon c = couponDAO.findByCode(code);
		CouponDTO couponDTO = new CouponDTO();
		
		couponDTO.setId(c.getId());
		couponDTO.setCode(c.getCode());
		couponDTO.setDiscountPercentage(c.getDiscountPercentage());
		couponDTO.setExpiredDate(c.getExpiredDate());
	  
		return couponDTO;
	}

}
