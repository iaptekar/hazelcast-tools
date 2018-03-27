package com.binaryknight.hazelcast.serialization;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NullValue {

	public long numeric() default 0;

	public String string() default "";

	public long datetime() default 0;

	public boolean logical() default false;

	public String enumeration() default "";

	public Class<?> type() default Object.class;

	public Class<? extends InitialValueFactory<?>> factory() default InitialValueFactory.NullFactory.class;
}
