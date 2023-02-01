package com.adoise.library.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import com.adoise.library.enums.ProductType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "drinks")
@SuppressWarnings("serial")
public class Drink extends Product{
	
	private int cc;
	
	public Drink() {}
	
	public Drink (Long id, @NotEmpty String name, ProductCat category, String subcategory, 	String description, ProductType type,
			@Range(min = 0, max = 100000) Integer price, boolean availability, Date createAt, Date updateAt, FileProduct fileProduct, String taste, int cc) {
		super(id, name, category, subcategory, description, type, price, availability, createAt, updateAt, fileProduct);
		
		this.cc = cc;
	}
}
