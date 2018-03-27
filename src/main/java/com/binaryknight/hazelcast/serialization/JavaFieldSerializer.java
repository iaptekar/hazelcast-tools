package com.binaryknight.hazelcast.serialization;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableWriter;

final class JavaFieldSerializer implements FieldSerializer {

	@Override
	public void serialize(Field field, PortableWriter writer, Portable portable) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		new ObjectOutputStream(out).writeObject(field.get(portable));
		writer.writeByteArray(field.getName(), out.toByteArray());
	}

}