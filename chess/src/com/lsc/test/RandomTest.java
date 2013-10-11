package com.lsc.test;

import java.util.Random;

public class RandomTest {
	public static void main(String[] args) {
		Random random = new Random(2);
		System.out.println(random.nextInt(100));
		random = new Random(2);
		System.out.println(random.nextInt(100));
		random = new Random(2);
		System.out.println(random.nextInt(100));
		Random r3 = new Random(2);
		System.out.println(r3.nextInt(100));
	}
}
