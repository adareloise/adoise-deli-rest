package com.adoise.library.entity.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adoise.library.entity.Ingredient;
import com.adoise.library.entity.Order;
import com.adoise.library.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_item")
public class ItemOrder  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
	private Integer quantity;
	
	private String msg;
	
	@ManyToMany(targetEntity=Ingredient.class, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "order_ingredients",
            joinColumns = @JoinColumn(
                    name = "order_item_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ingrediente_id", referencedColumnName = "id"
            )
    )
	private List<Ingredient> ingredients = new ArrayList<>();
		
	private static final long serialVersionUID = 1L;
	
	public void addIngrediente(Ingredient ingrediente) {
		ingredients.add(ingrediente);
	}
	
	public void removeIngrediente(Ingredient ingrediente) {
		ingredients.remove(ingrediente);
	}

}
