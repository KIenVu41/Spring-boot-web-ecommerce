package com.dev.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillProduct implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "quantity")
	@NotNull
	private int quantity;
	
	@Column(name = "price")
	@NotNull
	private long price;
	
	 @ManyToOne(cascade = CascadeType.MERGE,fetch=FetchType.LAZY)
	 @JoinColumn(name = "product_id", referencedColumnName = "id")
	 private Product product;
	 
	@ManyToOne(cascade = CascadeType.MERGE,fetch=FetchType.LAZY)
	@JoinColumn(name = "bill_id")
	private Bill bill;
}
