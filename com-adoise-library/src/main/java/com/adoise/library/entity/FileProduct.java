package com.adoise.library.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.adoise.library.enums.FileType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file_products")
public class FileProduct extends FileModel{

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "product_id")
	private Product product;

	public FileProduct() {
		super();
	}

	public FileProduct(Integer id, @NotEmpty String name, Date createAt, String extend, long size, FileType fileType) {
		super(id, name, createAt, extend, size, fileType);
	}

}
