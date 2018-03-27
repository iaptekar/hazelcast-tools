package com.binaryknight.hazelcast.serialization;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "A");

		dostuff(map);

		System.out.println(map);

	}

	private static void dostuff(Map<String, String> map) {
		Map<String, String> x = new HashMap<>();
		x.put("2", "B");
		map = x;

	}

}
