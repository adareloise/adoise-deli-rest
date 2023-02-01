package com.adoise.library.enums;

import com.adoise.library.base.BaseEnum;

public enum FileType implements BaseEnum{
	FILEPRODUCT	(0),
	FILECLIENT	(1),
	FILEPORTAL	(2);
	
    private final int value;

	FileType (int value) {
		this.value = value;
	}

	public static FileType fromValue (Integer n) {
		return BaseEnum.fromValue (FileType.class, n);
	}

	@Override
	public final int intValue () {
		return value;
	}
}
