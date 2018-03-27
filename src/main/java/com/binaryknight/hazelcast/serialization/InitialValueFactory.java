package com.binaryknight.hazelcast.serialization;


import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

public interface InitialValueFactory<T> {

	T writeValue(PortableWriter writer);

	T readValue(PortableReader reader);

	public class NullFactory implements InitialValueFactory<Object> {

		@Override
		public Object writeValue(PortableWriter writer) {
			return null;
		}

		@Override
		public Object readValue(PortableReader reader) {
			return null;
		}

	}
}
