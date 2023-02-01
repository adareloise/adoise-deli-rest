package com.adoise.library.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

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
@Table(name = "ingredients_category")
public class IngredientCat  implements Serializable{

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
	
	private boolean status;
	
	@OneToMany( mappedBy = "category",
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Ingredient> items = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public void addIngrediente(Ingredient ingrediente) {
		items.add(ingrediente);
	}
	
	public void removeIngrediente(Ingredient ingrediente) {
		items.remove(ingrediente);
	}
}
