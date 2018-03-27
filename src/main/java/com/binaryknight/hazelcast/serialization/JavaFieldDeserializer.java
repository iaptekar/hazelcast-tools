package com.binaryknight.hazelcast.serialization;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;

final class JavaFieldDeserializer implements FieldDeSerializer {

	@Override
	public void deserialize(Field field, PortableReader reader, Portable portable) throws Exception {
		byte[] bytes = reader.readByteArray(field.getName());
		field.set(portable, new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject());
	}

}