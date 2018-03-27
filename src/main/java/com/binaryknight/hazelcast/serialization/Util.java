package com.binaryknight.hazelcast.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.ZoneId;
import java.util.Collection;
import java.util.stream.Collectors;

public enum Util {

	;

	static final String INSTANT_SUFFIX = "Instant";
	static final String ZONE_ID_SUFFIX = "ZoneId";
	static final ZoneId UTC = ZoneId.of("UTC");

	static boolean isFieldSerializable(Field field) {
		return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers())
				&& !Modifier.isAbstract(field.getModifiers()) && !field.isAnnotationPresent(NonPortable.class);
	}

	static String[] enumToString(Collection<Enum<?>> value, Collection<?> collection) {
		return value.stream().map(Enum::name).collect(Collectors.toList()).toArray(new String[collection.size()]);
	}

}
