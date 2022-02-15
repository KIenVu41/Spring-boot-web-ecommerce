package com.dev.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private int id;
	private String name;
	private long price;
	private int quantity;
	private MultipartFile image;
	private String imageURL;
	private CategoryDTO category;
}
