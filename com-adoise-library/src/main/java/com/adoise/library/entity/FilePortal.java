package com.adoise.library.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.adoise.library.enums.FileType;
import com.adoise.library.enums.PortalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file_portal")
public class FilePortal extends FileModel{
	
	private PortalType type;

	public FilePortal() {
		super();
	}

	public FilePortal(Integer id, @NotEmpty String name, Date createAt, String extend, long size, FileType fileType, PortalType type) {
		super(id, name, createAt, extend, size, fileType);
	}
}
