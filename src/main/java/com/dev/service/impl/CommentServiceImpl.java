package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CommentDAO;
import com.dev.entity.Comment;
import com.dev.entity.Product;
import com.dev.entity.User;
import com.dev.model.CommentDTO;
import com.dev.model.ProductDTO;
import com.dev.model.UserDTO;
import com.dev.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDAO commentDAO;
	
	@Override
	public List<CommentDTO> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		List<Comment> comments = commentDAO.search(name, start, length);
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for(Comment c : comments) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(c.getId());
			commentDTO.setContent(c.getContent());
			
			UserDTO userDTO = new UserDTO();
			ProductDTO productDTO = new ProductDTO();
			userDTO.setId(c.getUser().getId());
			userDTO.setName(c.getUser().getName());
			productDTO.setId(c.getProduct().getId());
			productDTO.setName(c.getProduct().getName());
			
			commentDTO.setUser(userDTO);
			commentDTO.setProduct(productDTO);
			
			commentsDTO.add(commentDTO);
		}
		return commentsDTO;
	}

	@Override
	public List<CommentDTO> listAll() {
		// TODO Auto-generated method stub
		List<Comment> comments = commentDAO.listAll();
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for(Comment c : comments) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(c.getId());
			commentDTO.setContent(c.getContent());
			
			UserDTO userDTO = new UserDTO();
			ProductDTO productDTO = new ProductDTO();
			userDTO.setId(c.getUser().getId());
			userDTO.setName(c.getUser().getName());
			productDTO.setId(c.getProduct().getId());
			productDTO.setName(c.getProduct().getName());
			
			commentDTO.setUser(userDTO);
			commentDTO.setProduct(productDTO);
			
			commentsDTO.add(commentDTO);
		}
		return commentsDTO;
	}

	@Override
	public CommentDTO findById(int id) {
		// TODO Auto-generated method stub
		Comment c = commentDAO.findById(id);
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(c.getId());
		commentDTO.setContent(c.getContent());
		
		UserDTO userDTO = new UserDTO();
		ProductDTO productDTO = new ProductDTO();
		userDTO.setId(c.getUser().getId());
		userDTO.setName(c.getUser().getName());
		productDTO.setId(c.getProduct().getId());
		productDTO.setName(c.getProduct().getName());
		
		commentDTO.setUser(userDTO);
		commentDTO.setProduct(productDTO);
		return commentDTO;
	}

	@Override
	public void add(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		Comment comment = new Comment();
		User user = new User();
		Product product = new Product();
		
		comment.setId(commentDTO.getId());
		comment.setContent(commentDTO.getContent());
		user.setId(commentDTO.getUser().getId());
		product.setId(commentDTO.getProduct().getId());
		comment.setUser(user);
		comment.setProduct(product);
		
		commentDAO.add(comment);
	}

	@Override
	public void update(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		Comment comment = commentDAO.findById(commentDTO.getId());
		User user = new User();
		Product product = new Product();
		if(comment != null) {
			comment.setId(commentDTO.getId());
			comment.setContent(commentDTO.getContent());
			user.setId(commentDTO.getUser().getId());
			user.setName(commentDTO.getUser().getName());
			product.setId(commentDTO.getProduct().getId());
			product.setName(commentDTO.getProduct().getName());
			comment.setUser(user);
			comment.setProduct(product);
			
			commentDAO.update(comment);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Comment comment = commentDAO.findById(id);
		if(comment != null) {
			commentDAO.delete(comment);
		}
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return commentDAO.count();
	}

	@Override
	public List<CommentDTO> findByProductId(int id) {
		// TODO Auto-generated method stub
		List<Comment> comments = commentDAO.findByProductId(id);
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for(Comment c : comments) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(c.getId());
			commentDTO.setContent(c.getContent());
			
			UserDTO userDTO = new UserDTO();
			ProductDTO productDTO = new ProductDTO();
			userDTO.setId(c.getUser().getId());
			userDTO.setEmail(c.getUser().getEmail());
			productDTO.setId(c.getProduct().getId());
			
			commentDTO.setUser(userDTO);
			commentDTO.setProduct(productDTO);
			
			commentsDTO.add(commentDTO);
		}
		return commentsDTO;
	}

}
