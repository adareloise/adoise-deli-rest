package com.adoise.library.entity.items;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adoise.library.entity.Product;
import com.adoise.library.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tickets_items")
public class ItemTicket implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantity;
	
	private Integer quantityIngredients;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Ticket ticket;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Product product;

	public Double calculateLoad() {
		return quantity.doubleValue() * this.product.getPrice();
	}
	
	private static final long serialVersionUID = 1L;
}
