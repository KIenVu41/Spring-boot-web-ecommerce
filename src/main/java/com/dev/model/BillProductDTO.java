package com.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillProductDTO {
	private int id;
	private int quantity;
	private long price;
	private BillDTO bill;
	private ProductDTO product;
}
