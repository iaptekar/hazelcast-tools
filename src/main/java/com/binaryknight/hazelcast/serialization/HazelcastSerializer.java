package com.binaryknight.hazelcast.serialization;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HazelcastSerializer {

	private final static Map<Class<?>, FieldSerializer> serializers = new HashMap<>();
	private final static Map<Class<?>, FieldDeSerializer> deserializers = new HashMap<>();

	static {
		Initializer.init();
	}

	public static void register(Class<?> clazz, FieldSerializer serializer, FieldDeSerializer deserializer) {
		serializers.put(clazz, serializer);
		deserializers.put(clazz, deserializer);
	}

	public static void serialize(Portable portable, PortableWriter writer) throws IOException {
		try {
			for (Field field : portable.getClass().getDeclaredFields()) {
				if (Util.isFieldSerializable(field)) {
					field.setAccessible(true);
					Object value = field.get(portable);
					if (value == null) {
						if (field.isAnnotationPresent(NullValue.class)) {
							new NullHandler().ifNull(field, portable, writer);
						}
					}
					if (field.isAnnotationPresent(PortableSerialization.class)) {
						doCustomSerialization(field, portable, writer);
					} else {
						doDefaultSerialization(field, portable, writer);
					}
				}
			}
		} catch (Exception e) {
			throw e instanceof IOException ? (IOException) e : new IOException(e);
		}

	}

	private static void doDefaultSerialization(Field field, Portable portable, PortableWriter writer) throws Exception {
		String name = field.getName();
		Object value = field.get(portable);
		Class<?> type = field.getType();
		if (Portable.class.isAssignableFrom(type)) {
			writer.writePortable(name, (Portable) value);
		} else if (type.isArray() && Portable.class.isAssignableFrom(type.getComponentType())) {
			writer.writePortableArray(name, (Portable[]) value);
		} else if (field.isAnnotationPresent(PortableCollection.class)) {
			Class<?> componentType = field.getAnnotation(PortableCollection.class).componentType();
			Collection<?> collection = (Collection<?>) value;
			serializeCollection(name, componentType, collection, writer);
		} else {
			FieldSerializer serializer = type.isEnum() ? serializers.get(Enum.class) : serializers.get(type);
			if (serializer != null) {
				serializer.serialize(field, writer, portable);
			}
		}
	}

	private static void serializeCollection(String name, Class<?> componentType, Collection<?> collection,
			PortableWriter writer) throws IOException {
		if (componentType == String.class) {
			writer.writeUTFArray(name, ((Collection<String>) collection).toArray(new String[collection.size()]));
		}
		if (componentType == Integer.class) {
			Integer[] array = ((Collection<Integer>) collection).toArray(new Integer[collection.size()]);
			writer.writeIntArray(name, ArrayConverter.toPrimitive(array, 0));
		}
		if (componentType == Long.class) {
			Long[] array = ((Collection<Long>) collection).toArray(new Long[collection.size()]);
			writer.writeLongArray(name, ArrayConverter.toPrimitive(array, 0));
		}
		if (componentType == Float.class) {
			Float[] array = ((Collection<Float>) collection).toArray(new Float[collection.size()]);
			writer.writeFloatArray(name, ArrayConverter.toPrimitive(array, 0));
		}
		if (componentType == Double.class) {
			Double[] array = ((Collection<Double>) collection).toArray(new Double[collection.size()]);
			writer.writeDoubleArray(name, ArrayConverter.toPrimitive(array, 0));
		}
		if (componentType == Short.class) {
			Short[] array = ((Collection<Short>) collection).toArray(new Short[collection.size()]);
			writer.writeShortArray(name, ArrayConverter.toPrimitive(array, (short) 0));
		}
		if (componentType == Byte.class) {
			Byte[] array = ((Collection<Byte>) collection).toArray(new Byte[collection.size()]);
			writer.writeByteArray(name, ArrayConverter.toPrimitive(array, (byte) 0));
		}
		if (componentType == Boolean.class) {
			Boolean[] array = ((Collection<Boolean>) collection).toArray(new Boolean[collection.size()]);
			writer.writeBooleanArray(name, ArrayConverter.toPrimitive(array, false));
		}
		if (componentType == Character.class) {
			Character[] array = ((Collection<Character>) collection).toArray(new Character[collection.size()]);
			writer.writeCharArray(name, ArrayConverter.toPrimitive(array, (char) 0));
		}
		if (componentType.isEnum()) {
			writer.writeUTFArray(name, Util.enumToString((Collection<Enum<?>>) collection, collection));
		}
	}

	private static void doCustomSerialization(Field field, Portable portable, PortableWriter writer) throws Exception {
		PortableSerialization serialization = field.getAnnotation(PortableSerialization.class);
		switch (serialization.type()) {
		case JAVA:
			new JavaFieldSerializer().serialize(field, writer, portable);
			break;
		case CUSTOM:
			serialization.serializer().newInstance().serialize(field, writer, portable);
			break;
		default:
			throw new IllegalArgumentException("Serialization type " + serialization.type() + " is not recognized");
		}
	}

	public static void deserialize(Portable portable, PortableReader reader) throws IOException {
		try {
			for (Field field : portable.getClass().getDeclaredFields()) {
				if (Util.isFieldSerializable(field)) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(PortableSerialization.class)) {
						doCustomDeserialization(field, portable, reader);
					} else {
						doDefaultDeserialization(field, portable, reader);
					}
				}
			}
		} catch (Exception e) {
			throw e instanceof IOException ? (IOException) e : new IOException(e);
		}
	}

	private static void doDefaultDeserialization(Field field, Portable portable, PortableReader reader)
			throws Exception {
		String name = field.getName();
		Class<?> type = field.getType();
		if (Portable.class.isAssignableFrom(type)) {
			Portable value = reader.readPortable(name);
			if (value == null && field.isAnnotationPresent(NullValue.class)) {
				new NullHandler().ifNull(field, portable, reader);
			} else {
				field.set(portable, value);
			}
		} else if (type.isArray() && Portable.class.isAssignableFrom(type.getComponentType())) {
			Portable[] portables = reader.readPortableArray(name);
			Object values = Array.newInstance(type.getComponentType(), portables.length);
			System.arraycopy(portables, 0, values, 0, portables.length);
			field.set(portable, values);
		} else if (field.isAnnotationPresent(PortableCollection.class)) {
			Class<?> collectionType = field.getAnnotation(PortableCollection.class).type();
			Class componentType = field.getAnnotation(PortableCollection.class).componentType();
			if (componentType == String.class) {
				String[] strings = reader.readUTFArray(name);
				Collection<String> collection = (Collection<String>) collectionType.newInstance();
				if (strings != null) {
					collection.addAll(asList(strings));
				}
				field.set(portable, collection);
			}
			if (componentType == Integer.class) {
				int[] ints = reader.readIntArray(name);
				Collection<Integer> collection = (Collection<Integer>) collectionType.newInstance();
				if (ints != null) {
					collection.addAll(asList(ArrayConverter.toObject(ints)));
				}
				field.set(portable, collection);
			}
			if (componentType.isEnum()) {
				Collection<Enum> collection = (Collection<Enum>) collectionType.newInstance();
				String[] strings = reader.readUTFArray(name);
				for (String s : strings) {
					collection.add(Enum.valueOf(componentType, s));
				}
				field.set(portable, collection);
			}

		} else {
			FieldDeSerializer deserializer = type.isEnum() ? deserializers.get(Enum.class) : deserializers.get(type);
			if (deserializer != null) {
				deserializer.deserialize(field, reader, portable);
			}
		}
	}

	private static void doCustomDeserialization(Field field, Portable portable, PortableReader reader)
			throws Exception {
		PortableSerialization serialization = field.getAnnotation(PortableSerialization.class);
		switch (serialization.type()) {
		case JAVA:
			new JavaFieldDeserializer().deserialize(field, reader, portable);
			break;
		case CUSTOM:
			serialization.deserializer().newInstance().deserialize(field, reader, portable);
			break;
		default:
			throw new IllegalArgumentException("Serialization type " + serialization.type() + " is not recognized");
		}
	}

}
