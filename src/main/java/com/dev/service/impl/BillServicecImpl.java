package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.BillDAO;
import com.dev.entity.Bill;
import com.dev.entity.User;
import com.dev.model.BillDTO;
import com.dev.model.UserDTO;
import com.dev.service.BillService;

@Service
@Transactional
public class BillServicecImpl implements BillService {
	@Autowired
	BillDAO billDAO;
	
	@Override
	public List<BillDTO> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		List<Bill> bills = billDAO.search(name, start, length);
		List<BillDTO> billsDTO = new ArrayList<BillDTO>();
		
		for(Bill b: bills) {
			BillDTO billDTO = new BillDTO();
			UserDTO userDTO = new UserDTO();
			
			billDTO.setId(b.getId());
			billDTO.setBuyDate(b.getBuyDate());
			billDTO.setTotal(b.getTotalPrice());
			userDTO.setId(b.getUser().getId());
			userDTO.setName(b.getUser().getUsername());
			billDTO.setUserDTO(userDTO);
			billsDTO.add(billDTO);
		}
		return billsDTO;
	}

	@Override
	public List<BillDTO> listAll() {
		// TODO Auto-generated method stub
		List<Bill> bills = billDAO.listAll();
		List<BillDTO> billsDTO = new ArrayList<BillDTO>();
		
		for(Bill b: bills) {
			BillDTO billDTO = new BillDTO();
			UserDTO userDTO = new UserDTO();
			
			billDTO.setId(b.getId());
			billDTO.setBuyDate(b.getBuyDate());
			billDTO.setTotal(b.getTotalPrice());
			userDTO.setId(b.getUser().getId());
			userDTO.setName(b.getUser().getName());
			billDTO.setUserDTO(userDTO);
			
			billsDTO.add(billDTO);
		}
		return billsDTO;
	}

	@Override
	public int add(BillDTO billDTO) {
		// TODO Auto-generated method stub
		Bill bill = new Bill();
		User user = new User();
		
		bill.setId(billDTO.getId());
		bill.setBuyDate(billDTO.getBuyDate());
		bill.setTotalPrice(billDTO.getTotal());
		user.setId(billDTO.getUserDTO().getId());
		bill.setUser(user);
		
		return billDAO.add(bill);
	}

	@Override
	public void update(BillDTO billDTO) {
		// TODO Auto-generated method stu		
		Bill bill = billDAO.findById(billDTO.getId());
		User user = new User();
		if(bill != null) {
			bill.setId(billDTO.getId());
			bill.setBuyDate(billDTO.getBuyDate());
			bill.setTotalPrice(billDTO.getTotal());
			user.setId(billDTO.getUserDTO().getId());
			bill.setUser(user);
		
			billDAO.update(bill);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Bill bill = billDAO.findById(id);
		if(bill != null) {
			billDAO.delete(bill);
		}
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return billDAO.count();
	}

	@Override
	public BillDTO findById(int id) {
		// TODO Auto-generated method stub
		Bill b = billDAO.findById(id);
		BillDTO billDTO = new BillDTO();
		UserDTO userDTO = new UserDTO();
		

		billDTO.setId(b.getId());
		billDTO.setBuyDate(b.getBuyDate());
		billDTO.setTotal(b.getTotalPrice());
		userDTO.setId(b.getUser().getId());
		userDTO.setName(b.getUser().getName());
		billDTO.setUserDTO(userDTO);
		return billDTO;
	}

}
