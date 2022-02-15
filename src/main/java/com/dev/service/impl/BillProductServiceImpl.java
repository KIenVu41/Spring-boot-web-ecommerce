package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.BillProductDAO;
import com.dev.entity.Bill;
import com.dev.entity.BillProduct;
import com.dev.entity.Product;
import com.dev.model.BillDTO;
import com.dev.model.BillProductDTO;
import com.dev.model.ProductDTO;
import com.dev.service.BillProductService;

@Service
@Transactional
public class BillProductServiceImpl implements BillProductService {
	@Autowired
	BillProductDAO billProductDAO;
	
	@Override
	public void add(BillProductDTO billProductDTO) {
		// TODO Auto-generated method stub
		BillProduct billProduct = new BillProduct();
		billProduct.setPrice(billProductDTO.getPrice());
		billProduct.setQuantity(billProductDTO.getQuantity());
		
		Bill bill = new Bill();
		bill.setId(billProductDTO.getBill().getId());
		billProduct.setBill(bill);
		
		Product product = new Product();
		product.setId(billProductDTO.getProduct().getId());

		billProduct.setProduct(product);
		
		billProductDAO.add(billProduct);
	}

	@Override
	public void update(BillProductDTO billProductDTO) {
		// TODO Auto-generated method stub
		BillProduct billProduct = billProductDAO.findById(billProductDTO.getId());
		if (billProduct != null) {
			billProduct.setId(billProductDTO.getId());
			billProduct.setPrice(billProductDTO.getPrice());
			billProduct.setQuantity(billProductDTO.getQuantity());
			
			Bill bill = new Bill();
			bill.setId(billProductDTO.getBill().getId());
			billProduct.setBill(bill);
			
			Product product = new Product();
			product.setId(billProductDTO.getProduct().getId());

			billProduct.setProduct(product);
			
			billProductDAO.update(billProduct);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		BillProduct billProduct = billProductDAO.findById(id);
		if (billProduct != null) {
			billProductDAO.delete(billProduct);
		}
	}


	@Override
	public List<BillProductDTO> pagination(int id, int start, int length) {
		// TODO Auto-generated method stub
		List<BillProduct> billProducts = billProductDAO.pagination(id,start, length );
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();

		for (BillProduct billProduct : billProducts) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setId(billProduct.getId());
			billProductDTO.setQuantity(billProduct.getQuantity());
			billProductDTO.setPrice(billProduct.getPrice());

			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(billProduct.getProduct().getId());
			productDTO.setName(billProduct.getProduct().getName());
			productDTO.setImageURL(billProduct.getProduct().getImageURL());
			productDTO.setPrice(billProduct.getProduct().getPrice());
			billProductDTO.setProduct(productDTO);
			
			BillDTO billDTO = new BillDTO();
			billDTO.setId(billProduct.getBill().getId());
			billProductDTO.setBill(billDTO);
			
			billProductDTOs.add(billProductDTO);
			
		}
		return billProductDTOs;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return billProductDAO.count();
	}
}
