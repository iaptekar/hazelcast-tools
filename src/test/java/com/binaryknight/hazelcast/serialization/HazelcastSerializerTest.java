package com.binaryknight.hazelcast.serialization;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.AccessMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableFactory;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

@RunWith(MockitoJUnitRunner.class)
public class HazelcastSerializerTest {

	@Mock
	private PortableWriter writer;

	@Mock
	private PortableReader reader;

	@Test
	public void makeSureWriterWritesAllFields() throws Exception {
		TestPortable portable = new TestPortable();
		HazelcastSerializer.serialize(portable, writer);

		verify(writer).writeInt("intField", portable.intField);
		verify(writer).writeInt("integerField", portable.integerField);
		verify(writer).writeLong("longField", portable.longField);
		verify(writer).writeLong("longField2", portable.longField2);
		verify(writer).writeUTF("stringField", portable.stringField);
		verify(writer).writeUTF("enumField", portable.enumField.name());
		verify(writer).writeLong("instantField", portable.instantField.toEpochMilli());
		verify(writer).writeLong("zonedFieldInstant", portable.zonedField.toInstant().toEpochMilli());
		verify(writer).writeUTF("zonedFieldZoneId", portable.zonedField.getZone().getId());
		verify(writer).writeDouble("doubleField", portable.doubleField);
		verify(writer).writeDouble("doubleField2", portable.doubleField2);
		verify(writer).writeLong("localdateField", portable.localdateField.toEpochDay());
		verify(writer).writeLong("localtimeField", portable.localtimeField.toNanoOfDay());
		verify(writer).writeLong("localdatetimeField",
				portable.localdatetimeField.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
		verify(writer).writeBoolean("boolField", portable.boolField);
		verify(writer).writeBoolean("booleanField", portable.booleanField);
		verify(writer).writeUTFArray("stringArrayField", portable.stringArrayField);
		verify(writer).writeIntArray("intArrayField", portable.intArrayField);
		verify(writer).writeUTF("bigintField", portable.bigintField.toString());
		verify(writer).writeUTF("bigdecField", portable.bigdecField.toPlainString());
		verify(writer).writePortable("portable", portable.portable);
		verify(writer).writePortableArray("portables", portable.portables);
		verify(writer).writeInt("nullInteger", 100);
		verify(writer).writeUTF("nullString", "DEFAULT");
		verify(writer).writeUTF("nullEnum", "MINUTES");
		verify(writer).writeUTF("noInitialValue", "Initial Value");
		verify(writer).writeUTFArray("stringCollection", new String[] { "A", "B", "C" });
		verify(writer).writeIntArray("nullIntegerCollection", new int[0]);

		verifyNoMoreInteractions(writer);
	}

	@Test
	public void makeSureReaderReadsAllFields() throws Exception {
		when(reader.readInt("intField")).thenReturn(1);
		when(reader.readInt("integerField")).thenReturn(1);
		when(reader.readLong("longField")).thenReturn(1L);
		when(reader.readLong("longField2")).thenReturn(1L);
		when(reader.readUTF("stringField")).thenReturn("");
		when(reader.readUTF("enumField")).thenReturn(AccessMode.READ.name());
		when(reader.readLong("instantField")).thenReturn(1L);
		when(reader.readLong("zonedFieldInstant")).thenReturn(1L);
		when(reader.readUTF("zonedFieldZoneId")).thenReturn("UTC");
		when(reader.readDouble("doubleField")).thenReturn(1.0);
		when(reader.readDouble("doubleField2")).thenReturn(1.0);
		when(reader.readLong("localdateField")).thenReturn(1L);
		when(reader.readLong("localtimeField")).thenReturn(1L);
		when(reader.readLong("localdatetimeField")).thenReturn(1L);
		when(reader.readBoolean("boolField")).thenReturn(true);
		when(reader.readBoolean("booleanField")).thenReturn(true);
		when(reader.readUTFArray("stringArrayField")).thenReturn(new String[] { "" });
		when(reader.readIntArray("intArrayField")).thenReturn(new int[] { 1 });
		when(reader.readUTF("bigintField")).thenReturn("10");
		when(reader.readUTF("bigdecField")).thenReturn("12.34");
		when(reader.readPortable("portable")).thenReturn(new MyPortable());
		when(reader.readPortableArray("portables")).thenReturn(new Portable[] { new MyPortable() });
		when(reader.readInt("nullInteger")).thenReturn(0);
		when(reader.readUTF("nullString")).thenReturn("NULL");
		when(reader.readUTF("nullEnum")).thenReturn(null);

		TestPortable portable = new TestPortable();
		HazelcastSerializer.deserialize(portable, reader);

		verify(reader).readInt("intField");
		verify(reader).readInt("integerField");
		verify(reader).readLong("longField");
		verify(reader).readLong("longField2");
		verify(reader).readUTF("stringField");
		verify(reader).readUTF("enumField");
		verify(reader).readLong("instantField");
		verify(reader).readLong("zonedFieldInstant");
		verify(reader).readUTF("zonedFieldZoneId");
		verify(reader).readDouble("doubleField");
		verify(reader).readDouble("doubleField2");
		verify(reader).readLong("localdateField");
		verify(reader).readLong("localtimeField");
		verify(reader).readLong("localdatetimeField");
		verify(reader).readBoolean("boolField");
		verify(reader).readBoolean("booleanField");
		verify(reader).readUTFArray("stringArrayField");
		verify(reader).readIntArray("intArrayField");
		verify(reader).readUTF("bigintField");
		verify(reader).readUTF("bigdecField");
		verify(reader).readPortable("portable");
		verify(reader).readPortableArray("portables");
		verify(reader).readInt("nullInteger");
		verify(reader).readUTF("nullString");
		verify(reader).readUTF("nullEnum");
		verify(reader).readUTF("noInitialValue");
		verify(reader).readUTFArray("stringCollection");
		verify(reader).readIntArray("nullIntegerCollection");
		verify(reader, never()).readPortable("notused");

		verifyNoMoreInteractions(reader);
	}

	@Test
	public void testSerialization2() throws Exception {
		Config config = new Config("test");
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
		config.getGroupConfig().setName(UUID.randomUUID().toString());
		config.getGroupConfig().setPassword(UUID.randomUUID().toString());
		config.getSerializationConfig().setPortableVersion(1);
		config.getSerializationConfig().addPortableFactory(1, new TestPortableFactory());
		HazelcastInstance hz = Hazelcast.getOrCreateHazelcastInstance(config);
		IMap<Object, Object> cache = hz.getMap("test");

		TestPortable original = new TestPortable();
		cache.put("test", original);

		TestPortable clone = (TestPortable) cache.get("test");
		assertThat(clone.bigdecField).isEqualTo(original.bigdecField);
		assertThat(clone.bigintField).isEqualTo(original.bigintField);
		assertThat(clone.booleanField).isEqualTo(original.booleanField);
		assertThat(clone.boolField).isEqualTo(original.boolField);
		assertThat(clone.doubleField).isEqualTo(original.doubleField);
		assertThat(clone.enumField).isEqualTo(original.enumField);
		assertThat(clone.instantField).isEqualTo(original.instantField);
		assertThat(clone.intArrayField).isEqualTo(original.intArrayField);
		assertThat(clone.integerField).isEqualTo(original.integerField);
		assertThat(clone.intField).isEqualTo(original.intField);
		assertThat(clone.localdateField).isEqualTo(original.localdateField);
		assertThat(clone.localdatetimeField).isEqualTo(original.localdatetimeField);
		assertThat(clone.localtimeField).isEqualTo(original.localtimeField);
		assertThat(clone.longField).isEqualTo(original.longField);
		assertThat(clone.longField2).isEqualTo(original.longField2);
		assertThat(clone.portable).isEqualTo(original.portable);
		assertThat(clone.portables).isEqualTo(original.portables);
		assertThat(clone.stringArrayField).isEqualTo(original.stringArrayField);
		assertThat(clone.stringField).isEqualTo(original.stringField);
		assertThat(clone.zonedField).isEqualTo(original.zonedField);
		assertThat(clone.nullString).isEqualTo("DEFAULT");
		assertThat(clone.nullInteger).isEqualTo(100);
		assertThat(clone.nullEnum).isEqualTo(TimeUnit.MINUTES);
		assertThat(clone.noInitialValue).isEqualTo("Initial Value");
		assertThat(clone.stringCollection).isEqualTo(original.stringCollection);
		assertThat(clone.nullIntegerCollection).isEqualTo(new ArrayList<>());
	}

	private static class TestPortable implements Portable {

		public int intField = 10;
		public Integer integerField = Integer.valueOf(100);
		public long longField = 1000L;
		public Long longField2 = Long.valueOf(123456789L);
		public String stringField = "test";
		public AccessMode enumField = AccessMode.EXECUTE;
		public Instant instantField = Instant.now();
		public ZonedDateTime zonedField = ZonedDateTime.now();
		public LocalDate localdateField = LocalDate.now();
		public LocalTime localtimeField = LocalTime.now();
		public LocalDateTime localdatetimeField = LocalDateTime.now();
		public double doubleField = 4.56D;
		public Double doubleField2 = Double.valueOf(123.456D);
		public BigInteger bigintField = BigInteger.valueOf(123L);
		public BigDecimal bigdecField = new BigDecimal("123456.987654321");
		public boolean boolField = true;
		public Boolean booleanField = true;
		public String[] stringArrayField = { "a", "b", "c" };
		public int[] intArrayField = { 1, 2, 3, 4 };

		@NullValue(numeric = 100)
		public Integer nullInteger = null;
		@NullValue(string = "DEFAULT")
		public String nullString = null;

		@NullValue(enumeration = "MINUTES")
		public TimeUnit nullEnum = null;

		@NullValue(factory = TestInitialValueFactory.class)
		public String noInitialValue;

		public MyPortable portable = new MyPortable();
		public MyPortable[] portables = { new MyPortable("1"), new MyPortable("2"), new MyPortable("3") };

		@NonPortable
		public Object notused = new Object();

		@PortableCollection(type = ArrayList.class, componentType = String.class)
		public Collection<String> stringCollection = asList("A", "B", "C");

		@PortableCollection(type = ArrayList.class, componentType = Integer.class)
		@NullValue(type = ArrayList.class)
		public Collection<Integer> nullIntegerCollection;

		@Override
		public int getFactoryId() {
			return 1;
		}

		@Override
		public int getClassId() {
			return 1;
		}

		@Override
		public void writePortable(PortableWriter writer) throws IOException {
			HazelcastSerializer.serialize(this, writer);
		}

		@Override
		public void readPortable(PortableReader reader) throws IOException {
			HazelcastSerializer.deserialize(this, reader);
		}

	}

	private static class MyPortable implements Portable {

		private String testMethod = "Hello";

		public MyPortable(String value) {
			this.testMethod = value;
		}

		public MyPortable() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getFactoryId() {
			return 1;
		}

		@Override
		public int getClassId() {
			return 2;
		}

		@Override
		public void writePortable(PortableWriter writer) throws IOException {
			HazelcastSerializer.serialize(this, writer);
		}

		@Override
		public void readPortable(PortableReader reader) throws IOException {
			HazelcastSerializer.deserialize(this, reader);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((testMethod == null) ? 0 : testMethod.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MyPortable other = (MyPortable) obj;
			if (testMethod == null) {
				if (other.testMethod != null)
					return false;
			} else if (!testMethod.equals(other.testMethod))
				return false;
			return true;
		}

	}

	public static class TestInitialValueFactory implements InitialValueFactory<String> {

		@Override
		public String writeValue(PortableWriter writer) {
			return "Initial Value";
		}

		@Override
		public String readValue(PortableReader reader) {
			return "Initial Value";
		}

	}

	private static final class TestPortableFactory implements PortableFactory {

		@Override
		public Portable create(int classId) {
			switch (classId) {
			case 1:
				return new TestPortable();
			case 2:
				return new MyPortable();
			default:
				break;
			}
			return null;
		}

	}

}
