package com.dev.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int id;
	private String name;
	private String username;
	private String password;
	private String phone;
	private String email;
	private String address;
	private MultipartFile image;
	private String imageURL;
	private boolean enabled;
	private String gender;
	private String role;
	
	public boolean getEnabled() {
		return enabled;
	}
}
