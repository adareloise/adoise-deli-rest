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

@Entity
@Table(name = "file_clients")
public class FileClient extends FileModel {
	
	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "client_id")
	private Client client;

	public FileClient() {
		super();
	}

	public FileClient(Integer id, @NotEmpty String name, Date createAt, String extend, long size, FileType fileType, Client client) {
		super(id, name, createAt, extend, size, fileType);
		this.client = client;
	}
	
}
