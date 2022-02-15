package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.UserDAO;
import com.dev.entity.User;
import com.dev.model.UserDTO;
import com.dev.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<UserDTO> search(String name, int start, int length) {
		List<User> users = userDAO.search(name, start, length);
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		for (User u : users) {
			UserDTO userDTO = new UserDTO();
			
			userDTO.setId(u.getId());
			userDTO.setName(u.getName());
			userDTO.setImageURL(u.getImageURL());
			userDTO.setPassword(u.getPassword());
			userDTO.setUsername(u.getUsername());
			userDTO.setAddress(u.getAddress());
			userDTO.setPhone(u.getPhone());
			userDTO.setRole(u.getRole());
			userDTO.setGender(u.getGender());
			userDTO.setEnabled(u.getEnabled());
			userDTO.setEmail(u.getEmail());
			
			usersDTO.add(userDTO);
		}
		
		return usersDTO;
	}
	

	@Override
	public void add(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = new User();
		
		user.setName(userDTO.getName());
		user.setImageURL(userDTO.getImageURL());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setUsername(userDTO.getUsername());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setRole(userDTO.getRole());
		user.setGender(userDTO.getGender());
		user.setEnabled(true);
		user.setEmail(userDTO.getEmail());
		
		
		userDAO.add(user);
	}

	@Override
	public void update(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userDAO.getUserById(userDTO.getId());
		if(user != null) {	
			
			user.setId(userDTO.getId());
			user.setName(userDTO.getName());
			user.setImageURL(userDTO.getImageURL());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setUsername(userDTO.getUsername());
			user.setAddress(userDTO.getAddress());
			user.setPhone(userDTO.getPhone());
			user.setRole(userDTO.getRole());
			user.setGender(userDTO.getGender());
			user.setEnabled(true);
			user.setEmail(userDTO.getEmail());
		
			userDAO.update(user);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		User user = userDAO.getUserById(id);
		if(user != null) {
			userDAO.delete(user);
		}
	}

	@Override
	public UserDTO getUserById(int id) {
		// TODO Auto-generated method stub
		User u = userDAO.getUserById(id);
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(u.getId());
		userDTO.setName(u.getName());
		userDTO.setImageURL(u.getImageURL());
		userDTO.setPassword(u.getPassword());
		userDTO.setUsername(u.getUsername());
		userDTO.setAddress(u.getAddress());
		userDTO.setPhone(u.getPhone());
		userDTO.setRole(u.getRole());
		userDTO.setGender(u.getGender());
		userDTO.setEnabled(u.getEnabled());
		userDTO.setEmail(u.getEmail());
		
		return userDTO;
	}
	
	 @Override
	   public void registerNewUserAccount(UserDTO userDTO) throws Exception{
	        /*if (userDAO.findByEmail(userDTO.getEmail()) != null) {
	            throw new Exception("There is an account with that email address: "
	              + userDTO.getEmail());
	        }*/
	        User user = new User();
	        
	    	user.setName(userDTO.getName());
			user.setImageURL(userDTO.getImageURL());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setUsername(userDTO.getUsername());
			user.setAddress(userDTO.getAddress());
			user.setPhone(userDTO.getPhone());
			user.setRole("ROLE_ADMIN");
			user.setGender(userDTO.getGender());
			user.setEnabled(true);
			user.setEmail(userDTO.getEmail());
			
			userDAO.add(user);
	    }

	@Override
	public void changeEnable(int id, boolean status) {
		// TODO Auto-generated method stub
		userDAO.changeEnable(id, status);
	}


	@Override
	public int count() {
		// TODO Auto-generated method stub
		return userDAO.count();
	}


	@Override
	public List<UserDTO> listAll() {
		// TODO Auto-generated method stub
		List<User> users = userDAO.listAll();
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		for (User u : users) {
			UserDTO userDTO = new UserDTO();
			
			userDTO.setId(u.getId());
			userDTO.setName(u.getName());
			userDTO.setImageURL(u.getImageURL());
			userDTO.setPassword(u.getPassword());
			userDTO.setUsername(u.getUsername());
			userDTO.setAddress(u.getAddress());
			userDTO.setPhone(u.getPhone());
			userDTO.setRole(u.getRole());
			userDTO.setGender(u.getGender());
			userDTO.setEnabled(u.getEnabled());
			userDTO.setEmail(u.getEmail());
			
			usersDTO.add(userDTO);
		}
		
		return usersDTO;
	}


	@Override
	public void signup(UserDTO userDTO) throws Exception {
		// TODO Auto-generated method stub
		 User user = new User();
	        
	    	user.setName(userDTO.getName());
			user.setImageURL(userDTO.getImageURL());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setUsername(userDTO.getUsername());
			user.setAddress(userDTO.getAddress());
			user.setPhone(userDTO.getPhone());
			user.setRole("ROLE_USER");
			user.setGender(userDTO.getGender());
			user.setEnabled(true);
			user.setEmail(userDTO.getEmail());
			
			userDAO.add(user);
	}


	@Override
	public UserDTO getUserByUsername(String username) {
		// TODO Auto-generated method stub
		User u = userDAO.getUserByUsername(username);
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(u.getId());
		userDTO.setName(u.getName());
		userDTO.setImageURL(u.getImageURL());
		userDTO.setPassword(u.getPassword());
		userDTO.setUsername(u.getUsername());
		userDTO.setAddress(u.getAddress());
		userDTO.setPhone(u.getPhone());
		userDTO.setRole(u.getRole());
		userDTO.setGender(u.getGender());
		userDTO.setEnabled(u.getEnabled());
		userDTO.setEmail(u.getEmail());
		
		return userDTO;
	}

}
