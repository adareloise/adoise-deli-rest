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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adoise.library.entity.items.ItemOrder;
import com.adoise.library.enums.OrderStatus;
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
@Table(name = "orders")
public class Order  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	@Column(name="time_at")
	@Temporal(TemporalType.TIMESTAMP)
    private Date timeAt;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	private OrderStatus status; 

	@OneToMany( mappedBy = "order",	cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<ItemOrder> items = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		timeAt = new Date();
	}	

	public void addItemOrder(ItemOrder item) {
		this.items.add(item);
	}

	public void removeItemOrder(ItemOrder item) {
		items.remove(item);
	}
	
	private static final long serialVersionUID = 1L;

}
