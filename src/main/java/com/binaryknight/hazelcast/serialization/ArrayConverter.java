package com.binaryknight.hazelcast.serialization;

public enum ArrayConverter {
	;
	// private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	// private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
	// private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final long[] EMPTY_LONG_ARRAY = new long[0];
	private static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	private static final int[] EMPTY_INT_ARRAY = new int[0];
	private static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	private static final short[] EMPTY_SHORT_ARRAY = new short[0];
	private static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	private static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	private static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	private static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	private static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	private static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	private static final char[] EMPTY_CHAR_ARRAY = new char[0];
	private static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

	public static char[] toPrimitive(Character[] array, char nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		char[] result = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].charValue();
		}
		return result;
	}

	public static Character[] toObject(char[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_CHARACTER_OBJECT_ARRAY;
		}
		Character[] result = new Character[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Character.valueOf(array[i]);
		}
		return result;
	}

	public static long[] toPrimitive(Long[] array, long nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_LONG_ARRAY;
		}
		long[] result = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].longValue();
		}
		return result;
	}

	public static Long[] toObject(long[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_LONG_OBJECT_ARRAY;
		}
		Long[] result = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Long.valueOf(array[i]);
		}
		return result;
	}

	public static int[] toPrimitive(Integer[] array, int nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_INT_ARRAY;
		}
		int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].intValue();
		}
		return result;
	}

	public static Integer[] toObject(int[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_INTEGER_OBJECT_ARRAY;
		}
		Integer[] result = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Integer.valueOf(array[i]);
		}
		return result;
	}

	public static short[] toPrimitive(Short[] array, short nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_SHORT_ARRAY;
		}
		short[] result = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].shortValue();
		}
		return result;
	}

	public static Short[] toObject(short[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_SHORT_OBJECT_ARRAY;
		}
		Short[] result = new Short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Short.valueOf(array[i]);
		}
		return result;
	}

	public static byte[] toPrimitive(Byte[] array, byte nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BYTE_ARRAY;
		}
		byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].byteValue();
		}
		return result;
	}

	public static Byte[] toObject(byte[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BYTE_OBJECT_ARRAY;
		}
		Byte[] result = new Byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Byte.valueOf(array[i]);
		}
		return result;
	}

	public static double[] toPrimitive(Double[] array, double nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_DOUBLE_ARRAY;
		}
		double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].doubleValue();
		}
		return result;
	}

	public static Double[] toObject(double[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_DOUBLE_OBJECT_ARRAY;
		}
		Double[] result = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Double.valueOf(array[i]);
		}
		return result;
	}

	public static float[] toPrimitive(Float[] array, float nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_FLOAT_ARRAY;
		}
		float[] result = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].floatValue();
		}
		return result;
	}

	public static Float[] toObject(float[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_FLOAT_OBJECT_ARRAY;
		}
		Float[] result = new Float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Float.valueOf(array[i]);
		}
		return result;
	}

	public static boolean[] toPrimitive(Boolean[] array, boolean nullValue) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BOOLEAN_ARRAY;
		}
		boolean[] result = new boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] == null ? nullValue : array[i].booleanValue();
		}
		return result;
	}

	public static Boolean[] toObject(boolean[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BOOLEAN_OBJECT_ARRAY;
		}
		Boolean[] result = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
		}
		return result;
	}
}
