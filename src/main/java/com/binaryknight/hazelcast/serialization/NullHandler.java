package com.binaryknight.hazelcast.serialization;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

class NullHandler {

	public void ifNull(Field field, Portable portable, PortableWriter writer) throws Exception {
		NullValue nullValue = field.getAnnotation(NullValue.class);
		Object value = nullValue.factory().newInstance().writeValue(writer);
		if (value == null) {
			value = ifNull(field.getName(), field.getType(), nullValue);
		}
		field.set(portable, value);
	}

	public void ifNull(Field field, Portable portable, PortableReader reader) throws Exception {
		NullValue nullValue = field.getAnnotation(NullValue.class);
		Object value = nullValue.factory().newInstance().readValue(reader);
		if (value == null) {
			value = ifNull(field.getName(), field.getType(), nullValue);
		}
		field.set(portable, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object ifNull(String name, Class<?> type, NullValue nullValue) throws Exception {
		Object value = null;
		if (type.isPrimitive()) {
			throw new IllegalArgumentException("Primitive fields cannot be null so this annotation cannot be used");
		}
		if (Number.class.isAssignableFrom(type)) {
			if (type == Integer.class) {
				value = (int) nullValue.numeric();
			}
			if (type == BigInteger.class) {
				value = BigInteger.valueOf(nullValue.numeric());
			}
			if (type == BigDecimal.class) {
				value = BigDecimal.valueOf(nullValue.numeric());
			}
			if (type == Double.class) {
				value = (double) nullValue.numeric();
			}
			if (type == Float.class) {
				value = (float) nullValue.numeric();
			}
			if (type == Byte.class) {
				value = (byte) nullValue.numeric();
			}
			if (type == Short.class) {
				value = (short) nullValue.numeric();
			}
			if (type == Long.class) {
				value = nullValue.numeric();
			}
			if (value == null) {
				throw new IllegalArgumentException("The numeric field " + name + " of type " + type
						+ " is not allowed, use only Integer, Long, Double, Float, BigInteger, BigDecimal");
			}
		}
		if (type == String.class) {
			value = nullValue.string();
		}
		if (Temporal.class.isAssignableFrom(type)) {
			long datetime = nullValue.datetime();
			if (type == LocalDate.class) {
				value = LocalDate.ofEpochDay(datetime);
			}
			if (type == LocalTime.class) {
				value = LocalTime.ofNanoOfDay(datetime);
			}
			if (type == LocalDateTime.class) {
				value = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), Util.UTC);
			}
			if (type == ZonedDateTime.class) {
				value = ZonedDateTime.ofInstant(Instant.ofEpochMilli(datetime), Util.UTC);
			}
			if (type == Instant.class) {
				value = Instant.ofEpochMilli(datetime);
			}
			if (value == null) {
				throw new IllegalArgumentException("The temporal field " + name + " of type " + type
						+ " is not allowed, use only Instant, LocalDate, LocalTime, LocalDateTime, ZonedDateTime");
			}
		}
		if (type == Boolean.class) {
			value = nullValue.logical();
		}

		if (type.isEnum() && !nullValue.enumeration().isEmpty()) {
			value = Enum.valueOf((Class<Enum>) type, nullValue.enumeration());
		}

		if (value == null && nullValue.type() != Object.class) {
			value = nullValue.type().newInstance();
		}
		return value;
	}

}