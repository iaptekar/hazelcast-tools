package com.binaryknight.hazelcast.serialization;

import java.lang.reflect.Field;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableWriter;

public interface FieldSerializer {

	void serialize(Field field, PortableWriter writer, Portable portable) throws Exception;
}