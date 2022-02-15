package com.dev.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
	private int id;
	private Date buyDate;
	private long total;
	private UserDTO userDTO;
}
