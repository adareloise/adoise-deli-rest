package com.adoise.library.base;

public interface BaseEnum {

	int intValue ();

	String name ();

	static <T extends BaseEnum> T fromValue (Class<T> clazz, Integer value) {
		if (value == null) {
			return null;
		}

		T[] constants = clazz.getEnumConstants ();
		for (T constant : constants) {
			if (constant.intValue () == value) {
				return constant;
			}
		}
		return null;
	}

	static <T extends BaseEnum> T fromName (Class<T> clazz, String name) {
		if (name == null) {
			return null;
		}

		T[] constants = clazz.getEnumConstants ();
		for (T constant : constants) {
			if (constant.name ().equalsIgnoreCase (name)) {
				return constant;
			}
		}
		return null;
	}

}
