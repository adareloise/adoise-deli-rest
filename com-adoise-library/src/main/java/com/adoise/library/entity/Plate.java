package com.adoise.library.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import com.adoise.library.enums.ProductType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "plates")
@SuppressWarnings("serial")
public class Plate extends Product {
	
	
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "plate_ingredients",
            // The owning entity.
            joinColumns = @JoinColumn(
                    name = "plate_id", referencedColumnName = "id"
            ),
            // Non owning entity.
            inverseJoinColumns = @JoinColumn(
                    name = "ingrediente_id", referencedColumnName = "id"
            )
    )
	private List<Ingredient> ingredients = new ArrayList<>();
	
	public Plate() {}
	
	public Plate(Long id, @NotEmpty String name, ProductCat category, String subcategory, String description, ProductType type,
			@Range(min = 0, max = 100000) Integer price, boolean availability, Date createAt, Date updateAt,  FileProduct fileProduct, List<Ingredient> ingredients) {
		
		super(id, name, category, subcategory, description, type, price, availability, 
				createAt, updateAt, fileProduct);
		
		this.ingredients = ingredients;
	}

	public void addIngrediente(Ingredient ingrediente) {
		ingredients.add(ingrediente);
	}
	
	public void removeIngrediente(Ingredient ingrediente) {
		ingredients.remove(ingrediente);
	}
}
