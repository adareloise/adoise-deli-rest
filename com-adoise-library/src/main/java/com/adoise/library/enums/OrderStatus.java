package com.adoise.library.enums;

import com.adoise.library.base.BaseEnum;

public enum OrderStatus implements BaseEnum {
	
    LISTADAS 	(0),
    EN_CURSO 	(1),
    COMPLETADA 	(2);
	
	private final int value;

	OrderStatus (int value) {
		this.value = value;
	}

	public static OrderStatus fromValue (Integer n) {
		return BaseEnum.fromValue (OrderStatus.class, n);
	}

	@Override
	public final int intValue () {
		return value;
	}
}
