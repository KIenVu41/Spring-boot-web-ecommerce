package com.dev.service;

import java.util.List;

import com.dev.entity.User;
import com.dev.model.UserDTO;

public interface UserService {
	List<UserDTO> search(String name, int start, int length);
	
	public void add(UserDTO userDTO);
	
	public void update(UserDTO userDTO);
	
	public void delete(int id);
	
	public UserDTO getUserById(int id);
	//admin
	public void registerNewUserAccount(UserDTO userDto) throws Exception;
	//member
	public void signup(UserDTO userDTO) throws Exception;
	
	public void changeEnable(int id, boolean status);
	
	public int count();
	
	public UserDTO getUserByUsername(String username);
	
	public List<UserDTO> listAll();
}
