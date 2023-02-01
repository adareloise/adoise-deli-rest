package com.adoise.library.enums;

import com.adoise.library.base.BaseEnum;

public enum PortalType implements BaseEnum {
	
	HEADER		(0),
	CORRUSEAL	(1);
	
    private final int value;

    PortalType (int value) {
		this.value = value;
	}

	public static PortalType fromValue (Integer n) {
		return BaseEnum.fromValue (PortalType.class, n);
	}

	@Override
	public final int intValue () {
		return value;
	}

}
