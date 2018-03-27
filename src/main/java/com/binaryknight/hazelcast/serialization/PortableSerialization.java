package com.binaryknight.hazelcast.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PortableSerialization {

	public enum PortableSerializationType {

		JAVA, CUSTOM
	}

	public PortableSerializationType type() default PortableSerializationType.JAVA;

	public Class<FieldSerializer> serializer() default FieldSerializer.class;

	public Class<FieldDeSerializer> deserializer() default FieldDeSerializer.class;
}
