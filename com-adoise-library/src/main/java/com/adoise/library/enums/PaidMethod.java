package com.adoise.library.enums;

import com.adoise.library.base.BaseEnum;

public enum PaidMethod implements BaseEnum{
    
	TRANSFERENCIA 	(0),
    EFECTIVO 		(1),
    WEBPAY 			(2),
    TRANSBANK 		(3);
    
    private final int value;

	PaidMethod (int value) {
		this.value = value;
	}

	public static PaidMethod fromValue (Integer n) {
		return BaseEnum.fromValue (PaidMethod.class, n);
	}

	@Override
	public final int intValue () {
		return value;
	}
}
