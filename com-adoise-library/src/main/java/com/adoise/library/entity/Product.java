package com.adoise.library.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import com.adoise.library.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Enumerated(value = EnumType.STRING)
 	private ProductType type;
	
	private Integer price;
	
	private boolean availability;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "update_at")
	private Date updateAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private ProductCat category;
	
	private String subcategory;
	
	private String description;
	
	@OneToOne( fetch = FetchType.LAZY, 
			   cascade = CascadeType.ALL)
	@JoinColumn( name="file_id")
	private FileProduct fileProduct;
	
	public Product() {
		super();
	}
	
	public Product(Long id, @NotEmpty String name, ProductCat category, String subcategory, String description, ProductType type,
			@Range(min = 0, max = 100000) Integer price, boolean availability, Date createAt, Date updateAt, FileProduct fileProduct) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.subcategory = subcategory;
		this.description = description;
		this.type = type;
		this.price = price;
		this.availability = availability;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.fileProduct = fileProduct;
	}
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
		updateAt = new Date();

	}
	
	@PreUpdate
	public void preUpdate() {
		updateAt = new Date();
	} 

}
