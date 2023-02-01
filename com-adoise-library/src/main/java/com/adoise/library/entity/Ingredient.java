package com.adoise.library.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adoise.library.entity.items.ItemOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	private String name;

	private String meter;
		
	private int quantity;
	
	private boolean availability;
	
	private int purchasePrice;
	
	private int salePrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private IngredientCat category;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<Plate> plates = new ArrayList<>();
    
    @ManyToMany(targetEntity=ItemOrder.class, mappedBy = "ingredients")
	private  List<ItemOrder> itemsOrder  = new ArrayList<>();;
	
	private static final long serialVersionUID = 1L;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	public void addItemOrder(ItemOrder item) {
		itemsOrder.add(item);
	}
	
	public void removeItemOrder(ItemOrder item) {
		itemsOrder.remove(item);
	}
	
	public void addPlate(Plate plate) {
		plates.add(plate);
	}
	
	public void removePlate(Plate plate) {
		plates.remove(plate);
	}
	
}