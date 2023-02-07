package com.adoise.library.base;

public abstract class UtilityClass {

	protected UtilityClass () {
		// Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract utility classes, which can be extended, should not have public constructors.
		throw new IllegalStateException ("Abstract Class");
	}

}
