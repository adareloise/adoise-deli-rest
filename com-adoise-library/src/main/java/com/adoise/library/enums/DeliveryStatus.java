package com.adoise.library.enums;

import com.adoise.library.base.BaseEnum;

public enum DeliveryStatus  implements BaseEnum  {
	
    EN_COCINA 		(0),
    PARA_REPARTO	(1),
    EN_REPARTO 		(2),
    COMPLETADO 		(3);
    
    private final int value;

	DeliveryStatus (int value) {
		this.value = value;
	}

	public static DeliveryStatus fromValue (Integer n) {
		return BaseEnum.fromValue (DeliveryStatus.class, n);
	}

	@Override
	public final int intValue () {
		return value;
	}
}
