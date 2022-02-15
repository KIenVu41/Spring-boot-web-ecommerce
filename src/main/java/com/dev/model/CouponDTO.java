package com.dev.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
	private int id;
	private String code;
	private int discountPercentage;
	private Date expiredDate;
	private BillDTO bill;
}
