package com.binaryknight.hazelcast.serialization;

import java.lang.reflect.Field;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;

public interface FieldDeSerializer {

	void deserialize(Field field, PortableReader reader, Portable portable) throws Exception;
}