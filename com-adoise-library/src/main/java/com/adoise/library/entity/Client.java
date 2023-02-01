
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.adoise.library.enums.Gender;
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
@Table(name = "clients")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date dateOfBirth; 
    
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;
    
	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "update_at")
	private Date updateAt;
		
	@OneToMany( mappedBy = "client",
				fetch = FetchType.LAZY, 
				cascade = CascadeType.ALL, 
				orphanRemoval = true)
	@JsonManagedReference
	private List<Ticket> tickets = new ArrayList<>();

	@OneToOne( fetch = FetchType.LAZY, 
			   cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private FileClient fileClient;
	
	@OneToMany( mappedBy = "client",
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<Adress> adresses = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		updateAt = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		updateAt = new Date();
	} 
	
	public void addAdress(Adress adress) {
		adresses.add(adress);
	}
	
	public void removeAdress(Adress adress) {
		adresses.remove(adress);
	}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}
	
	public void removeTicket(Ticket ticket) {
		tickets.remove(ticket);
	}
	
}
