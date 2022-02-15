package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.UserDAO;
import com.dev.entity.User;

@Service
@Transactional
public class UserLoginServiceImpl implements UserDetailsService {
	@Autowired
	UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDAO.getUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("no user with username" + username);
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword().trim(), true, true, true, true, authorities);
		
		return userDetails;
	}
	
}
