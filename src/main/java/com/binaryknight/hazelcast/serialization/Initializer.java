package com.binaryknight.hazelcast.serialization;

import static com.binaryknight.hazelcast.serialization.HazelcastSerializer.register;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Initializer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void init() {
		register(int.class, (field, writer, portable) -> writer.writeInt(field.getName(), field.getInt(portable)),
				(field, reader, portable) -> field.setInt(portable, reader.readInt(field.getName())));
		register(long.class, (field, writer, portable) -> writer.writeLong(field.getName(), field.getLong(portable)),
				(field, reader, portable) -> field.setLong(portable, reader.readLong(field.getName())));
		register(double.class,
				(field, writer, portable) -> writer.writeDouble(field.getName(), field.getDouble(portable)),
				(field, reader, portable) -> field.setDouble(portable, reader.readDouble(field.getName())));
		register(boolean.class,
				(field, writer, portable) -> writer.writeBoolean(field.getName(), field.getBoolean(portable)),
				(field, reader, portable) -> field.setBoolean(portable, reader.readBoolean(field.getName())));
		register(byte.class, (field, writer, portable) -> writer.writeByte(field.getName(), field.getByte(portable)),
				(field, reader, portable) -> field.setByte(portable, reader.readByte(field.getName())));
		register(short.class, (field, writer, portable) -> writer.writeShort(field.getName(), field.getShort(portable)),
				(field, reader, portable) -> field.setShort(portable, reader.readShort(field.getName())));
		register(char.class, (field, writer, portable) -> writer.writeChar(field.getName(), field.getChar(portable)),
				(field, reader, portable) -> field.setChar(portable, reader.readChar(field.getName())));
		register(float.class, (field, writer, portable) -> writer.writeFloat(field.getName(), field.getFloat(portable)),
				(field, reader, portable) -> field.setFloat(portable, reader.readFloat(field.getName())));
		register(Integer.class,
				(field, writer, portable) -> writer.writeInt(field.getName(), (Integer) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readInt(field.getName())));
		register(Long.class, (field, writer, portable) -> writer.writeLong(field.getName(), (Long) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readLong(field.getName())));
		register(Double.class,
				(field, writer, portable) -> writer.writeDouble(field.getName(), (Double) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readDouble(field.getName())));
		register(Boolean.class,
				(field, writer, portable) -> writer.writeBoolean(field.getName(), (Boolean) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readBoolean(field.getName())));
		register(Byte.class, (field, writer, portable) -> writer.writeByte(field.getName(), (Byte) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readByte(field.getName())));
		register(Short.class,
				(field, writer, portable) -> writer.writeShort(field.getName(), (Short) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readShort(field.getName())));
		register(Character.class,
				(field, writer, portable) -> writer.writeChar(field.getName(), (Character) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readChar(field.getName())));
		register(Float.class,
				(field, writer, portable) -> writer.writeFloat(field.getName(), (Float) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readFloat(field.getName())));
		register(BigInteger.class,
				(field, writer, portable) -> writer.writeUTF(field.getName(),
						((BigInteger) field.get(portable)).toString()),
				(field, reader, portable) -> field.set(portable, new BigInteger(reader.readUTF(field.getName()))));
		register(BigDecimal.class,
				(field, writer, portable) -> writer.writeUTF(field.getName(),
						((BigDecimal) field.get(portable)).toString()),
				(field, reader, portable) -> field.set(portable, new BigDecimal(reader.readUTF(field.getName()))));
		register(String.class,
				(field, writer, portable) -> writer.writeUTF(field.getName(), (String) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readUTF(field.getName())));
		register(Instant.class, (field, writer, portable) -> {
			Instant value = (Instant) field.get(portable);
			writer.writeLong(field.getName(), value == null ? 0 : value.toEpochMilli());
		}, (field, reader, portable) -> {
			long datetime = reader.readLong(field.getName());
			field.set(portable, datetime == 0 ? null : Instant.ofEpochMilli(datetime));
		});
		register(int[].class,
				(field, writer, portable) -> writer.writeIntArray(field.getName(), (int[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readIntArray(field.getName())));
		register(long[].class,
				(field, writer, portable) -> writer.writeLongArray(field.getName(), (long[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readLongArray(field.getName())));
		register(short[].class,
				(field, writer, portable) -> writer.writeShortArray(field.getName(), (short[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readShortArray(field.getName())));
		register(byte[].class,
				(field, writer, portable) -> writer.writeByteArray(field.getName(), (byte[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readByteArray(field.getName())));
		register(boolean[].class,
				(field, writer, portable) -> writer.writeBooleanArray(field.getName(), (boolean[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readBooleanArray(field.getName())));
		register(double[].class,
				(field, writer, portable) -> writer.writeDoubleArray(field.getName(), (double[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readDoubleArray(field.getName())));
		register(float[].class,
				(field, writer, portable) -> writer.writeFloatArray(field.getName(), (float[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readFloatArray(field.getName())));
		register(char[].class,
				(field, writer, portable) -> writer.writeCharArray(field.getName(), (char[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readCharArray(field.getName())));
		register(String[].class,
				(field, writer, portable) -> writer.writeUTFArray(field.getName(), (String[]) field.get(portable)),
				(field, reader, portable) -> field.set(portable, reader.readUTFArray(field.getName())));
		register(Class.class, (field, writer, portable) -> {
			Class<?> value = (Class<?>) field.get(portable);
			writer.writeUTF(field.getName(), value == null ? null : value.getName());
		}, (field, reader, portable) -> {
			String value = reader.readUTF(field.getName());
			field.set(portable, value == null ? null : Class.forName(value));
		});
		register(LocalDate.class, (field, writer, portable) -> {
			LocalDate value = (LocalDate) field.get(portable);
			writer.writeLong(field.getName(), value == null ? 0 : value.toEpochDay());
		}, (field, reader, portable) -> {
			long datetime = reader.readLong(field.getName());
			field.set(portable, datetime == 0 ? null : LocalDate.ofEpochDay(datetime));
		});
		register(LocalTime.class, (field, writer, portable) -> {
			LocalTime value = (LocalTime) field.get(portable);
			writer.writeLong(field.getName(), value == null ? 0 : value.toNanoOfDay());
		}, (field, reader, portable) -> {
			long datetime = reader.readLong(field.getName());
			field.set(portable, datetime == 0 ? null : LocalTime.ofNanoOfDay(datetime));
		});
		register(LocalDateTime.class, (field, writer, portable) -> {
			LocalDateTime value = (LocalDateTime) field.get(portable);
			writer.writeLong(field.getName(), value == null ? 0 : value.atZone(Util.UTC).toInstant().toEpochMilli());
		}, (field, reader, portable) -> {
			long datetime = reader.readLong(field.getName());
			field.set(portable,
					datetime == 0 ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), Util.UTC));
		});
		register(ZonedDateTime.class, (field, writer, portable) -> {
			ZonedDateTime value = (ZonedDateTime) field.get(portable);
			writer.writeLong(field.getName() + Util.INSTANT_SUFFIX,
					value == null ? 0 : value.toInstant().toEpochMilli());
			writer.writeUTF(field.getName() + Util.ZONE_ID_SUFFIX, value == null ? null : value.getZone().getId());
		}, (field, reader, portable) -> {
			long datetime = reader.readLong(field.getName() + Util.INSTANT_SUFFIX);
			if (datetime == 0) {
				field.set(portable, null);
			} else {
				Instant instant = Instant.ofEpochMilli(datetime);
				ZoneId zone = ZoneId.of(reader.readUTF(field.getName() + Util.ZONE_ID_SUFFIX));
				field.set(portable, ZonedDateTime.ofInstant(instant, zone));
			}
		});
		register(Enum.class, (field, writer, portable) -> {
			Enum<?> value = (Enum<?>) field.get(portable);
			writer.writeUTF(field.getName(), value == null ? null : value.name());
		}, (field, reader, portable) -> {
			String value = reader.readUTF(field.getName());
			field.set(portable, value == null ? null : Enum.valueOf((Class<Enum>) field.getType(), value));
		});
	}
}
