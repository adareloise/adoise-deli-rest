package com.adoise.library.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.adoise.library.entity.items.ItemSubcategoryProduct;
import com.adoise.library.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "products_category")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductCat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	@NotEmpty
	@Column(unique=true)
	private String name;
	
	@Enumerated(value = EnumType.STRING)
 	private ProductType type;
	
	@OneToMany( mappedBy = "category",
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	@JsonManagedReference
    private List<ItemSubcategoryProduct> subcategories = new ArrayList<>();
	
	private boolean status;
	
	@OneToMany( mappedBy = "category",
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> items = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
		
	public void addProduct(Product product) {
		items.add(product);
	}
	
	public void removeProduct(Product product) {
		items.remove(product);
	}
	
	public void addSubcategory(ItemSubcategoryProduct item) {
		subcategories.add(item);
	}
	
	public void removeSubcategory(ItemSubcategoryProduct item) {
		subcategories.remove(item);
	}
}
