package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.ReviewDAO;
import com.dev.entity.Product;
import com.dev.entity.Review;
import com.dev.entity.User;
import com.dev.model.ProductDTO;
import com.dev.model.ReviewDTO;
import com.dev.model.UserDTO;
import com.dev.service.ReviewService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewDAO reviewDAO;

	@Override
	public List<ReviewDTO> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		List<Review> reviews = reviewDAO.search(name, start, length);
		List<ReviewDTO> reviewsDTO = new ArrayList<ReviewDTO>();
		
		for(Review rv : reviews) {
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setId(rv.getId());
			reviewDTO.setStarCounter(rv.getStarCounter());
			UserDTO userDTO = new UserDTO();
			ProductDTO productDTO = new ProductDTO();
			userDTO.setId(rv.getUser().getId());
			userDTO.setName(rv.getUser().getName());
			productDTO.setId(rv.getProduct().getId());
			productDTO.setName(rv.getProduct().getName());
			reviewDTO.setUser(userDTO);
			reviewDTO.setProduct(productDTO);
			
			reviewsDTO.add(reviewDTO);
		}
		return reviewsDTO;
	}

	@Override
	public List<ReviewDTO> listAll() {
		// TODO Auto-generated method stub
		List<Review> reviews = reviewDAO.listAll();
		List<ReviewDTO> reviewsDTO = new ArrayList<ReviewDTO>();
		
		for(Review rv : reviews) {
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setId(rv.getId());
			reviewDTO.setStarCounter(rv.getStarCounter());
			UserDTO userDTO = new UserDTO();
			ProductDTO productDTO = new ProductDTO();
			userDTO.setId(rv.getUser().getId());
			userDTO.setName(rv.getUser().getName());
			productDTO.setId(rv.getProduct().getId());
			productDTO.setName(rv.getProduct().getName());
			reviewDTO.setUser(userDTO);
			reviewDTO.setProduct(productDTO);
			
			reviewsDTO.add(reviewDTO);
		}
		return reviewsDTO;
	}

	@Override
	public void add(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		Review review = new Review();
		User user = new User();
		Product product = new Product();
		
		review.setId(reviewDTO.getId());
		review.setStarCounter(reviewDTO.getStarCounter());
		user.setId(reviewDTO.getUser().getId());
		product.setId(reviewDTO.getProduct().getId());
		review.setUser(user);
		review.setProduct(product);
		
		reviewDAO.add(review);
	}

	@Override
	public void update(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		Review review = reviewDAO.findById(reviewDTO.getId());
		User user = new User();
		Product product = new Product();
		
		if(review != null) {
			review.setId(reviewDTO.getId());
			review.setStarCounter(reviewDTO.getStarCounter());
			user.setId(reviewDTO.getUser().getId());
			product.setId(reviewDTO.getProduct().getId());
			review.setUser(user);
			review.setProduct(product);
			
			reviewDAO.update(review);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Review review = reviewDAO.findById(id);
		if(review != null) {
			reviewDAO.delete(review);
		}
	}

	@Override
	public ReviewDTO findById(int id) {
		// TODO Auto-generated method stub
		Review rv = reviewDAO.findById(id);
		ReviewDTO reviewDTO = new ReviewDTO();
		
		reviewDTO.setId(rv.getId());
		reviewDTO.setStarCounter(rv.getStarCounter());
		
		UserDTO userDTO = new UserDTO();
		ProductDTO productDTO = new ProductDTO();
		userDTO.setId(rv.getUser().getId());
		userDTO.setName(rv.getUser().getName());
		
		productDTO.setId(rv.getProduct().getId());
		productDTO.setName(rv.getProduct().getName());
		
		reviewDTO.setUser(userDTO);
		reviewDTO.setProduct(productDTO);
		
		return reviewDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return reviewDAO.count();
	}
	
}
