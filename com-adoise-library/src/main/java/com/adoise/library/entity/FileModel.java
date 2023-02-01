package com.adoise.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.adoise.library.enums.FileType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FileModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	private String extend;
	
	private FileType fileType;
	
	private long size;
		
	public FileModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileModel(Integer id, @NotEmpty String name, Date createAt, String extend, long size, FileType fileType) {
		super();
		this.id = id;
		this.name = name;
		this.extend = extend;
		this.size = size;
		this.fileType = fileType;
		this.createAt = createAt;
	}
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
}
