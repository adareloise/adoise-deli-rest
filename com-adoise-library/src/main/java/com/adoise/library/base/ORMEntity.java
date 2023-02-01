package com.adoise.library.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface ORMEntity extends Serializable {

	AbstractData toDto ();

	default AbstractData toVO (boolean includeChildren) {
		return this.toDto ();
	}

	default <T extends AbstractData, Y extends ORMEntity> List<T> toDtoList (Class<T> type, List<Y> entities) {
		List<T> dtos = new ArrayList<>();
		if ((entities == null) || (entities.isEmpty ())) {
			return dtos;
		}

		for (ORMEntity entity : entities) {
			dtos.add (type.cast (entity.toDto ()));
		}

		return dtos;
	}

}
