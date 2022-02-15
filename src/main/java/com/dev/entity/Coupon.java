package com.dev.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	@NotNull
	private String code;
	
	@Column(name = "discount_Percentage")
	@NotNull
	private int discountPercentage;
	
	 @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	 @JoinColumn(name = "bill_id") 
	 private Bill bill;
	
	@Column(name = "expired_Date")
	@NotNull
	private Date expiredDate;
	
}
