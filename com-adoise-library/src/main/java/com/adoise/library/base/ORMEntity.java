package com.adoise.library.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface ORMEntity extends Serializable {

	AbstractData toVO ();

	default AbstractData toVO (boolean includeChildren) {
		return this.toVO ();
	}

	default <T extends AbstractData, Y extends ORMEntity> List<T> toVOList (Class<T> type, List<Y> entities) {
		List<T> vos = new ArrayList<> ();
		if ((entities == null) || (entities.isEmpty ())) {
			return vos;
		}

		for (ORMEntity entity : entities) {
			vos.add (type.cast (entity.toVO ()));
		}

		return vos;
	}

}